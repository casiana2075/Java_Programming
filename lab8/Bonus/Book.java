package org.example;

import java.util.*;

public class Book {
    private Integer id;
    private String title;
    private List<String> authors;
    private List<String> genres;
    private String language;
    private Date publicationDate;
    private Integer numberOfPages;

    public Book() {
        this.genres = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public List<String> assignRandomGenres() {
        List<String> allGenres = Arrays.asList("Fantasy", "Sci-Fi", "Mystery", "Romance", "Horror", "Thriller", "Non-fiction"); // Add all possible genres here

        Random rand = new Random();
        int numberOfGenres = rand.nextInt(3) + 1; // Generate a random number between 1 and 3

        for (int i = 0; i < numberOfGenres; i++) {
            int randomGenreIndex = rand.nextInt(allGenres.size());
            String randomGenre = allGenres.get(randomGenreIndex);
            if (!this.genres.contains(randomGenre)) { // Check if the genre is not already assigned
                this.genres.add(randomGenre);
            }
        }
        return genres;
    }

    public String getTitle() {
        return title;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public String getLanguage() {
        return language;
    }
    public List<String> getGenres() {
        return genres;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public Date getPublicationDate() {
        return publicationDate;
    }
    public int getId() {
        return id;
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
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "\'" + title + '\'';
    }
}