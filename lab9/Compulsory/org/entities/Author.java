package org.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "authors")
@NamedQueries({
        @NamedQuery(name = "Author.create",
                query = "insert into Author (name) values (:name)"),
        @NamedQuery(name = "Author.findById",
                query = "select e from Author e where e.id=(:id)"),
        @NamedQuery(name = "Author.findByName",
                query = "select e from Author e where e.name=(:name)"),
})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Author() {
        // Default constructor required by JPA
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}