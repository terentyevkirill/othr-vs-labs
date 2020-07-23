package com.othr.vs.simplesockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 1200;
        System.out.print("Enter text: ");
        Scanner scanner = new Scanner(System.in);
        String request = scanner.nextLine();
        try (Socket serverSocket = new Socket(serverHost, serverPort)) {
            InputStream in = serverSocket.getInputStream();
            OutputStream out = serverSocket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(out);

            writer.println(request);
            writer.flush();

            String serverReply = reader.readLine();
            System.out.println("Reply from server: " + serverReply);

            reader.close();
            writer.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
