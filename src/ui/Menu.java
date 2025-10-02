package ui;

import entities.*;
import entities.enums.TransactionType;
import service.ClientService;
import service.CompteService;
import service.RapportService;
import service.TransactionService;
import util.NomberChecker;

import java.time.LocalDateTime;
import java.util.*;

public class Menu {

    private static Scanner scanner = new Scanner(System.in);
    private static NomberChecker nomberChecker = new NomberChecker();
    private static ClientService clientService = new ClientService();
    private static CompteService compteService = new CompteService();
    private static TransactionService transactionService = new TransactionService();
    private static RapportService rapportService = new RapportService();

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
        scanner.nextLine();

        switch (choix) {
            case 1 -> createAccount();
            case 2 -> updateAccount();
            case 3 -> findByClientIdOrNumber();
            case 4 -> filterByMinAndMax();
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
        scanner.nextLine();

        switch (choix) {
            case 1 -> versement();
            case 2 -> retrait();
            case 3 -> virement();
            case 4 -> listAllTransactions();
            case 5 -> filterTransactionsMenuSimple();
            case 6 -> groupTransactionsMenu();
            case 7 -> transactionStatsMenu();
            case 8 -> detectSuspiciousMenu();
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
        System.out.println("4. information generale");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix: ");
        int choix = nomberChecker.lireEntier(scanner);

        switch (choix) {
            case 1 -> top5clientParSolde();
//            case 2 -> rapportMonsuel();
//            case 3 -> comptesInactifs();
            case 4 -> rapportService.informationsGenerales();
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
//            case 1 -> alertSoldeBas();
//            case 2 -> alertInactivite();
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


    //comptes methods
    static void createAccount(){
        Random random = new Random();
        int numero = random.nextInt(Integer.MAX_VALUE);

        System.out.println("Entrer init solde :");
        double solde = scanner.nextDouble();
        if(solde <= 0){
            System.out.println("Solde est negative!");
            return;
        }
        scanner.nextLine();
        getAllClients();

        System.out.println("Entrer client id: ");
        String clientId = scanner.nextLine();

        if(clientService.findById(clientId) == null){
            return;
        }

        System.out.println("Entrer compte type: ");
        System.out.println("1. Courant");
        System.out.println("2. Epargne");
        System.out.println("Choix");

        int choix = nomberChecker.lireEntier(scanner);
        scanner.nextLine();

        String type = null;
        double decouvert = 0;
        double tauxInteret = 0;
        Compte compte = null;

        do{
            if(choix == 1){
                type = "COURANT";
            }else if (choix == 2){
                type = "EPARGNE";
            }else{
                System.out.println("Invalid selection");
            }
        }
        while (type == null);

        if(choix == 1){
            System.out.println("Entrer le montant de decouvert: ");
            decouvert = scanner.nextDouble();
            if(decouvert <= 0){
                System.out.println("Entrer une valide nomber > 0");
                return;
            }
            compte = new CompteCourant(clientId, numero, solde, decouvert);
        } else {
            System.out.println("Entrer le taux d'interet (0,04 est 4%): ");
            tauxInteret = scanner.nextDouble();
            if(tauxInteret < 0){
                System.out.println("Entrer une valide nomber > 0");
                return;
            }
            compte = new CompteEpargne(clientId, numero, solde, tauxInteret);
        }

        compteService.createCompte(compte);
        System.out.println("Compte crée avec sucess");
        System.out.println("votre numero de compte : "+numero);
    }

    static void updateAccount() {
        List<Compte> comptes = compteService.findAll();

        System.out.println("Comptes List ===========================");
        comptes.forEach(System.out::println);
        System.out.println("===========================");
        System.out.println("Entrer id de compte :");
        String id = scanner.nextLine();

        Compte compte = compteService.findById(id);

        if (compte == null) {
            System.out.println("Aucun compte avec cette id!");
            return;
        }

        System.out.println("Account found!");

        double solde = compte.getSolde();
        String type = compte.getTypeCompte();
        Double decouvert = null;
        Double tauxInteret = null;

        if (compte instanceof CompteCourant) {
            decouvert = ((CompteCourant) compte).getDecouvertAutorise();
        } else if (compte instanceof CompteEpargne) {
            tauxInteret = ((CompteEpargne) compte).getTauxInteret();
        }

        while (true) {
            System.out.println("\nActuellement Account type : " + type);
            System.out.println("Solde actuel: " + solde);

            if (type.equals("COURANT")) {
                System.out.println("Découvert autorisé: " + decouvert);
            } else {
                System.out.println("Taux d'intérêt: " + tauxInteret);
            }

            System.out.println("\nMenu:");
            System.out.println("1. Modifier solde");
            System.out.println("2. Modifier Account type");
            if (type.equals("COURANT")) {
                System.out.println("3. Modifier Découvert");
            } else {
                System.out.println("3. Modifier Taux d'intérêt");
            }
            System.out.println("0. Save changes");

            int choix = nomberChecker.lireEntier(scanner);
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.println("Entrer le nouveau solde: ");
                    solde = scanner.nextDouble();
                    scanner.nextLine();
                    if (solde <= 0) {
                        System.out.println("Solde doit être positif!");
                    }
                }
                case 2 -> {
                    System.out.println("Entrer le nouveau Account type: ");
                    System.out.println("1. COURANT");
                    System.out.println("2. EPARGNE");

                    int typeChoix = nomberChecker.lireEntier(scanner);
                    scanner.nextLine();

                    if (typeChoix == 1) {
                        type = "COURANT";
                        System.out.println("Entrer le découvert autorisé :");
                        decouvert = scanner.nextDouble();
                        scanner.nextLine();

                        tauxInteret = null;
                    } else if (typeChoix == 2) {
                        type = "EPARGNE";
                        System.out.println("Entrer le taux d'intérêt :");
                        tauxInteret = scanner.nextDouble();
                        scanner.nextLine();

                        decouvert = null;
                    } else {
                        System.out.println("Type invalide");
                    }
                }
                case 3 -> {
                    if (type.equals("COURANT")) {
                        System.out.println("Entrer le nouveau découvert :");
                        decouvert = scanner.nextDouble();
                        scanner.nextLine();
                        if (decouvert <= 0) {
                            System.out.println("Découvert doit être positif!");
                        }
                    } else if (type.equals("EPARGNE")) {
                        System.out.println("Entrer le nouveau taux d'intérêt :");
                        tauxInteret = scanner.nextDouble();
                        scanner.nextLine();
                        if (tauxInteret <= 0) {
                            System.out.println("Taux d'intérêt doit être positif!");
                        }
                    }
                }
                case 0 -> {
                    if (type.equals("COURANT") && decouvert != null) {
                        compte = new CompteCourant(compte.getId(), compte.getIdClient(), compte.getNumero(), solde, decouvert);
                    } else if(tauxInteret != null){
                        compte = new CompteEpargne(compte.getId(), compte.getIdClient(), compte.getNumero(), solde, tauxInteret);
                    }
                    compteService.updateCompte(compte);
                    System.out.println("Compte modifié avec succès!");
                    return;
                }
                default -> System.out.println("Sélection invalide.");
            }
        }
    }

    static void findByClientIdOrNumber() {
        System.out.println("Entrer le client id ou le numero de compte :");
        String searchTerm = scanner.nextLine();

        if (!compteService.findByClientId(searchTerm).isEmpty()) {
            System.out.println("Found accounts by client ID.");
            System.out.println("===========================================");
            compteService.findByClientId(searchTerm).forEach(System.out::println);
            System.out.println("===========================================");
        } else {
            try {
                int numero = Integer.parseInt(searchTerm);
                if (compteService.findByNumero(numero) != null) {
                    System.out.println("Found account by account number.");
                    System.out.println(compteService.findByNumero(numero));
                } else {
                    System.out.println("No account found with this client ID or account number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid account number format.");
            }
        }
    }

    static void filterByMinAndMax(){
        System.out.println("Max And Min solde Accounts: ===========================");
        Map<String, Optional<Compte>> maxAndMin = new HashMap<>();
        maxAndMin = compteService.findMaxAndMin();

        for (String i : maxAndMin.keySet()) {
            maxAndMin.get(i).ifPresent(compte -> {
                System.out.println(i +" ================================");
                System.out.println(compte);
            });
        }
    }

    // transactions methods
    static void versement(){
        System.out.println("Entrer le numero de compte pour versement: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Entrer le montant :");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Entrer le lieu");
        String lieu = scanner.nextLine();

        transactionService.viresement(numero, montant, lieu);
    }

    static void retrait() {
        System.out.println("Entrer le numero de compte pour retrait: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Entrer le montant :");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Entrer le lieu de transaction :");
        String lieu = scanner.nextLine();

        transactionService.retrait(numero, montant, lieu);
    }

    static void virement(){
        System.out.println("Entrer le numero de sender :");
        int senderNumero = nomberChecker.lireEntier(scanner);
        scanner.nextLine();

        System.out.println("Entrer le numero de reciever: ");
        int recieverNumero = nomberChecker.lireEntier(scanner);
        scanner.nextLine();

        System.out.println("Entrer le montant :");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Entrer le lieu: ");
        String lieu = scanner.nextLine();

        transactionService.virement(senderNumero, recieverNumero, montant, lieu);
    }

    static void listAllTransactions() {
        System.out.println("Lister transactions par:");
        System.out.println("1. Compte");
        System.out.println("2. Client");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix == 1) {
            System.out.print("Entrer l'ID du compte: ");
            String idCompte = scanner.nextLine();
            transactionService.listTransactionsByCompte(idCompte)
                    .forEach(System.out::println);
        } else if (choix == 2) {
            System.out.print("Entrer l'ID du client: ");
            String idClient = scanner.nextLine();
            transactionService.listTransactionsByClient(idClient)
                    .forEach(System.out::println);
        } else {
            System.out.println("Choix invalide!");
        }
    }

    static void filterTransactionsMenuSimple() {
        System.out.print("Entrer ID du compte: ");
        String idCompte = scanner.nextLine();

        System.out.print("Montant minimum (0 si aucun): ");
        double montantMin = scanner.nextDouble();

        System.out.print("Montant maximum (0 si aucun): ");
        double montantMax = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Type (RETRAIT, VERSEMENT, VIREMENT) ou vide: ");
        String typeStr = scanner.nextLine();
        TransactionType type = typeStr.isBlank() ? null : TransactionType.valueOf(typeStr.toUpperCase());

        System.out.print("Date début (yyyy-MM-ddTHH:mm) ou vide: ");
        String dateFromStr = scanner.nextLine();
        LocalDateTime dateFrom = dateFromStr.isBlank() ? null : LocalDateTime.parse(dateFromStr);

        System.out.print("Date fin (yyyy-MM-ddTHH:mm) ou vide: ");
        String dateToStr = scanner.nextLine();
        LocalDateTime dateTo = dateToStr.isBlank() ? null : LocalDateTime.parse(dateToStr);

        System.out.print("Lieu ou vide: ");
        String lieuStr = scanner.nextLine();
        String lieu = lieuStr.isBlank() ? null : lieuStr;

        List<Transaction> filtered = transactionService.filterTransactionsSimple(
                idCompte, montantMin, montantMax, type, dateFrom, dateTo, lieu
        );

        System.out.println("Transactions filtrées ====================");
        filtered.forEach(System.out::println);
        System.out.println("=========================================");
    }

    static void groupTransactionsMenu() {
        System.out.print("Entrer ID du compte: ");
        String idCompte = scanner.nextLine();

        System.out.print("Regrouper par (type / day / month / year): ");
        String groupBy = scanner.nextLine();

        try {
            Map<String, List<Transaction>> grouped = transactionService.groupTransactionsByTypeOrPeriod(idCompte, groupBy);

            grouped.forEach((key, list) -> {
                System.out.println("\n=== " + key + " ===");
                list.forEach(System.out::println);
            });
        } catch (IllegalArgumentException e) {
            System.out.println("Option invalide pour regroupement !");
        }
    }

    static void transactionStatsMenu() {
        System.out.print("Voulez-vous calculer pour un compte ou un client ? (compte/client): ");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("compte")) {
            System.out.print("Entrer ID du compte: ");
            String idCompte = scanner.nextLine();

            double total = transactionService.totalTransactionsByCompte(idCompte);
            double avg = transactionService.averageTransactionsByCompte(idCompte);

            System.out.println("Total des transactions: " + total);
            System.out.println("Moyenne des transactions: " + avg);

        } else if(choice.equalsIgnoreCase("client")) {
            System.out.print("Entrer ID du client: ");
            String idClient = scanner.nextLine();

            double total = transactionService.totalTransactionsByClient(idClient);
            double avg = transactionService.averageTransactionsByClient(idClient);

            System.out.println("Total des transactions du client: " + total);
            System.out.println("Moyenne des transactions du client: " + avg);

        } else {
            System.out.println("Choix invalide !");
        }
    }

    static void detectSuspiciousMenu() {
        System.out.print("Entrer ID du compte: ");
        String idCompte = scanner.nextLine();

        System.out.print("Seuil de montant (€): ");
        double montantSeuil = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Pays habituel: ");
        String paysHabituel = scanner.nextLine();

        List<Transaction> suspicious = transactionService.detectSuspiciousTransactions(idCompte, montantSeuil, paysHabituel);

        System.out.println("Transactions suspectes ====================");
        if(suspicious.isEmpty()) {
            System.out.println("Aucune transaction suspecte trouvée !");
        } else {
            suspicious.forEach(System.out::println);
        }
        System.out.println("==========================================");
    }

    static void top5clientParSolde(){
        List<Client> top5 = rapportService.top5ClientsBySolde();
        System.out.println("=== Top 5 clients par solde ===");
        top5.forEach(System.out::println);
    }
}
