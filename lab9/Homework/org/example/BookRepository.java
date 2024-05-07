package org.example;

import jakarta.persistence.EntityManager;
import org.entities.Book;

public class BookRepository extends AbstractRepository<Book> {
    public BookRepository() {
        super(Book.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}