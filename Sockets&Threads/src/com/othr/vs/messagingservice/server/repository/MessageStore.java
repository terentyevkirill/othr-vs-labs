package com.othr.vs.messagingservice.server.repository;

import com.othr.vs.messagingservice.server.entity.Message;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageStore {
    private final Map<String, List<Message>> messages = new HashMap<>();
    private final Lock monitor = new ReentrantLock();

    public String addUser(String username) {
        try {
            monitor.lock();
            if (messages.containsKey(username)) {
                return null;   // duplicate username, user not added
            } else {
                messages.put(username, new ArrayList<>());
                return username;    // new user added
            }
        } finally {
            monitor.unlock();
        }
    }

    public Message addMessage(String from, String to, String text) {
        Message message = null;
        try {
            monitor.lock();
            if (messages.containsKey(from) && messages.containsKey(to)) {
                message = new Message(from, to, text);
                messages.get(to).add(message);
            }
            return message;
        } finally {
            monitor.unlock();
        }

    }

    public Set<String> getUsers() {
        try {
            monitor.lock();
            return messages.keySet();
        } finally {
            monitor.unlock();
        }
    }

    public List<Message> getNewMessages(String username) {
        try {
            monitor.lock();
            if (messages.containsKey(username)) {
                List<Message> newMessages = messages.get(username);
                messages.replace(username, new ArrayList<>());  // delete read messages
                return newMessages;
            } else {
                return null; // asked for unregistered user's messages
            }
        } finally {
            monitor.unlock();
        }
    }
}
