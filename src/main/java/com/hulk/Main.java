package com.hulk;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://127.0.0.1:5432/users-db";
        String username = "postgres";
        String password = "qwe";

        DataBase psc = new DataBase(url, username, password);

        System.out.println(psc.createUser("Sergey"));
    }
}
