package com.helpbotfx.helpbotfx.database;

import com.helpbotfx.helpbotfx.model.Conversation;

public class SessionConversation {
    private static Conversation conversationActuelle;

    public static void setConversationActuelle(Conversation c) {
        conversationActuelle = c;
    }

    public static Conversation getConversationActuelle() {
        return conversationActuelle;
    }

    public static void clear() {
        conversationActuelle = null;
    }
}
