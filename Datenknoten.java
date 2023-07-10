import java.util.Date;

public class Datenknoten implements Knoten
{
    private Knoten naechster;
    private Reservierung data;

    public Datenknoten(Knoten next, Reservierung inhalt)

    {
        naechster = next;
        data = inhalt;
    }

    public Knoten einfuegen(Reservierung reservierung) {
        //Datum des Inputs "reservierung" mit dem Datum des vorhandenen ("data") vergleichen
        //wenn Datum von data VOR neuem reservierungs-Datum -> mache beim Nachfolger weiter
        if (reservierung.datum.compareTo(data.datum)>0) {
            naechster = naechster.einfuegen(reservierung);
            return this;
        }
        if (reservierung.datum.compareTo(data.datum)<0){
            //Wenn das data Datum NACH inhalt-Datum ist: Erzeuge neuen Datenknoten mit "altem Inhalt", verzeigere neu und fülle den alten Knoten mit neuem Inhalt
            Datenknoten neu = new Datenknoten(naechster,data);
            this.naechster= neu;
            this.data=reservierung;
        }
        return this;
    }

    public void stornieren(Date datum, Koffer koffer){
        //Wenn das Datum und der Koffer vom Nächster der gesuchte Datensatz ist --> verzeigere um,
        //sonst führe die Methode stonieren auf dem Nächsten auf.
        if((naechster.gibData() != null && naechster.gibData().gibDatum() == datum && naechster.gibData().gibKoffer() == koffer)){
            System.out.println("Die Reservierung von " + naechster.gibData().gibName() + " am " + naechster.gibData().gibDatum() + " wurde gelöscht.");
            naechster = naechster.gibNaechster();
        } else {
            naechster.stornieren(datum, koffer);
        }
    }

    public Reservierung gibData() {
        return data;
    }

    public Knoten gibNaechster() {
        return naechster;
    }
}
