import java.io.IOException;
import java.util.Date;

public class Testklasse {
    public Date datum1;
    public Date datum2;
    public Date datum3;

    public Testklasse() throws IOException {

        datum1 = new Date(2023, 6, 1);
        datum2 = new Date(2023, 3, 9);
        datum3 = new Date(2023, 7, 24);

        Main.reservierungsliste.reservieren(datum1, "Hans", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum2, "Peter", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum3, "Max", Main.reservierungsliste.kofferliste[1]);

        //Main.reservierungsliste.stornieren(datum1, Main.reservierungsliste.kofferliste[1] );
        //Main.reservierungsliste.stornieren(datum2, Main.reservierungsliste.kofferliste[1] );
        //Main.reservierungsliste.stornieren(datum3, Main.reservierungsliste.kofferliste[1] );
    }


}

