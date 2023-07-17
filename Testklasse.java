import java.io.IOException;
import java.util.Date;

public class Testklasse {

    public Testklasse() throws IOException, ClassNotFoundException {

    // Erstelle ein Objekt der Klasse KompositumSerializer, um ein Abspeichern bzw. Laden
    // der Reservierungsliste zu ermöglichen.
    KompositumSerializer ks = new KompositumSerializer();

    // Initialisieren der Reservierungsliste
    Reservierungsliste reservierungsliste = new Reservierungsliste();

    //Beginn der Testreihe

    Date testdatum = new java.util.Date();
    Koffer koffer = new Koffer(0);

        System.out.println("Test 1: Durchführen einer Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0'");
    // Methode reservieren ausführen.
        reservierungsliste.reservieren(testdatum, "Max Mustermann", koffer);
        System.out.println("");

        System.out.println("Test 2: Überprüfen, ob Reservierung mit dem aktuellen Datum, Max Mustermann, Koffer 0' vorliegt.");
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

    // Speichere Reservierungsliste...
            ks.speichern(reservierungsliste);

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

    // Laden der Reservierungsliste
    Reservierungsliste geladeneListe = ks.laden();

        System.out.println("");

        System.out.println("Test 5: Überprüfen, ob Laden der Liste erfolgreich war, indem die Reservierung mit dem aktuellen Datum, Max Mustermann, Koffer 0 vorliegt.");
        System.out.println("Erwartete Meldung: Test erfolgreich");
    // Methode istReserviert ausführen
            if (geladeneListe.istReserviert(testdatum)) {
        System.out.println("Test erfolgreich");
    } else {
        System.out.println("Test fehlgeschlagen.");
    }
            System.out.println("");

    }
}

