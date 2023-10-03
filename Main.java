import java.io.IOException;
import java.io.InvalidClassException;
import java.time.LocalDate;
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
            if(benutzerliste.gibAngemeldeterBenutzer()!=null){
                benutzerliste.benutzerAbmelden();
            }


      // Testklasse test = new Testklasse();

        // Lade die GUI
 GUI gui = new GUI(reservierungsliste, benutzerliste);



    }
}


