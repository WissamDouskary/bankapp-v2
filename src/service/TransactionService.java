package service;

import dao.impl.CompteDAOImpl;
import dao.impl.TransactionDAOImpl;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;
import entities.Transaction;
import entities.enums.TransactionType;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
}
