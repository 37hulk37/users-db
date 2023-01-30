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
            PreparedStatement check = conn.prepareStatement("SELECT NOT EXISTS(SELECT name FROM users WHERE name = ?)");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();

            if ( rs.next() ) {
                rs.close();
                PreparedStatement send = conn.prepareStatement("INSERT INTO users (name) VALUES (?)");
                send.setString(1, username);

                send.executeUpdate();
                isCreated = true;
                send.close();
            }

            check.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isCreated;
    }

    public boolean deleteUser(User user) {
        boolean isDeleted = false;

        try {
            PreparedStatement send = conn.
                    prepareStatement("DELETE FROM users WHERE EXISTS( SELECT id FROM users WHERE id = ?)");
            send.setInt(1, user.getId());
            isDeleted = send.execute();

            send.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isDeleted;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement check = conn.prepareStatement("SELECT id, name FROM users");
            ResultSet rs = check.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
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
            PreparedStatement check = conn.prepareStatement("SELECT NOT EXISTS(SELECT gname FROM groups WHERE gname = ?)");
            check.setString(1, gname);
            ResultSet rs = check.executeQuery();

            if ( rs.next() ) {
                rs.close();
                PreparedStatement send = conn.prepareStatement("INSERT INTO groups (gname) VALUES (?)");
                send.setString(1, gname);

                send.executeUpdate();
                isCreated = true;
                send.close();
            }

            check.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isCreated;
    }

    public User getUser(String username) {
        User user = null;
        try {
            PreparedStatement send = conn.prepareStatement("SELECT id, name FROM users WHERE name = ?");
            send.setString(1, username);
            ResultSet rs = send.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public boolean addMember(User user, Role role) {
        boolean isAdded = false;


    }
}
