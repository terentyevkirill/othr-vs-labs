package com.othr.vs.training.parallelchat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class KeyboardListener implements Runnable {

    private final Socket socket;

    public KeyboardListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Scanner scan = new Scanner(System.in);
             OutputStream out = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(out)) {
            String input;
            do {
                input = scan.nextLine();
                writer.println(input);
                writer.flush();
            } while (input != null && !input.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
