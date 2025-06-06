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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
        currectConversation.setCellFactory(listView -> new javafx.scene.control.ListCell<>() {
            private final javafx.scene.control.Label label = new javafx.scene.control.Label();
            {
                label.setWrapText(true);
                label.setStyle("-fx-padding: 5px;"); // optionnel : ajoute du padding
                // Option 1 : largeur fixe légèrement inférieure à celle du ListView
                label.setMaxWidth(310);
                // Option 2 : dynamique (mieux si tu redimensionnes)
                // label.maxWidthProperty().bind(currectConversation.widthProperty().subtract(20));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        });

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
                        exp.setText("");
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
    private void newQuestion() throws IOException {
        String question = newQuestion.getText().trim();
        if (question.isEmpty()) {
            exp.setText("Posez une question !");
        } else {
            String localDateTime = LocalDateTime.now().toString();
            Message userMsg = new Message(question, AuteurMessage.USER.toString(), localDateTime, conversationActuelle);
            if (messageDAO.ajouterMessage(userMsg)) {
                conversationActuelle.ajouterMessage(userMsg);
                afficherMessages(conversationActuelle);
                newQuestion.clear();

                // ✅ Appel REST au chatbot distant (Spring AI)
                String reponse = chatbotClient.askChatbot(question);

                Message botMsg = new Message(reponse, AuteurMessage.CHATBOT.toString(), LocalDateTime.now().toString(), conversationActuelle);
                if (messageDAO.ajouterMessage(botMsg)) {
                    conversationActuelle.ajouterMessage(botMsg);
                    afficherMessages(conversationActuelle);
                }
                exp.setText("");
            } else {
                exp.setText("Le message n’a pas pu être ajouté !");
            }
        }
    }

    @FXML void nouveauChat() throws IOException {
        Main m = new Main();
        m.changeScene("/com/helpbotfx/new-conversation.fxml");
    }

    @FXML private ImageView like;
    @FXML private ImageView deslike;
    @FXML private Button signaler;
    @FXML
    public void handleLike(MouseEvent event) {
        exp.setText("Merci pour votre FeedBack");
    }
    @FXML
    public void handleDeslike(MouseEvent event) {
        exp.setText("Merci pour votre FeedBack");
    }
    @FXML
    public void signaler(){
        exp.setText("Un agent vous contactera bientôt pour le suivi.");
    }
}
