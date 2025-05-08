package com.helpbotfx.helpbotfx.model;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionAnalysee {
    private int id;
    private String questionOriginale;
    private List<String> motsCles;
    private LocalDateTime dateAnalyse;
    private Message message;
    private Reponse reponse;

    public QuestionAnalysee(int id, String questionOriginale, List<String> motsCles, LocalDateTime dateAnalyse, Message message) {
        this.id = id;
        this.questionOriginale = questionOriginale;
        this.motsCles = motsCles;
        this.dateAnalyse = dateAnalyse;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionOriginale() {
        return questionOriginale;
    }

    public void setQuestionOriginale(String questionOriginale) {
        this.questionOriginale = questionOriginale;
    }

    public List<String> getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(List<String> motsCles) {
        this.motsCles = motsCles;
    }

    public LocalDateTime getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(LocalDateTime dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }
}
