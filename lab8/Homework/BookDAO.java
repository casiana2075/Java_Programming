package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection con = Database.getConnection();

    public void create(Book book) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into books (title, language, publication_date, number_of_pages) values (?, ?, ?, ?)")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getLanguage());
            pstmt.setDate(3, new java.sql.Date(book.getPublicationDate().getTime()));
            pstmt.setInt(4, book.getNumberOfPages());
            pstmt.executeUpdate();
            System.out.println("Successfully added book: " + book.getTitle());
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to add book: " + book.getTitle());
            System.err.println(e.getMessage());
        }
    }


    public List<Book> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<Book> books = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from books")) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setLanguage(rs.getString("language"));
                book.setPublicationDate(rs.getDate("publication_date"));
                book.setNumberOfPages(rs.getInt("number_of_pages"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to retrieve books.");
            System.err.println(e.getMessage());
        }
        return books;
    }

    public void addAuthor(Book book, Author author) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into book_author (book_id, author_id) values (?, ?)")) {
            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, author.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to add author to book.");
            System.err.println(e.getMessage());
        }
    }

    public Book findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select * from books where id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setLanguage(rs.getString("language"));
                    book.setPublicationDate(rs.getDate("publication_date"));
                    book.setNumberOfPages(rs.getInt("number_of_pages"));
                    return book;
                } else {
                    return null;
                }
            }
        }
    }
}