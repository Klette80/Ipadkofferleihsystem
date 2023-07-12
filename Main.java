public class Main {


    public static void main(String[] args) {}
        Reservierungsliste reservierungsliste = new Reservierungsliste();

    Date testdatum;
    testdatum = new java.util.Date();
    Koffer koffer = new Koffer(0);
        System.out.println("Test 1: Initialisieren einer Reservierungsliste");
    // Initialisieren einer Reservierungsliste
    Reservierungsliste rs = new Reservierungsliste();
        System.out.println("Test 2: Durchführen einer Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0'");
    // Methode reservieren ausführen.
        rs.reservieren(testdatum, koffer, "Max Mustermann");
        System.out.println("Test 3: Überprüfen, ob Reservierung mit den Daten '2023-11-11, Max Mustermann, Koffer 0' vorliegt.");
    // Methode istReserviert ausführen
        if (rs.istReserviert(testdatum)) {
        System.out.println("Test erfolgreich");
    }
        else {
        System.out.println("Test fehlgeschlagen.");
    }

   }
