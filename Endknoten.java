import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

public class Endknoten implements Knoten, Serializable
{
    public Endknoten() { }

    public Knoten reservieren(Reservierung reservierung) throws IOException {
        //Main.ks.speichern(Main.reservierungsliste);
        return new Datenknoten(this, reservierung);
    }

    public void stornieren(LocalDate datum, int stunde, Koffer koffer){
        System.out.println("Die Reservierung wurde nicht gefunden");
    }

    public Reservierung gibDaten() {
        return null;
    }

    public Knoten gibNaechster() {
        return null;
    }

    @Override
    public boolean istReserviert(LocalDate datum, int stunde, Koffer koffer) {
        return false;
    }

    public void alleReservierungenAusgeben(int i){ System.out.println("Ende der Liste."); }

    public int reservierungenBenutzerAnzeigen(String name){
        return 0;
    }

    @Override
    public void setzeNaechster(Knoten naechster) {

    }

}
