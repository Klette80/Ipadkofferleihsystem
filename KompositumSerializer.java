import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class KompositumSerializer {
    private FileOutputStream filestream;
    private ObjectOutputStream os;

    public KompositumSerializer() throws IOException {
        try {
            filestream = new FileOutputStream("GespeicherteListe.ser");
            os = new ObjectOutputStream(filestream);
        } catch (IOException e) {
            throw new IOException("Fehler beim Erstellen der Datei", e);
        }
    }

    public void speichern(Reservierungsliste l) throws IOException {
        os.writeObject(l);
        os.close();
    }

    public Reservierungsliste laden() throws IOException, ClassNotFoundException {
        try (FileInputStream fileStream = new FileInputStream("GespeicherteListe.ser");
             ObjectInputStream is = new ObjectInputStream(fileStream)) {
            return (Reservierungsliste) is.readObject();
        }
    }
}
