package com.example.project;

public record Author(Integer id,String name) {
    public int getId() {
        return id;
    }
}
