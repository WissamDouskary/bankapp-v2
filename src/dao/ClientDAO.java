package dao;

import entities.Client;

import java.util.List;

public interface ClientDAO {
    void save(Client client);
    void update(Client client);
    void delete(String id);
    Client findById(String id);
    List<Client> findAll();
}
