package com.helpbotfx.helpbotfx.model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private LocalDateTime dateCreation;
    private StatutTicket statut;
    private User user;
    private Conversation conversation;

    public Ticket(int id, LocalDateTime dateCreation, StatutTicket statut, User user, Conversation conversation) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.user = user;
        this.conversation = conversation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutTicket getStatut() {
        return statut;
    }

    public void setStatut(StatutTicket statut) {
        this.statut = statut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
