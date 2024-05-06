package org.example;

import java.time.LocalDateTime;
import java.util.Set;

public class ReadingList {
    private String name;
    private LocalDateTime creationTimestamp;
    private Set<Book> setOfBooks;

    public ReadingList(String name, LocalDateTime creationTimestamp, Set<Book> setOfBooks) {
        this.name = name;
        this.creationTimestamp = creationTimestamp;
        this.setOfBooks = setOfBooks;
    }

    @Override
    public String toString() {
        return "ReadingList{" +
                "name='" + name + '\'' +
                ", creationTimestamp=" + creationTimestamp +
                ", setOfBooks=" + setOfBooks +
                '}';
    }
}