package com.hulk.app;

import com.hulk.models.*;
import com.hulk.models.Member.Role;

import java.sql.*;
import java.util.*;

public class DataBase {
    private Connection conn;

    public DataBase(String url, String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Successfully connected");
    }

    public boolean createOrDeleteQuery(PreparedStatement st) throws SQLException {
        boolean isExecuted = !st.execute();
        st.close();

        return isExecuted;
    }

    // rewrite getQuery using reflection api
//    public ArrayList<?> getQuery(PreparedStatement st) throws SQLException {
//        ArrayList<?> list = new ArrayList<>();
//
//        ResultSet rs = st.executeQuery();
//        while (rs.next()) {
//            SomeClass = new User(rs.getInt("user_id"), rs.getString("name"));
//        }
//
//        return list;
//    }

    public boolean createUser(String username) throws SQLException {
        PreparedStatement send = conn
                .prepareStatement("INSERT INTO users (name) SELECT ? WHERE NOT EXISTS (SELECT name FROM users WHERE name = ?)");
        send.setString(1, username);
        send.setString(2, username);

        return createOrDeleteQuery(send);
    }

    public boolean deleteUser(User user) throws SQLException {
        PreparedStatement send = conn
                .prepareStatement("DELETE FROM users WHERE EXISTS( SELECT user_id FROM users WHERE user_id = ?)");
        send.setInt(1, user.getId());

        return createOrDeleteQuery(send);
    }

    public User getUser(String username) throws SQLException {
        User user = null;

        PreparedStatement send = conn.prepareStatement("SELECT user_id, name FROM users WHERE name = ?");
        send.setString(1, username);
        ResultSet rs = send.executeQuery();

        while (rs.next()) {
            user = new User(rs.getInt("user_id"), rs.getString("name"));
        }

        return user;
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement check = conn.prepareStatement("SELECT user_id, name FROM users");
        ResultSet rs = check.executeQuery();

        while (rs.next()) {
            users.add(new User(rs.getInt("user_id"),
                    rs.getString("name")));
        }

        return users;
    }

    public boolean createGroup(String gname) throws SQLException {
        PreparedStatement send = conn
                .prepareStatement("INSERT INTO groups (gname) SELECT ? WHERE NOT EXISTS (SELECT gname FROM groups WHERE gname = ?)");
        send.setString(1, gname);
        send.setString(2, gname);

        return createOrDeleteQuery(send);
    }

    public Group getGroup(String gname) throws SQLException {
        Group group = null;

        PreparedStatement send = conn.prepareStatement("SELECT group_id, gname, is_closed FROM groups WHERE gname = ?");
        send.setString(1, gname);
        ResultSet rs = send.executeQuery();

        while (rs.next()) {
            group = new Group(rs.getInt("group_id"),
                    rs.getString("gname"),
                    rs.getBoolean("is_closed"));
        }

        return group;
    }

    public ArrayList<Group> getGroups() throws SQLException {
        ArrayList<Group> groups = new ArrayList<>();

        PreparedStatement check = conn.prepareStatement("SELECT group_id, gname, is_closed FROM groups");
        ResultSet rs = check.executeQuery();

        while (rs.next()) {
            groups.add(new Group(rs.getInt("group_id"),
                    rs.getString("gname"),
                    rs.getBoolean("is_closed")));
        }

        return groups;
    }

    public boolean deleteGroup(Group group) throws SQLException {
    PreparedStatement send = conn
                .prepareStatement("DELETE FROM groups WHERE EXISTS( SELECT group_id FROM groups WHERE group_id = ?)");
        send.setInt(1, group.getId());

        return createOrDeleteQuery(send);
    }

    public boolean addMember(User user, Group group, Role role) throws SQLException {
        PreparedStatement send = conn.prepareStatement(
                    "INSERT INTO members (user_id, group_id, role) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT user_id, group_id FROM members WHERE user_id = ? AND group_id = ?)");
        send.setInt(1, user.getId());
        send.setInt(2, group.getId());
        send.setString(3, role.toString());
        send.setInt(4, user.getId());
        send.setInt(5, group.getId());

        return createOrDeleteQuery(send);
    }

