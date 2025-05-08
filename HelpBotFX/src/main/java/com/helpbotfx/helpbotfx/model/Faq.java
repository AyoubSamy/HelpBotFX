package com.helpbotfx.helpbotfx.model;

public class Faq {
    private int id;
    private String question;
    private String reponse;
    private String motsCles;

    public Faq() {}

    public Faq(String question, String reponse, String motsCles) {
        this.question = question;
        this.reponse = reponse;
        this.motsCles = motsCles;
    }

    public Faq(int id, String question, String reponse, String motsCles) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.motsCles = motsCles;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse; }

    public String getMotsCles() { return motsCles; }
    public void setMotsCles(String motsCles) { this.motsCles = motsCles; }

    @Override
    public String toString() {
        return question;
    }
}
