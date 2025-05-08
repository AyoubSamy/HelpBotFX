package com.helpbotfx.helpbotfx.model;

public class Reponse {
    private int id;
    private String contenu;
    private String source;
    private QuestionAnalysee questionAnalysee;

    public Reponse(int id, String contenu, String source, QuestionAnalysee questionAnalysee) {
        this.id = id;
        this.contenu = contenu;
        this.source = source;
        this.questionAnalysee = questionAnalysee;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public QuestionAnalysee getQuestionAnalysee() {
        return questionAnalysee;
    }

    public void setQuestionAnalysee(QuestionAnalysee questionAnalysee) {
        this.questionAnalysee = questionAnalysee;
    }
}

