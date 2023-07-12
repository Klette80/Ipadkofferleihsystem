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


    }



}
