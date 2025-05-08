package com.helpbotfx.helpbotfx.model;

public class Feedback {
    private int id;
    private int note; // par exemple de 1 à 5
    private String commentaire;
    private Message message;

    public Feedback(int id, int note, String commentaire, Message message) {
        this.id = id;
        this.note = note;
        this.commentaire = commentaire;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
