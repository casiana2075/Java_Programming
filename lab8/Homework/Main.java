package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = Database.getConnection();

        try {// Import books from CSV
            importBooksFromCSV();

            System.out.println("Book 1:" + new BookDAO().findAll().get(0));

        } catch (SQLException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static void importBooksFromCSV() throws IOException, ParseException, SQLException {
        String csvFile = "src/main/resources/books.csv";
        String line = "";
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // skip header line
            br.readLine();
            BookDAO bookDAO = new BookDAO();

            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(csvSplitBy);
                String title = bookData[1];
                String language = bookData[6];
                Date publicationDate = null;
                try{
                     publicationDate = dateFormat.parse(bookData[10]);
                }catch (ParseException e) {
                System.err.println("Skipping row due to unparseable date: " + line);
                continue;
            }
                int numberOfPages = Integer.parseInt(bookData[7]);

                // create Book object
                Book book = new Book();
                book.setTitle(title);
                book.setLanguage(language);
                book.setPublicationDate(new java.sql.Date(publicationDate.getTime()));
                book.setNumberOfPages(numberOfPages);

                // Insert book into database

                    bookDAO.create(book);


                System.out.println("Book inserted: " + book);
            }
        }
    }
}