package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection con = Database.getConnection();

    public void create(Book book) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "insert into books (title, authors, lang, genres, publication_date, number_of_pages) values (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors().toString());
            pstmt.setString(3, book.getLanguage());
            pstmt.setString(4, book.getGenres().toString());
            pstmt.setDate(5, new Date(book.getPublicationDate().getTime()));
            pstmt.setInt(6, book.getNumberOfPages());
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
                book.setAuthors(List.of(rs.getString("authors").split(",")));
                book.setLanguage(rs.getString("lang"));
                book.setGenres(List.of(rs.getString("genres").split(",")));
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
}