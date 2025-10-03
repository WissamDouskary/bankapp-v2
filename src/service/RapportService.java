package service;

import dao.impl.TransactionDAOImpl;
import entities.Client;
import entities.Compte;
import entities.Transaction;
import entities.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RapportService {

    private ClientService clientService = new ClientService();
    private CompteService compteService = new CompteService();
    private TransactionService transactionService = new TransactionService();
    private TransactionDAOImpl transactionDAOimpl = new TransactionDAOImpl();

    public List<Client> top5ClientsBySolde() {
        return clientService.listAllClients().stream()
                .sorted((c1, c2) -> {
                    double total1 = compteService.findByClientId(c1.id()).stream()
                            .mapToDouble(Compte::getSolde)
                            .sum();
                    double total2 = compteService.findByClientId(c2.id()).stream()
                            .mapToDouble(Compte::getSolde)
                            .sum();
                    return Double.compare(total2, total1);
                })
                .limit(5)
                .toList();
    }

    public void generalReport() {
        List<Compte> comptes = compteService.findAll();
        double totalSolde = comptes.stream().mapToDouble(Compte::getSolde).sum();
        int nombreComptes = comptes.size();
        int nombreClients = clientService.listAllClients().size();

        System.out.println("=== Rapport général ===");
        System.out.println("Nombre de clients: " + nombreClients);
        System.out.println("Nombre de comptes: " + nombreComptes);
        System.out.println("Solde total de tous les comptes: " + totalSolde);
        System.out.println("======================");
    }

    public void rapportMensuel(int month, int year) {
        List<Transaction> allTransactions = new ArrayList<>();
        compteService.findAll().forEach(c ->
                allTransactions.addAll(transactionDAOimpl.findByCompte(c.getId()))
        );

        List<Transaction> monthTransactions = allTransactions.stream()
                .filter(t -> t.date().getMonthValue() == month && t.date().getYear() == year)
                .toList();

        Map<TransactionType, List<Transaction>> grouped = monthTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));

        System.out.println("=== Rapport mensuel " + month + "/" + year + " ===");
        grouped.forEach((type, list) -> {
            double totalMontant = list.stream().mapToDouble(Transaction::montant).sum();
            System.out.println(type + ": " + list.size() + " transactions, total = " + totalMontant);
        });
        System.out.println("====================================");
    }

    public List<Compte> comptesInactifs(LocalDateTime depuis) {
        List<Compte> comptes = compteService.findAll();
        List<Compte> inactifs = new ArrayList<>();

        for (Compte c : comptes) {
            List<Transaction> txs = transactionDAOimpl.findByCompte(c.getId());
            boolean actif = txs.stream().anyMatch(t -> t.date().isAfter(depuis));
            if (!actif) inactifs.add(c);
        }

        return inactifs;
    }

    public void alerteSoldeBas(double seuil) {
        List<Compte> comptes = compteService.findAll();
        System.out.println("=== Alertes: Solde bas (< " + seuil + ") ===");
        for (Compte c : comptes) {
            if (c.getSolde() < seuil) {
                System.out.println("Compte " + c.getId() + " - Client: " + c.getIdClient() + " | Solde: " + c.getSolde());
            }
        }
        System.out.println("==========================================");
    }

    public void alerteInactivite(int moisInactifs) {
        List<Compte> comptes = compteService.findAll();
        LocalDateTime now = LocalDateTime.now();

        System.out.println("=== Alertes: Inactivité prolongée (> " + moisInactifs + " mois) ===");

        for (Compte c : comptes) {
            List<Transaction> txs = transactionService.findByCompte(c.getId());

            LocalDateTime lastTxDate = txs.isEmpty() ? null :
                    txs.stream().map(Transaction::date).max(LocalDateTime::compareTo).orElse(null);

            if (lastTxDate == null) {
                System.out.println("Compte " + c.getId() + " - Client: " + c.getIdClient() + " | Jamais utilisé");
            } else {
                long diffMonths = ChronoUnit.MONTHS.between(lastTxDate, now);
                if (diffMonths >= moisInactifs) {
                    System.out.println("Compte " + c.getId() + " - Client: " + c.getIdClient()
                            + " | Dernière transaction: " + lastTxDate);
                }
            }
        }
        System.out.println("=================================================");
    }

    public void informationsGenerales() {
        List<Compte> comptes = compteService.findAll();
        List<Client> clients = clientService.listAllClients();

        double soldeTotal = comptes.stream().mapToDouble(Compte::getSolde).sum();
        int nombreComptes = comptes.size();
        int nombreClients = clients.size();
        double soldeMoyen = nombreComptes > 0 ? soldeTotal / nombreComptes : 0;

        System.out.println("=== Informations générales ===");
        System.out.println("Nombre de clients      : " + nombreClients);
        System.out.println("Nombre de comptes      : " + nombreComptes);
        System.out.println("Solde total            : " + soldeTotal);
        System.out.println("Solde moyen par compte : " + soldeMoyen);
        System.out.println("==============================");
    }

}
