package com.othr.vs.training.parallelchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputListener implements Runnable {

    private final Socket socket;

    public InputListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String input;
            do {
                input = reader.readLine();
                System.out.println(input);
            } while (input != null && !input.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
