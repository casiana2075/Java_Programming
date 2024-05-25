package com.example.project;

import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorDAO {

    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into authors (name) values (?)")) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added author: " + name);
            } else {
                System.out.println("Failed to add author: " + name);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to add author: " + name);
            System.err.println(e.getMessage());
        }
    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select id from authors where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public Author findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from authors where id=" + id)) {
            if (rs.next()) {
                return new Author(rs.getInt("id"), rs.getString("name"));
            } else {
                return null;
            }
        }
    }

    public List<Author> findAll() throws SQLException {
        List<Author> authors = new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from authors")) {
            while (rs.next()) {
                Author author = new Author(rs.getInt("id"), rs.getString("name"));
                authors.add(author);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to retrieve authors.");
            System.err.println(e.getMessage());
        }
        return authors;
    }
}
