package com.example.project;

import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Repository
public class BookDAO {

    private Connection con = Database.getConnection();

    public void create(Book book) throws SQLException {
        String sql = "insert into books (title, authors, lang, genres, publication_date, number_of_pages, publisher_id, year) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors().toString());
            pstmt.setString(3, book.getLanguage());
            pstmt.setString(4, book.getGenres().toString());
            java.sql.Date publicationDate = new java.sql.Date(book.getPublicationDate().getTime());
            pstmt.setDate(5, publicationDate);
            pstmt.setInt(6, book.getNumberOfPages());

            // Set publisherId as a random number between 1 and 10
            int publisherId = new Random().nextInt(10) + 1;
            pstmt.setInt(7, publisherId);

            // Get the year from publication date
            Calendar cal = Calendar.getInstance();
            cal.setTime(publicationDate);
            int year = cal.get(Calendar.YEAR);
            pstmt.setInt(8, year);

            pstmt.executeUpdate();

            // Get the generated key (id)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }

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
             ResultSet rs = stmt.executeQuery("select * from books LIMIT 1000")) { // only 1000 in demonstrative way
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));

                String authors = rs.getString("authors");
                if (authors != null) {
                    book.setAuthors(List.of(authors.split(",")));
                }

                book.setLanguage(rs.getString("lang"));

                String genres = rs.getString("genres");
                if (genres != null) {
                    book.setGenres(List.of(genres.split(",")));
                }

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

    public Book findById(int id) {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select * from books where id=" + id)) {
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));

                String authors = rs.getString("authors");
                if (authors != null) {
                    book.setAuthors(List.of(authors.split(",")));
                }

                book.setLanguage(rs.getString("lang"));

                String genres = rs.getString("genres");
                if (genres != null) {
                    book.setGenres(List.of(genres.split(",")));
                }

                book.setPublicationDate(rs.getDate("publication_date"));
                book.setNumberOfPages(rs.getInt("number_of_pages"));
                return book;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to retrieve book with id: " + id);
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void update(Book book) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "update books set title=?, authors=?, lang=?, genres=?, publication_date=?, number_of_pages=? where id=?")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors().toString());
            pstmt.setString(3, book.getLanguage());
            pstmt.setString(4, book.getGenres().toString());
            pstmt.setDate(5, new Date(book.getPublicationDate().getTime()));
            pstmt.setInt(6, book.getNumberOfPages());
            pstmt.setInt(7, book.getId());
            pstmt.executeUpdate();
            System.out.println("Successfully updated book with id: " + book.getId());
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to update book with id: " + book.getId());
            System.err.println(e.getMessage());
        }
    }

    public void updateTitle(int id, String newTitle) throws SQLException {
        String sql = "UPDATE books SET title = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated book title with id: " + id);
            } else {
                System.out.println("No book found with id: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to update book title with id: " + id);
            System.err.println(e.getMessage());
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("delete from books where id=?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Successfully deleted book with id: " + id);
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while trying to delete book with id: " + id);
            System.err.println(e.getMessage());
        }
    }
}