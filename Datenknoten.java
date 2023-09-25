import java.io.IOException;
import java.io.Serializable;
//import java.util.Date;
import java.time.LocalDate;

public class Datenknoten implements Knoten, Serializable {
    private Knoten naechster;
    private Reservierung daten;

    public Datenknoten(Knoten naechster, Reservierung inhalt) {
        this.naechster = naechster;
        daten = inhalt;
    }

    public Knoten reservieren(Reservierung reservierung) throws IOException {
        // Datum des Inputs "reservierung" mit dem Datum des vorhandenen ("data") vergleichen
        // wenn Datum von data VOR neuem Reservierungsdatum -> mache beim Nachfolger weiter
        if (reservierung.gibDatum().compareTo(daten.gibDatum()) > 0) {
            naechster = naechster.reservieren(reservierung);
            return this;
        }
        //wenn das Datum gleich ist, vergleiche die Stunden
        if(reservierung.gibDatum().compareTo(daten.gibDatum()) == 0){
           //bei kleinerer Stunde -> mache bei nächstem Knoten weiter
            if(reservierung.gibStunde()> daten.gibStunde()){
                naechster = naechster.reservieren(reservierung);
                return this;
            }
            //bei größerer Stunde -> umverzeigern
            if(reservierung.gibStunde()< daten.gibStunde()){
                Datenknoten neu = new Datenknoten(naechster, daten);
                this.naechster = neu;
                this.daten = reservierung;
                }
        }
        if (reservierung.gibDatum().compareTo(daten.gibDatum()) < 0) {
            //Wenn das data Datum NACH inhalt-Datum ist: Erzeuge neuen Datenknoten mit "altem Inhalt", verzeigere neu und fülle den alten Knoten mit neuem Inhalt
            Datenknoten neu = new Datenknoten(naechster, daten);
            this.naechster = neu;
            this.daten = reservierung;

        }
        return this;
    }

    public void stornieren(LocalDate datum, int stunde, Koffer koffer) throws IOException {
        // Wenn das Datum, die Stunde und der Koffer von Nächster der gesuchte Datensatz ist --> verzeigere um,
        // sonst führe die Methode stornieren auf dem Nächsten auf.
        if ((naechster.gibDaten() != null && naechster.gibDaten().gibStunde() == stunde && naechster.gibDaten().gibDatum().compareTo(datum) == 0 && naechster.gibDaten().gibKoffer() == koffer)) {
            System.out.println("Die Reservierung von " + naechster.gibDaten().gibName() + " am " + naechster.gibDaten().gibDatum() + " in Stunde " + naechster.gibDaten().gibStunde() + " wurde gelöscht.");
            naechster = naechster.gibNaechster();
        } else {
            naechster.stornieren(datum, stunde, koffer);
        }
    }

    public void alleReservierungenAusgeben(int i){
        System.out.println(i + ". Reservierung: Datum: " + daten.gibDatum() + ", Schulstunde: " + daten.gibStunde() + ", Name: " + daten.gibName() + ", Koffer mit der Nummer: " + daten.gibKoffer().gibNummer());
        int j = i + 1;
        naechster.alleReservierungenAusgeben(j);
    }

    public int reservierungenBenutzerAnzeigen(String name){
        int i = 0;
        if (daten.gibName().compareTo(name)==0){
            i = 1;
        }
        return i + naechster.reservierungenBenutzerAnzeigen(name);
    }

    public Reservierung gibDaten() {
        return daten;
    }

    public Knoten gibNaechster() {
        return naechster;
    }

    @Override
    public boolean istReserviert(LocalDate datum, int stunde, Koffer koffer) {
        if (daten.gibDatum().compareTo(datum) == 0 && daten.gibStunde() == stunde && daten.gibKoffer().gibNummer() == koffer.gibNummer()) {
            return true;
        } else {
            return naechster.istReserviert(datum, stunde, koffer);
        }
    }
}
