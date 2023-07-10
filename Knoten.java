import java.util.Date;

public interface Knoten
{
    public Knoten einfuegen(Reservierung inhalt);
    public void stornieren(Date datum, Koffer koffer);
    public Reservierung gibData();
    public Knoten gibNaechster();
}
