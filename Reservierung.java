import java.io.Serializable;
import java.util.Date;

public class Reservierung implements Serializable
{
    Date datum;
    String name;
    Koffer koffer;

    public Reservierung(Date datum, String name, Koffer koffer)
    {
        this.datum = datum;
        this.name = name;
        this.koffer = koffer;
    }

    public Date gibDatum(){
        return datum;
    }

    public String gibName() {
        return name;
    }

    public Koffer gibKoffer() {
        return koffer;
    }
}
