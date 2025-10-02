package service;

import dao.impl.ClientDAOImpl;
import entities.Client;

import java.util.List;

public class ClientService {
    private final ClientDAOImpl clientDAOImpl = new ClientDAOImpl();

    public String createClient(Client client) {
        clientDAOImpl.save(client);
        return "Client added sucessfuly!";
    }

    public void updateClient(Client client){
        clientDAOImpl.update(client);
        System.out.println("Client infos modified avec sucess");
    }

    public void deleteClient(String id){
        clientDAOImpl.delete(id);
        System.out.println("Client supprimer avec sucess");
    }

    public List<Client> listAllClients() {
        return clientDAOImpl.findAll();
    }

    public Client findById(String id) {
        return clientDAOImpl.findById(id);
    }

    public Client findByIdOrNom(String searchTerm) {
        Client client = clientDAOImpl.findById(searchTerm);
        if (client != null) return client;

        return clientDAOImpl.findbyName(searchTerm);
    }
}
