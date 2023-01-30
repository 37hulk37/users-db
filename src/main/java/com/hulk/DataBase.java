package com.hulk;

import java.sql.*;

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
            Statement st = conn.prepareStatement("SELECT EXISTS( SELECT name FROM users WHERE name = ?)");
            if ( st.executeQuery("SELECT EXISTS( SELECT name FROM users WHERE name = 'username')").next() ) {

                isCreated = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isCreated;
    }
}
