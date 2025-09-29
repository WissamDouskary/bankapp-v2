package dao;

import entities.Compte;

import java.util.List;

public interface CompteDAO {
    void save(Compte compte);
    void update(Compte compte);
    void delete(String id);
    Compte findById(String id);
    List<Compte> findByClient(String idClient);
}
