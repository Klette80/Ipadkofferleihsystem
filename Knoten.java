import java.io.Serializable;
import java.util.Date;

public interface Knoten
{
    public Knoten reservieren(Reservierung inhalt);
    public void stornieren(Date datum, Koffer koffer);
    public Reservierung gibDaten();
    public Knoten gibNaechster();
    public boolean istReserviert(Date datum);
}

