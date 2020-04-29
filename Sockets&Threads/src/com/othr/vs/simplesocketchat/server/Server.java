package com.othr.vs.simplesocketchat.server;

import com.othr.vs.simplesocketchat.util.InputStreamListener;
import com.othr.vs.simplesocketchat.util.KeyboardListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static final String END_STRING = "Bye";
    public static int PORT = 1212;
    public static final String HOST = "im-lamport.oth-regensburg.de";   // only in OTH VPN

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started, waiting for connection...");
            Socket client = serverSocket.accept();
            System.out.println("Connection created: " + client.toString());

            Runnable keyboardListener = new KeyboardListener(client);
            Runnable inputStreamListener = new InputStreamListener(client);
            new Thread(keyboardListener).start();
            new Thread(inputStreamListener).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
