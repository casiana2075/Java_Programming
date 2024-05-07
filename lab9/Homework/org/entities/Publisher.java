package org.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Book> books;

    public void setName(String name){
        this.name = name;
    }
    public void setBooks(Set<Book> books){
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Publisher :" +
                "name='" + name + '\'' + "\n";
    }
}