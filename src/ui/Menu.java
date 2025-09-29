package ui;

import entities.Client;
import service.ClientService;
import util.NomberChecker;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner = new Scanner(System.in);
    private static NomberChecker nomberChecker = new NomberChecker();
    private static ClientService clientService = new ClientService();

    public void afficherMenuPrincipal() {
        int choix;
        do {
            System.out.println("\n===== BANQUE MENU =====");
            System.out.println("1. Gestion des clients");
            System.out.println("2. Gestion des comptes");
            System.out.println("3. Gestion des transactions");
            System.out.println("4. Analyses et rapports");
            System.out.println("5. Alertes");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");

            choix = nomberChecker.lireEntier(scanner);
            scanner.nextLine();
            switch (choix) {
                case 1 -> menuClients();
                case 2 -> menuComptes();
                case 3 -> menuTransactions();
                case 4 -> menuAnalyses();
                case 5 -> menuAlertes();
                case 0 -> System.out.println("Merci d’avoir utilisé l’application.");
                default -> System.out.println("Choix invalide, réessayez.");
            }

        } while (choix != 0);
    }

    // client menu
    private void menuClients() {
        System.out.println("\n=== Gestion des Clients ===");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Modifier un client");
        System.out.println("3. Supprimer un client");
        System.out.println("4. Rechercher un client par ID ou Nom");
        System.out.println("5. Lister tous les clients");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = nomberChecker.lireEntier(scanner);
        scanner.nextLine();
        // Placeholder pour les actions
        switch (choix) {
            case 1 -> saveClient();
            case 2 -> modifyClient();
            case 3 -> deleteClient();
            case 4 -> clientByIdOrNom();
            case 5 -> getAllClients();
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
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
        int choix = nomberChecker.lireEntier(scanner);

        switch (choix) {
            case 1 -> System.out.println("[Créer un compte]");
            case 2 -> System.out.println("[Mettre à jour un compte]");
            case 3 -> System.out.println("[Rechercher comptes]");
            case 4 -> System.out.println("[Compte avec solde max/min]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
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
        int choix = nomberChecker.lireEntier(scanner);

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
        int choix = nomberChecker.lireEntier(scanner);

        switch (choix) {
            case 1 -> System.out.println("[Top 5 clients par solde]");
            case 2 -> System.out.println("[Rapport mensuel]");
            case 3 -> System.out.println("[Comptes inactifs]");
            case 4 -> System.out.println("[Transactions suspectes]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
        }
    }

    // Menu alers
    private void menuAlertes() {
        System.out.println("\n=== Alertes ===");
        System.out.println("1. Solde bas");
        System.out.println("2. Inactivité prolongée");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = nomberChecker.lireEntier(scanner);

        switch (choix) {
            case 1 -> System.out.println("[Alerte solde bas]");
            case 2 -> System.out.println("[Alerte inactivité prolongée]");
            case 0 -> System.out.println("Retour au menu principal");
            default -> System.out.println("Choix invalide.");
        }
    }

    // client methods
    static void saveClient() {
        String nom;
        do {
            System.out.print("Entrer client nom : ");
            nom = scanner.nextLine().trim();
            if (nom.isEmpty()) {
                System.out.println("Le nom ne peut pas être vide !");
            }
        } while (nom.isEmpty());

        String email;
        do {
            System.out.print("Entrer client email : ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("L'email ne peut pas être vide !");
            }
        } while (email.isEmpty());

        Client client = new Client(nom, email);
        System.out.println(clientService.createClient(client));
    }

    static void modifyClient() {
        List<Client> clientList = clientService.listAllClients();
        System.out.println("Modification du client informations =============\n");
        System.out.println("Client List ============================================================");
        clientList.forEach(System.out::println);
        System.out.println("========================================================================");

        System.out.print("Entrer l'ID du client que tu veux modifier: ");
        String ID = scanner.nextLine();

        Client client = clientService.findById(ID);
        if (client == null) {
            System.out.println("Aucun account avec ce id!");
            return;
        }

        String nom = client.nom();
        String email = client.email();

        while (true) {
            System.out.println("1. Modifier le nom");
            System.out.println("2. Modifier l'email");
            System.out.println("0. Save modifications");
            System.out.print("Choix: ");

            int choix = nomberChecker.lireEntier(scanner);
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.print("Entrer le nouveau nom: ");
                    nom = scanner.nextLine();
                }
                case 2 -> {
                    System.out.print("Entrer le nouveau email: ");
                    email = scanner.nextLine();
                }
                case 0 -> {
                    client = new Client(ID, nom, email);
                    clientService.updateClient(client);
                    return;
                }
                default -> System.out.println("Sélection invalide!");
            }
        }
    }

    static void deleteClient(){
        List<Client> clientList = clientService.listAllClients();
        System.out.println("Deletion du client informations =============\n");
        System.out.println("Client List ============================================================");
        clientList.forEach(System.out::println);
        System.out.println("========================================================================");

        System.out.println("Entrer ID du client :");
        String id = scanner.nextLine();
        if(clientService.findById(id) == null){
            System.out.println("Aucun client avec cette id!");
            return;
        }
        clientService.deleteClient(id);
    }

    static void clientByIdOrNom(){
        System.out.println("Entrer le nom ou id du client :");
        String searchTerm = scanner.nextLine();

        Client client = clientService.findByIdOrNom(searchTerm);

        if(client == null){
            System.out.println("Aucun client avec cette id ou ce nom");
            return;
        }
        System.out.println("Client found: ===========");
        System.out.println(client);
    }

    static void getAllClients(){
        List<Client> clientList = clientService.listAllClients();
        System.out.println("Client List ============================================================");
        clientList.forEach(System.out::println);
        System.out.println("========================================================================");
    }
}
