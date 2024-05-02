package org.example;

public record Author(Integer id,String name) {
    public int getId() {
        return id;
    }
}
