package org.example;

import org.entities.Author;

public class Main {
    public static void main(String[] args) {

        AuthorRepository authorRepo = new AuthorRepository();
        Author author = new Author("Mark Twain");
        authorRepo.create(author);

        System.out.println(authorRepo.findById(1));

        System.out.println(authorRepo.findByName("Robert Greene"));

    }
}