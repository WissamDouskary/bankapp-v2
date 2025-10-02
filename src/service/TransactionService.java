package service;

import dao.impl.CompteDAOImpl;
import dao.impl.TransactionDAOImpl;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;
import entities.Transaction;
import entities.enums.TransactionType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {
    private TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl();
    private CompteService compteService = new CompteService();

    public void createTransaction(Transaction transaction) {
        transactionDAOImpl.save(transaction);
    }

    public void retrait(int numeroCompte, double montant, String lieu) {
        Compte compte = compteService.findByNumero(numeroCompte);

        if (compte == null) {
            System.out.println("Aucun compte trouvé avec ce numéro.");
            return;
        }

        if (montant <= 0) {
            System.out.println("Le montant doit être positif.");
            return;
        }

        if (compte instanceof CompteCourant compteCourant) {
            if (compteCourant.getSolde() >= montant) {
                compteCourant.setSolde(compteCourant.getSolde() - montant);
                compteService.updateCompte(compteCourant);
                System.out.println("Retrait effectué avec succès !");
            } else if ((compteCourant.getSolde() + compteCourant.getDecouvertAutorise()) >= montant) {
                double montantDeficit = montant - compteCourant.getSolde();
                compteCourant.setSolde(0);
                compteCourant.setDecouvertAutorise(compteCourant.getDecouvertAutorise() - montantDeficit);

                compteService.updateCompte(compteCourant);
                System.out.println("Retrait effectué, mais découvert utilisé. Nouveau découvert: "
                        + compteCourant.getDecouvertAutorise());
            } else {
                System.out.println("Fonds insuffisants, même avec le découvert autorisé !");
                return;
            }
        } else if (compte instanceof CompteEpargne compteEpargne) {
            if (compteEpargne.getSolde() >= montant) {
                compteEpargne.setSolde(compteEpargne.getSolde() - montant);
                compteService.updateCompte(compteEpargne);
                System.out.println("Retrait effectué avec succès !");
            } else {
                System.out.println("Solde insuffisant sur le compte épargne !");
                return;
            }
        }

        Transaction transaction = new Transaction(
                montant,
                TransactionType.RETRAIT,
                lieu,
                compte.getId(),
                null
        );
        createTransaction(transaction);
    }

    public void viresement(int id, double montant, String lieu){
        Compte compte = compteService.findByNumero(id);

        if(montant <= 0){
            System.out.println("Montant doit etre positive!");
            return;
        }

        if(compte == null){
            System.out.println("aucun compte avec ce id!");
            return;
        }

        Transaction transaction = new Transaction(
                montant,
                TransactionType.VERSEMENT,
                lieu,
                compte.getId(),
                null
        );

        createTransaction(transaction);

        compte.setSolde(compte.getSolde() + montant);
        compteService.updateCompte(compte);
        System.out.println("Le versement fait avec succes!");
    }

    public void virement(int senderId, int recieverId, double montant, String lieu){
        Compte senderCompte = compteService.findByNumero(senderId);
        Compte recieverCompte = compteService.findByNumero(recieverId);

        if(senderCompte == null){
            System.out.println("aucun Sender compte avec cette id");
            return;
        }

        if(recieverCompte == null){
            System.out.println("aucun reciever compte avec cette id");
            return;
        }

        if(senderCompte.getSolde() <= montant){
            System.out.println("Vous n'avez pas assez d'argent pour prendre la versement : "+ senderCompte.getSolde());
            return;
        }

        senderCompte.setSolde(senderCompte.getSolde() - montant);
        recieverCompte.setSolde(recieverCompte.getSolde() + montant);

        compteService.updateCompte(senderCompte);

        compteService.updateCompte(recieverCompte);

        Transaction transaction = new Transaction(montant, TransactionType.VIREMENT, lieu, senderCompte.getId(), recieverCompte.getId());
        createTransaction(transaction);

        System.out.println("Le virement fait avec succes");
    }

    public List<Transaction> listTransactionsByCompte(String idCompte) {
        return transactionDAOImpl.findByCompte(idCompte)
                .stream()
                .sorted(Comparator.comparing(Transaction::date))
                .toList();
    }

    public List<Transaction> listTransactionsByClient(String idClient) {
        return transactionDAOImpl.findByIdClient(idClient)
                .stream()
                .sorted(Comparator.comparing(Transaction::date).reversed())
                .toList();
    }

    public List<Transaction> filterTransactionsSimple(
            String idCompte,
            double montantMin,
            double montantMax,
            TransactionType type,
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            String lieu
    ) {
        return transactionDAOImpl.findByCompte(idCompte)
                .stream()
                .filter(t -> montantMin <= 0 || t.montant() >= montantMin)
                .filter(t -> montantMax <= 0 || t.montant() <= montantMax)
                .filter(t -> type == null || t.type() == type)
                .filter(t -> dateFrom == null || !t.date().isBefore(dateFrom))
                .filter(t -> dateTo == null || !t.date().isAfter(dateTo))
                .filter(t -> lieu == null || t.lieu().equalsIgnoreCase(lieu))
                .sorted(Comparator.comparing(Transaction::date))
                .toList();
    }

    public Map<String, List<Transaction>> groupTransactionsByTypeOrPeriod(
            String idCompte,
            String groupBy
    ) {
        List<Transaction> transactions = transactionDAOImpl.findByCompte(idCompte);

        if (groupBy.equalsIgnoreCase("type")) {
            return transactions.stream()
                    .collect(Collectors.groupingBy(t -> t.type().name()));
        } else if (groupBy.equalsIgnoreCase("day")) {
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return transactions.stream()
                    .collect(Collectors.groupingBy(t -> t.date().format(dayFormatter)));
        } else if (groupBy.equalsIgnoreCase("month")) {
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            return transactions.stream()
                    .collect(Collectors.groupingBy(t -> t.date().format(monthFormatter)));
        } else if (groupBy.equalsIgnoreCase("year")) {
            DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
            return transactions.stream()
                    .collect(Collectors.groupingBy(t -> t.date().format(yearFormatter)));
        } else {
            throw new IllegalArgumentException("Invalid groupBy option");
        }
    }

    public double totalTransactionsByCompte(String idCompte) {
        List<Transaction> transactions = transactionDAOImpl.findByCompte(idCompte);
        return transactions.stream()
                .mapToDouble(Transaction::montant)
                .sum();
    }

    public double averageTransactionsByCompte(String idCompte) {
        List<Transaction> transactions = transactionDAOImpl.findByCompte(idCompte);
        return transactions.stream()
                .mapToDouble(Transaction::montant)
                .average()
                .orElse(0);
    }

    public double totalTransactionsByClient(String idClient) {
        List<Compte> comptes = compteService.findByClientId(idClient);
        return comptes.stream()
                .flatMap(c -> transactionDAOImpl.findByCompte(c.getId()).stream())
                .mapToDouble(Transaction::montant)
                .sum();
    }

    public double averageTransactionsByClient(String idClient) {
        List<Compte> comptes = compteService.findByClientId(idClient);
        return comptes.stream()
                .flatMap(c -> transactionDAOImpl.findByCompte(c.getId()).stream())
                .mapToDouble(Transaction::montant)
                .average()
                .orElse(0);
    }

    public List<Transaction> detectSuspiciousTransactions(
            String idCompte,
            double montantSeuil,
            String paysHabituel
    ) {
        List<Transaction> transactions = transactionDAOImpl.findByCompte(idCompte);
        List<Transaction> suspicious = new ArrayList<>();

        LocalDateTime lastTime = null;

        for (Transaction t : transactions) {
            boolean isSuspicious = false;

            if (t.montant() > montantSeuil) {
                isSuspicious = true;
            }

            if (!t.lieu().equalsIgnoreCase(paysHabituel)) {
                isSuspicious = true;
            }

            if (lastTime != null) {
                long secondsDiff = Duration.between(lastTime, t.date()).getSeconds();
                if (secondsDiff < 60) {
                    isSuspicious = true;
                }
            }
            lastTime = t.date();

            if (isSuspicious && !suspicious.contains(t)) {
                suspicious.add(t);
            }
        }

        return suspicious;
    }
}
