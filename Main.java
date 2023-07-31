import java.io.IOException;
import java.io.InvalidClassException;
import java.util.*;
import java.nio.file.*;

public class Main {

    public static Reservierungsliste reservierungsliste;
    public static BenutzerSerializer bs;
    public static KompositumSerializer ks;
    public static Benutzerliste benutzerliste;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidClassException {
        reservierungsliste = new Reservierungsliste();
        benutzerliste = new Benutzerliste();
        ks = new KompositumSerializer();

        // Der relative Dateipfad zu unserer vorhandenen Liste wird gespeichert.
        Path dateipfad = Paths.get("GespeicherteListe.ser");

        // Falls bereits eine Liste vorliegt, wird diese in die neu instanzierte Reservierungsliste geladen,
        // damit in der Vergangenheit getätigte Reservierungen verfügbar sind.
        if (Files.exists(dateipfad)) {
            reservierungsliste = ks.laden();
        }

        Testklasse_ohne_Benutzer test = new Testklasse_ohne_Benutzer();
        reservierungsliste.alleReservierungenAusgeben();
        System.out.println("Main: Ende");
        GUI gui = new GUI(reservierungsliste);


    }
}


