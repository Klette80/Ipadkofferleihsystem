import java.util.Date;

public class Endknoten implements Knoten
{
    public Endknoten()
    {

    }

    public Knoten einfuegen(Reservierung reservierung) {
        return new Datenknoten(this, reservierung);
    }

    public void stornieren(Date datum, Koffer koffer){
        System.out.println("Die Reservierung wurde nicht gefunden");
    }

    public Reservierung gibData() {
        return null;
    }

    public Knoten gibNaechster() {
        return null;
    }
}
