package util;

import java.util.Scanner;

public class NomberChecker {

    public int lireEntier(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrée invalide, veuillez saisir un nombre.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
