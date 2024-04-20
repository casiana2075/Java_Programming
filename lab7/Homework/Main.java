package org.example;

public class Main {
    public static void main(String args[]) {
        Game game = new Game(1000);
        game.addPlayer(new Player("Player 1", game, 0));
        game.addPlayer(new Player("Player 2", game, 1));
        game.addPlayer(new Player("Player 3", game, 2));
        game.play();
    }
}
