package com.othr.vs.parallelsocketchat.client;

import com.othr.vs.parallelsocketchat.server.Server;
import com.othr.vs.parallelsocketchat.util.InputStreamListener;
import com.othr.vs.parallelsocketchat.util.KeyboardListener;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            // do not pack in try with resources, otherwise socket is closed when used in runnables !
            Socket server = new Socket(Server.HOST, Server.PORT);
            Runnable keyboardListener = new KeyboardListener(server);
            Runnable inputStreamListener = new InputStreamListener(server);
            new Thread(keyboardListener).start();
            new Thread(inputStreamListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
