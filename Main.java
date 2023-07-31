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

            reservierungsliste = ks.laden();


            benutzerliste = bs.laden();

        Testklasse test = new Testklasse();
        reservierungsliste.alleReservierungenAusgeben();

        // Lade die GUI
        GUI gui = new GUI(reservierungsliste);

    }
}


