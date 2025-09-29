package dao.impl;

import dao.TransactionDAO;
import entities.Transaction;

import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public void save(Transaction transaction) {

    }

    @Override
    public void update(Transaction transaction) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Transaction findById(String id) {
        return null;
    }

    @Override
    public List<Transaction> findByCompte(String idCompte) {
        return List.of();
    }
}
