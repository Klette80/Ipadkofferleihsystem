import java.io.IOException;
import java.time.LocalDate;

public interface Knoten
{
    public Knoten reservieren(Reservierung inhalt) throws IOException;
    public void stornieren(LocalDate datum, int stunde, Koffer koffer) throws IOException;
    public Reservierung gibDaten();
    public Knoten gibNaechster();
    public boolean istReserviert(LocalDate datum, int stunde, Koffer koffer);
    public void alleReservierungenAusgeben(int i);
    public int reservierungenBenutzerAnzeigen(String name);

}

