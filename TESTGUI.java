import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class TESTGUI {
    public TESTGUI(Reservierungsliste reservierungsliste, Benutzerliste benutzerliste) {
        final String pickedDate;

        //Logo für alle Fenster. Bild wird in Label importiert, Label in einem Logo_Panel als Panel-Option für Grid- und Border-Layout in anderen Panels. Größe festlegen über getScaledImage
        ImageIcon logo = new ImageIcon("ipadkoffer_logo.png");
        logo.setImage(logo.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));
        JLabel logo_label = new JLabel(logo);



        //Haupt-Fenster JDialog
        JDialog dialog_start = new JDialog();
        dialog_start.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog_start.setSize(300,300);

        //Start-Panel mit Nutzername und Passworteingabe. BorderLayout(): North, South, Est, West für oben, unten... Logo links bei allen Fenstern
        JPanel panel_start = new JPanel(new BorderLayout());
        panel_start.add(logo_label,BorderLayout.WEST);
        //Text oben im panel_start
        JPanel eingabe = new JPanel(new BorderLayout());
        JLabel lbl_1 = new JLabel("Nutzername und Passwort eingeben");
        eingabe.add(lbl_1,BorderLayout.NORTH);
        JPanel eingabe_1 = new JPanel();
        JTextField  tf_nutzer = new JTextField(20);
        JPasswordField tf_passwort = new JPasswordField(20);
        JButton btn_1 = new JButton("OK");
        eingabe_1.add(tf_nutzer);
        eingabe_1.add(tf_passwort);
        eingabe_1.add(btn_1);
        eingabe.add(eingabe_1, BorderLayout.CENTER);
        panel_start.add(eingabe,BorderLayout.EAST);

        dialog_start.add(panel_start);
        dialog_start.pack();
        dialog_start.setVisible(true);
//Aktion bei OK
        btn_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nutzer = tf_nutzer.getText();
                String pw = tf_passwort.getText();
                //Benutzer mit eingegebenen Daten anmelden
                benutzerliste.benutzerAnmelden(tf_nutzer.getText(), tf_passwort.getText());
                //benutzerliste.baRekursiv(tf_nutzer.getText(), tf_passwort.getText());
                //prüfe, ob Benutzer und PW stimmen. Wenn nicht: Pop-Up und Rückkehr zu Anmeldemaske, sonst User- oder admin GUI
                if (benutzerliste.gibAngemeldeterBenutzer() == null) {
                    JDialog falsch = new JDialog();
                    falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    falsch.setLocation(430, 100);
                    JPanel panel_falsch = new JPanel();
                    falsch.add(panel_falsch);
                    JLabel lbl_falsch = new JLabel("Nutzername und Passwort stimmen nicht überein");
                    lbl_falsch.setVisible(true);
                    panel_falsch.add(lbl_falsch);
                    falsch.pack();
                    falsch.setVisible(true);
                    tf_nutzer.setText("");
                    tf_passwort.setText("");

                }
                //Wenn Nutzer Admin ist: Admin Fenster öffnen
                if (tf_nutzer.getText().equals("admin")) {
                    //Start-Dialog unsichtbar machen, neuer Menu-Dialog
                    tf_nutzer.setText("");
                    tf_passwort.setText("");
                    dialog_start.setVisible(false);


                    JDialog dialog_menu_admin = new JDialog();
                    dialog_menu_admin.setSize(300, 300);
                    dialog_menu_admin.setVisible(true);

                    JPanel panel_menu_admin=new JPanel(new BorderLayout());

                    panel_menu_admin.add(new JLabel(logo),BorderLayout.WEST);

                    JPanel admin_action = new JPanel(new GridLayout(6,1));
                    JPanel panel_text = new JPanel();
                    JLabel lbl_menu = new JLabel("Hallo "+ benutzerliste.gibAngemeldeterBenutzer().gibVorname()+". Was willst du tun?");
                    panel_text.add(lbl_menu);
                    admin_action.add(panel_text);

                    JPanel admin_action_standard = new JPanel();
                    JButton reservieren = new JButton("Reservieren");
                    JButton stornieren = new JButton("Stornieren");
                    JButton passwort = new JButton("Passwort ändern");
                    admin_action_standard.add(reservieren);
                    admin_action_standard.add(stornieren);
                    admin_action_standard.add(passwort);
                    admin_action.add(admin_action_standard);

                    JPanel admin_user_action = new JPanel();
                    JButton benutzer_neu= new JButton("Neuen Benutzer anlegen");
                    JButton benutzer_weg=new JButton("Benutzer löschen");
                    JButton alle_benutzer = new JButton("alle Benutzer anzeigen");
                    admin_user_action.add(benutzer_neu);
                    admin_user_action.add(benutzer_weg);
                    admin_user_action.add(alle_benutzer);
                    admin_action.add(admin_user_action);

                    JPanel admin_koffer_action = new JPanel();
                    JButton koffer_neu = new JButton("Neuen Koffer anlegen");
                    JButton koffer_weg = new JButton("Koffer löschen");
                    JButton alle_koffer = new JButton("Alle Koffer anzeigen");
                    admin_koffer_action.add(koffer_neu);
                    admin_koffer_action.add(koffer_weg);
                    admin_koffer_action.add(alle_koffer);
                    admin_action.add(admin_koffer_action);

                    JPanel panel_abmelden = new JPanel();
                    JButton abmelden = new JButton("Abmelden");
                    panel_abmelden.add(abmelden);

                    admin_action.add(panel_abmelden);

                    panel_menu_admin.add(admin_action,BorderLayout.EAST);


                    dialog_menu_admin.add(panel_menu_admin);
                    dialog_menu_admin.pack();
                    dialog_menu_admin.setVisible(true);
                    //Bei Klick auf x: Abmelden und Start-Menu öffnen
                    dialog_menu_admin.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                            benutzerliste.benutzerAbmelden();
                            dialog_menu_admin.dispose();
                            dialog_start.setVisible(true);
                        }
                    });
                }
            }
        });
    }
}