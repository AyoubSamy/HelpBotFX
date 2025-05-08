package com.helpbotfx.helpbotfx.database;

import com.helpbotfx.helpbotfx.model.User;

public class Session {
    private static User utilisateurConnecte;

    public static void setUtilisateurConnecte(User u) {
        utilisateurConnecte = u;
    }

    public static User getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static void clear() {
        utilisateurConnecte = null;
    }
}
