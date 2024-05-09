package org.example;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.entities.Book;
import org.entities.Publisher;
import org.solver.BookSelectionSolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        //demo first dot
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String daoType = properties.getProperty("daoType");
        DaoFactory factory = DaoFactoryProducer.getDaoFactory(daoType);

        assert factory != null;
        AuthorDao authorDao = factory.getAuthorDao();
        BookDao bookDao = factory.getBookDao();
        PublisherDao publisherDao = factory.getPublisherDao();

        System.out.println(authorDao.findById(5));

        System.out.println( bookDao.findByTitle("Notes from a Small Island"));

        Publisher publisher = new Publisher();
        publisher.setName("Bloomsbury");
        publisherDao.create(publisher);
        System.out.println(publisher);


        //demo for second dot
        // Get DAO Factory
        DaoFactory daoFactory = DaoFactoryProducer.getDaoFactory(properties.getProperty("daoType"));

        // Get BookDAO
        BookDaoJdbc bookDAO = (BookDaoJdbc) daoFactory.getBookDao();

        // Get all books
        List<Book> books = new ArrayList<>(bookDAO.findAll());

        // Define k and p
        int k = 10; // Minimum number of books to select
        int p = 3; // Maximum difference in publication years

        // Create BookSelectionSolver and solve
        BookSelectionSolver bookSelectionSolver = new BookSelectionSolver();
        bookSelectionSolver.solve(books, k, p);
    }
}