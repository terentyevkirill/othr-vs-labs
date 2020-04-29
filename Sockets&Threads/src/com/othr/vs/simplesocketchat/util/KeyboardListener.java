package com.othr.vs.simplesocketchat.util;

import com.othr.vs.simplesocketchat.server.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class KeyboardListener implements Runnable {
    private Socket socket;

    public KeyboardListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // listen to the keyboard, send in socket
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            Scanner scanner = new Scanner(System.in);
            String input;

            do {    // blocking
                input = scanner.nextLine();
                writer.println(input);
                writer.flush();
            } while (input != null && !input.equalsIgnoreCase(Server.END_STRING));

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
