package dao.impl;

import config.DBConnection;
import dao.TransactionDAO;
import entities.Transaction;
import entities.enums.TransactionType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    private Connection conn = DBConnection.getInstance().getConnection();

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction(id, montant, typetransaction, lieu, idCompte, reciever_id) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, transaction.id());
            st.setDouble(2, transaction.montant());
            st.setObject(3, transaction.type(), Types.OTHER);
            st.setString(4, transaction.lieu());
            st.setString(5, transaction.idCompte());
            if(transaction.type().equals(TransactionType.VIREMENT)){
                st.setString(6, transaction.recieverId());
            } else {
                st.setNull(6, Types.VARCHAR);
            }


            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Problem en creation du transaction : "+ e.getMessage());
            e.printStackTrace();
        }
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
