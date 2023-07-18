import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservierung that = (Reservierung) o;
        return Objects.equals(datum, that.datum) &&
                Objects.equals(name, that.name) &&
                Objects.equals(koffer, that.koffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum, name, koffer);
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
