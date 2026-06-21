package com.Blood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String sender;      // e.g. "admin" or session ID / user email
    private String receiver;    // e.g. "admin" or session ID / user email
    private String message;
    private String senderName;
    private long timestamp;

    public ChatMessage() {
        super();
    }

    public ChatMessage(int id, String sender, String receiver, String message, String senderName, long timestamp) {
        super();
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessage [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", message=" + message
                + ", senderName=" + senderName + ", timestamp=" + timestamp + "]";
    }
}
