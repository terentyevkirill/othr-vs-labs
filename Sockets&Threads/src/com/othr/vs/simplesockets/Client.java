package com.othr.vs.simplesockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
//        String serverHost = args[0];
//        int serverPort = Integer.parseInt(args[1]);
        String serverHost = "localhost";
        int serverPort = 1200;

        System.out.println("Enter text: ");
        Scanner scanner = new Scanner(System.in);
        String textForServer = scanner.nextLine();
        try {
            Socket serverSocket = new Socket(serverHost, serverPort);

            OutputStream out = serverSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            InputStream in = serverSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            writer.println(textForServer);
            writer.flush();

            String serverReply = reader.readLine();
            System.out.println("Reply from Server: " + serverReply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
