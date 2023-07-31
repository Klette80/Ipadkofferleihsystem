import java.io.IOException;
import java.time.chrono.ChronoLocalDate;
//import java.util.Date;
//https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html#of-int-int-int-int-int-
import java.time.LocalDate;

public class Testklasse_ohne_Benutzer {
    public LocalDate datum1;
    public LocalDate datum2;
    public LocalDate datum3;

    //public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute)
    public LocalDate datum4;

    public Testklasse_ohne_Benutzer() throws IOException {

        datum1 = LocalDate.of(2023, 6, 1);
        datum2 = LocalDate.of(2023, 3, 9);
        datum3 = LocalDate.of(2023, 10, 2);
        datum4 = LocalDate.of(2024, 9, 29);
        Main.reservierungsliste.reservieren(datum1, "Hans", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum2, "Peter", Main.reservierungsliste.kofferliste[1]);
        Main.reservierungsliste.reservieren(datum3, "Max", Main.reservierungsliste.kofferliste[1]);


        Main.reservierungsliste.alleReservierungenAusgeben();
       /*
        Main.benutzerliste.benutzerlisteAusgeben();

        Main.benutzerliste.benutzerAnmelden("admin", "admin");
        Main.benutzerliste.benutzerEinfuegen("Daniel", "Liebscher", "lida", "passwort");
        Main.benutzerliste.benutzerEinfuegen("Daniel", "Simon", "sida", "passwort");
        Main.benutzerliste.benutzerAbmelden();
        Main.benutzerliste.benutzerAnmelden("sida", "passwort");
        Main.reservierungsliste.reservieren(datum3, Main.benutzerliste.gibNameAngemeldeterBenutzer(), Main.reservierungsliste.kofferliste[1]);
        Main.benutzerliste.benutzerLoeschen("sida");
        Main.benutzerliste.benutzerAbmelden();
        Main.benutzerliste.benutzerAnmelden("admin", "admin");
        //Main.benutzerliste.benutzerLoeschen("sida");
        Main.benutzerliste.benutzerLoeschen("admin");

        //Main.reservierungsliste.reservieren(datum1, Main.benutzerliste.gibNameAngemeldeterBenutzer(), Main.reservierungsliste.kofferliste[1]);
*/
        //Main.reservierungsliste.alleReservierungenAusgeben();
        //Main.benutzerliste.benutzerlisteAusgeben();
    }


}

