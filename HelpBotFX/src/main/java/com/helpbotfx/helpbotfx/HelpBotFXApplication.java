package com.helpbotfx.helpbotfx;


import com.helpbotfx.helpbotfx.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelpBotFXApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseInitializer.initializeDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(HelpBotFXApplication.class.getResource("/com/helpbotfx/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("HelpBotFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}