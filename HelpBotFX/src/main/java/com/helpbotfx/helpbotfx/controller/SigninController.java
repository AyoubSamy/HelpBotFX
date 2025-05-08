package com.helpbotfx.helpbotfx.controller;

import com.helpbotfx.Main;
import com.helpbotfx.helpbotfx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.helpbotfx.helpbotfx.DAOs.UserDAO;
import com.helpbotfx.helpbotfx.model.User;

import java.io.IOException;

/**
 * @author
 **/
public class SigninController {
    @FXML
    private Button signin;
    @FXML
    private Label incorrectSignup;
    @FXML
    protected TextField nom;
    @FXML
    protected TextField email;
    @FXML
    protected PasswordField password1;
    @FXML
    private PasswordField password2;

    @FXML
    public void userSignin() throws IOException {
        if(nom.getText().isEmpty() || email.getText().isEmpty() || password1.getText().isEmpty() || password2.getText().isEmpty()) {
            incorrectSignup.setText("Veuillez saisir vos données.");
        } else if(password1.getText().equals(password2.getText())){
                String username = nom.getText();
                String mail = email.getText();
                String mdp = password1.getText();

                User utilisateur = new User(username, mail, mdp);
                UserDAO userDAO = new UserDAO();
                userDAO.ajouterUser(utilisateur);

                Main m = new Main();
                m.changeScene("/com/helpbotfx/login.fxml");
            }
         else {
            incorrectSignup.setText("Les deux passwords doivent être égaux!");
        }
    }

    @FXML
    public void loginView() throws IOException {
        Main m = new Main();
        m.changeScene("/com/helpbotfx/login.fxml");
    }
}
