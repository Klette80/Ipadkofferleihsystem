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

        Main.reservierungsliste.alleReservierungenAusgeben();
        Main.benutzerliste.benutzerlisteAusgeben();
        System.out.println("Starte Testklasse");

        Main.reservierungsliste.reservieren(datum1, "Hans", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum2, "Peter", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum3, "Peter", Main.reservierungsliste.kofferliste[1]);

        //Main.reservierungsliste.stornieren(datum1, Main.reservierungsliste.kofferliste[1] );
        //Main.reservierungsliste.stornieren(datum2, Main.reservierungsliste.kofferliste[1] );
        Main.reservierungsliste.stornieren(datum3, Main.reservierungsliste.kofferliste[1] );

        Main.benutzerliste.benutzerEinfuegen("Daniel", "Liebscher", "lida", "passwort");
        Main.benutzerliste.benutzerAnmelden("admin", "admin");
        Main.benutzerliste.benutzerEinfuegen("Daniel", "Simon", "sida", "passwort");
        Main.benutzerliste.benutzerAbmelden();
        Main.benutzerliste.benutzerAnmelden("sida", "passwort");
        Main.reservierungsliste.reservieren(datum3, Main.benutzerliste.gibNameAngemeldeterBenutzer(), Main.reservierungsliste.kofferliste[1]);


        Main.reservierungsliste.alleReservierungenAusgeben();
        Main.benutzerliste.benutzerlisteAusgeben();
    }


}

