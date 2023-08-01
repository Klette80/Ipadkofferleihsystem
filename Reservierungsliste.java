import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
//import java.util.Date;
import java.time.LocalDate;

public class Reservierungsliste implements Serializable {
    private static final long serialVersionUID = 7640516691716884831L;
    private Knoten root;
    public Koffer[] kofferliste;
    private Array[] speicherArray;
    public String gewaehltesDatum;

    public Reservierungsliste() throws IOException {
        root = new Endknoten();
        Koffer koffer = new Koffer(1);
        kofferliste = new Koffer[1];
        kofferliste[0] = koffer;
        KompositumSerializer ks = new KompositumSerializer();
        ks.speichern(this);
    }

    //Einen iPad-Koffer reservieren
    public void reservieren(LocalDate datum, String name, Koffer koffer) throws IOException {
        //Prüfe, ob schon eine Reservierung vorliegt
        if (istReserviert(datum, koffer) == false) {
            Reservierung reservierung = new Reservierung(datum, name, koffer);
            root = root.reservieren(reservierung);
            speichern();
        } else {
            System.out.println("An diesem Datum liegt schon eine Reservierung vor");
        }
    }

    //Eine vorhandene iPad-Koffer-Reservierung stornieren
    public void stornieren(LocalDate datum, Koffer koffer) throws IOException {
        //Prüfe, ob zu löschender Datensatz root ist
        if (root.gibDaten().gibDatum() == datum) {
            System.out.println("Die Reservierung von " + root.gibDaten().gibName() + " am " + root.gibDaten().gibDatum() + " wurde gelöscht.");
            root = root.gibNaechster();
            speichern();
        } else {
            root.stornieren(datum, koffer);
        }
    }

    public boolean istReserviert(LocalDate datum, Koffer koffer) {
        // Prüfe, ob Koffer bereits reserviert ist
        return root.istReserviert(datum, koffer);

    }

    public void alleReservierungenAusgeben(){
        root.alleReservierungenAusgeben(1);
    }

    //Koffer hinzufügen
    public void neuerKoffer(int nummer) throws IOException {
        Koffer neuerKoffer = new Koffer(nummer);
        if (kofferliste[0] == null) {
            kofferliste[0] = neuerKoffer;
            System.out.println("Der Koffer mit der Nummer " + kofferliste[0].gibNummer() + " wurde angelegt.");
        } else {
            boolean kofferBereitsVorhanden = false;
            for (int i = 0; i < kofferliste.length; i++){
                if(kofferliste[i].gibNummer() == nummer){
                    kofferBereitsVorhanden = true;
                    System.out.println("Diese Koffernummer ist bereits vergeben");
                    break;
                }
            }
            if (kofferBereitsVorhanden == false){
                Koffer[] neueKofferliste = new Koffer[kofferliste.length +1];
                int zaehlerEinfuegepositon = 0;
                int zaehlerPositionKofferarray = 0;
                while (zaehlerEinfuegepositon < neueKofferliste.length){
                    if (zaehlerPositionKofferarray < kofferliste.length && kofferliste[zaehlerPositionKofferarray].gibNummer() < nummer){
                        neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                        zaehlerEinfuegepositon++;
                        zaehlerPositionKofferarray++;
                    } else {
                        neueKofferliste[zaehlerEinfuegepositon] = neuerKoffer;
                        System.out.println("Der Koffer mit der Nummer " + neueKofferliste[zaehlerEinfuegepositon].gibNummer() + " wurde angelegt.");
                        zaehlerEinfuegepositon++;
                        while (zaehlerEinfuegepositon < neueKofferliste.length){
                            neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                            zaehlerEinfuegepositon++;
                            zaehlerPositionKofferarray++;
                        }
                    }
                }
                kofferliste = neueKofferliste;
                speichern();
            }
        }
    }

    //alle angelegenten Koffer anzeigen
    public void kofferAnzeigen() {
        System.out.println("Liste der zur Verfügung stehenden Koffer:");
        for (int i = 0; i < kofferliste.length; i++) {
            if (kofferliste[i] != null) {
                System.out.println("Koffernummer " + kofferliste[i].gibNummer());
            }
        }
    }

    //Koffer entfernen
    public void kofferEntfernen(int nummer) throws IOException {
        if (kofferliste[nummer] == null) {
            System.out.println("Der Koffer mit der Nummer " + nummer + " existiert nicht.");
        } else {
            kofferliste[nummer] = null;
            System.out.println("Der Koffer mit der Nummer " + nummer + " wurde entfernt.");
        }
        speichern();
    }

    public void speichern() throws IOException {
        Main.ks.speichern(Main.reservierungsliste);
    }

    //public int gibKofferzanzahl(){
    //    return kofferanzahl;
    //}

}
