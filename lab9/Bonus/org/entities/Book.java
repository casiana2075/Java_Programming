package org.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import static java.lang.Math.random;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.create",
                query = "insert into Book (title, authors, lang, genres, publication_date, number_of_pages, publisher) values (:title, :authors, :lang, :genres, :publication_date, :number_of_pages, :publisher)"),
        @NamedQuery(name = "Book.findById",
                query = "select e from Book e where e.id=(:id)"),
        @NamedQuery(name = "Book.findByTitle",
                query = "select e from Book e where e.title=(:title)"),
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String lang;
    private String genres;
    private Date publication_date;
    private Integer number_of_pages;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Book() { // default constructor for JPA
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String title) { this.title = title; }
    public String getAuthors() {
        return authors.toString();
    }
    public String getLang() {
        return lang;
    }
    public String getGenres() {
        return genres.toString();
    }
    public Date getPublication_date() {
        return publication_date;
    }
    public Integer getNumber_of_pages() {
        return number_of_pages;
    }
    public int getPublisher_id() {
        return (int) ((Math.random() * (10 - 1)) + 1);// number between 1 and 10
    }

    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book :" + title + '\'';
    }
}