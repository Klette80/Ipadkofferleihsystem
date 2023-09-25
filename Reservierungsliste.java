import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
//import java.util.Date;
import java.time.LocalDate;

public class Reservierungsliste implements Serializable {
    private static final long serialVersionUID = 7640516691716884831L;
    private Knoten root;
    private Koffer[] kofferliste;


    public Reservierungsliste() throws IOException {
        root = new Endknoten();
        Koffer koffer = new Koffer(1);
        kofferliste = new Koffer[1];
        kofferliste[0] = koffer;
    }

    //Einen iPad-Koffer reservieren
    public void reservieren(LocalDate datum, int stunde, String name, Koffer koffer) throws IOException {
        //Prüfe, ob schon eine Reservierung vorliegt

        if (istReserviert(datum, stunde, koffer) == false) {
            Reservierung reservierung = new Reservierung(datum, stunde, name, koffer);
            root = root.reservieren(reservierung);
            speichern();
        } else {
            System.out.println("An diesem Datum liegt schon eine Reservierung vor");
        }
    }

    //Eine vorhandene iPad-Koffer-Reservierung stornieren
    public void stornieren(LocalDate datum, int stunde, Koffer koffer) throws IOException {
        //Prüfe, ob zu löschender Datensatz root ist
        if (root.gibDaten().gibDatum() == datum && root.gibDaten().gibStunde() == stunde) {
            System.out.println("Die Reservierung von " + root.gibDaten().gibName() + " am " + root.gibDaten().gibDatum() + " in Stunde " + root.gibDaten().gibStunde() + " wurde gelöscht.");
            root = root.gibNaechster();
            speichern();
        } else {
            root.stornieren(datum, stunde, koffer);
        }
    }

    public boolean istReserviert(LocalDate datum, int stunde, Koffer koffer) {
        // Prüfe, ob Koffer bereits reserviert ist
        return root.istReserviert(datum, stunde, koffer);

    }

    public void alleReservierungenAusgeben(){
        root.alleReservierungenAusgeben(1);
    }

    //alle Reservierungen eines Benutzers anzeigen
    public Reservierung[] reservierungenBenutzerAnzeigen(String name){
        //Anzahl der Reservierungen ermitteln
        int anzahl = root.reservierungenBenutzerAnzeigen(name);
        //Wenn keine Reservierungen gefunden wurden
        if (anzahl == 0){
            System.out.println("Sie haben keine Reservierungen.");
            return null;
        } else {
            //Array mit der Anzahl der gefundenen Reservierungen anlegen
            Reservierung reservierungen[] = new Reservierung[anzahl];
            Knoten aktuellerKnoten = root;
            int einfuegeposition = 0;
            //Array mit den Reservierungen befüllen
            while (aktuellerKnoten.gibDaten() != null && einfuegeposition < anzahl) {
                if (aktuellerKnoten.gibDaten().gibName().compareTo(name) == 0) {
                    reservierungen[einfuegeposition] = aktuellerKnoten.gibDaten();
                    einfuegeposition++;
                }
                aktuellerKnoten = aktuellerKnoten.gibNaechster();
            }
            return reservierungen;
        }
    }

