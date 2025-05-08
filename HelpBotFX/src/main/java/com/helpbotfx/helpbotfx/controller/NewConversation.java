package com.helpbotfx.helpbotfx.controller;

import com.helpbotfx.Main;
import com.helpbotfx.helpbotfx.DAOs.ConversationDAO;
import com.helpbotfx.helpbotfx.database.SessionConversation;
import com.helpbotfx.helpbotfx.model.Conversation;
import com.helpbotfx.helpbotfx.DAOs.MessageDAO;
import com.helpbotfx.helpbotfx.database.Session;
import com.helpbotfx.helpbotfx.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author
 **/
public class NewConversation implements Initializable {
    Main m = new Main();
    User user = Session.getUtilisateurConnecte();
    final ConversationDAO conversationDAO = new ConversationDAO();
    final MessageDAO messageDAO = new MessageDAO();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Conversation> conversations = conversationDAO.getByUtilisateur(user);
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
                        try {
                            m.changeScene("/com/helpbotfx/conversation.fxml");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
    @FXML
    private ListView<Conversation> historique;
    @FXML
    private TextField newQuestion;
    @FXML
    private Text currentHistorique;
    @FXML
    public void newQuestion() throws IOException {
        if(newQuestion.getText().isEmpty()){
            currentHistorique.setText("poser question!");
        }else {
//            ajouter et lancer nouveau conversation pour user
            String localDateTime = LocalDateTime.now().toString();
            Conversation conversation = new Conversation(localDateTime, StatutConversation.EN_COURS.toString(), user);
            if(conversationDAO.ajouterConversation(conversation)){
                Message message = new Message(newQuestion.getText(), AuteurMessage.USER.toString(), localDateTime, conversation);
                if (messageDAO.ajouterMessage(message)){
                    conversation.ajouterMessage(message);
                    user.ajouterConversation(conversation);
                    SessionConversation.setConversationActuelle(conversation);
                    m.changeScene("/com/helpbotfx/conversation.fxml");
                } else {
                    currentHistorique.setText("Message n'ai pas ajoutée sur conversation !");
                }
            }else {
                currentHistorique.setText("Conversation n'ai pas ajoutée !");
            }
        }
    }
}