    public Member getMember(int groupId, int userId) throws SQLException {
        Member member = null;

        PreparedStatement send = conn
                .prepareStatement("SELECT u.name, g.gname, m.role, m.user_id, m.group_id FROM users u, groups g, members m WHERE u.user_id = m.user_id AND g.group_id = m.group_id AND m.group_id = ? AND u.user_id = ?");
        send.setInt(1, groupId);
        send.setInt(2, userId);
        ResultSet rs = send.executeQuery();

        while (rs.next()) {
            member = new Member(rs.getString("name"),
                    rs.getString("gname"),
                    Role.valueOf(rs.getString("role")),
                    rs.getInt("user_id"),
                    rs.getInt("group_id"));
        }

        return member;
    }

    public ArrayList<Member> getMembers(int groupId) throws SQLException {
        ArrayList<Member> members = new ArrayList<>();

        PreparedStatement send = conn
                .prepareStatement("SELECT u.name, g.gname, m.role, m.user_id, m.group_id FROM users u, groups g, members m WHERE u.user_id = m.user_id AND g.group_id = m.group_id AND m.group_id = ?");
        send.setInt(1, groupId);
        ResultSet rs = send.executeQuery();

        while (rs.next()) {
            members.add(new Member(rs.getString("name"),
                    rs.getString("gname"),
                    Role.valueOf(rs.getString("role")),
                    rs.getInt("user_id"),
                    rs.getInt("group_id")));
        }

        return members;
    }

    public boolean deleteMember(Member member) throws SQLException {
        PreparedStatement send = conn
                .prepareStatement("DELETE FROM members WHERE EXISTS( SELECT user_id FROM members WHERE user_id = ?)");
        send.setInt(1, member.getId());

        return createOrDeleteQuery(send);
    }

    public void setSantas(int groupId, Member admin) throws SQLException {
        ArrayList<Santa> santas = new ArrayList<>();
        ArrayList<Member> members = getMembers(groupId);

        if (admin.getRole() == Role.Admin) {
            PreparedStatement closeGroup = conn
                    .prepareStatement("UPDATE groups SET is_closed = 't' WHERE group_id = ?");
            closeGroup.setInt(1, groupId);

            Collections.shuffle(members);

            for (int i = 0; i < members.size() / 2; i++) {
                santas.add(new Santa(members.get(i).getId(),
                        members.get(i).getName(),
                        members.get(i + 1).getId(),
                        members.get(i + 1).getName(),
                        groupId));
            }
        }

        for (Santa s: santas) {
            PreparedStatement send = conn.prepareStatement("INSERT INTO santas (rec_id, self_id, group_id) VALUES (?, ?, ?)");
            send.setInt(1, s.getRecipientId());
            send.setInt(2, s.getSelfId());
            send.setInt(3, s.getGroupId());

            createOrDeleteQuery(send);
        }
    }

    public ArrayList<Santa> getSantas(int groupId, Member admin) throws SQLException {
        ArrayList<Santa> santas = new ArrayList<>();

        PreparedStatement send = conn
                .prepareStatement("SELECT u_s.name, u_r.name, s.group_id FROM santas s JOIN users u_s ON s.rec_id = u_s.user_id JOIN users u_r ON s.rec_id = u_r.user_id AND s.group_id = ?");
        send.setInt(1, groupId);
        ResultSet rs = send.executeQuery();

        while (rs.next()) {

            santas.add(new Santa(rs.getString(1),
                    rs.getString(2),
                    rs.getInt("group_id")));
        }

        return santas;
    }

    public boolean deleteSantas(Santa santa) throws SQLException {
        PreparedStatement send = conn
                .prepareStatement("DELETE FROM santas WHERE EXISTS( SELECT user_id FROM members WHERE user_id = ?)");
        send.setInt(1, santa.getSelfId());

        return createOrDeleteQuery(send);
    }
}
