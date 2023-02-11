package com.hulk.app;

import com.hulk.models.*;
import com.hulk.models.Member.Role;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/users-db";
            String username = "username";
            String password = "password";

            DataBase db = new DataBase(url, username, password);

            System.out.println(db.createUser("Alex"));
            System.out.println(db.createUser("Dima"));
            System.out.println(db.createUser("Vova"));
            System.out.println(db.createUser("Andrey"));

            db.createGroup("rust");

            User user = db.getUser("Alex");
            db.addMember(user, db.getGroup("rust"), Role.Admin);
            db.addMember(db.getUser("Vova"), db.getGroup("rust"), Role.User);
            db.addMember(db.getUser("Dima"), db.getGroup("rust"), Role.User);
            db.addMember(db.getUser("Andrey"), db.getGroup("rust"), Role.User);

            ArrayList<User> users = db.getUsers();

            Group group = db.getGroup("rust");

            Member admin = db.getMember(group.getId(), user.getId());
            db.setSantas(group.getId(), admin);

            ArrayList<Member> members = db.getMembers(group.getId());
            ArrayList<Group> groups = db.getGroups();
            ArrayList<Santa> santas = db.getSantas(group.getId(), admin);

            for (Santa s : santas) {
                System.out.println(s);
            }

            for (Member m : members) {
                System.out.println(m);
            }

            for (User u : users) {
                System.out.println(u);
            }

            for (Group g : groups) {
                System.out.println(g);
            }

            for (Santa s : santas) {
                System.out.println(db.deleteSantas(s));
            }

            for (Member m : members) {
                System.out.println(db.deleteMember(m));
            }
            for (Group g : groups) {
                System.out.println(db.deleteGroup(g));
            }
            for (User u : users) {
                System.out.println(db.deleteUser(u));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
