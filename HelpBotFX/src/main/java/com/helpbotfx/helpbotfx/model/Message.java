package com.helpbotfx.helpbotfx.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String contenu;
    private String auteur;
    private String horodatage;
    private Conversation conversation;
    private Feedback feedback;
    private QuestionAnalysee questionAnalysee;

    public Message(int id, String contenu, String auteur, String horodatage, Conversation conversation) {
        this.id = id;
        this.contenu = contenu;
        this.auteur = auteur;
        this.horodatage = horodatage;
        this.conversation = conversation;
    }
    public Message(String contenu, String auteur, String horodatage, Conversation conversation) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.horodatage = horodatage;
        this.conversation = conversation;
    }
    public Message(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getHorodatage() {
        return horodatage;
    }

    public void setHorodatage(String horodatage) {
        this.horodatage = horodatage;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public QuestionAnalysee getQuestionAnalysee() {
        return questionAnalysee;
    }

    public void setQuestionAnalysee(QuestionAnalysee questionAnalysee) {
        this.questionAnalysee = questionAnalysee;
    }
}
