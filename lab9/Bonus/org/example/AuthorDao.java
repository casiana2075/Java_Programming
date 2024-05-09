package org.example;

import org.entities.Author;

public interface AuthorDao {
    void create(Author author);
    Author findById(Integer id);
    Author findByName(String name);
}