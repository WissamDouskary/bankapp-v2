package entities;

public final class CompteEpargne extends Compte {

    public final double tauxInteret;

    public CompteEpargne(String idClient, String numero, double solde, double tauxInteret) {
        super(idClient, numero, solde, "EPARGNE");
        this.tauxInteret = tauxInteret;
    }

    public void appliquerInteret() {
        double interet = getSolde() * tauxInteret / 100;
        setSolde(getSolde() + interet);
    }
}
