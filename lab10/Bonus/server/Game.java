package org.server;

import java.io.PrintWriter;
import java.util.*;

public class Game {
    private Board board1;
    private Board board2;
    private List<String> players = new ArrayList<>();
    private PrintWriter player1Writer;
    private PrintWriter player2Writer;
    private boolean gameStarted = false;
    private int currentPlayer;
    private Timer moveTimer;
    private GameServer gameServer;
    private String gameCode;

    public Game(String player, PrintWriter player1Writer, GameServer gameServer, String gameCode) {
        this.board1 = new Board();
        this.board1.generateBoard();
        this.players.add(player);
        this.player1Writer = player1Writer;
        this.currentPlayer = 2;
        this.gameServer = gameServer;
        this.gameCode = gameCode;
    }

    public Board getBoard(int playerNumber) {
        return playerNumber == 1 ? board1 : board2;
    }

    public String getEncryptedBoard(int playerNumber) {
        return playerNumber == 1 ? board2.toString().replace('o', '~') : board1.toString().replace('o', '~');
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean addPlayer(String player, PrintWriter player2Writer) {
        if (players.size() < 2) {
            players.add(player);
            this.player2Writer = player2Writer;
            this.board2 = new Board();
            this.board2.generateBoard();
            startGame();
            startMoveTimer();
            if (player1Writer != null) {
                player1Writer.println("Player 2 has joined the game!");
                player1Writer.println("The game has started!");
                player1Writer.println("Your board is:");
                player1Writer.println(board1.toString());
                player1Writer.println("Opponent's board is:");
                player1Writer.println(getEncryptedBoard(1));
                player1Writer.println("Player 2 make the first move!");
                player1Writer.flush();
            }
            return true;
        }
        return false;
    }

    private void startGame() {
        this.gameStarted = true;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    private void startMoveTimer() {
        moveTimer = new Timer();
        moveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentPlayer == 1 && player1Writer != null) {
                    player1Writer.println("You didn't make a move in 20 seconds. You lost!");
                    player1Writer.flush();
                    if (player2Writer != null) {
                        player2Writer.println("Player 1 didn't make a move in 20 seconds. You win!");
                        player2Writer.flush();
                    }
                    endGame(players.get(0), false);
                } else if (currentPlayer == 2 && player2Writer != null) {
                    player2Writer.println("You didn't make a move in 20 seconds. You lost!");
                    player2Writer.flush();
                    if (player1Writer != null) {
                        player1Writer.println("Player 2 didn't make a move in 20 seconds. You win!");
                        player1Writer.flush();
                    }
                    endGame(players.get(1), false);
                }
            }
        }, 20000);  // 20 seconds
    }

    public void cancelMoveTimer() {
        if (moveTimer != null) {
            moveTimer.cancel();
            moveTimer = null;
        }
    }

    public String makeMove(String player, String move) {
        move = move.toUpperCase();
        int playerNumber = players.indexOf(player) + 1;
        int opponentNumber = playerNumber == 1 ? 2 : 1;
        Board opponentBoard = getBoard(opponentNumber);
        if (playerNumber != currentPlayer) {
            return "It's not your turn!";
        }

        int row = Integer.parseInt(move.substring(1)) - 1;
        int col = move.charAt(0) - 'A';

        //check row and col to be valid indices
        if (row < 0 || row >= 10 || col < 0 || col >= 10) {
            return "Invalid move!";
        }

        PrintWriter opponentPlayerWriter = playerNumber == 1 ? player2Writer : player1Writer;
        switch (opponentBoard.getCell(row, col)) {
            case 'o':
                opponentBoard.setCell(row, col, 'x');
                if (opponentBoard.areAllBoatsHit()) {
                    endGame(player, false);
                    if (opponentPlayerWriter != null) {
                        opponentPlayerWriter.println("All your boats have been hit. You lost!");
                        opponentPlayerWriter.flush();
                    }
                    return "You hit all boats and there are no boats left. You win!";
                }
                break;
            case '~':
                opponentBoard.setCell(row, col, '*');
                break;
            default:
                return "Invalid move!";
        }

        // Print the updated boards
        PrintWriter currentPlayerWriter = playerNumber == 1 ? player1Writer : player2Writer;

        if (currentPlayerWriter != null) {
            currentPlayerWriter.println("Your board is:");
            currentPlayerWriter.println(getBoard(playerNumber).toString());
            currentPlayerWriter.println("Opponent's board is:");
            currentPlayerWriter.println(getEncryptedBoard(playerNumber));
            currentPlayerWriter.println("Waiting for opponent to make a move...");
            currentPlayerWriter.flush();
        }

        if (opponentPlayerWriter != null) {
            opponentPlayerWriter.println("Your board is:");
            opponentPlayerWriter.println(getBoard(opponentNumber).toString());
            opponentPlayerWriter.println("Opponent's board is:");
            opponentPlayerWriter.println(getEncryptedBoard(opponentNumber));
            opponentPlayerWriter.println("It's your turn!");
            opponentPlayerWriter.flush();
        }
        currentPlayer = playerNumber == 1 ? 2 : 1; //swith the current player

        // Cancel the current timer and start a new one
        cancelMoveTimer();
        startMoveTimer();

        return opponentBoard.getCell(row, col) == 'x' ? "Hit!" : "Miss!";
    }

    public void endGame(String exitingPlayer, boolean isExitCommand) {
        if (moveTimer != null) {
            moveTimer.cancel();
        }
        if (isExitCommand && gameStarted) {
            if (exitingPlayer.equals(players.get(0)) && player2Writer != null) {
                player2Writer.println("Player 1 has exited the game. You are the winner!");
                player2Writer.flush();
            } else if (exitingPlayer.equals(players.get(1)) && player1Writer != null) {
                player1Writer.println("Player 2 has exited the game. You are the winner!");
                player1Writer.flush();
            }
        } else {
            gameOverExitPlayers();
        }
        gameServer.removeGame(gameCode);
        gameStarted = false;
    }
    private void gameOverExitPlayers() {
        if (player1Writer != null) {
            player1Writer.println("Game over. Please exit the game.");
            player1Writer.flush();
        }
        if (player2Writer != null) {
            player2Writer.println("Game over. Please exit the game.");
            player2Writer.flush();
        }
    }
}
