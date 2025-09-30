package dao.impl;

import config.DBConnection;
import dao.CompteDAO;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class CompteDAOImpl implements CompteDAO {
    private Connection conn = DBConnection.getInstance().getConnection();

    @Override
    public void save(Compte compte) {
        String sql = "INSERT INTO compte(id, numero, solde, idclient, typecompte, decouvertautorise, tauxinteret) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, compte.getId());
            st.setInt(2, compte.getNumero());
            st.setDouble(3, compte.getSolde());
            st.setString(4, compte.getIdClient());
            st.setString(5, compte.getTypeCompte());

            if(compte instanceof CompteCourant){
                CompteCourant compteCourant = (CompteCourant) compte;
                st.setDouble(6, compteCourant.getDecouvertAutorise());
                st.setObject(7, null, Types.NULL);
            }else if (compte instanceof CompteEpargne){
                CompteEpargne compteEpargne = (CompteEpargne) compte;
                st.setObject(6, null, Types.NULL);
                st.setDouble(7, compteEpargne.getTauxInteret());
            }

            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error while creating compte :"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Compte compte) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Compte findById(String id) {
        return null;
    }

    @Override
    public List<Compte> findByClient(String idClient) {
        return List.of();
    }
}
