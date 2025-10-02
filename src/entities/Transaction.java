package entities;

import entities.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record Transaction(String id, LocalDateTime date, double montant, TransactionType type, String lieu, String idCompte, String recieverId) {

    public Transaction(double montant, TransactionType type, String lieu, String idCompte, String recieverId){
        this(UUID.randomUUID().toString(), LocalDateTime.now(), montant, type, lieu, idCompte, recieverId);
    }

}

