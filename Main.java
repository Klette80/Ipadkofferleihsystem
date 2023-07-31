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

        ks = new KompositumSerializer();
        bs = new BenutzerSerializer();

    try {
        // Versuche die Reservierungsliste aus der Datei GespeicherteListe.ser zu laden.
            reservierungsliste = ks.laden();
    }
    catch (Exception ice) {
        // Sollte das Laden fehlschlagen, erstelle eine neues Objekt Reservierungsliste:
        reservierungsliste = new Reservierungsliste();
    }
    try {
        // Folgt derselben Logik wie oben.
        benutzerliste = bs.laden();
    }
    catch (Exception e) {

        benutzerliste = new Benutzerliste();
    }

        Testklasse test = new Testklasse();
        reservierungsliste.alleReservierungenAusgeben();

        // Lade die GUI
        GUI gui = new GUI(reservierungsliste);

    }
}


