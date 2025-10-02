package service;

import dao.impl.CompteDAOImpl;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;

import java.util.*;
import java.util.stream.Collectors;

public class CompteService {
    private CompteDAOImpl compteDaoImpl = new CompteDAOImpl();

    public void createCompte(Compte compte){
        if(compte.getTypeCompte().equals("COURANT") && compte instanceof CompteCourant){
            CompteCourant compte1 = (CompteCourant) compte;
            Compte compteCourant = new CompteCourant(compte1.getIdClient(), compte1.getNumero(), compte1.getSolde(), compte1.getDecouvertAutorise());
            compteDaoImpl.save(compteCourant);
        }else{
            CompteEpargne compte2 = (CompteEpargne) compte;
            Compte compteCourant = new CompteEpargne(compte2.getIdClient(), compte2.getNumero(), compte2.getSolde(), compte2.getTauxInteret());
            compteDaoImpl.save(compteCourant);
        }
    }

    public void updateCompte(Compte compte){
        if(compte.getTypeCompte().equals("COURANT") && compte instanceof CompteCourant){
            CompteCourant compteCourant = (CompteCourant) compte;
            Compte compte1 = new CompteCourant(compte.getId(), compte.getIdClient(), compte.getNumero(), compte.getSolde(), compteCourant.getDecouvertAutorise());
            compteDaoImpl.update(compte1);
        }else{
            CompteEpargne compteEpargne = (CompteEpargne) compte;
            Compte compte2 = new CompteEpargne(compte.getId(), compte.getIdClient(), compte.getNumero(), compte.getSolde(), compteEpargne.getTauxInteret());
            compteDaoImpl.update(compte2);
        }
    }

    public Compte findById(String id){
        return compteDaoImpl.findById(id);
    }

    public List<Compte> findAll() {
        return compteDaoImpl.findAllComptes();
    }

    public List<Compte> findByClientId(String clientId) {
        return findAll().stream().filter(e -> e.getIdClient().equals(clientId)).toList();
    }

    public Compte findByNumero(int searchTerm){
        return findAll().stream()
                .filter(e -> e.getNumero() == searchTerm)
                .findFirst()
                .orElse(null);
    }

    public Map<String, Optional<Compte>> findMaxAndMin(){
        Map<String, Optional<Compte>> maxAndMin = new HashMap<>();
        List<Compte> comptes = findAll();

        Optional<Compte> maxCompte = comptes.stream()
                .max(Comparator.comparingDouble(Compte::getSolde));

        Optional<Compte> minCompte = comptes.stream()
                .min(Comparator.comparingDouble(Compte::getSolde));
                ;
        if(!maxCompte.isEmpty() && !minCompte.isEmpty()){
            maxAndMin.put("Max", maxCompte);
            maxAndMin.put("Min", minCompte);
        }
        return maxAndMin;
    }
}
