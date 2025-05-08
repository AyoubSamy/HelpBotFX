module com.helpbotfx.helpbotfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;


    opens com.helpbotfx.helpbotfx to javafx.fxml;
    exports com.helpbotfx.helpbotfx;
    exports com.helpbotfx;
    opens com.helpbotfx to javafx.fxml;
    exports com.helpbotfx.helpbotfx.controller;
    opens com.helpbotfx.helpbotfx.controller to javafx.fxml;
    exports com.helpbotfx.helpbotfx.database;
    opens com.helpbotfx.helpbotfx.database to javafx.fxml;
}