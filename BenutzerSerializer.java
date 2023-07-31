import java.io.*;

// Das Objekt wird nach der Initialisierung in der Lage sein, mithilfe seiner Methoden eine
// Benutzerliste zu speichern bzw. zu laden. Dieser Prozess kann auf verschiedene Weisen
// durchgeführt werden, wir verwenden hier die Java-Möglichkeit des Serialisierens bzw. Deserialisierens
// von Objekten. Dabei wird ein Objekt zunächst auf seine Attribute zusammengeschrumpft und diese
// dann in einer Datei gespeichert.
// Damit dieser Prozess funktioniert, müssen alle serialisierbaren Objekte die Standartbibliothek
// "Serializable" implementieren.
public class BenutzerSerializer {

    public BenutzerSerializer() {}

    public void speichern(Benutzerliste l) throws IOException {
        // Diese Methode serialisiert eine übergebene Reservierungsliste.
        // Die Methode muss einen möglichen kritischen Fehler beim Erstellen der Datei für die
        // Reservierungsliste abfangen können.
        try {
            // Versuche, einen Anschlussstream zu erstellen, der eine Datei zum Speichern der Daten
            // erstellt.
            FileOutputStream dateiAusgabe = new FileOutputStream("BenutzerListe.ser");
            // Versuche, Objektausgabe mit dem Dateiausgabenfluss zu verketten.
            ObjectOutputStream objektAusgabe = new ObjectOutputStream(dateiAusgabe);

            // Der Objektoutputstream (Objektausgabe) schreibt das übergebene Objekt in den filestream (Dateiausgabe)
            objektAusgabe.writeObject(l);

            // Der Outputstream bzw. die Datei wird geschlossen.
            objektAusgabe.close();
        }

        catch(Exception speichern){
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

    public Benutzerliste laden() throws IOException, ClassNotFoundException {
        // Diese Methode deserialisiert eine Reservierungsliste.
        // Die Methode muss einen möglichen kritischen Fehler beim Laden der Datei für die
        // Reservierungsliste abfangen können.

        // Wir versuchen, den Filestream mit einer Datei zu verketten. Existiert die Datei nicht,
        // wird sie automatisch erstellt.
        try {FileInputStream dateiEingabe = new FileInputStream("BenutzerListe.ser");
            // Der Objektinputstream wird mit dem Filestream verkettet.
            ObjectInputStream objektEingabe = new ObjectInputStream(dateiEingabe);
            // Das eingelesene/geladene Objekt wird zurückgegeben und muss dazu als Reservierungslistenobjekt gecastet werden ()
            //return (Reservierungsliste) is.readObject();
            return (Benutzerliste) objektEingabe.readObject();
        }
        catch (Exception laden) {
            laden.printStackTrace();
            throw new IOException("Fehler beim Laden der Datei.", laden);
        }
    }
}
