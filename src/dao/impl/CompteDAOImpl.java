package dao.impl;

import config.DBConnection;
import dao.CompteDAO;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAOImpl implements CompteDAO {
    private Connection conn = DBConnection.getInstance().getConnection();

    @Override
    public void save(Compte compte) {
        String sql = "INSERT INTO compte(id, numero, solde, idclient, typecompte, decouvertautorise, tauxinteret) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, compte.getId());
            st.setInt(2, compte.getNumero());
            st.setDouble(3, compte.getSolde());
            st.setString(4, compte.getIdClient());
            st.setString(5, compte.getTypeCompte());

            if (compte instanceof CompteCourant) {
                CompteCourant compteCourant = (CompteCourant) compte;
                st.setDouble(6, compteCourant.getDecouvertAutorise());
                st.setNull(7, Types.DOUBLE);
            } else if (compte instanceof CompteEpargne) {
                CompteEpargne compteEpargne = (CompteEpargne) compte;
                st.setNull(6, Types.DOUBLE);
                st.setDouble(7, compteEpargne.getTauxInteret());
            }

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while creating compte :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Compte compte) {
        String sql = "UPDATE compte SET solde = ?, typeCompte = ?, decouvertAutorise = ?, tauxInteret = ? WHERE id = ?";
        System.out.println("Updating compte with ID: " + compte.getId());

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, compte.getSolde());
            ps.setString(2, compte.getTypeCompte());

            if (compte instanceof CompteCourant cc) {
                ps.setDouble(3, cc.getDecouvertAutorise());
                ps.setNull(4, java.sql.Types.DOUBLE);
            } else if (compte instanceof CompteEpargne ce) {
                ps.setNull(3, java.sql.Types.DOUBLE);
                ps.setDouble(4, ce.getTauxInteret());
            } else {
                ps.setNull(3, java.sql.Types.DOUBLE);
                ps.setNull(4, java.sql.Types.DOUBLE);
            }

            ps.setString(5, compte.getId());

            ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error while updaing : "+e.getMessage());
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Compte findById(String id) {
        String sql = "SELECT * FROM compte WHERE id = ?";
        Compte compte = null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);
            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    if (rs.getString("typecompte").equals("COURANT")) {
                        compte = new CompteCourant(rs.getString("id"), rs.getString("idclient"), rs.getInt("numero"), rs.getDouble("solde"), rs.getDouble("decouvertautorise"));
                    } else {
                        compte = new CompteEpargne(rs.getString("id"), rs.getString("idclient"), rs.getInt("numero"), rs.getDouble("solde"), rs.getDouble("tauxinteret"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while find compte by id : " + e.getMessage());
            e.printStackTrace();
        }
        return compte;
    }

    @Override
    public List<Compte> findAllComptes() {
        String sql = "SELECT * FROM compte";
        List<Compte> comptes = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getString("typecompte").equals("COURANT")) {
                    comptes.add(new CompteCourant(rs.getString("id"), rs.getString("idclient"), rs.getInt("numero"), rs.getDouble("solde"), rs.getDouble("decouvertautorise")));
                } else {
                    comptes.add(new CompteEpargne(rs.getString("id"), rs.getString("idclient"), rs.getInt("numero"), rs.getDouble("solde"), rs.getDouble("tauxinteret")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while find compte by id : " + e.getMessage());
            e.printStackTrace();
        }
        return comptes;
    }

}
