import java.util.*;

public class Main {


    public static void main(String[] args) {
        Reservierungsliste reservierungsliste = new Reservierungsliste();

        //Test geht hier los

        Date testdatum = new java.util.Date();
        Date datum1 = new Date(2023, 07, 04);
        Koffer koffer = new Koffer(0);

        System.out.println("Test 1: Initialisieren einer Reservierungsliste");
        // Initialisieren einer Reservierungsliste
        // Reservierungsliste rs = new Reservierungsliste();
        System.out.println("Test 2: Durchführen einer Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0'");

        // Methode reservieren ausführen.
        reservierungsliste.reservieren(testdatum, "Max Mustermann", koffer);
        System.out.println("Test 3: Überprüfen, ob Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0' vorliegt.");

        // Methode istReserviert ausführen
        if (reservierungsliste.istReserviert(testdatum)) {
            System.out.println("Test erfolgreich");
        } else {
            System.out.println("Test fehlgeschlagen.");
        }

        // Methode reservieren mit dem gleichen Datum noch einmal ausführen.
        System.out.println("Test 4: Überprüfen, ob Reservierung mit den selben Daten aus Test 3 möglich ist.");
        System.out.println("Erwartete Meldung: An diesem Datum liegt schon eine Reservierung vor.");
        reservierungsliste.reservieren(testdatum, "Max Mustermann", koffer);

        // Methode stornieren ausführen.
        reservierungsliste.stornieren(testdatum, koffer);
        System.out.println("Test 5: Überprüfen, ob die Reservierung storniert werden kann.");
        System.out.println("Erwartete Meldung: Stornierung erfolgreich.");

        // Methode istReserviert ausführen
        if (reservierungsliste.istReserviert(testdatum)) {
            System.out.println("Stornierung fehlgeschlagen");
        } else {
            System.out.println("Stornierung erfolgreich.");
        }




    }



}
