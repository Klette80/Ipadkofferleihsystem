public class Benutzerliste {
    private Node erster;
    private Benutzer angemeldeterBenutzer;
    private Benutzer neu;

    public Benutzerliste() {
        erster = new EndNode();
        angemeldeterBenutzer = null;
        neu = new Benutzer("Admin", "Admin", "admin", "admin");
        erster = erster.einfuegen(neu);
    }

    //Benutzer anmelden
    public void benutzerAnmelden(String benutzername, String passwort){
        if(angemeldeterBenutzer == null){
            Node aktuell = erster;
            while (aktuell.gibNaechster() != null){
                if(aktuell.gibInhalt().gibBenutzername() == benutzername && aktuell.gibInhalt().gibPasswort() == passwort){
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
    public void benutzerEinfuegen(String vorname, String name, String benutzername, String passwort){
        if(angemeldeterBenutzer == null){
            System.out.println("Es ist kein Benutzer angemeldet.");
        } else if(angemeldeterBenutzer.gibBenutzername() == "admin"){
            Benutzer neuerBenutzer = new Benutzer(vorname, name, benutzername, passwort);
            erster = erster.einfuegen(neuerBenutzer);
        } else {
            System.out.println("Neue Benutzer können nur vom Admin angelegt werden.");
        }
    }

    //Benutzer entfernen - nur für Admin möglich
    public void benutzerLoeschen(String benutzername){
        if(angemeldeterBenutzer == null){
            System.out.println("Es ist kein Benutzer angemeldet.");
        }else if(benutzername == "admin"){
            System.out.println("Der Admin kann nicht gelöscht werden.");
        } else if(angemeldeterBenutzer.gibBenutzername() == "admin"){
            if(erster.gibInhalt().gibBenutzername() == benutzername){
                System.out.println("Der Benutzer " + erster.gibInhalt().gibBenutzername() + " wurde gelöscht.");
                erster = erster.gibNaechster();
            } else {
                erster.loeschen(benutzername);
            }
        } else {
            System.out.println("Neue Benutzer können nur vom Admin angelegt werden.");
        }
    }

    //Benutzerpasswort ändern - nur für den angemeldeten Benutzer möglich
    public void benutzerPasswortAendern(String benutzer, String neuesPasswort){
        if(angemeldeterBenutzer == null){
            System.out.println("Es ist kein Benutzer angemeldet.");
        } else if(angemeldeterBenutzer.gibBenutzername() == benutzer){
            angemeldeterBenutzer.setzePasswort(neuesPasswort);
        } else {
            System.out.println("Das Passwort kann nur vom angemeldeten Benutzer geändert werden.");
        }
    }

    //Eine Liste aller Benutzer ausgeben
    public void benutzerlisteAusgeben(){
        erster.benutzerlisteAusgeben();
    }

    //Gib den Benutzernamen zurück
    public String gibNameAngemeldeterBenutzer(){
        if(angemeldeterBenutzer == null){
            return "Es ist kein Benutzer angelmeldet.";
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
}

