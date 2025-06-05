package com.helpbotfx.helpbotfx.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ChatbotClient {
    private static final String BASE_URL = "http://localhost:8080/chat/ask"; //acceder au API du chatbot et le prompter depuis cet url

    public String askChatbot(String question) {
        try {
            String encodedQuestion = URLEncoder.encode(question, StandardCharsets.UTF_8);
            String fullUrl = BASE_URL + "?question=" + encodedQuestion; //assurer l'url complet en ajoutant la question
            System.out.println("URL envoyée : " + fullUrl); // Log de l'URL complète
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());//envoie d'une requete Get aux api modele et recuperation de la reponse
            System.out.println("Réponse du chatbot : " + response.body()); // Log de la réponse
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur de communication avec le chatbot.";
        }
    }
}
