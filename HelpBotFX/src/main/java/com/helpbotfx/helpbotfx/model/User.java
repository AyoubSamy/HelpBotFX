package com.helpbotfx.helpbotfx.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String nom;
    private String email;
    private String password;

    // Relations
    private List<Ticket> tickets;
    private List<Conversation> conversations;

    public User() {
        tickets = new ArrayList<>();
        conversations = new ArrayList<>();
    }

    public User(String nom, String email, String motDePasse) {
        this();
        this.nom = nom;
        this.email = email;
        this.password = motDePasse;
    }

    public User(int id, String nom, String email, String motDePasse) {
        this(nom, email, motDePasse);
        this.id = id;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String motDePasse) { this.password = motDePasse; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public List<Conversation> getConversations() {return conversations;}

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    // Optionnel : ajouter un ticket ou message
    public void ajouterTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void ajouterConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
