package entities.enums;

public enum TransactionType {
    VERSEMENT("Versement"),
    RETRAIT("Retrait"),
    VIREMENT("Virement");

    private final String displayName;

    TransactionType(String name){
        this.displayName = name;
    }

    public String getDisplayName(){
        return this.displayName;
    }
}