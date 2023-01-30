package com.hulk;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://127.0.0.1:5432/users-db";
        String username = "postgres";
        String password = "qwe";

        DataBase db = new DataBase(url, username, password);

        System.out.println(db.createUser("Alex"));
        System.out.println(db.createUser("Dima"));
        System.out.println(db.createUser("Vova"));

        db.createGroup("rust");

        db.addMember(db.getUser("Alex"), Role.Admin);
        db.addMember(db.getUser("Vova"), Role.User);
        db.addMember(db.getUser("Dima"), Role.User);

        ArrayList<User> users = db.getUsers();

        for (User u: users) {
            System.out.println(db.deleteUser(u));
        }
    }
}
