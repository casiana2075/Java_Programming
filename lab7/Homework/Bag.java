package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<Tile> tiles;

    public Bag(int k) {
        tiles = new ArrayList<>();
        for (int i = 1; i < k; i++) {
            for (int j = i + 1; j <= k; j++) {
                tiles.add(new Tile(i, j));
                tiles.add(new Tile(j, i));
            }
        }
        System.out.println("Tiles before the game starts are: "+ tiles);
        Collections.shuffle(tiles); //randomize the tiles by shuffle them
    }

    public synchronized Tile extractTile() {
        if (tiles.isEmpty()) {
            return null;
        }
        return tiles.remove(tiles.size() - 1);
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }
}
