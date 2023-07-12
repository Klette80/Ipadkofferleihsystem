import java.util.Date;

public class Datenknoten implements Knoten {
    private Knoten naechster;
    private Reservierung daten;

    public Datenknoten(Knoten naechster, Reservierung inhalt) {
        this.naechster = naechster;
        daten = inhalt;
    }

    public Knoten reservieren(Reservierung reservierung) {
        //Datum des Inputs "reservierung" mit dem Datum des vorhandenen ("data") vergleichen
        //wenn Datum von data VOR neuem reservierungs-Datum -> mache beim Nachfolger weiter
        if (reservierung.datum.compareTo(daten.datum) > 0) {
            naechster = naechster.reservieren(reservierung);
            return this;
        }
        if (reservierung.datum.compareTo(daten.datum) < 0) {
            //Wenn das data Datum NACH inhalt-Datum ist: Erzeuge neuen Datenknoten mit "altem Inhalt", verzeigere neu und fülle den alten Knoten mit neuem Inhalt
            Datenknoten neu = new Datenknoten(naechster, daten);
            this.naechster = neu;
            this.daten = reservierung;
        }
        return this;
    }

    public void stornieren(Date datum, Koffer koffer) {
        //Wenn das Datum und der Koffer vom Nächster der gesuchte Datensatz ist --> verzeigere um,
        //sonst führe die Methode stonieren auf dem Nächsten auf.
        if ((naechster.gibDaten() != null && naechster.gibDaten().gibDatum() == datum && naechster.gibDaten().gibKoffer() == koffer)) {
            System.out.println("Die Reservierung von " + naechster.gibDaten().gibName() + " am " + naechster.gibDaten().gibDatum() + " wurde gelöscht.");
            naechster = naechster.gibNaechster();
        } else {
            naechster.stornieren(datum, koffer);
        }
    }

    public Reservierung gibDaten() {
        return daten;
    }

    public Knoten gibNaechster() {
        return naechster;
    }

    @Override
    public boolean istReserviert(Date datum) {
        if (daten.gibDatum().compareTo(datum) == 0) {
            return true;
        } else {
            naechster.istReserviert(datum);
        }
        return false;
    }
}
