
package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running = true;
    private List<Tile> playerTiles = new LinkedList<>();   // tiles of each player
    private List<List<Tile>> playerSequences = new LinkedList<>(); // sequences for each player
    private int maxSequenceLength; // length of the longest sequence
    private final int playerNumber;

    public Player(String name, Game game, int playerNumber) {
        this.name = name;
        this.game = game;
        this.maxSequenceLength = 0;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public void addTile(Tile tile) {
        playerTiles.add(tile);

        boolean tileAdded = false;
        for (List<Tile> sequence : playerSequences) {
            if (sequence.isEmpty()) {
                sequence.add(tile);
                maxSequenceLength = Math.max(maxSequenceLength, sequence.size());
                tileAdded = true;
                break;
            } else {
                Tile firstTile = sequence.get(0);
                Tile lastTile = sequence.get(sequence.size() - 1);
                if (firstTile.first() == tile.second()) {
                    sequence.add(0, tile);
                    maxSequenceLength = Math.max(maxSequenceLength, sequence.size());
                    tileAdded = true;
                    break;
                } else if (lastTile.second() == tile.first()) {
                    sequence.add(tile);
                    maxSequenceLength = Math.max(maxSequenceLength, sequence.size());
                    tileAdded = true;
                    break;
                }
            }
        }

        if (!tileAdded) {
            List<Tile> newSequence = new LinkedList<>();
            newSequence.add(tile);
            playerSequences.add(newSequence);
            maxSequenceLength = Math.max(maxSequenceLength, newSequence.size());
        }
    }

    public int getScore() {
        return maxSequenceLength;
    }
    public void stopRunning() {
        running = false;
    }

    public void printSequences() {
        System.out.println("Player " + name + "'s sequences:");
        int sequenceNum = 1;
        for (List<Tile> sequence : playerSequences) {
            System.out.print(sequenceNum + ". ");
            for (Tile tile : sequence) {
                System.out.print("(" + tile.first() + ", " + tile.second() + ") ");
            }
            System.out.println();
            sequenceNum++;
        }
    }

    public void run() {
        while (running) {
            synchronized (game) {
                while (game.getCurrentPlayer() != playerNumber) {
                    try {
                        game.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Tile tile = game.extractTile();
                if (tile == null) {
                    running = false;
                    game.playerFinished();
                } else {
                    addTile(tile);
                    System.out.println(name + " picked tile (" + tile.first() + ", " + tile.second() + ").");
                }

                game.nextPlayer();
                game.notifyAll();
            }
        }
    }
}