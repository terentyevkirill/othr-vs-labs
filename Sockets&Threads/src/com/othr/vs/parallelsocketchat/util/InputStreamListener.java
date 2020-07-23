package com.othr.vs.parallelsocketchat.util;

import com.othr.vs.parallelsocketchat.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputStreamListener implements Runnable {
    private final Socket socket;

    public InputStreamListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String input;
            do {    // blocking
                input = reader.readLine();
                System.out.println(input);
            } while (input != null && !input.equalsIgnoreCase(Server.END_STRING));

            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
