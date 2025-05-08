package com.helpbotfx.helpbotfx.database;

import com.helpbotfx.helpbotfx.DAOs.FaqDAO;
import com.helpbotfx.helpbotfx.model.Faq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:helpbotfx.db"; // fichier dans le dossier du projet

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    // Création des tables si elles n'existent pas
                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS users (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nom TEXT NOT NULL,
                            email TEXT NOT NULL,
                            password TEXT NOT NULL
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS conversations (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            dateDebut TEXT,
                            statut TEXT,
                            utilisateurId INTEGER,
                            FOREIGN KEY(utilisateurId) REFERENCES users(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS messages (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            contenu TEXT,
                            auteur TEXT,
                            horodatage TEXT,
                            conversationId INTEGER,
                            FOREIGN KEY(conversationId) REFERENCES conversations(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS feedbacks (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            note INTEGER,
                            commentaire TEXT,
                            messageId INTEGER,
                            FOREIGN KEY(messageId) REFERENCES messages(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS questionsAnalysees (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            questionOriginale TEXT,
                            motsCles TEXT,
                            dateAnalyse TEXT,
                            messageId INTEGER,
                            FOREIGN KEY(messageId) REFERENCES messages(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS reponses (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            contenu TEXT,
                            source TEXT,
                            questionId INTEGER,
                            FOREIGN KEY(questionId) REFERENCES questionsAnalysees(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS tickets (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            dateCreation TEXT,
                            statut TEXT,
                            priorite TEXT,
                            utilisateurId INTEGER,
                            conversationId INTEGER,
                            FOREIGN KEY(utilisateurId) REFERENCES users(id),
                            FOREIGN KEY(conversationId) REFERENCES conversations(id)
                        );
                    """);

                    stmt.execute("""
                        CREATE TABLE IF NOT EXISTS faq (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        question TEXT NOT NULL,
                        reponse TEXT NOT NULL,
                        mots_cles TEXT
                    );
                    """);

                    System.out.println("✅ Base de données initialisée avec succès.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'initialisation de la base de données : " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialiserDonnees() {
        initialiserFaqs();
    }

    private static void initialiserFaqs() {
        FaqDAO faqDAO = new FaqDAO();

        if (faqDAO.count() == 0) {
            List<Faq> faqs = Arrays.asList(
                    new Faq("Pourquoi mon Wi-Fi se déconnecte sans arrêt ?", "Je pense que ton frére jeux avec routeur.", "wifi;deconnecte;signal"),
                    new Faq("Comment réinitialiser ma connexion Wi-Fi ?", "Tfi w3awd cha3al Wi-Fi, ou redémarrez le routeur.", "wifi;reinitialiser;connexion"),
                    new Faq("Pourquoi je ne trouve pas mon réseau Wi-Fi ?", "Wi-Fi wa9ila tafi et que le SSID est diffusé.", "wifi;invisible;reseau"),
                    new Faq("Le Wi-Fi fonctionne sur mon téléphone mais pas sur mon PC, pourquoi ?", "Yemken problema f carte réseau wla f paramètres dyal IP dyal l'PC.", "wifi;pc;connecte"),
                    new Faq("Mon Wi-Fi est lent, que faire ?", "Redémarré l-router, ghayr l-canal wla qrab mən l-box.", "wifi;lent;debit"),
                    new Faq("Erreur 'IP non valide', que faire ?", "Ḥawel tḥell o tʿawed renew l-adresse IP mn paramètres dyal réseau.", "wifi;ip;non;valide"),
                    new Faq("Comment oublier un réseau Wi-Fi ?", "Sir f Paramètres dyal Wi-Fi, ktar l-réseau o ḍɣet 3la 'Oublier'.", "wifi;oublier;reseau"),
                    new Faq("Pourquoi mon mot de passe Wi-Fi ne fonctionne pas ?", "Vérifiya bach katkteb l-mot de passe bikhir (majuscules/minuscules o caractères spéciaux).", "wifi;mot;de;passe;erreur"),
                    new Faq("Comment voir les réseaux Wi-Fi disponibles ?", "Chadd l-Wi-Fi o ḍɣet 3la l'icône dyal réseau bach tchouf la liste.", "wifi;disponible;voir"),
                    new Faq("Puis-je partager ma connexion Wi-Fi ?", "Eh, via Hotspot wla QR code mn paramètres.", "wifi;partager;connexion"),
                    new Faq("Comment réinitialiser mon mot de passe ?", "Ḍɣ 3la Mot de passe oublié f écran de connexion o ssuivé l-instructions.", "mot de passe;réinitialiser"),
                    new Faq("J'ai oublié mon mot de passe Windows, que faire ?", "Redémarré f mode sans échec o st3mel compte admin bach tbddlou.", "windows;mot de passe;oublié"),
                    new Faq("Mot de passe incorrect malgré la bonne saisie ?", "Chouf touche Verr Maj o clavier (disposition).", "mot de passe;incorrect;majuscule"),
                    new Faq("Je n'ai pas reçu l'e-mail de réinitialisation.", "Chouf spam dyalek wla 3awed b3ath l-mail.", "email;reinitialisation;non;recu"),
                    new Faq("Peut-on voir un mot de passe enregistré ?", "Ah, f paramètres dyal navigateur wla password manager.", "mot de passe;enregistré;voir"),
                    new Faq("Combien de fois puis-je tenter un mot de passe ?", "T dépend dyal service, mais évité tzid 3la 5 tentatives.", "tentative;mot de passe;limite"),
                    new Faq("Comment créer un mot de passe sécurisé ?", "St3mel 12 caractères minimum (maj, chiffres, symboles).", "mot de passe;fort;sécurisé"),
                    new Faq("Pourquoi mon mot de passe a expiré ?", "Kayn organisations kytḥtṭo durée dyal validité bach sécurité tkun 9wa.", "mot de passe;expiré"),
                    new Faq("Puis-je utiliser le même mot de passe partout ?", "Machi m7assan. St3mel password manager.", "mot de passe;réutiliser;risque"),
                    new Faq("Comment activer la double authentification ?", "Sir f paramètres dyal sécurité dyal compte dyalek.", "2fa;double;authentification;mot de passe"),
                    new Faq("Pourquoi mon imprimante ne répond pas ?", "Chouf la connexion USB wla réseau w 3awed chghol l'imprimante.", "imprimante;ne répond pas"),
                    new Faq("Comment ajouter une imprimante ?", "Sir l paramètres système > Périphériques > Imprimantes et scanners.", "ajouter;imprimante"),
                    new Faq("L'impression sort en noir et blanc, pourquoi ?", "Chouf paramètres d'impression, imken l'option couleur ghadi msdda.", "imprimante;noir;blanc"),
                    new Faq("Pourquoi mon imprimante affiche 'bourrage papier' ?", "Kherj l'papier li mtsals f dakhel w 3awed dir impression.", "imprimante;bourrage;papier"),
                    new Faq("Comment changer les cartouches ?", "7el l'capot supérieur dyal l'imprimante w suivé l'instructions f l'écran.", "imprimante;changer;cartouche"),
                    new Faq("Je reçois l'erreur 'pilote manquant'", "Téléchargi l'pilote mn site dyal l'fabricant.", "imprimante;pilote;manquant"),
                    new Faq("Comment imprimer en PDF ?", "Choisissez 'Khtar 'Imprimer' > 'Imprimante PDF' f logiciel dyalek.", "impression;pdf"),
                    new Faq("Pourquoi l'imprimante est hors ligne ?", "3awed chgholha w chouf les connexions réseau.", "imprimante;hors ligne"),
                    new Faq("Puis-je imprimer depuis mon téléphone ?", "Ah, b application mobile wla Google Cloud Print.", "impression;mobile"),
                    new Faq("Les feuilles sortent vides, que faire ?", "Chouf niveau d'encre w naddaf têtes d'impression.", "imprimante;feuille;vide"),
                    new Faq("Comment installer un logiciel sous Windows ?", "Téléchargi l'installeur, ft7h w suivé l'étapes.", "installation;logiciel;windows"),
                    new Faq("Puis-je installer des logiciels sans droits admin ?", "La, khassk tkun 3andk droits d'administrateur.", "installation;admin;droits"),
                    new Faq("Comment désinstaller un programme ?", "Sir l Panneau de configuration > Programmes > Désinstaller.", "désinstaller;programme"),
                    new Faq("Erreur 'fichier corrompu' à l'installation.", "3awed téléchargi l'fichier mn source officielle.", "erreur;installation;corrompu"),
                    new Faq("Pourquoi le logiciel ne s'ouvre pas ?", "Chouf compatibilité m3a système dyalek.", "logiciel;ne s'ouvre pas"),
                    new Faq("Comment mettre à jour un logiciel ?", "Ft7 l'logiciel w chérché option 'Mise à jour' wla téléchargi dernière version.", "mise à jour;logiciel"),
                    new Faq("Peut-on installer deux versions du même logiciel ?", "F ba3d l'fois Ah, mais ça dépend dyal logiciel.", "installer;deux versions"),
                    new Faq("Installation bloquée par antivirus, pourquoi ?", "Ba3d l'installeurs kayt7sbouhom faux positifs.", "antivirus;blocage;installation"),
                    new Faq("Comment installer un .exe téléchargé ?", "Double-clique 3la l'fichier w qbel les permissions.", "installation;exe;fichier"),
                    new Faq("Puis-je installer des logiciels depuis clé USB ?", "Ah, ila l'logiciel portable wla 3andk l'installeur.", "clé usb;installation"),
                    new Faq("Erreur 404 : que signifie-t-elle ?", "Page makhdamach, l'URL ghalta wla Page mssou7a.", "erreur;404;page"),
                    new Faq("Erreur 403 : accès refusé ?", "Makaynch 3andk les droits lli khassek.", "erreur;403;accès"),
                    new Faq("Erreur 'accès refusé' à un dossier.", "Chouf les autorisations dyal système dyalek.", "erreur;dossier;autorisation"),
                    new Faq("Blue Screen sur Windows, que faire ?", "3awed chghol l'PC. Ila b9a, chouf les pilotes wla RAM.", "erreur;blue screen;windows"),
                    new Faq("Erreur JavaScript sur une page web.", "Dir refresh lla page wla 3awed ft7ha f navigateur khor.", "javascript;erreur"),
                    new Faq("Erreur de disque dur non détecté.", "Chouf les câbles SATA/USB wla jrib port akhor.", "erreur;disque;dur"),
                    new Faq("Erreur 'mémoire insuffisante'.", "Sedd ba3d l'programmes wla zid RAM.", "erreur;mémoire;ram"),
                    new Faq("Erreur de compilation en Java.", "Chouf syntaxe dyal projet w les dépendances.", "erreur;java;compilation"),
                    new Faq("Erreur 500 : serveur interne.", "L'mochkil mn serveur, machi mn ntik.", "erreur;500;serveur"),
                    new Faq("Erreur de certificat HTTPS.", "L'horloge système ghalta wla site machi sécurisé.", "erreur;https;certificat"),
                    new Faq("Comment configurer Outlook ?", "Zid compte f Fichier > Ajouter un compte, o kteb identifiants dyalek.", "outlook;configuration"),
                    new Faq("Quels sont les ports pour IMAP ?", "IMAP: 993 (SSL), SMTP: 465 wla 587.", "imap;smtp;port"),
                    new Faq("Erreur d'authentification mail ?", "Chouf l'adresse w l-mot de passe li dkhelti.", "authentification;mail;erreur"),
                    new Faq("Comment activer l'authentification OAuth ?", "Sir l paramètres dyal fournisseur mail.", "oauth;authentification;mail"),
                    new Faq("Puis-je avoir deux adresses mail dans le même client ?", "Ah, la plupart dyal clients kayqdero.", "mail;plusieurs comptes"),
                    new Faq("Mail envoyé mais non reçu ?", "Chouf spam w inbox dyal destinataire.", "mail;non reçu"),
                    new Faq("Problème de synchronisation mail.", "Réinitialisé l'compte wla chouf la connexion.", "mail;synchronisation"),
                    new Faq("Comment configurer Gmail sur Thunderbird ?", "Sta3mel IMAP m3a smtp.gmail.com o chadd IMAP f Google.", "gmail;thunderbird"),
                    new Faq("Mail reste bloqué dans la boîte d'envoi.", "Chouf la connexion o 3awed b3ath.", "mail;bloqué;boite envoi"),
                    new Faq("Message 'serveur introuvable'.", "Chouf adresses dyal serveurs IMAP w SMTP.", "serveur;mail;introuvable"),
                    new Faq("Comment libérer de l'espace disque ?", "Sta3mel nettoyage de disque wla ssupprimi les fichiers temporaires.", "disque;libérer;espace"),
                    new Faq("Puis-je supprimer le dossier Windows.old ?", "Ah, men b3d mise à jour, imkenek tsupprimih.", "windows.old;supprimer"),
                    new Faq("Comment vider le cache système ?", "Sta3mel outil nettoyage de disque wla CCleaner.", "cache;système;vider"),
                    new Faq("Mémoire RAM saturée, que faire ?", "Sedd les applications li makhdaminch wla 3awed chghol l'PC.", "mémoire;ram;saturée"),
                    new Faq("Mon disque est à 100% d'utilisation.", "Désactivi les services inutiles wla analysi les processus.", "disque;100%;utilisation"),
                    new Faq("Quels fichiers temporaires sont inutiles ?", "Les fichiers .tmp, cache navigateur, w logs.", "fichiers;temporaires;inutile"),
                    new Faq("Utilisation excessive de la mémoire par Chrome.", "Sedd ba3d l'onglets wla sir 3la navigateur khfif.", "chrome;mémoire;excessive"),
                    new Faq("Nettoyage automatique sur Windows ?", "Chadd l'Assistant stockage f paramètres.", "nettoyage;automatique;windows"),
                    new Faq("Dois-je défragmenter mon disque ?", "Ah, ila kan disque dur classique (HDD), machi SSD.", "défragmenter;disque"),
                    new Faq("Pourquoi mon PC rame ?", "Souvent à cause manque dyal mémoire wla stockage.", "pc;lent;ram")
            );

            for (Faq faq : faqs) {
                faqDAO.ajouter(faq);
            }

            System.out.println("FAQs initialisées avec succès !");
        } else {
            System.out.println("Les FAQs sont déjà présentes.");
        }
    }
}
