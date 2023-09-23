import java.io.Serializable;

public class EndNode implements Node, Serializable {

    public EndNode() {

    }

    public Node einfuegen(Benutzer inhalt) {
        return new DataNode(this, inhalt);
    }

    public void loeschen(String benutzername) {
        System.out.println("Benutzname ist nicht in der Liste enthalten.");
    }

    public Benutzer gibInhalt() {
        return null;
    }

    public Node gibNaechster() {
        return null;
    }

    public void benutzerlisteAusgeben() {
        System.out.println("Ende der Liste");
    }

    public Benutzer baRekursiv(String benutzername, String passwort) {
        return null;
    }
}
