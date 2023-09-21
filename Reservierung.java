import java.io.Serializable;
//import java.util.Date;
import java.time.LocalDate;

public class Reservierung implements Serializable
{
    /* To-Do:
    Datenkapslung: Attribute auf private setzen?
     */
    private LocalDate datum;
    private String name;
    private Koffer koffer;
    private int schulstunde;


    public Reservierung(LocalDate datum, int stunde, String name, Koffer koffer)
    {
        this.datum = datum;
        this.name = name;
        this.koffer = koffer;
        this.schulstunde = stunde;
    }

    public LocalDate gibDatum(){
        return datum;
    }
    public String gibName() {
        return name;
    }

    public Koffer gibKoffer() {
        return koffer;
    }

    public int gibStunde() {return schulstunde;}
}
