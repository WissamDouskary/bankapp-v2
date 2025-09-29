package ui;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);

    public void afficherMenuPrincipal() {
        int choix;
        do {
            System.out.println("\n===== BANQUE AL BARAKA - ANALYSE DES TRANSACTIONS =====");
            System.out.println("1. Gestion des clients");
            System.out.println("2. Gestion des comptes");
            System.out.println("3. Gestion des transactions");
            System.out.println("4. Analyses et rapports");
            System.out.println("5. Alertes");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");

            choix = lireEntier();

            switch (choix) {
                case 1 -> menuClients();
                case 2 -> menuComptes();
                case 3 -> menuTransactions();
                case 4 -> menuAnalyses();
                case 5 -> menuAlertes();
                case 0 -> System.out.println("✅ Merci d’avoir utilisé l’application.");
                default -> System.out.println("⚠️ Choix invalide, réessayez.");
            }

        } while (choix != 0);
    }

    // client menu
    private void menuClients() {
        System.out.println("\n=== Gestion des Clients ===");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Modifier un client");
        System.out.println("3. Supprimer un client");
        System.out.println("4. Rechercher un client par ID");
        System.out.println("5. Rechercher un client par Nom");
        System.out.println("6. Lister tous les clients");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = lireEntier();

        // Placeholder pour les actions
        switch (choix) {
            case 1 -> System.out.println("[Ajouter un client]");
            case 2 -> System.out.println("[Modifier un client]");
            case 3 -> System.out.println("[Supprimer un client]");
            case 4 -> System.out.println("[Rechercher un client par ID]");
            case 5 -> System.out.println("[Rechercher un client par Nom]");
            case 6 -> System.out.println("[Lister tous les clients]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("⚠️ Choix invalide.");
        }
    }

    // Menu comptes
    private void menuComptes() {
        System.out.println("\n=== Gestion des Comptes ===");
        System.out.println("1. Créer un compte (Courant / Épargne)");
        System.out.println("2. Mettre à jour le solde ou paramètres");
        System.out.println("3. Rechercher comptes par client ou numéro");
        System.out.println("4. Compte avec solde max / min");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = lireEntier();

        switch (choix) {
            case 1 -> System.out.println("[Créer un compte]");
            case 2 -> System.out.println("[Mettre à jour un compte]");
            case 3 -> System.out.println("[Rechercher comptes]");
            case 4 -> System.out.println("[Compte avec solde max/min]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("⚠️ Choix invalide.");
        }
    }

    // Menu transaction
    private void menuTransactions() {
        System.out.println("\n=== Gestion des Transactions ===");
        System.out.println("1. Versement");
        System.out.println("2. Retrait");
        System.out.println("3. Virement");
        System.out.println("4. Lister les transactions d’un compte ou client");
        System.out.println("5. Filtrer transactions (montant, type, date, lieu)");
        System.out.println("6. Regrouper transactions par type ou période");
        System.out.println("7. Calculer moyenne / total par client ou compte");
        System.out.println("8. Détecter transactions suspectes");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = lireEntier();

        switch (choix) {
            case 1 -> System.out.println("[Versement - Émettre un dépôt sur un compte]");
            case 2 -> System.out.println("[Retrait - Retirer de l'argent d'un compte]");
            case 3 -> System.out.println("[Virement - Transférer de l'argent vers un autre compte]");
            case 4 -> System.out.println("[Lister les transactions]");
            case 5 -> System.out.println("[Filtrer les transactions]");
            case 6 -> System.out.println("[Regrouper transactions]");
            case 7 -> System.out.println("[Calculer moyenne / total]");
            case 8 -> System.out.println("[Détecter transactions suspectes]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println(" Choix invalide.");
        }
    }

    // Menu analyses
    private void menuAnalyses() {
        System.out.println("\n=== Analyses et Rapports ===");
        System.out.println("1. Top 5 clients par solde");
        System.out.println("2. Rapport mensuel (transactions par type et volume total)");
        System.out.println("3. Comptes inactifs");
        System.out.println("4. Transactions suspectes");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = lireEntier();

        switch (choix) {
            case 1 -> System.out.println("[Top 5 clients par solde]");
            case 2 -> System.out.println("[Rapport mensuel]");
            case 3 -> System.out.println("[Comptes inactifs]");
            case 4 -> System.out.println("[Transactions suspectes]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("⚠️ Choix invalide.");
        }
    }

    // Menu alers
    private void menuAlertes() {
        System.out.println("\n=== Alertes ===");
        System.out.println("1. Solde bas");
        System.out.println("2. Inactivité prolongée");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = lireEntier();

        switch (choix) {
            case 1 -> System.out.println("[Alerte solde bas]");
            case 2 -> System.out.println("[Alerte inactivité prolongée]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("⚠️ Choix invalide.");
        }
    }

    // ====================== Utilitaire ======================
    private int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.println("⚠️ Entrée invalide, veuillez saisir un nombre.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
