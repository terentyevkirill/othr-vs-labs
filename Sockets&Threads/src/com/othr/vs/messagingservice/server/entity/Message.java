package com.othr.vs.messagingservice.server.entity;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Message {
    private final String id;
    private final String from;
    private final String to;
    private final String text;
    private final Date timestamp;

    public Message(String from, String to, String text) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = new Date();
        this.from = from;
        this.to = to;
        this.text = text;
    }

    @Override
    public String toString() {
        return id + "#" +
                from + "#" +
                to + "#" +
                text + "#" +
                timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
