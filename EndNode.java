import java.io.Serializable;

public class EndNode implements Node, Serializable {

    public EndNode() {

    }

    public Node einfuegen(Benutzer inhalt) {
        return new DataNode(this, inhalt);
    }

    public void loeschen(String benutzername) {
        System.out.println("Benutzername ist nicht in der Liste enthalten.");
    }

    public Benutzer gibInhalt() {
        return null;
    }

    public Node gibNaechster() {
        return null;
    }

    public String benutzerlisteAusgeben() {
        return "";
    }
    public int benutzerListeLaenge(){
        return 1;
    };

    @Override
    public boolean nutzerNameVorhanden(String name) {
        return false;
    }
}
