import java.io.IOException;
import java.util.*;

public class Main {

    public static Reservierungsliste reservierungsliste;
    public static KompositumSerializer ks;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        reservierungsliste = new Reservierungsliste();
        ks = new KompositumSerializer();
        //reservierungsliste = ks.laden();

        reservierungsliste.alleReservierungenAusgeben();
        Testklasse test = new Testklasse();
        //reservierungsliste.alleReservierungenAusgeben();

    }


}
