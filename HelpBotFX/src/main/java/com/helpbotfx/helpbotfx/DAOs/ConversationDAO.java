package com.helpbotfx.helpbotfx.DAOs;

import com.helpbotfx.helpbotfx.database.DatabaseInitializer;
import com.helpbotfx.helpbotfx.model.Conversation;
import com.helpbotfx.helpbotfx.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 **/
public class ConversationDAO{
    public boolean ajouterConversation(Conversation conv) {
        String sql = "INSERT INTO conversations(utilisateurId, dateDebut, statut) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, conv.getUser().getId());
            stmt.setString(2, conv.getDateDebut().toString());
            stmt.setString(3, conv.getStatut().toString());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    conv.setId(rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout conversation : " + e.getMessage());
        }
        return false;
    }

    public List<Conversation> getByUtilisateur(User utilisateur) {
        List<Conversation> list = new ArrayList<>();
        String sql = "SELECT * FROM conversations WHERE utilisateurId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utilisateur.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conversation c = new Conversation();
                c.setId(rs.getInt("id"));
                c.setDateDebut(rs.getString("dateDebut"));
                c.setStatut(rs.getString("statut"));
                c.setUser(utilisateur);
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

