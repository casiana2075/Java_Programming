package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        Connection conn = Database.getConnection();

        try {

            var authors = new AuthorDAO();
            authors.create("Robert Greene");

            System.out.println("Author:" + authors.findById(2) + " id: " + authors.findByName(authors.findById(2)));

            System.out.println();

            conn.commit();

            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e);
            conn.rollback();

        }

    }
}