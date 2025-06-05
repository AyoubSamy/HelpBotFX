package com.helpbotfx.helpbotfx.controller;

import com.helpbotfx.Main;
import com.helpbotfx.helpbotfx.DAOs.ConversationDAO;
import com.helpbotfx.helpbotfx.DAOs.FaqDAO;
import com.helpbotfx.helpbotfx.DAOs.MessageDAO;
import com.helpbotfx.helpbotfx.database.Session;
import com.helpbotfx.helpbotfx.database.SessionConversation;
import com.helpbotfx.helpbotfx.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.application.Platform;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.helpbotfx.helpbotfx.Service.ChatbotClient;

/**
 * @author
 **/
public class ConversationController implements Initializable {

    private Conversation conversationActuelle=SessionConversation.getConversationActuelle();
    User user = Session.getUtilisateurConnecte();
    final ConversationDAO conversationDAO = new ConversationDAO();
    final MessageDAO messageDAO = new MessageDAO();
    private final ChatbotClient chatbotClient = new ChatbotClient();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Conversation> conversations = conversationDAO.getByUtilisateur(user);
        afficherMessages(conversationActuelle);
        currectConversation.getItems();
        for (Conversation conv : conversations) {
            List<Message> messages = messageDAO.getByConversation(conv);
            messages.sort(Comparator.comparing(Message::getHorodatage));
            conv.setMessages(messages);
        }
        // historique affichera automatiquement dans la liste des éléments le texte généré par toString()
        historique.setItems(FXCollections.observableArrayList(conversations));
        historique.getItems();

        historique.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldConv, selectedConv) -> {
                    if (selectedConv != null) {
                        SessionConversation.setConversationActuelle(selectedConv);
                        conversationActuelle = SessionConversation.getConversationActuelle();
                        afficherMessages(conversationActuelle);
                    }
                }
        );
    }

    private void afficherMessages(Conversation conversationActuelle) {
        // Charger les messages liés à cette conversation
        List<Message> messages = messageDAO.getByConversation(conversationActuelle);

        // Trier par date/heure si besoin
        messages.sort(Comparator.comparing(Message::getHorodatage));

        // Préparer les chaînes à afficher
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Message m : messages) {
            String line = String.format("[%s] %s : %s",
                    m.getHorodatage().substring(11, 19),    // heure
                    m.getAuteur(),                          // UTILISATEUR ou CHATBOT
                    m.getContenu().replace("\n", " ")
            );
            items.add(line);
        }
        currectConversation.setItems(items);
        System.out.println("Messages chargés pour la conversation : " + conversationActuelle.getId());
        System.out.println("avant le lançement de la methode New Question");   
    }
  
    @FXML
    private ListView<Conversation> historique;
    @FXML
    private ListView<String> currectConversation;
    @FXML
    private TextField newQuestion;
    @FXML
    private Button noveauChat;
    @FXML
    private Text exp;
    
    @FXML
    private void handleNewQuestion() throws IOException {
        try {
            System.out.println("Méthode newQuestion appelée.");
            String question = newQuestion.getText().trim();
            if (question.isEmpty()) {
                exp.setText("Posez une question !");
                return;
            }

            System.out.println("Question posée : " + question);
            String localDateTime = LocalDateTime.now().toString();
            Message userMsg = new Message(question, AuteurMessage.USER.toString(), localDateTime, conversationActuelle);
            
            if (messageDAO.ajouterMessage(userMsg)) {
                conversationActuelle.ajouterMessage(userMsg);
                afficherMessages(conversationActuelle);
                newQuestion.clear();

                // Appel REST au chatbot dans un thread séparé
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return chatbotClient.askChatbot(question);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> exp.setText("Erreur de communication avec le chatbot"));
                        return null;
                    }
                }).thenAccept(reponse -> {
                    if (reponse != null) {
                        Platform.runLater(() -> {
                            try {
                                Message botMsg = new Message(reponse, AuteurMessage.CHATBOT.toString(), 
                                    LocalDateTime.now().toString(), conversationActuelle);
                                
                                if (messageDAO.ajouterMessage(botMsg)) {
                                    conversationActuelle.ajouterMessage(botMsg);
                                    afficherMessages(conversationActuelle);
                                } else {
                                    exp.setText("Le message du chatbot n'a pas pu être ajouté !");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                exp.setText("Erreur lors du traitement de la réponse du chatbot");
                            }
                        });
                    }
                });
            } else {
                exp.setText("Le message utilisateur n'a pas pu être ajouté !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            exp.setText("Une erreur est survenue");
        }
    }

    @FXML void nouveauChat() throws IOException {
        Main m = new Main();
        m.changeScene("/com/helpbotfx/new-conversation.fxml");
    }
}
