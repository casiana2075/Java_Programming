package org.example;

import jakarta.persistence.EntityManager;
import org.entities.Book;

public class BookRepository extends AbstractRepository<Book> implements BookDao {
    public BookRepository() {
        super(Book.class);
    }

    @Override
    public void create(Book book) {
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
    }
    @Override
    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }
    @Override
    public Book findByTitle(String title) {
        return em.createNamedQuery("Book.findByTitle", Book.class)
                .setParameter("title", title).getResultList().get(0);
    }
}