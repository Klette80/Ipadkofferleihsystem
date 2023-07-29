import java.io.IOException;
import java.util.*;
import java.nio.file.*;

public class Main {

    public static Reservierungsliste reservierungsliste;
    public static KompositumSerializer ks;
    public static  Benutzerliste benutzerliste;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        reservierungsliste = new Reservierungsliste();
        benutzerliste = new Benutzerliste();
        ks = new KompositumSerializer();

        // Der relative Dateipfad zu unserer vorhandenen Liste wird gespeichert.
        Path dateipfad = Paths.get("GespeicherteListe.ser");

        // Falls bereits eine Liste vorliegt, wird diese in die neu instanziierte Reservierungsliste geladen,
        // damit in der Vergangenheit getätigte Reservierungen verfügbar sind.
        if (Files.exists(dateipfad)) {
            reservierungsliste = ks.laden();
            //Wenn der angemeldete Benutzer mit gespeichert wurde, wird dieser nach dem Laden abgemeldet
            benutzerliste.benutzerAbmelden();
        }

        Testklasse test = new Testklasse();

    }


}
