
package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag;
    private final List<Player> players = new ArrayList<>();
    private int playersFinished = 0;
    private final Object lock = new Object();
    private int currentPlayer = 0;
    private boolean gameRunning = true;

    public Game(int k) {
        bag = new Bag(k);
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Tile extractTile() {
        return bag.extractTile();
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }
    public synchronized void playerFinished() {
        playersFinished++;
        if (playersFinished >= players.size()) {
            // Notify the main thread that all players have finished
            synchronized (lock) {
                lock.notify();
            }
        }
    }
    public void stopGame() {
        gameRunning = false;
        for (Player player : players) {
            player.stopRunning();
        }
    }

    public void play() {
        Thread timeKeeperThread = new Thread(new TimeKeeper(this, 3000)); // 3 seconds time limit
        timeKeeperThread.setDaemon(true);
        timeKeeperThread.start();

        for (int i = 0; i < players.size(); i++) {
            new Thread(players.get(i)).start();
        }

        synchronized (lock) {
            try {
                // Wait until all players have finished
                while (playersFinished < players.size()) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Determine the winner
        Player winner = null;
        int maxScore = 0;
        for (Player player : players) {
            int score = player.getScore();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            }
        }

        if (winner != null) {
            System.out.println("The winner is " + winner.getName() + " with a score of " + maxScore);
            // Print sequences for each player
            for (Player player : players) {
                player.printSequences();
            }
        } else {
            System.out.println("No winner. All players have a score of 0.");
        }
    }
}
