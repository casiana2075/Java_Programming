package org.example;

import jakarta.persistence.EntityManager;
import org.entities.Author;
import org.entities.Book;
import org.entities.Publisher;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Create repositories
        AuthorRepository authorRepo = new AuthorRepository();
        BookRepository bookRepo = new BookRepository();
        PublisherRepository publisherRepo = new PublisherRepository();

        //Create authors
        Author author1 = new Author("Mark Twain");
        Author author2 = new Author("Charles Dickens");

        // Create books
        Book book1 = new Book();
        book1.setTitle("The Adventures of Tom Sawyer");
        Book book2 = new Book();
        book2.setTitle("A Tale of Two Cities");

        // Create a publisher
        Publisher publisher = new Publisher();
        publisher.setName("Penguin Books");

        // Add books to authors
        Set<Book> author1Books = new HashSet<>();
        author1Books.add(book1);
        author1.setBooks(author1Books);

        Set<Book> author2Books = new HashSet<>();
        author2Books.add(book2);
        author2.setBooks(author2Books);

        // Add books to publisher
        Set<Book> publisherBooks = new HashSet<>();
        publisherBooks.add(book1);
        publisherBooks.add(book2);
        publisher.setBooks(publisherBooks);

        // Persist entities
        // persist entities
        publisherRepo.save(publisher);
        bookRepo.save(bookRepo.getEntityManager().merge(book1));
        bookRepo.save(bookRepo.getEntityManager().merge(book2));
        authorRepo.create(author1);
        authorRepo.create(author2);

        // Fetch and print entities
        System.out.println(authorRepo.findById(author1.getId()));
        System.out.println(authorRepo.findById(author2.getId()));
        System.out.println(bookRepo.find(book1.getId()));
        System.out.println(bookRepo.find(book2.getId()));
        System.out.println(publisherRepo.find(publisher.getId()));

        // update
        book1.setTitle("The Adventures of Finn & Jake");
        bookRepo.merge(book1);

        // delete
        authorRepo.delete(author2);

        System.out.println(authorRepo.findById(author1.getId()));
        //System.out.println(authorRepo.findById(author2.getId())); // return null
        System.out.println(bookRepo.find(book1.getId())); //return the updated book
        System.out.println(bookRepo.find(book2.getId()));
        System.out.println(publisherRepo.find(publisher.getId()));
    }
}