package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running = true;
    private List<Tile> playerTiles = new LinkedList<>();

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    public void addTile(Tile tile) {
        playerTiles.add(tile);
    }

    public void run() {
        while (running) {
            Tile tile = game.extractTile();
            if (tile == null) {
                running = false;
            } else {
                addTile(tile);
                System.out.println( name + " picked tile (" + tile.first() + ", " + tile.second() + ")." );
            }
        }
    }
}
