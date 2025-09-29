package dao;

import entities.Transaction;

import java.util.List;

public interface TransactionDAO {
    void save(Transaction transaction);
    void update(Transaction transaction);
    void delete(String id);
    Transaction findById(String id);
    List<Transaction> findByCompte(String idCompte);
}
