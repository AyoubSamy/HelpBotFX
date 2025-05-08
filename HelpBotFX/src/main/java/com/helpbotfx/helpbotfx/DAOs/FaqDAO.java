package com.helpbotfx.helpbotfx.DAOs;

import com.helpbotfx.helpbotfx.database.DatabaseInitializer;
import com.helpbotfx.helpbotfx.model.Faq;

import java.sql.*;
import java.util.*;

public class FaqDAO {

    public boolean ajouter(Faq faq) {
        String sql = "INSERT INTO faq (question, reponse, mots_cles) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, faq.getQuestion());
            stmt.setString(2, faq.getReponse());
            stmt.setString(3, faq.getMotsCles());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public List<Faq> getAll() {
//        List<Faq> faqs = new ArrayList<>();
//        String sql = "SELECT * FROM faq";
//        try (Connection conn = DatabaseInitializer.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Faq faq = new Faq(
//                        rs.getInt("id"),
//                        rs.getString("question"),
//                        rs.getString("reponse"),
//                        rs.getString("mots_cles")
//                );
//                faqs.add(faq);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return faqs;
//    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM faq";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Optional<Faq> rechercherParPertinence(String requete) {
        String sql = "SELECT * FROM faq";
        Map<Faq, Integer> pertinenceMap = new HashMap<>();
        String[] motsQuestion = requete.toLowerCase().split("\\s+");

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String motsClefs = rs.getString("mots_cles");
                String[] motsFaq = motsClefs.toLowerCase().split(";");

                // Compter le nombre de mots-clés en commun
                int score = 0;
                for (String motFaq : motsFaq) {
                    for (String motQ : motsQuestion) {
                        if (motFaq.trim().equals(motQ.trim())) {
                            score++;
                        }
                    }
                }

                if (score > 0) {
                    Faq faq = new Faq(
                            rs.getInt("id"),
                            rs.getString("question"),
                            rs.getString("reponse"),
                            motsClefs
                    );
                    pertinenceMap.put(faq, score);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourner la FAQ avec le plus de mots en commun
        return pertinenceMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
