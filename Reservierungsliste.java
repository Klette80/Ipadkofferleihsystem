public class Reservierungsliste {
    private Knoten root;
    public Reservierungsliste()
    {
        root = new Endknoten();
    }

    public void reservieren(Date datum, String name, Koffer koffer) {
        //Prüfe, ob schon eine Reservierung vorliegt
        if (istReserviert(datum )== false) {
            Reservierung reservierung = new Reservierung(datum, name, koffer);
            root=root.einfuegen(reservierung);
        }
        else{
            System.out.println("An diesem Datum liegt schon eine Reservierung vor");
        }
    }

    public void stornieren(Date datum, Koffer koffer){
        //Prüfe, ob zu löschender Datensatz root ist
        if(root.gibData().gibDatum() == datum){
            System.out.println("Die Reservierung von " + root.gibData().gibName() + " am " + root.gibData().gibDatum() + " wurde gelöscht.");
            root = root.gibNaechster();
        } else {
            root.stornieren(datum, koffer);
        }
    }

    public boolean istReserviert(Date datum){
        return false;
    }
}
