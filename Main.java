import java.io.IOException;
import java.util.*;
import java.nio.file.*;

public class Main {

    public static Reservierungsliste reservierungsliste;
    public static KompositumSerializer ks;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        reservierungsliste = new Reservierungsliste();
        ks = new KompositumSerializer();

        // Der relative Dateipfad zu unserer vorhandenen Liste wird gespeichert.
        Path dateipfad = Paths.get("GespeicherteListe.ser");

        // Falls bereits eine Liste vorliegt, wird diese in die neu instanzierte Reservierungsliste geladen,
        // damit in der Vergangenheit getätigte Reservierungen verfügbar sin.
        if (Files.exists(dateipfad)) {
            reservierungsliste = (Reservierungsliste) ks.laden();
        }

        Testklasse test = new Testklasse();
        reservierungsliste.alleReservierungenAusgeben();

    }


}
