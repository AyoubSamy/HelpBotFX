package com.helpbotfx.helpbotfx.controller;

import com.helpbotfx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * @author
 **/
public class Go {
    @FXML
    private Button go;
    @FXML
    public void go() throws IOException {
        Main m = new Main();
        m.changeScene("/com/helpbotfx/login.fxml");
    }
}
