package com.helpbotfx.helpbotfx.DAOs;

import com.helpbotfx.helpbotfx.model.Conversation;
import com.helpbotfx.helpbotfx.model.Message;
import com.helpbotfx.helpbotfx.database.DatabaseInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public boolean ajouterMessage(Message msg) {
        String sql = "INSERT INTO messages(conversationId, contenu, auteur, horodatage) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, msg.getConversation().getId());
            stmt.setString(2, msg.getContenu());
            stmt.setString(3, msg.getAuteur());
            stmt.setString(4, msg.getHorodatage());

            int lignes = stmt.executeUpdate();
            if (lignes > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    msg.setId(rs.getInt(1));
                }
                System.out.println("Ajout du message : " + msg.getContenu());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout message : " + e.getMessage());
        }
        return false;
    }

    public List<Message> getByConversation(Conversation conversation) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE conversationId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, conversation.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setContenu(rs.getString("contenu"));
                m.setAuteur(rs.getString("auteur"));
                m.setHorodatage(rs.getString("horodatage"));
                m.setConversation(conversation);
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
