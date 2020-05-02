package com.othr.vs.chatsystem.server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Set;

// active class
public class ClientEndpoint implements Runnable {
    protected String username;
    protected PrintWriter writer;
    protected BufferedReader reader;
    private final Socket socket;
    private final Dispatcher dispatcher;
    private boolean isRunning = true;

    public ClientEndpoint(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // wait until client sends OPEN#
            String openMsg = reader.readLine();
            if (openMsg == null) {
                return;
            }
            if (!openMsg.startsWith("OPEN#")) {
                writer.println("ADMN#Error, client has to send OPEN followed by HASH and USERNAME as first message. Socket will be closed by server.");
                writer.flush();
                socket.close();
                return;
            }
            Set<ClientEndpoint> online = dispatcher.getClients();
            writer.print("ADMN#" + online.size() + " users online: ");
            online.forEach(c -> writer.print(c.username+"; "));
            writer.println();
            writer.flush();
            String[] openMsgParams = openMsg.split("#");
            username = openMsgParams[1].trim();
            dispatcher.addClient(this);
            writer.println("ADMN#Welcome " + username + "!");
            writer.flush();
            System.out.println("LOG:New Client for " + this.username + " added");

            // after OPEN wait for client's messages
            while (isRunning) {
                String received = reader.readLine();
                System.out.println("LOG:New String received: " + received);
                if (received == null || received.startsWith("EXIT")) {
                    isRunning = false;
                    dispatcher.removeClient(this);
                } else if (received.startsWith("PUBL#")) {
                    String[] msgParams = received.split("#");
                    dispatcher.dispatchMessage(this, msgParams[1]);
                } else {
                    writer.println("ADMN#Error, wrong command: " + received);
                    writer.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dispatcher.removeClient(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientEndpoint)) return false;

        ClientEndpoint that = (ClientEndpoint) o;

        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
