package com.othr.vs.simplesocketchat.client;

import com.othr.vs.simplesocketchat.server.Server;
import com.othr.vs.simplesocketchat.util.InputStreamListener;
import com.othr.vs.simplesocketchat.util.KeyboardListener;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket server = new Socket("localhost", Server.PORT);
            Runnable keyboardListener = new KeyboardListener(server);
            Runnable inputStreamListener = new InputStreamListener(server);
            new Thread(keyboardListener).start();
            new Thread(inputStreamListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
