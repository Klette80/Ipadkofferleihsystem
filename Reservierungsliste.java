import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;

public class Reservierungsliste implements Serializable {
    private Knoten root;
    public Koffer[] kofferliste; //public für Test
    private Array[] speicherArray;

    public Reservierungsliste() {
        root = new Endknoten();
        Koffer koffer = new Koffer(1);
        kofferliste = new Koffer[100];
        kofferliste[1] = koffer;
    }

    //Einen iPad-Koffer reservieren
    public void reservieren(Date datum, String name, Koffer koffer) throws IOException {
        //Prüfe, ob schon eine Reservierung vorliegt
        if (istReserviert(datum) == false) {
            Reservierung reservierung = new Reservierung(datum, name, koffer);
            root = root.reservieren(reservierung);
            Main.ks.speichern(Main.reservierungsliste);
        } else {
            System.out.println("An diesem Datum liegt schon eine Reservierung vor");
        }
    }

    //Eine vorhandene iPad-Koffer-Reservierung stornieren
    public void stornieren(Date datum, Koffer koffer) throws IOException {
        //Prüfe, ob zu löschender Datensatz root ist
        if (root.gibDaten().gibDatum() == datum) {
            System.out.println("Die Reservierung von " + root.gibDaten().gibName() + " am " + root.gibDaten().gibDatum() + " wurde gelöscht.");
            root = root.gibNaechster();
            Main.ks.speichern(Main.reservierungsliste);
        } else {
            root.stornieren(datum, koffer);
        }
    }

    public boolean istReserviert(Date datum) {
        // Prüfe, ob Koffer bereits reserviert ist
        return root.istReserviert(datum);

    }

    public void alleReservierungenAusgeben(){
        root.alleReservierungenAusgeben(1);
    }

    //Koffer hinzufügen
    public void neuerKoffer(int nummer) {
        if (kofferliste[nummer] != null) {
            System.out.println("Diese Koffernummer ist bereits vergeben");
        } else {
            Koffer koffer = new Koffer(nummer);
            kofferliste[nummer] = koffer;
            System.out.println("Der Koffer mit der Nummer " + nummer + " wurde angelegt.");
        }
    }

    //alle angelegenten Koffer anzeigen
    public void kofferAnzeigen() {
        System.out.println("Lister der zur Verfügung stehenden Koffer:");
        for (int i = 1; i < kofferliste.length; i++) {
            if (kofferliste[i] != null) {
                System.out.println("Koffernummer " + i);
            }
        }
    }

    //Koffer entfernen
    public void kofferEntfernen(int nummer) {
        if (kofferliste[nummer] == null) {
            System.out.println("Der Koffer mit der Nummer " + nummer + " existiert nicht.");
        } else {
            kofferliste[nummer] = null;
            System.out.println("Der Koffer mit der Nummer " + nummer + " wurde entfernt.");
        }
    }

}
