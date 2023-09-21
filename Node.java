public interface Node
{
    public Node einfuegen(Benutzer inhalt);
    public void loeschen(String benutzername);
    public Benutzer gibInhalt();
    public Node gibNaechster();
    public void benutzerlisteAusgeben();
    public Benutzer baRekursiv(String benutzername, String passwort);
}

