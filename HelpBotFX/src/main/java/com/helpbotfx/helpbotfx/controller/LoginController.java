package com.helpbotfx.helpbotfx.controller;
import com.helpbotfx.Main;
import com.helpbotfx.helpbotfx.DAOs.UserDAO;
import com.helpbotfx.helpbotfx.database.Session;
import com.helpbotfx.helpbotfx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
public class LoginController {

    public LoginController() {

    }

    @FXML
    private Button login;
    @FXML
    private Label incorrectLogin;
    @FXML
    private TextField nom;
    @FXML
    private PasswordField password;

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        UserDAO userDAO  =new UserDAO();
        User utilisateur = userDAO.connecterUser(nom.getText(), password.getText());

        if(nom.getText().isEmpty() && password.getText().isEmpty()) {
            incorrectLogin.setText("Veuillez saisir vos données.");
        }else {
            if (utilisateur != null) {
                Session.setUtilisateurConnecte(utilisateur);
                System.out.println("✅ Connexion réussie ! Bienvenue " + utilisateur.getNom());
                m.changeScene("/com/helpbotfx/new-conversation.fxml");
            } else {
                incorrectLogin.setText("username ou password incorrect!");
            }
        }
    }

    @FXML
    public void signView() throws IOException {
        Main m = new Main();
        m.changeScene("/com/helpbotfx/signup.fxml");
    }
}