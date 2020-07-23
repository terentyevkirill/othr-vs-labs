package com.othr.vs.sequentialsocketchat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1200;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();

                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                PrintWriter writer = new PrintWriter(out);

                String clientRequest = reader.readLine();
                System.out.println("Receved from port " + clientSocket.getPort() + ": " + clientRequest);

                writer.println("*" + clientRequest + "*");
                writer.flush();

                reader.close();
                writer.close();
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
