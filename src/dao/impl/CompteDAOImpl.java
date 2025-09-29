package dao.impl;

import dao.CompteDAO;
import entities.Compte;

import java.util.List;

public class CompteDAOImpl implements CompteDAO {
    @Override
    public void save(Compte compte) {

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
