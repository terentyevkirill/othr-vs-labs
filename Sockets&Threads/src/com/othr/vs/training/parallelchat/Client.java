package com.othr.vs.training.parallelchat;

import java.io.IOException;
import java.net.Socket;

import static com.othr.vs.training.parallelchat.Server.*;

public class Client {

    public static void main(String[] args) {
        try {
            Socket server = new Socket(HOST, PORT);
            InputListener inputListener = new InputListener(server);
            KeyboardListener keyboardListener = new KeyboardListener(server);
            new Thread(inputListener).start();
            new Thread(keyboardListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
