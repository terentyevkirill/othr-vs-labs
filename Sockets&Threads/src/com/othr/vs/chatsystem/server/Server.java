package com.othr.vs.chatsystem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// active class
public class Server extends Thread {
    private final int port;
    private final Dispatcher dispatcher;

    public Server(int port, Dispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }


    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        int port = 8080;
        new Server(port, dispatcher).start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientEndpoint clientEndpoint = new ClientEndpoint(clientSocket, dispatcher);
                new Thread(clientEndpoint).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
