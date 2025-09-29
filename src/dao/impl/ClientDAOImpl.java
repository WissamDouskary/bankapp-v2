package dao.impl;

import config.DBConnection;
import dao.ClientDAO;
import entities.Client;

import java.lang.ref.Cleaner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private Connection conn = DBConnection.getInstance().getConnection();
    @Override
    public void save(Client client) {
        String sql = "INSERT INTO client(id, nom, email) VALUES (?, ?, ?)";
        try(PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, client.id());
            st.setString(2, client.nom());
            st.setString(3, client.email());

            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error while creating client :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE client SET nom = ?, email = ? WHERE id = ? ";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, client.nom());
            st.setString(2, client.email());
            st.setString(3, client.id());

            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error while updating client :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM client WHERE id = ? ";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, id);

            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error while deleting client :" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Client findById(String id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        Client client = null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    client = new Client(
                            rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while finding client by id: " + e.getMessage());
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM client";
        List<Client> clients = new ArrayList<>();

        try(PreparedStatement st = conn.prepareStatement(sql)){
            try (ResultSet rs = st.executeQuery()){
                 while (rs.next()){
                     clients.add(new Client(rs.getString("id"), rs.getString("nom"), rs.getString("email")));
                 }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e){
            System.out.println("Error while find client by id :" + e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }
}
