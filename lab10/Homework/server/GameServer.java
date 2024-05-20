package org.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {
    private ServerSocket serverSocket;
    private List<ClientThread> clients = new ArrayList<>();
    public Map<String, Game> activeGames = new ConcurrentHashMap<>();

    public GameServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);
        } catch (IOException e) {
            System.out.println("Could not set up server socket: " + e.getMessage());
        }
    }

    public void startServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientThread clientThread = new ClientThread(socket, this);
                clients.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    public void stopServer() {
        for (ClientThread client : clients) {
            client.stopClient();
        }
        System.exit(0);
    }

    public Map<String, Game> getActiveGames() {
        return activeGames;
    }
}