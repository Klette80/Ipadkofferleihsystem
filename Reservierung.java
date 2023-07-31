import java.io.Serializable;
//import java.util.Date;
import java.time.LocalDate;

public class Reservierung implements Serializable
{
    LocalDate datum;
    String name;
    Koffer koffer;

    public Reservierung(LocalDate datum, String name, Koffer koffer)
    {
        this.datum = datum;
        this.name = name;
        this.koffer = koffer;
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

}
