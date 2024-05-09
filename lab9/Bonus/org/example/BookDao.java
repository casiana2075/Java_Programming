package org.example;

import org.entities.Book;

public interface BookDao {
    void create(Book book);
    Book findById(Integer id);
    Book findByTitle(String title);
}
