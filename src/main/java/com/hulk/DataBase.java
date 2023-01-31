package com.hulk;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private Connection conn;

    public DataBase(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected");
        } catch (SQLException ex) {
            System.out.println("Connection Failed");
            ex.printStackTrace();
        }
    }

    public boolean createUser(String username) {
        boolean isCreated = false;

        try {
            PreparedStatement send = conn
                    .prepareStatement("INSERT INTO users (name) SELECT ? WHERE NOT EXISTS (SELECT name FROM users WHERE name = ?)");
            send.setString(1, username);
            send.setString(2, username);

            send.executeUpdate();
            isCreated = true;
            send.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isCreated;
    }

    public boolean deleteUser(User user) {
        boolean isDeleted = false;

        try {
            PreparedStatement send = conn
                    .prepareStatement("DELETE FROM users WHERE EXISTS( SELECT user_id FROM users WHERE user_id = ?)");
            send.setInt(1, user.getId());
            isDeleted = true;
            send.execute();

            send.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isDeleted;
    }

    public User getUser(String username) {
        User user = null;
        try {
            PreparedStatement send = conn.prepareStatement("SELECT user_id, name FROM users WHERE name = ?");
            send.setString(1, username);
            ResultSet rs = send.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("name"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement check = conn.prepareStatement("SELECT user_id, name FROM users");
            ResultSet rs = check.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("user_id"),
                        rs.getString("name")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public boolean createGroup(String gname) {
        boolean isCreated = false;

        try {
            PreparedStatement send = conn
                    .prepareStatement("INSERT INTO groups (gname) SELECT ? WHERE NOT EXISTS (SELECT gname FROM groups WHERE gname = ?)");
            send.setString(1, gname);
            send.setString(2, gname);

            send.executeUpdate();
            isCreated = true;
            send.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isCreated;
    }

    public Group getGroup(String gname) {
        Group group = null;
        try {
            PreparedStatement send = conn.prepareStatement("SELECT group_id, gname, is_closed FROM groups WHERE gname = ?");
            send.setString(1, gname);
            ResultSet rs = send.executeQuery();

            while (rs.next()) {
                group = new Group(rs.getInt("group_id"),
                        rs.getString("gname"),
                        rs.getBoolean("is_closed"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return group;
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups = new ArrayList<>();

        try {
            PreparedStatement check = conn.prepareStatement("SELECT group_id, gname, is_closed FROM groups");
            ResultSet rs = check.executeQuery();

            while (rs.next()) {
                groups.add(new Group(rs.getInt("group_id"),
                        rs.getString("gname"),
                        rs.getBoolean("is_closed")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return groups;
    }

    public boolean deleteGroup(Group group) {
        boolean isDeleted = false;

        try {
            PreparedStatement send = conn
                    .prepareStatement("DELETE FROM groups WHERE EXISTS( SELECT group_id FROM groups WHERE group_id = ?)");
            send.setInt(1, group.getId());
            isDeleted = true;
            send.execute();

            send.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isDeleted;
    }

    public boolean addMember(User user, Group group, Role role) {
        boolean isAdded = false;

        try {
            PreparedStatement send = conn.prepareStatement(
                    "INSERT INTO members (user_id, group_id, role) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT user_id, group_id FROM members WHERE user_id = ? AND group_id = ?)");
            send.setInt(1, user.getId());
            send.setInt(2, group.getId());
            send.setString(3, role.toString());
            send.setInt(4, user.getId());
            send.setInt(5, group.getId());

            send.executeUpdate();
            isAdded = true;
            send.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isAdded;
    }

    public ArrayList<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();

        try {
            PreparedStatement check = conn
                    .prepareStatement("SELECT u.name, g.gname, m.role, m.user_id FROM users u, groups g, members m WHERE u.user_id = m.user_id AND g.group_id = m.group_id");
            ResultSet rs = check.executeQuery();

            while (rs.next()) {
                members.add(new Member(rs.getString("name"),
                        rs.getString("gname"),
                        Role.valueOf(rs.getString("role")),
                        rs.getInt("user_id")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return members;
    }

    public boolean deleteMember(Member member) {
        boolean isDeleted = false;

        try {
            PreparedStatement send = conn
                    .prepareStatement("DELETE FROM members WHERE EXISTS( SELECT user_id FROM members WHERE user_id = ?)");
            send.setInt(1, member.getId());
            isDeleted = true;
            send.execute();

            send.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isDeleted;
    }

//    public ArrayList<Santa> setSantas(User admin) {
//        closeGroup();
//        ArrayList<Santa> santas = null;
//
//        ArrayList<User> users = getUsers();
//
//        shuffle();
//
//        for (int i = 0; i < users.size(); i += 2) {
//
//        }
//
//        //query to db to add list of santas to table Santas
//
//        return santas;
//    }
}
