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
        System.out.println(db.createUser("Andrey"));

        db.createGroup("rust");

        db.addMember(db.getUser("Alex"), db.getGroup("rust"), Role.Admin);
        db.addMember(db.getUser("Vova"), db.getGroup("rust"), Role.User);
        db.addMember(db.getUser("Dima"), db.getGroup("rust"), Role.User);
        db.addMember(db.getUser("Andrey"), db.getGroup("rust"), Role.User);

        ArrayList<User> users = db.getUsers();
        ArrayList<Group> groups = db.getGroups();
        //ArrayList<Santa> santas = db.setSantas(db.getUser("Alex"));

//        for (Group g: groups) {
//            System.out.println(g);
//        }

        for (User u: users) {
            System.out.println(u);
        }
        for (Group g: groups) {
            System.out.println(g);
        }

//        for (Group g: groups) {
//            System.out.println(db.deleteSantas(g));
//        }

        for (Group g: groups) {
            System.out.println(db.deleteGroup(g));
        }

        for (User u: users) {
            System.out.println(db.deleteUser(u));
        }
    }
}
