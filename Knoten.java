import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public interface Knoten
{
    public Knoten reservieren(Reservierung inhalt) throws IOException;
    public void stornieren(Date datum, Koffer koffer) throws IOException;
    public Reservierung gibDaten();
    public Knoten gibNaechster();
    public boolean istReserviert(Date datum, Koffer koffer);
    public void alleReservierungenAusgeben(int i);

}

