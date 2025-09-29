package entities;

import java.util.UUID;

public sealed abstract class Compte permits CompteCourant, CompteEpargne {
    private String id;
    private String numero;
    private double solde;
    private String idClient;
    private String typeCompte;
    private double decouvertAutoriser;
    private double tauxInteret;

    public Compte(String idClient, String numero, double solde, String typeCompte) {
        this.id = UUID.randomUUID().toString();
        this.idClient = idClient;
        this.numero = numero;
        this.solde = solde;
        this.typeCompte = typeCompte;
    }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
    public String getId() { return id; }
}