package com.othr.vs.training.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server works...");
            while (true) {
                try {
                    Socket client = server.accept();
                    System.out.println("New connection: " + client.toString());
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    PrintWriter writer = new PrintWriter(out);

                    String request = reader.readLine();
                    System.out.println(client.toString() + ": " + request);

                    writer.println("*" + request + "*");
                    writer.println();

                    writer.close();
                    reader.close();
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
