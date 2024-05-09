package org.example;

import org.entities.Author;

import java.sql.*;

public class AuthorDaoJdbc implements AuthorDao{
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "STUDENT";

    @Override
    public void create(Author author) {
        String SQL = "INSERT INTO authors(name) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, author.getName());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Author findById(Integer id) {
        String SQL = "SELECT * FROM authors WHERE id = ?";
        Author author = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return author;
    }

    @Override
    public Author findByName(String name) {
        String SQL = "SELECT * FROM authors WHERE name = ?";
        Author author = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return author;
    }
}