package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag;
    private final List<Player> players = new ArrayList<>();

    public Game(int k) {
        bag = new Bag(k);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Tile extractTile() {
        return bag.extractTile();
    }

    public void play() {
        for (Player player : players) {
            new Thread(player).start();
        }
    }

    public static void main(String args[]) {
        Game game = new Game(5);
        game.addPlayer(new Player("Player 1", game));
        game.addPlayer(new Player("Player 2", game));
        game.addPlayer(new Player("Player 3", game));
        game.play();
    }
}
