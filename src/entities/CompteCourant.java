package entities;

public final class CompteCourant extends Compte {

    private double decouvertAutorise = 1000;

    public CompteCourant(String idClient, String numero, double solde, double decouvertAutorise) {
        super(idClient, numero, solde, "COURANT");
        this.decouvertAutorise = decouvertAutorise;
    }
}
