import java.io.IOException;
import java.time.LocalDate;

public interface Knoten
{
    public Knoten reservieren(Reservierung inhalt) throws IOException;
    public void stornieren(LocalDate datum, Koffer koffer) throws IOException;
    public Reservierung gibDaten();
    public Knoten gibNaechster();
    public boolean istReserviert(LocalDate datum, Koffer koffer);
    public void alleReservierungenAusgeben(int i);

}

