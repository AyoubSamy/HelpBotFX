package com.helpbotfx.helpbotfx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int id;
    private String dateDebut;
    private String statut;
    private User user;
    private List<Message> messages;

    public Conversation(){
        messages = new ArrayList<>();
    }
    public Conversation(String dateDebut, String statut, User user) {
        this();
        this.dateDebut = dateDebut;
        this.statut = statut;
        this.user = user;
    }
    public Conversation(int id, String dateDebut, String statut, User user) {
        this();
        this.dateDebut = dateDebut;
        this.id = id;
        this.statut = statut;
        this.user = user;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void ajouterMessage(Message m){
        messages.add(m);
    }
    @Override
    public String toString() {
        if (messages != null && !messages.isEmpty()) {
            Message first = messages.get(0);
            return String.format("Conv #%d (%s) : %s", id, first.getHorodatage().substring(0, 10), first.getContenu());
        }
        return "Conv #" + id + " (vide)";
    }
}
