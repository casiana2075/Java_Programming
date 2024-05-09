package org.example;

import org.entities.Book;

import java.sql.*;

public class BookDaoJdbc implements BookDao{
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "STUDENT";

    @Override
    public void create(Book book) {
        String SQL = "INSERT INTO books(title, authors, lang, genres, publication_date, number_of_pages, publisher_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors());
            pstmt.setString(3, book.getLang());
            pstmt.setString(4, book.getGenres());
            pstmt.setDate(5, book.getPublication_date());
            pstmt.setInt(6, book.getNumber_of_pages());
            pstmt.setInt(7, book.getPublisher_id());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Book findById(Integer id) {
        String SQL = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return book;
    }

    @Override
    public Book findByTitle(String title) {
        String SQL = "SELECT * FROM books WHERE title = ?";
        Book book = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return book;
    }
}