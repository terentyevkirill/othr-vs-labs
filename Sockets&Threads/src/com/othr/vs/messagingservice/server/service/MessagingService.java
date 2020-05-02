package com.othr.vs.messagingservice.server.service;

import com.othr.vs.messagingservice.server.repository.MessageStore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server, passive class
 * Use com.othr.vs.simplesocketchat.client.Client as Client
 */
public class MessagingService {
    public static final String HOST = "localhost";  // im-lamport.oth-regensburg.de
    public static final int PORT = 8080;            // 1213
    public static final String REGISTER_CMD = "REG";
    public static final String SEND_CMD = "SND";
    public static final String RECEIVE_CMD = "RCV";

    public static void main(String[] args) {
        MessageStore store = new MessageStore();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for connection...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection created: " + clientSocket.toString());
                Runnable request = new ClientRequest(clientSocket, store);
                threadPool.execute(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
