//import swing_1.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.*;
import java.nio.file.*;


public class Main {

    public static Reservierungsliste reservierungsliste;
    public static KompositumSerializer ks;
    public boolean reserviert;


    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidClassException {

        ks = new KompositumSerializer();

    try {
        // Versuche die Reservierungsliste aus der Datei GespeicherteListe.ser zu laden.
            reservierungsliste = ks.laden();
    }
    catch (Exception ice) {
        // Sollte das Laden fehlschlagen, erstelle eine neues Objekt Reservierungsliste:
        reservierungsliste = new Reservierungsliste();
    }

        Testklasse test = new Testklasse();
        reservierungsliste.alleReservierungenAusgeben();

        // Lade die GUI
        GUI gui = new GUI(reservierungsliste);




    }


}


