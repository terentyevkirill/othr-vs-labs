package com.othr.vs.training.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.othr.vs.training.chat.Server.*;

public class Client {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter message: ");
        String request = scan.nextLine();

        try (Socket server = new Socket(HOST, PORT)) {
            InputStream in = server.getInputStream();
            OutputStream out = server.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(out);

            writer.println(request);
            writer.flush();

            String response = reader.readLine();
            System.out.println(server.toString() + ": " + response);

            writer.close();
            reader.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
