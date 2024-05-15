package org.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public String setTitle(String title){
        return this.title = title;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Book: " + title + '\'';
    }
}