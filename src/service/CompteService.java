package service;

import dao.impl.CompteDAOImpl;
import entities.Compte;
import entities.CompteCourant;
import entities.CompteEpargne;

import java.util.List;
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

    public List<Compte> findAll(){
        return compteDaoImpl.findAllComptes();
    }

    public List<Compte> findByClientId(String searchTerm){
        List<Compte> comptes = findAll();

        return comptes.stream()
                .filter(e -> e.getIdClient().equals(searchTerm))
                .toList();
    }

    public Compte findByNumero(int searchTerm){
        List<Compte> comptes = findAll();
        Compte compte = null;

        compte = comptes.stream()
                .filter(e -> e.getNumero() == searchTerm)
                .findFirst()
                .orElse(null);

        return compte;
    }
}
