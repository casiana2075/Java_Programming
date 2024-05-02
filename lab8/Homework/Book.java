package org.example;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class Book {
    private Integer id;
    private String title;
    private List<Author> authors;
    private String language;
    private Date publicationDate;
    private Integer numberOfPages;

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublicationDate(java.sql.Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getId() {
        return id;
    }
}