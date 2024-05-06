package org.example;

import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.alg.coloring.Coloring;
import org.graph4j.alg.coloring.eq.EquitableColoringAlgorithm;
import org.graph4j.alg.coloring.eq.GreedyEquitableColoring;
import org.graph4j.generate.GraphGenerator;
import org.graph4j.util.VertexSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Main {
    private static Graph<Book, Edge<Integer>> bookGraph;

    public static void main(String[] args) throws Exception {
        try {

            importBooksFromCSV();

            //Create the reading lists based on the graph with greedy coloring
            createReadingLists().stream().forEach(readingList -> System.out.println("\n"+readingList));

        } catch (SQLException ex) {

            System.out.println(ex);
            System.out.println("Commiting rollback...");

            try {
                Database.getConnection().rollback();
            } catch (SQLException e) {
                System.out.println("Error at committing rollback:" + e);
            }
        }

    }

    private static void importBooksFromCSV() throws IOException, SQLException {
        String csvFile = "src/main/resources/books.csv";
        String line = "";
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // skip header line
            br.readLine();
            var bookDAO = new BookDAO();

            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(csvSplitBy);
                String title = bookData[1];
                String lang = bookData[6];
                List<String> authors = List.of(bookData[2].split("/"));
                List<String> genres;
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
                genres = book.assignRandomGenres();

                book.setTitle(title);
                book.setAuthors(authors);
                book.setLanguage(lang);
                book.setGenres(genres);
                book.setPublicationDate(new java.sql.Date(publicationDate.getTime()));
                book.setNumberOfPages(numberOfPages);

                // Insert book into database
                bookDAO.create(book);

                System.out.println("Inserted book : " + book);
            }
        }

        var booksDAO = new BookDAO();
        Set<Book> books = booksDAO.findAll().stream().collect(Collectors.toSet());
        new GraphGenerator();
        // Create the graph
        bookGraph = GraphGenerator.empty(books.size());
        {
            AtomicInteger count = new AtomicInteger(0);

            // Add the books as vertices
            books.stream().forEach(book -> {
                bookGraph.addVertex(book);
                bookGraph.setVertexLabel(count.getAndIncrement(), book);
            });
        }
        // Create the edges between books that have a common author or common genre
        for (Book book1 : books) {
            for (Book book2 : books) {
                if (!book1.equals(book2)) {
                    // Add edege between books that have the same genre
                    if (book1.getGenres().equals(book2.getGenres())) {
                        bookGraph.addEdge(book1, book2);
                    }

                    List<String> Book1Authors = book1.getAuthors();
                    List<String> Book2Authors = book2.getAuthors();

                    // Add edge between books that have a common author
                    for (String author : Book1Authors) {
                        if (Book2Authors.contains(author)) {
                            bookGraph.addEdge(book1, book2);
                            break;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void createBookGraph() throws SQLException {

        var booksDAO = new BookDAO();
        Set<Book> books = booksDAO.findAll().stream().collect(Collectors.toSet());

        new GraphGenerator();
        // Create the graph
        bookGraph = GraphGenerator.empty(books.size());
        {
            AtomicInteger count = new AtomicInteger(0);

            // Add the books as vertices
            books.stream().forEach(book -> {
                bookGraph.addVertex(book);
                bookGraph.setVertexLabel(count.getAndIncrement(), book);
            });
        }
        // Create the edges beetwen books that have a common author or common genre
        for (Book book1 : books) {
            for (Book book2 : books) {
                if (!book1.equals(book2)) {
                    // Add edege between books that have the same genre
                    if (book1.getGenres().equals(book2.getGenres())) {
                        bookGraph.addEdge(book1, book2);
                    }

                    List<String> Book1Authors = book1.getAuthors();
                    List<String> Book2Authors = book2.getAuthors();

                    // Add edge between books that have a common author
                    for (String author : Book1Authors) {
                        if (Book2Authors.contains(author)) {
                            bookGraph.addEdge(book1, book2);
                            break;
                        }
                    }
                }

            }
        }

    }
    public static List<ReadingList> createReadingLists() throws SQLException {
        // Apply equitable coloring algorithm
        EquitableColoringAlgorithm coloring = new GreedyEquitableColoring(bookGraph);
        Coloring greedyColoring = coloring.findColoring();

        // Get the coloring information
        Map<Integer, VertexSet> colorClasses = greedyColoring.getColorClasses();

        List<ReadingList> readingLists = new ArrayList<>();

        //Each Integre represents a color therefore a different Reading List
        for (Map.Entry<Integer, VertexSet> entry : colorClasses.entrySet()) {

            Set<Book> currentReadingListBooks = new HashSet<>();

            entry.getValue().forEach(vertex -> {

                Book book = bookGraph.getVertexLabel(vertex);
                currentReadingListBooks.add(book);

            });

            ReadingList readingList = new ReadingList("ReadingList" + entry.getKey(), LocalDateTime.now(), currentReadingListBooks);
            readingLists.add(readingList);

        }
        return readingLists;

    }
}