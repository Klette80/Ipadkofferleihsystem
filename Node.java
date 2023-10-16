public interface Node
{
    public Node einfuegen(Benutzer inhalt);
    public void loeschen(String benutzername);
    public Benutzer gibInhalt();
    public Node gibNaechster();

    public int benutzerListeLaenge();
    public String benutzerlisteAusgeben();

    public boolean nutzerNameVorhanden(String name);


}

