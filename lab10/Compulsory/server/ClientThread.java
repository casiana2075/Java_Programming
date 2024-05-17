package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

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
                if ("stop".equals(request)) {
                    out.println("Server stopped");
                    server.stopServer();
                    break;
                } else {
                    out.println("Server received the request: " + request);
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in client thread: " + e.getMessage());
        }
    }

    public void stopClient() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
    }
}