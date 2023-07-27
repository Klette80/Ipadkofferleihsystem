public class Benutzer
{
    private String vorname;
    private String name;
    private String benutzername;
    private String passwort;

    public Benutzer(String vorname, String name, String benutzername, String passwort)
    {
        this.vorname = vorname;
        this.name = name;
        this.benutzername = benutzername;
        this.passwort = passwort;
    }

    public String gibVorname(){
        return vorname;
    }

    public String gibName(){
        return name;
    }

    public String gibBenutzername(){
        return benutzername;
    }

    public String gibPasswort(){
        return passwort;
    }

    public void setzePasswort(String neuesPasswort){
        passwort = neuesPasswort;
        System.out.println("Das Passwort wurde geändert.");
    }
}
