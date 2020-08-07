package com.othr.vs.training.parallelchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String HOST = "localhost";
    public static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for connections...");
                Socket client = server.accept();
                System.out.println("Connection created: " + client.toString());
                InputListener inputListener = new InputListener(client);
                KeyboardListener keyboardListener = new KeyboardListener(client);
                new Thread(inputListener).start();
                new Thread(keyboardListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
