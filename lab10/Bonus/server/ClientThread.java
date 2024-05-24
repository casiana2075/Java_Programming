package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;
    private Random random = new Random();
    private boolean waitingForPlayer = false;


    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Received request: " + request);  // Debugging line
                if ("stop".equals(request)) {
                    out.println("Server stopped!!");
                    out.flush();
                    server.stopServer();
                    break;
                } else if (request.startsWith("create game")) {
                    if (waitingForPlayer) {
                        out.println("Can't create another game because you are already in a game. Please wait for the player 2 or type \"exit\".");
                    } else {
                        String gameCode;
                        do {
                            gameCode = String.format("%04d", random.nextInt(10000));
                        } while (server.getActiveGames().containsKey(gameCode));

                        Game game = new Game(this.getName(), out, server, gameCode);
                        server.getActiveGames().put(gameCode, game);
                        out.println("You are player 1!");
                        out.println("Your board is:");
                        out.println(game.getBoard(1));
                        out.println("Waiting for player 2 to join...");
                        waitingForPlayer = true;
                    }
                    out.flush();
                } else if (request.startsWith("join game")) {
                    String gameCode = request.substring("join game".length()).trim();
                    if (server.getActiveGames().values().stream().anyMatch(game -> game.getPlayers().contains(this.getName()))) {
                        out.println("You are already in a game. Please finish your current game before joining a new one.");
                    } else if (server.getActiveGames().containsKey(gameCode)) {
                        Game game = server.getActiveGames().get(gameCode);
                        if (game.addPlayer(this.getName(), out)) {
                            out.println("You are player 2!");
                            out.println("Your board is:");
                            out.println(game.getBoard(2));
                            out.println("Opponent's board is:");
                            out.println(game.getEncryptedBoard(1));
                            waitingForPlayer = false;
                            if (game.isGameStarted()) {
                                out.println("The game has started!");
                                out.println("Player 2 starts first!");
                            }
                        } else {
                            out.println("Game is full. Please try another game.");
                        }
                    } else {
                        out.println("Game doesn't exist. Please try again.");
                    }
                    out.flush();
                } else if (request.startsWith("active games")) {
                    out.println("Active games: " + server.getActiveGames().keySet());
                    out.flush();
                }else if (request.startsWith("submit move")) {
                    Game game = getActiveGame();
                    if (game == null) {
                        out.println("You are not currently in a game.");
                    } else {
                        String move = request.substring("submit move".length()).trim();
                        String result = game.makeMove(this.getName(), move);
                        out.println(result);
                    }
                    out.flush();
                } else if (request.equals("exit")) {
                    server.getActiveGames().values().stream()
                            .filter(game -> game.getPlayers().contains(this.getName()))
                            .forEach(game -> {
                                game.endGame(this.getName(), true);
                                server.getActiveGames().remove(game);
                            });
                    break;
                } else {
                    out.println("Server received the request: " + request);
                    out.flush();
                }
            }
            System.out.println("Client disconnected");
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in client thread: " + e.getMessage());
        }
    }

    private Game getActiveGame() {
        return server.getActiveGames().values().stream()
                .filter(game -> game.getPlayers().contains(this.getName()))
                .findFirst().orElse(null);
    }

    public void stopClient() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
    }
}