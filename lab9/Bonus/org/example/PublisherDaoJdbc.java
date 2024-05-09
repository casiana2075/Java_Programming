package org.example;

import org.entities.Publisher;

import java.sql.*;

public class PublisherDaoJdbc implements PublisherDao{
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "STUDENT";

    @Override
    public void create(Publisher publisher) {
        String SQL = "INSERT INTO publishers(name) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, publisher.getName());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Publisher findById(Integer id) {
        String SQL = "SELECT * FROM publishers WHERE id = ?";
        Publisher publisher = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                publisher = new Publisher();
                publisher.setId(rs.getInt("id"));
                publisher.setName(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return publisher;
    }

    @Override
    public Publisher findByName(String name) {
        String SQL = "SELECT * FROM publishers WHERE name = ?";
        Publisher publisher = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                publisher = new Publisher();
                publisher.setId(rs.getInt("id"));
                publisher.setName(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return publisher;
    }
}