    //Koffer hinzufügen
    public void neuerKoffer(int nummer) throws IOException {
        Koffer neuerKoffer = new Koffer(nummer);
        //Wenn die Kofferliste leer ist, wird der Koffer als erster eingefügt
        if (kofferliste[0] == null) {
            kofferliste[0] = neuerKoffer;
            System.out.println("Der Koffer mit der Nummer " + kofferliste[0].gibNummer() + " wurde angelegt.");
        } else {
            //Prüft, ob ein Koffer mit der gleichen Nummer bereits existiert
            boolean kofferBereitsVorhanden = false;
            for (int i = 0; i < kofferliste.length; i++){
                if(kofferliste[i].gibNummer() == nummer){
                    kofferBereitsVorhanden = true;
                    System.out.println("Diese Koffernummer ist bereits vergeben");
                    break;
                }
            }
            //Wenn der Koffer noch nicht existiert
            if (kofferBereitsVorhanden == false) {
                Koffer[] neueKofferliste = new Koffer[kofferliste.length + 1];
                int zaehlerEinfuegepositon = 0;
                int zaehlerPositionKofferarray = 0;
                //Füge die Koffer aufsteigend in die neue Kofferliste ein
                while (zaehlerEinfuegepositon < neueKofferliste.length) {
                    //Wenn die Koffernummer im Kofferarray kleiner ist als die neue Koffernummer, füge den Koffer aus dem Kofferarray ein
                    if (zaehlerPositionKofferarray < kofferliste.length && kofferliste[zaehlerPositionKofferarray].gibNummer() < nummer) {
                        neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                        zaehlerEinfuegepositon++;
                        zaehlerPositionKofferarray++;
                    } else {
                        //Ansonsten füge den neuen Koffer ein
                        neueKofferliste[zaehlerEinfuegepositon] = neuerKoffer;
                        System.out.println("Der Koffer mit der Nummer " + neueKofferliste[zaehlerEinfuegepositon].gibNummer() + " wurde angelegt.");
                        zaehlerEinfuegepositon++;
                        //Fülle den Rest der neuen Kofferliste mit dem restlichen Inhalt des Kofferarrays auf
                        while (zaehlerEinfuegepositon < neueKofferliste.length) {
                            neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                            zaehlerEinfuegepositon++;
                            zaehlerPositionKofferarray++;
                        }
                    }
                }
                //Mache das die neue Kofferliste zur Kofferliste
                kofferliste = neueKofferliste;
                speichern();
            }
        }
    }

    //Koffer entfernen
    public void kofferEntfernen(int nummer) throws IOException {
        if (kofferliste.length == 1) {
            if (kofferliste[0].gibNummer() == nummer) {
                System.out.println("Der Koffer kann nicht gelöscht werden. In der Auswahlliste muss mindestens ein Koffer enthalten sein.");
            } else {
                System.out.println("Der Koffer mit der Nummer " + nummer + " existiert nicht.");
            }
        } else {
            boolean kofferBereitsVorhanden = false;
            for (int i = 0; i < kofferliste.length; i++) {
                if (kofferliste[i].gibNummer() == nummer) {
                    kofferBereitsVorhanden = true;
                    break;
                }
            }
            if (kofferBereitsVorhanden == true) {
                Koffer[] neueKofferliste = new Koffer[kofferliste.length - 1];
                int zaehlerEinfuegepositon = 0;
                int zaehlerPositionKofferarray = 0;
                //Füge die Koffer ohne den zu löschenden Koffer aufsteigend in die neue Kofferliste ein
                while (zaehlerPositionKofferarray < kofferliste.length) {
                    //Wenn die Koffernummer nicht die zu löschende Nummer ist
                    if (kofferliste[zaehlerPositionKofferarray].gibNummer() != nummer) {
                        neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                        zaehlerEinfuegepositon++;
                        zaehlerPositionKofferarray++;
                    } else {
                        //Überspringe den zu löschenden Koffer
                        System.out.println("Der Koffer mit der Nummer " + nummer + " wurde entfernt.");
                        zaehlerPositionKofferarray++;
                        //Fülle den Rest der neuen Kofferliste mit dem restlichen Inhalt des Kofferarrays auf
                        while (zaehlerPositionKofferarray < kofferliste.length) {
                            neueKofferliste[zaehlerEinfuegepositon] = kofferliste[zaehlerPositionKofferarray];
                            zaehlerEinfuegepositon++;
                            zaehlerPositionKofferarray++;
                        }
                    }
                }
                //Mache das die neue Kofferliste zur Kofferliste
                kofferliste = neueKofferliste;
                speichern();
            } else {
                System.out.println("Der Koffer mit der Nummer " + nummer + " existiert nicht.");
            }
        }
    }

    //alle angelegten Koffer anzeigen
    public void kofferAnzeigen() {
        System.out.println("Liste der zur Verfügung stehenden Koffer:");
        for (int i = 0; i < kofferliste.length; i++) {
            if (kofferliste[i] != null) {
                System.out.println("Koffernummer " + kofferliste[i].gibNummer());
            }
        }
    }

    private void speichern () throws IOException {
        Main.ks.speichern(Main.reservierungsliste);
    }
public Koffer[] gibKofferListe(){
        return kofferliste;
}
}
