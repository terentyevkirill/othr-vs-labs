package com.othr.vs.chatsystem.server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// passive class
public class Dispatcher {
    private final Set<ClientEndpoint> clients = new HashSet<>();
    private final Lock monitor = new ReentrantLock();

    public void addClient(ClientEndpoint client) {
        monitor.lock();
        dispatchAdminMessage(client.username + " has entered the chat. Welcome!");
        clients.add(client);
        monitor.unlock();
    }

    public Set<ClientEndpoint> getClients() {
        return clients;
    }

    public void removeClient(ClientEndpoint client) {
        monitor.lock();
        dispatchAdminMessage(client.username + " has left the chat. Goodbye!");
        clients.remove(client);
        monitor.unlock();
    }

    public void dispatchAdminMessage(String msg) {
        monitor.lock();
        for (ClientEndpoint to : clients) {
            to.writer.println("ADMN#" + msg);
            to.writer.flush();
        }
        monitor.unlock();
    }

    public void dispatchMessage(ClientEndpoint from, String msg) {
        monitor.lock();
        for (ClientEndpoint to : clients) {
            if (!to.equals(from)) {
                to.writer.println("SHOW#" + from.username + "#" + msg);
                to.writer.flush();
            }
        }
        monitor.unlock();
    }


}
