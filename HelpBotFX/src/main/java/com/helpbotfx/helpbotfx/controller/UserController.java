package com.helpbotfx.helpbotfx.controller;

import com.helpbotfx.helpbotfx.DAOs.UserDAO;
import com.helpbotfx.helpbotfx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {

    @FXML
    private TextField nomField, emailField, passwordField;

    @FXML
    private TextArea affichageArea;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void ajouterUser() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String mdp = passwordField.getText();

        User utilisateur = new User(nom, email, mdp);
        userDAO.ajouterUser(utilisateur);
    }
}
