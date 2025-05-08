package com.helpbotfx.helpbotfx.DAOs;

import com.helpbotfx.helpbotfx.database.DatabaseInitializer;
import com.helpbotfx.helpbotfx.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User connecterUser(String nom, String password) {
        String sql = "SELECT * FROM users WHERE nom = ? AND password = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Utilisateur trouvé, retourner l'objet
                return new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
        }

        return null; // Aucun utilisateur correspondant
    }


    public void ajouterUser(User utilisateur) {
        String sql = "INSERT INTO users(nom, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPassword());

            pstmt.executeUpdate();
            System.out.println("✅ Utilisateur ajouté !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur d'ajout utilisateur : " + e.getMessage());
        }
    }
}
