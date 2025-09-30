package entities;

import java.util.UUID;

public sealed abstract class Compte permits CompteCourant, CompteEpargne {
    private String id;
    private int numero;
    private double solde;
    private String idClient;
    private String typeCompte;

    public Compte(String idClient, int numero, double solde, String typeCompte) {
        this.id = UUID.randomUUID().toString();
        this.idClient = idClient;
        this.numero = numero;
        this.solde = solde;
        this.typeCompte = typeCompte;
    }

    public Compte(String id, String idClient, int numero, double solde, String typeCompte){
        this.id = id;
        this.idClient = idClient;
        this.numero = numero;
        this.solde = solde;
        this.typeCompte = typeCompte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    @Override
    public String toString() {
        return "id = '" + getId() + '\'' +
                ", numero = " + getNumero() +
                ", solde = " + getSolde() +
                ", idClient = '" + getIdClient() + '\'' +
                ", typeCompte = '" + getTypeCompte() + '\''
                ;
    }
}