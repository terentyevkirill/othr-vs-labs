package com.othr.vs.messagingservice.server.service;

import com.othr.vs.messagingservice.server.entity.Message;
import com.othr.vs.messagingservice.server.repository.MessageStore;

import static com.othr.vs.messagingservice.server.service.MessagingService.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Set;

// Active class
public class ClientRequest implements Runnable {
    private final MessageStore store;
    private final Socket socket;    // client socket

    public ClientRequest(Socket socket, MessageStore store) {
        this.store = store;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(out);

            writer.println("This Server uses protocol:");
            writer.println("REG");
            writer.println("REGAlex");
            writer.println("SNDAlex#Tom#Hi Tommy, how are you today?");
            writer.println("RCVTom");
            writer.flush();

            String request = reader.readLine();
            String command = request.substring(0, 3).trim();  // REG, SND, RCV
            String paramString = request.substring(3).trim();
            String[] params = paramString.split("#");
            String user = params[0].trim();
            if (user.isEmpty()) {
                Set<String> users = store.getUsers();
                if (users.isEmpty()) {
                    writer.println("No registered users yet.");
                } else {
                    writer.println("Registered users:");
                    users.forEach(writer::println);
                }
                writer.flush();
            } else {
                switch (command) {
                    case REGISTER_CMD:
                        if (store.addUser(user) != null) {
                            writer.println("OK " + user);
                        } else {
                            writer.println("<ERROR> Could not add user. User " + user + " already exists!");
                        }
                        writer.flush();
                        break;
                    case SEND_CMD:
                        String to = params[1].trim();
                        String text = params[2].trim();
                        Message message = store.addMessage(user, to, text);
                        if (message != null) {
                            writer.println("OK " + message);
                        } else {
                            writer.println("<ERROR> Could not send message. Check sender and receiver usernames!");
                        }
                        writer.flush();
                        break;
                    case RECEIVE_CMD:
                        List<Message> messages = store.getNewMessages(user);
                        if (messages != null) {
                            writer.println("OK " + messages.size());
                            messages.forEach(writer::println);
                        } else {
                            writer.println("<ERROR> Could not receive messages. User " + user + " not found!");
                        }
                        writer.flush();
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
