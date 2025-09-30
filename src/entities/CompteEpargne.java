package entities;

public final class CompteEpargne extends Compte {

    public double tauxInteret;

    public CompteEpargne(String idClient, int numero, double solde, double tauxInteret) {
        super(idClient, numero, solde, "EPARGNE");
        this.tauxInteret = tauxInteret;
    }

    public void appliquerInteret() {
        double interet = getSolde() * tauxInteret / 100;
        setSolde(getSolde() + interet);
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
}
