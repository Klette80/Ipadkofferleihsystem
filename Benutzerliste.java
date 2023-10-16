import java.io.IOException;
import java.io.Serializable;

public class Benutzerliste implements Serializable {
    private Node erster;
    private Benutzer angemeldeterBenutzer;

    public Benutzerliste() throws IOException {
        erster = new EndNode();
        angemeldeterBenutzer = null;
        Benutzer neu = new Benutzer("Admin", "Admin", "admin", "admin");
        erster = erster.einfuegen(neu);
    }

    //Benutzer anmelden
    public void benutzerAnmelden(String benutzername, String passwort){
        if(angemeldeterBenutzer == null){
            Node aktuell = erster;
            while (aktuell.gibNaechster() != null){
                if(aktuell.gibInhalt().gibBenutzername().equals(benutzername) && aktuell.gibInhalt().gibPasswort().equals(passwort)){
                    angemeldeterBenutzer = aktuell.gibInhalt();
                    break;
                } else {
                    aktuell = aktuell.gibNaechster();
                }
            }
            if(angemeldeterBenutzer == null){
                System.out.println("Der Benutzername oder das Passwort wurden nicht gefunden.");
            } else {
                System.out.println("Sie sind als " + angemeldeterBenutzer.gibBenutzername() + " angemeldet.");
            }
        } else {
            System.out.println("Sie sind bereits als " + angemeldeterBenutzer.gibBenutzername() + " angemeldet.");
        }
    }

    //Benutzer abmelden
    public void benutzerAbmelden(){
        angemeldeterBenutzer = null;
    }

    //Benutzer hinzufügen - nur für Admin möglich
    public void benutzerEinfuegen(String vorname, String name, String benutzername, String passwort) throws IOException {
        String admin = "admin";
        if(angemeldeterBenutzer == null){
            System.out.println("Es ist kein Benutzer angemeldet.");
        } else if(angemeldeterBenutzer.gibBenutzername().equals(admin)){
            Benutzer neuerBenutzer = new Benutzer(vorname, name, benutzername, passwort);
            erster = erster.einfuegen(neuerBenutzer);
            speichern();
            //System.out.println("Neuer Nutzer erfolgreich angelegt.");
        } else {
            System.out.println("Neue Benutzer können nur vom Admin angelegt werden.");
        }
    }

    //Benutzer entfernen - nur für Admin möglich
    public void benutzerLoeschen(String benutzername) throws IOException {
        String admin = "admin";
        if(angemeldeterBenutzer == null){
            System.out.println("Es ist kein Benutzer angemeldet.");
        }else if(benutzername.equals(admin)){
            System.out.println("Der Admin kann nicht gelöscht werden.");
        } else if(angemeldeterBenutzer.gibBenutzername().equals(admin)){
            if(erster.gibInhalt().gibBenutzername().equals(benutzername)){
                System.out.println("Der Benutzer " + erster.gibInhalt().gibBenutzername() + " wurde gelöscht.");
                erster = erster.gibNaechster();
                speichern();
            } else {
                erster.loeschen(benutzername);
                speichern();
            }
        } else {
            System.out.println("Neue Benutzer können nur vom Admin angelegt werden.");
        }
    }

    //Eine Liste aller Benutzer ausgeben
    public String benutzerlisteAusgeben(){
        return erster.benutzerlisteAusgeben();
    }

    //Gib den Benutzernamen zurück
    public String gibNameAngemeldeterBenutzer(){
        if(angemeldeterBenutzer == null){
            return "Es ist kein Benutzer angemeldet.";
        } else {
            return angemeldeterBenutzer.gibBenutzername();
        }
    }

    //Gibt den angemeldeten Benutzer zurück
    public Benutzer gibAngemeldeterBenutzer(){
        return angemeldeterBenutzer;
    }

    //Gibt den das erste Objekt in der Benutzerliste zurück
    public Node gibErster(){
        return erster;
    }

    //speichert die Benutzerliste bei Änderungen
    private void speichern() throws IOException {
        Main.bs.speichern(Main.benutzerliste);
    }

}


