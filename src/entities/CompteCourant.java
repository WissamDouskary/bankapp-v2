package entities;

public final class CompteCourant extends Compte {

    private double decouvertAutorise = 1000;

    public CompteCourant(String idClient, int numero, double solde, double decouvertAutorise) {
        super(idClient, numero, solde, "COURANT");
        this.decouvertAutorise = decouvertAutorise;
    }

    public CompteCourant(String id, String idClient, int numero, double solde, double decouvertAutorise) {
        super(id, idClient, numero, solde, "COURANT");
        this.decouvertAutorise = decouvertAutorise;
    }

    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }
}
