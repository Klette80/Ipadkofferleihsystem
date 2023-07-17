import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

// Das Objekt wird nach der Initialisierung in der Lage sein, mithilfe seiner Methoden eine
// Reservierungsliste zu speichern bzw. zu laden. Dieser Prozess kann auf verschiedene Weisen
// durchgeführt werden, wir verwenden hier die Java-Möglichkeit des Serialisierens bzw. Deserialisierens
// von Objekten. Dabei wird ein Objekt zunächst auf seine Attribute zusammengeschrumpft und diese
// dann in einer Datei gespeichert.
// Damit dieser Prozess funktioniert, müssen alle serialisierbaren Objekte die Standarbibliothek
// "Serializable" implementieren.
public class KompositumSerializer {

    public KompositumSerializer() {}

    public void speichern(Reservierungsliste l) throws IOException {
        // Diese Methode serialisiert eine übergebene Reservierungsliste.
        // Die Methode muss einen möglichen kritischen Fehler beim Erstellen der Datei für die
        // Reservierungsliste abfangen können.
        try {
            // Versuche, einen Anschlusstream zu erstellen, der eine Datei zum Speichern der Daten
            // erstellt.
            FileOutputStream dateiAusgabe = new FileOutputStream("GespeicherteListe.ser");
            // Versuche, Objektausgabe mit dem Dateiausgabenfluss zu verketten.
            ObjectOutputStream objektAusgabe = new ObjectOutputStream(dateiAusgabe);

            // Der Objekctoutputstream (Objektausgabe) schreibt das übergebene Objekt in den filestream (Dateiausgabe)
            objektAusgabe.writeObject(l);

            // Der Outputstream bzw. die Datei wird geschlossen.
            objektAusgabe.close();
        }

        catch(IOException speichern){
            // Wenn eine Ausnahme oder ein Fehler auftritt, wird normalerweise eine sogenannte
            // "Stacktrace"-Information erzeugt. Diese Information gibt Auskunft darüber, welche Methoden
            // aufgerufen wurden und in welcher Reihenfolge sie aufgerufen wurden, bevor der Fehler
            // aufgetreten ist. Der Stacktrace ist wie eine Art "Verlauf" der Methodenaufrufe.
            // Wenn man printStackTrace() aufruft, wird der Stacktrace auf der Konsole ausgegeben.
            // Dabei werden die Methodennamen, Dateinamen und Zeilennummern angezeigt, um den Ablauf der
            // Methodenaufrufe zu verfolgen. Die Ausgabe enthält normalerweise die Fehlermeldung
            // sowie den Stacktrace.
                speichern.printStackTrace();
            // Falls dieser Vorgang schiefgeht, gib folgende (verständliche) Fehlermeldung an den Benutzer aus:
                throw new IOException("Fehler beim Erstellen der Datei.", speichern);
            }

    }

    public Reservierungsliste laden() throws IOException, ClassNotFoundException {
        // Diese Methode deserialisiert eine Reservierungsliste.
        // Die Methode muss einen möglichen kritischen Fehler beim Laden der Datei für die
        // Reservierungsliste abfangen können.

        // Wir versuchen, den Filstream mit einer Datei zu verketten. Existiert die Datei nicht,
        // wird sie automatisch erstellt.
        try {FileInputStream dateiEingabe = new FileInputStream("GespeicherteListe.ser");
             // Der Ojbektinputstream wird mit dem Filestream verkettet.
             ObjectInputStream objektEingabe = new ObjectInputStream(dateiEingabe);
            // Das eingelesene/geladene Objekt wird zurückgegeben und muss dazu als Reservierungslistenobjekt gecastet werden ()
            //return (Reservierungsliste) is.readObject();
            return (Reservierungsliste) objektEingabe.readObject();
        }
        catch (IOException | ClassNotFoundException laden) {
            laden.printStackTrace();
            if (laden instanceof ClassNotFoundException) {
                throw new IOException("Fehler beim Laden der Klasse.", laden);
            } else {
                throw new IOException("Fehler beim Laden der Datei.", laden);
            }
        }

    }
}
