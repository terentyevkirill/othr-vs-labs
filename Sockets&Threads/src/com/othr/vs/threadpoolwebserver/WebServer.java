package com.othr.vs.threadpoolwebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    public static final int DEFAULT_PORT = 8080;
    public static final int THREAD_POOL_SIZE = 10;
    public final int port;
    public final ExecutorService threadPool;

    public static void main(String[] args) {
        new WebServer(DEFAULT_PORT);
    }


    public WebServer(int port) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        startWebServer();
    }

    private void startWebServer() {
        try {
            // wait for new browser connection...
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New request from "
                        + clientSocket.getInetAddress().getHostAddress());

                // ... as soon as new connection performed,
                // create Runnable and pass to ThreadPool
                Runnable requestHandler = new RequestHandler(clientSocket);
                threadPool.execute(requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
