import java.io.Serializable;
import java.util.Objects;
public class Koffer implements Serializable {
    private int nummer;

    public Koffer(int nummer){
        this.nummer=nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Koffer koffer = (Koffer) o;
        return nummer == koffer.nummer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nummer);
    }
}
