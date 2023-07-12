import java.util.*;

public class Main {


    public static void main(String[] args) {
        Reservierungsliste reservierungsliste = new Reservierungsliste();

        //Test geht hier los

        Date testdatum = new java.util.Date();
        Koffer koffer = new Koffer(0);

        System.out.println("Test 1: Durchführen einer Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0'");
        // Methode reservieren ausführen.
        reservierungsliste.reservieren(testdatum, "Max Mustermann", koffer);
        System.out.println("");

        System.out.println("Test 2: Überprüfen, ob Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0' vorliegt.");
        // Methode istReserviert ausführen
        if (reservierungsliste.istReserviert(testdatum)) {
            System.out.println("Test erfolgreich");
        } else {
            System.out.println("Test fehlgeschlagen.");
        }
        System.out.println("");

        // Methode reservieren mit dem gleichen Datum noch einmal ausführen.
        System.out.println("Test 3: Überprüfen, ob Reservierung mit den selben Daten aus Test 2 möglich ist.");
        System.out.println("Erwartete Meldung: An diesem Datum liegt schon eine Reservierung vor.");
        System.out.println("Ergebnis:");
        reservierungsliste.reservieren(testdatum, "Max Mustermann", koffer);
        System.out.println("");

        // Methode stornieren ausführen.
        System.out.println("Test 4: Überprüfen, ob die Reservierung storniert werden kann.");
        System.out.println("Erwartete Meldung: Stornierung erfolgreich.");
        System.out.println("Ergebnis:");
        reservierungsliste.stornieren(testdatum, koffer);

        // Methode istReserviert ausführen
        if (reservierungsliste.istReserviert(testdatum)) {
            System.out.println("Stornierung fehlgeschlagen");
        } else {
            System.out.println("Stornierung erfolgreich.");
        }




    }



}
