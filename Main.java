import java.io.IOException;
import java.util.*;
import java.nio.file.*;

public class Main {

    public static Reservierungsliste reservierungsliste;
    public static KompositumSerializer ks;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        reservierungsliste = new Reservierungsliste();
        ks = new KompositumSerializer();

        Path dateipfad = Paths.get("GespeicherteListe.ser");
        if (Files.exists(dateipfad)) {
            reservierungsliste = (Reservierungsliste) ks.laden();
        }

        //reservierungsliste.alleReservierungenAusgeben();
        Testklasse test = new Testklasse();
        reservierungsliste.alleReservierungenAusgeben();

    }


}
