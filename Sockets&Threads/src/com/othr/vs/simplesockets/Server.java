package com.othr.vs.simplesockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
//        int port = Integer.parseInt(args[0]);
        int port = 1200;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept(); // blokierend

                InputStream in = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                OutputStream out = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(out);

                String clientRequest = reader.readLine();   // blokierend
                System.out.println("Received: " + clientRequest);

                writer.println("*" + clientRequest + "*");
                writer.flush();

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
