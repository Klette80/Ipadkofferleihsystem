import java.io.Serializable;

public class DataNode implements Node, Serializable
{
    private Node naechster;
    private Benutzer inhalt;

    public DataNode(Node naechster, Benutzer inhalt)
    {
        this.naechster = naechster;
        this.inhalt = inhalt;
    }

    public Node einfuegen(Benutzer neuerBenutzer){
        if(neuerBenutzer.gibBenutzername().equals(inhalt.gibBenutzername())) {
            System.out.println("Benutzername ist bereis vergeben.");
            return this;
        }else if(neuerBenutzer.gibBenutzername().compareTo(inhalt.gibBenutzername()) > 0){
            naechster = naechster.einfuegen(neuerBenutzer);
            return this;
        } else {
            DataNode neu = new DataNode(naechster, inhalt);
            this.naechster = neu;
            this.inhalt = neuerBenutzer;
            System.out.println("Neuer Nutzer wurde erfolgreich angelegt.");
        }
        return this;
    }

    public void loeschen(String benutzername){
        //Wenn der Benutzername vom Nächsten der gesuchte Datensatz ist --> verzeigere um,
        //sonst führe die Methode loeschen auf dem Nächsten aus.
        if((naechster.gibInhalt() != null && naechster.gibInhalt().gibBenutzername().equals(benutzername))){
            System.out.println("Der Benutzer " + naechster.gibInhalt().gibBenutzername() + " wurde gelöscht.");
            naechster = naechster.gibNaechster();
        } else {
            naechster.loeschen(benutzername);
        }
    }

    public Benutzer gibInhalt(){
        return inhalt;
    }

    public Node gibNaechster(){
        return naechster;
    }

    public void benutzerlisteAusgeben(){
        System.out.println("Vorname: " + inhalt.gibVorname() + " Nachname: " + inhalt.gibName() + " Benutzername: " + inhalt.gibBenutzername());
        naechster.benutzerlisteAusgeben();
    }
}
