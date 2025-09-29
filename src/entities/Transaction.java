package entities;

import entities.enums.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public record Transaction(String id, LocalDate date, double montant, TransactionType type, String lieu, int idCompte) {

    public Transaction(LocalDate date, double montant, TransactionType type, String lieu, int idCompte){
        this(UUID.randomUUID().toString(), date, montant, type, lieu, idCompte);
    }

}

