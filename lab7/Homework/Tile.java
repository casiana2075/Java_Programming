package org.example;

public record Tile(int first, int second) {

    @Override
    public String toString() {
        return "(" + first + ", " + second +")";
    }
}
