package org.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public GameClient(String serverAddress, int serverPort) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void startClient(String clientName) throws Exception {

        try{
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String command = "";
            while (!command.equals("exit")) {
                System.out.println("Enter command for " + clientName + ":");
                command = keyboard.readLine();
                output.println(command);
                if (command.equals("stop")) {
                    break;
                }
                String serverResponse = input.readLine();
                System.out.println("Server response: " + serverResponse);
                if (serverResponse.equals("Server stopped")) {
                    break;
                }
            }
            socket.close();
        }catch (java.net.SocketException e){
            System.out.println(clientName + " connection was aborted. Server stopped.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        GameClient client1 = new GameClient("localhost", 5000);
        GameClient client2 = new GameClient("localhost", 5000);
        GameClient client3 = new GameClient("localhost", 5000);
        client1.startClient("client1");
        client2.startClient("client2");
        client3.startClient("client3");
    }
}