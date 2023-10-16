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


public class GUI {

    public GUI(Reservierungsliste reservierungsliste, Benutzerliste benutzerliste) {
        final String pickedDate;

        //Logo für alle Fenster. Bild wird in Label importiert, Label in einem Logo_Panel als Panel-Option für Grid- und Border-Layout in anderen Panels. Größe festlegen über getScaledImage. Nutzen als new JLabel(logo)
       ImageIcon logo = new ImageIcon("ipadkoffer_logo.png");
        logo.setImage(logo.getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));




        //Haupt-Fenster JDialog
        JDialog dialog_start = new JDialog();
        dialog_start.setTitle("IPad-Koffer-Leihsystem: Anmelden");
        dialog_start.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog_start.setSize(300,300);

        //Start-Panel mit Nutzername und Passworteingabe. BorderLayout(): North, South, Est, West für oben, unten... Logo links bei allen Fenstern
        JPanel panel_start = new JPanel(new BorderLayout());
        panel_start.add(new JLabel(logo),BorderLayout.WEST);
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
        JButton beenden = new JButton("Beenden");
        eingabe_1.add(beenden,BorderLayout.SOUTH);
        panel_start.add(eingabe,BorderLayout.EAST);



        dialog_start.add(panel_start);
        dialog_start.pack();
        dialog_start.setVisible(true);

        //Action bei Beenden
        beenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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
                if(tf_nutzer.getText().equals("admin")){
                    //Start-Dialog unsichtbar machen, neuer Menu-Dialog
                    tf_nutzer.setText("");
                    tf_passwort.setText("");
                    dialog_start.setVisible(false);

                    JDialog dialog_menu_admin = new JDialog();
                    dialog_menu_admin.setTitle("IPad-Koffer-Leihsystem: Hauptmenu");
                    dialog_menu_admin.setSize(300,300);
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
                    //Aktionen bei Buttons
                    reservieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu-Dialog unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Fenster für Reservieren
                            JDialog dialog_reservieren = new JDialog();
                            dialog_reservieren.setTitle("IPad-Koffer reservieren");
                            //Bei x: Fenster schließen, Admin-Menu öffnen
                            dialog_reservieren.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_reservieren.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });

                            JPanel panel_reservieren=new JPanel(new BorderLayout());
                            panel_reservieren.setVisible(true);
                            //Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_logo = new ImageIcon("koffer.png");
                            koffer_logo.setImage(koffer_logo.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                            panel_reservieren.add(new JLabel(koffer_logo),BorderLayout.WEST);

                            JPanel panel_reservieren_menu = new JPanel();
                            JLabel lbl_reservieren = new JLabel("Für Reservieren Koffer wählen");
                            //Kofferliste aus Reservierungsliste
                            String[] koffers= new String[Main.reservierungsliste.gibKofferListe().length];
                            for (int j = 0;j<reservierungsliste.gibKofferListe().length;j++){
                                koffers[j] = "Koffer " + Main.reservierungsliste.gibKofferListe()[j].gibNummer();
                            }
                            //Drop-Dowm-Menü für Kofferliste
                            final JComboBox<String> cb = new JComboBox<String>(koffers);
                            JButton btn_reservieren = new JButton("OK");
                            JButton btn_reservieren_back = new JButton("zurück");

                            panel_reservieren_menu.add(lbl_reservieren);
                            panel_reservieren_menu.add(cb);
                            panel_reservieren_menu.add(btn_reservieren);
                            panel_reservieren_menu.add(btn_reservieren_back);

                            panel_reservieren.add(panel_reservieren_menu,BorderLayout.CENTER);
                            dialog_reservieren.add(panel_reservieren);
                            dialog_reservieren.pack();
                            dialog_reservieren.setVisible(true);
                            //Aktion bei zurück: Fenster verschrotten, Admin-Menu wieder sichtbar machen
                            btn_reservieren_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_reservieren.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });

                            //Aktion bei OK: Kalender öffnen und Datum wählen, Schulstunden-Pop-up-Fenster öffnen
                            btn_reservieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String gewaehltesDatum;
                                    gewaehltesDatum = new Kalender(dialog_menu_admin, reservierungsliste, Main.reservierungsliste.gibKofferListe()[cb.getSelectedIndex()]).setPickedDate();
                                    //Aus Datum-String mache int-Datum
                                    int year = (1000 * (Character.getNumericValue(gewaehltesDatum.charAt(6)))) + (100 * Character.getNumericValue(gewaehltesDatum.charAt(7))) + (10 * Character.getNumericValue(gewaehltesDatum.charAt(8))) + (Character.getNumericValue(gewaehltesDatum.charAt(9)));
                                    int month = (10 * (Character.getNumericValue(gewaehltesDatum.charAt(3)))) + (Character.getNumericValue(gewaehltesDatum.charAt(4)));
                                    int day = (10 * Character.getNumericValue(gewaehltesDatum.charAt(0))) + Character.getNumericValue(gewaehltesDatum.charAt(1));

                                    LocalDate pickedDate = LocalDate.of(year, month, day);

                                    //Fenster für Schulstunde erzeugen und öffnen
                                    JDialog dialog_schulstunde = new JDialog();
                                    dialog_schulstunde.setLocation(dialog_menu_admin.getX()+50,dialog_menu_admin.getY()+200);
                                    //Bei Klick auf x
                                    dialog_schulstunde.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            super.windowClosing(e);
                                            //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                            dialog_reservieren.dispose();
                                            dialog_schulstunde.dispose();
                                            dialog_menu_admin.setVisible(true);
                                        }
                                    });
                                    JPanel panel_schulstunde=new JPanel();
                                    panel_schulstunde.setVisible(true);
                                    JLabel lbl_schulstunde = new JLabel("Schuldstunde am "+gewaehltesDatum+" wählen");
                                    panel_schulstunde.add(lbl_schulstunde);
                                    for(int i =0;i<10;i++){
                                        final int gewaehlte_stunde = i;
                                        JButton btn_i=new JButton("Stunde "+i );
                                        btn_i.setBackground(Color.white);
                                        //wenn eine Reservierung vorliegt, mache den Button rot
                                        if(reservierungsliste.istReserviert(pickedDate,gewaehlte_stunde,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()])){
                                            btn_i.setBackground(Color.red);
                                        }
                                        panel_schulstunde.add(btn_i);
                                        btn_i.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                //Prüfe, ob eine Reservierung vorliegt: Wenn ja: Pop-Up: "Keine Reservierung möglich, zurück zu Koffer-Auswahl-Fenster, sonst: Mit Datum und Namen reservieren
                                                if(reservierungsliste.istReserviert(pickedDate,gewaehlte_stunde,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()])) {
                                                    dialog_schulstunde.dispose();
                                                    JDialog end = new JDialog();
                                                    end.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end.setLocation(800, 100);
                                                    JPanel panel_end = new JPanel();
                                                    end.add(panel_end);
                                                    JLabel lbl_end = new JLabel("Keine Reservierung in dieser Stunde möglich");
                                                    lbl_end.setVisible(true);
                                                    panel_end.add(lbl_end);
                                                    end.pack();
                                                    end.setVisible(true);
                                                    dialog_reservieren.setVisible(true);

                                                }
                                                else {
                                                    try {
                                                        reservierungsliste.reservieren(pickedDate,gewaehlte_stunde,nutzer,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()]);

                                                    } catch (IOException ex) {
                                                        throw new RuntimeException(ex);
                                                    }
                                                    JDialog end_reservieren = new JDialog();
                                                    end_reservieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end_reservieren.setLocation(900, 100);
                                                    JPanel panel_end_reservieren = new JPanel();
                                                    end_reservieren.add(panel_end_reservieren);
                                                    JLabel lbl_end_reservieren = new JLabel("Koffer " + reservierungsliste.gibKofferListe()[cb.getSelectedIndex()].gibNummer() + " für " + nutzer + " am Datum " + gewaehltesDatum + " in Stunde "+ gewaehlte_stunde+" reserviert.");
                                                    lbl_end_reservieren.setVisible(true);
                                                    panel_end_reservieren.add(lbl_end_reservieren);
                                                    end_reservieren.pack();
                                                    end_reservieren.setVisible(true);
                                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Hauptframe wieder sichtbar machen
                                                    dialog_reservieren.dispose();
                                                    dialog_schulstunde.dispose();
                                                    dialog_menu_admin.setVisible(true);

                                                }

                                            }
                                        });
                                    }
                                    JButton btn_stunde_back=new JButton("Zurück");
                                    btn_stunde_back.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            //Stunden Fenster schließen, Admin-Menu wieder sichtbar machen
                                            dialog_schulstunde.dispose();

                                        }
                                    });
                                    panel_schulstunde.add(btn_stunde_back);
                                    dialog_schulstunde.add(panel_schulstunde);
                                    dialog_schulstunde.pack();
                                    dialog_schulstunde.setVisible(true);


                                }
                            });

                        }
                    });
                    stornieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Fenster für Stornieren
                            JDialog dialog_stornieren = new JDialog();
                            dialog_stornieren.setTitle("Reservierung stornieren");
                            //Bei x: Fenster schließen, Admin-Menu öffnen
                            dialog_stornieren.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_stornieren.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_stornieren = new JPanel(new BorderLayout());
                            //Durchgestrichener Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_cross_logo = new ImageIcon("koffer_cross.png");
                            koffer_cross_logo.setImage(koffer_cross_logo.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                            panel_stornieren.add(new JLabel(koffer_cross_logo),BorderLayout.WEST);
                            panel_stornieren.setVisible(true);
                          JPanel panel_stornieren_menu = new JPanel();

                            JLabel lbl_stornieren = new JLabel("Koffer und Datum zum Stornieren wählen, mit OK bestätigen");

                            //Drop Down Menu für getätigte Reservierungen, wenn keine Reservierungen vorhanden, Pop-Up und zurück zu Menu
                            if (reservierungsliste.reservierungenBenutzerAnzeigen(nutzer) == null) {
                                JDialog end_no_res = new JDialog();
                                end_no_res.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_res.setLocation(800, 100);
                                JPanel panel_end_stornieren = new JPanel();
                                end_no_res.add(panel_end_stornieren);
                                JLabel lbl_end_stornieren = new JLabel("Es sind keine Reservierungen auf deinen Namen vorhanden");
                                lbl_end_stornieren.setVisible(true);
                                panel_end_stornieren.add(lbl_end_stornieren);
                                end_no_res.pack();
                                end_no_res.setVisible(true);

                                //Stornieren Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                dialog_stornieren.dispose();
                               dialog_menu_admin.setVisible(true);
                            }
                            else{
                            String[] reservierungen = new String[reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length];

                            for (int i = 0; i < reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length; i++) {
                                reservierungen[i] = "Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibKoffer().gibNummer() + ", Datum: " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibDatum()+" Stunde " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibStunde();
                            }
                            final JComboBox<String> cb_reservierungen = new JComboBox<String>(reservierungen);
                            JButton btn_stornieren = new JButton("OK");
                            JButton btn_back=new JButton("Zurück");


                            panel_stornieren_menu.add(lbl_stornieren);
                            panel_stornieren_menu.add(cb_reservierungen);
                            panel_stornieren_menu.add(btn_stornieren);
                            panel_stornieren_menu.add(btn_back);
                            panel_stornieren.add(panel_stornieren_menu,BorderLayout.EAST);
                            dialog_stornieren.add(panel_stornieren);
                            dialog_stornieren.pack();
                            dialog_stornieren.setVisible(true);

                            btn_stornieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Stornieren
                                    String storniert = new String("Reservierung von Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer().gibNummer() + " am " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum() + " storniert.");
                                    try {
                                        reservierungsliste.stornieren(reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum(),reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibStunde(), reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer());
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    //Pop-up Bestätigung, Stornieren Fenster plattmachen, Admin-Menu wieder sichtbar machen
                                    JDialog end_stornieren = new JDialog();
                                    end_stornieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                    end_stornieren.setLocation(800, 100);
                                    JPanel panel_end_stornieren = new JPanel();
                                    end_stornieren.add(panel_end_stornieren);
                                    JLabel lbl_end_stornieren = new JLabel(storniert);
                                    lbl_end_stornieren.setVisible(true);
                                    panel_end_stornieren.add(lbl_end_stornieren);
                                    end_stornieren.pack();
                                    end_stornieren.setVisible(true);

                                    //Stornieren Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                    dialog_stornieren.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }

                            });
                            btn_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Stornieren Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                    dialog_stornieren.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });


                        }
                    }
                    });
                    passwort.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Neues Fenster zur neuen Passworteingabe öffnen
                            JDialog dialog_passwort=new JDialog();
                            dialog_passwort.setTitle("Passwort ändern");
                            //Bei x: Fenster schließen, Admin-Menu öffnen
                            dialog_passwort.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_passwort.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_passwort = new JPanel();
                            panel_passwort.setVisible(true);
                            JLabel lbl_pw_alt = new JLabel("Altes Passwort eingeben");
                            JPasswordField tf_pw_alt = new JPasswordField(20);
                            JLabel lbl_pw_neu = new JLabel("Neues Passwort eingeben");
                            JPasswordField tf_pw_neu = new JPasswordField(20);
                            JLabel lbl_pw_neu_2 = new JLabel("Neues Passwort wiederholen");
                            JPasswordField tf_pw_neu_2 = new JPasswordField(20);
                            JButton btn_passwort = new JButton("OK");
                            JButton btn_passwort_back = new JButton("zurück");
                            panel_passwort.add(lbl_pw_alt);
                            panel_passwort.add(tf_pw_alt);
                            panel_passwort.add(lbl_pw_neu);
                            panel_passwort.add(tf_pw_neu);
                            panel_passwort.add(lbl_pw_neu_2);
                            panel_passwort.add(tf_pw_neu_2);
                            panel_passwort.add(btn_passwort);
                            panel_passwort.add(btn_passwort_back);

                            dialog_passwort.add(panel_passwort);
                            dialog_passwort.pack();
                            dialog_passwort.setVisible(true);

                            //Aktion bei zurück: Fenster verschrotten, Admin-Menu sichtbar machen
                            btn_passwort_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_passwort.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            //Aktion bei OK
                            btn_passwort.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Passwort prüfen: passt -> neues Passwort setzen, passt nicht -> Fenster zu
                                    if(tf_pw_alt.getText().equals(benutzerliste.gibAngemeldeterBenutzer().gibPasswort())&&tf_pw_neu.getText().equals(tf_pw_neu_2.getText())){
                                        benutzerliste.gibAngemeldeterBenutzer().setzePasswort(tf_pw_neu.getText());
                                        JDialog end_passwort = new JDialog();
                                        end_passwort.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_passwort.setLocation(800, 100);
                                        JPanel panel_end_passwort = new JPanel();
                                        end_passwort.add(panel_end_passwort);
                                        JLabel lbl_end_passwort = new JLabel("Passwort geändert");
                                        lbl_end_passwort.setVisible(true);
                                        panel_end_passwort.add(lbl_end_passwort);
                                        end_passwort.pack();
                                        end_passwort.setVisible(true);


                                    }
                                    //Passwort Fenster verschrotten, Admin-Menu wieder sichtbar machen


                                    else{JDialog end_pw_falsch = new JDialog();
                                        end_pw_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_pw_falsch.setLocation(800, 100);
                                        JPanel panel_end_pw_falsch = new JPanel();
                                        end_pw_falsch.add(panel_end_pw_falsch);
                                        JLabel lbl_end_pw_falsch = new JLabel("Passwort falsch oder neue Passwörter passen nicht zusammen.");
                                        lbl_end_pw_falsch.setVisible(true);
                                        panel_end_pw_falsch.add(lbl_end_pw_falsch);
                                        end_pw_falsch.pack();
                                        end_pw_falsch.setVisible(true);

                                    }
                                    dialog_passwort.dispose();
                                    dialog_menu_admin.setVisible(true);

                                }
                            });

                        }
                    });
                    abmelden.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Neues Fenster zum Abmelden
                            JDialog dialog_abmelden=new JDialog();
                            //Bei x: Fenster schließen, User-Menu öffnen
                            dialog_abmelden.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_abmelden.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            dialog_abmelden.setTitle("Im Programm abmelden");
                            dialog_abmelden.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Abmelden Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_abmelden.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_abmelden = new JPanel();
                            panel_abmelden.setVisible(true);
                            JLabel lbl_abmelden = new JLabel("Sicher, dass Du dich abmelden willst, "+nutzer+"?");
                            JButton btn_abmelden_ja = new JButton("Ja");
                            JButton btn_abmelden_nein = new JButton("Nein");
                            lbl_abmelden.setVisible(true);
                            btn_abmelden_ja.setVisible(true);
                            btn_abmelden_nein.setVisible(true);
                            panel_abmelden.add(lbl_abmelden);
                            panel_abmelden.add(btn_abmelden_ja);
                            panel_abmelden.add(btn_abmelden_nein);

                            dialog_abmelden.add(panel_abmelden);
                            dialog_abmelden.pack();
                            dialog_abmelden.setVisible(true);

                            btn_abmelden_ja.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Frame "abmelden" verschrotten, Start-Dialog wieder aufbauen
                                    benutzerliste.benutzerAbmelden();
                                    dialog_abmelden.dispose();
                                    tf_nutzer.setText("");
                                    tf_passwort.setText("");
                                    dialog_start.setVisible(true);
                                }
                            });
                            btn_abmelden_nein.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Fenster "abmelden" verschrotten, Admin-Menu wieder aufbauen
                                    dialog_abmelden.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });

                        }
                    });
                    benutzer_neu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Fenster für Neuen Benutzer
                            JDialog dialog_benutzer_neu = new JDialog();
                            dialog_benutzer_neu.setTitle("Neuen Benutzer anlegen");
                            //bei x: Schließen und Admin-Menu öffnen
                            dialog_benutzer_neu.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_benutzer_neu.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_benutzer_neu=new JPanel(new GridLayout(7,2));
                            panel_benutzer_neu.setVisible(true);
                            JLabel lbl_benutzer_neu = new JLabel("Daten des neuen Benutzers eingeben");
                            JLabel lbl_vorname = new JLabel("Vorname:");
                            JTextField tf_vorname = new JTextField(20);
                            JLabel lbl_nachname = new JLabel("Nachname:");
                            JTextField tf_nachname = new JTextField(20);
                            JLabel lbl_nutzername = new JLabel("Nutzername:");
                            JTextField tf_nutzername = new JTextField(20);
                            JLabel lbl_passwort = new JLabel("Passwort:");
                            JPasswordField tf_passwort = new JPasswordField(20);
                            JLabel lbl_passwort_wdh = new JLabel("Passwort wiederholen:");
                            JPasswordField tf_passwort_wdh = new JPasswordField(20);
                            JButton btn_benutzer_neu=new JButton("OK");
                            JButton btn_benutzer_back = new JButton("zurück");

                            panel_benutzer_neu.add(lbl_benutzer_neu);
                            panel_benutzer_neu.add(new JLabel(""));
                            panel_benutzer_neu.add(lbl_vorname);
                            panel_benutzer_neu.add(tf_vorname);
                            panel_benutzer_neu.add(lbl_nachname);
                            panel_benutzer_neu.add(tf_nachname);
                            panel_benutzer_neu.add(lbl_nutzername);
                            panel_benutzer_neu.add(tf_nutzername);
                            panel_benutzer_neu.add(lbl_passwort);
                            panel_benutzer_neu.add(tf_passwort);
                            panel_benutzer_neu.add(lbl_passwort_wdh);
                            panel_benutzer_neu.add(tf_passwort_wdh);
                            panel_benutzer_neu.add(btn_benutzer_neu);
                            panel_benutzer_neu.add(btn_benutzer_back);

                            dialog_benutzer_neu.add(panel_benutzer_neu);
                            dialog_benutzer_neu.pack();
                            dialog_benutzer_neu.setVisible(true);

                            // Aktion bei zurück: Fenster zu, Admin-Menu auf
                            btn_benutzer_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_benutzer_neu.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            //Bei OK neuen Benutzer anlegen
                            btn_benutzer_neu.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Wenn nicht alle felder ausgefüllt sind: Pop-Up: Alle Felder ausfüllen!
                                    if(tf_vorname.getText().equals("")){
                                        System.out.println("Vorname leer");
                                    }
                                    if(tf_vorname.getText().equals("")||tf_nachname.getText().equals("")||tf_nutzername.getText().equals("")||tf_passwort.getText().equals("")||tf_passwort_wdh.equals("")){
                                        JDialog end_benutzer_neu_full = new JDialog();
                                        end_benutzer_neu_full.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_benutzer_neu_full.setLocation(800, 100);
                                        JPanel panel_end_benutzer_neu_full = new JPanel();
                                        end_benutzer_neu_full.add(panel_end_benutzer_neu_full);
                                        JLabel lbl_end_benutzer_neu = new JLabel("Bitte alle Felder ausfüllen.");
                                        lbl_end_benutzer_neu.setVisible(true);
                                        panel_end_benutzer_neu_full.add(lbl_end_benutzer_neu);
                                        end_benutzer_neu_full.pack();
                                        end_benutzer_neu_full.setVisible(true);
                                    }
                                    else {
                                        //Nutzername abgleichen, wenn schon vorhanden, Pop-Up und zurück
                                        if (benutzerliste.gibErster().nutzerNameVorhanden(tf_nutzername.getText()) == true) {
                                            JDialog end_benutzer_neu_nutzer_vorhanden = new JDialog();
                                            end_benutzer_neu_nutzer_vorhanden.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                            end_benutzer_neu_nutzer_vorhanden.setLocation(800, 100);
                                            JPanel panel_end_benutzer_neu_nutzer_vorhanden = new JPanel();
                                            end_benutzer_neu_nutzer_vorhanden.add(panel_end_benutzer_neu_nutzer_vorhanden);
                                            JLabel lbl_end_benutzer_neu_nutzer_vorhanden = new JLabel("Benutzer " + tf_nutzername.getText() + " schon vorhanden.");
                                            lbl_end_benutzer_neu_nutzer_vorhanden.setVisible(true);
                                            panel_end_benutzer_neu_nutzer_vorhanden.add(lbl_end_benutzer_neu_nutzer_vorhanden);
                                            end_benutzer_neu_nutzer_vorhanden.pack();
                                            end_benutzer_neu_nutzer_vorhanden.setVisible(true);
                                        } else {
                                            // Passwörter vergleichen, wenn gleich, Benutzer anlegen. Ansonsten: Zurück zu Eingabefeld
                                            if (tf_passwort.getText().equals(tf_passwort_wdh.getText())) {
                                                try {
                                                    benutzerliste.benutzerEinfuegen(tf_vorname.getText(), tf_nachname.getText(), tf_nutzername.getText(), tf_passwort.getText());
                                                } catch (IOException ex) {
                                                    throw new RuntimeException(ex);
                                                }
                                                JDialog end_benutzer_neu = new JDialog();
                                                end_benutzer_neu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                end_benutzer_neu.setLocation(800, 100);
                                                JPanel panel_end_benutzer_neu = new JPanel();
                                                end_benutzer_neu.add(panel_end_benutzer_neu);
                                                JLabel lbl_end_benutzer_neu = new JLabel("Benutzer " + tf_nutzername.getText() + " angelegt.");
                                                lbl_end_benutzer_neu.setVisible(true);
                                                panel_end_benutzer_neu.add(lbl_end_benutzer_neu);
                                                end_benutzer_neu.pack();
                                                end_benutzer_neu.setVisible(true);

                                            } else {
                                                JDialog end_benutzer_neu_falsch = new JDialog();
                                                end_benutzer_neu_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                end_benutzer_neu_falsch.setLocation(430, 100);
                                                JPanel panel_end_benutzer_neu_falsch = new JPanel();
                                                end_benutzer_neu_falsch.add(panel_end_benutzer_neu_falsch);
                                                JLabel lbl_end_benutzer_neu_falsch = new JLabel("Passwörter stimmen nicht überein");
                                                lbl_end_benutzer_neu_falsch.setVisible(true);
                                                panel_end_benutzer_neu_falsch.add(lbl_end_benutzer_neu_falsch);
                                                end_benutzer_neu_falsch.pack();
                                                end_benutzer_neu_falsch.setVisible(true);
                                            }
                                        }
                                    }
                                    //Neuer Benutzer Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                    dialog_benutzer_neu.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                        }
                    });
                    benutzer_weg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Prüfe, ob es außer dem Admin noch andere Benutzer gibt
                            //String aus Benntzerliste in Array umwandeln
                            String[] split = benutzerliste.benutzerlisteAusgeben().split("-x-");
                            String[] split_ohne_admin = new String[split.length - 1];

                            for (int i = 0, j=0; i < split.length; i++) {
                                if(split[i].equals("admin")==true){
                                   // split_ohne_admin[i]=split[j+1];
                                }
                                else {
                                    split_ohne_admin[j] = split[i];
                                   j++;
                                }
                            }


                            //Wenn es gar keine Nutzer gibt, Info-Pop-Up
                            if (split_ohne_admin.length == 0) {
                                JDialog dialog_no_user = new JDialog();
                                dialog_no_user.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                dialog_no_user.setLocation(800, 100);
                                JPanel panel_no_user = new JPanel();
                                JLabel lbl_no_user = new JLabel("Es sind keine Benutzer außer dem Admin angelegt.");
                                panel_no_user.add(lbl_no_user);
                                dialog_no_user.add(panel_no_user);
                                dialog_no_user.pack();
                                dialog_no_user.setVisible(true);
                            } else {


                                //Admin-Fenster unsichtbar machen
                                dialog_menu_admin.setVisible(false);

                                //Fenster für zu löschenden Benutzer
                                JDialog dialog_benutzer_weg = new JDialog();
                                dialog_benutzer_weg.setTitle("Benutzer aus dem System löschen");
                                //bei x: Schließen und Admin-Menu öffnen
                                dialog_benutzer_weg.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosing(WindowEvent e) {
                                        super.windowClosing(e);
                                        //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                        dialog_benutzer_weg.dispose();
                                        dialog_menu_admin.setVisible(true);
                                    }
                                });

                                JPanel panel_benutzer_weg = new JPanel();
                                panel_benutzer_weg.setVisible(true);
                                final JComboBox<String> cb_namensliste = new JComboBox<String>(split_ohne_admin);
                                JLabel lbl_benutzer_weg = new JLabel("Benutzernamen des zu löschenden Benutzers auswählen");
                                JButton btn_benutzer_weg = new JButton("OK");
                                JButton btn_benutzer_back = new JButton("zurück");
                                panel_benutzer_weg.add(lbl_benutzer_weg);

                                panel_benutzer_weg.add(cb_namensliste);
                                panel_benutzer_weg.add(btn_benutzer_weg);
                                panel_benutzer_weg.add(btn_benutzer_back);

                                dialog_benutzer_weg.add(panel_benutzer_weg);
                                dialog_benutzer_weg.pack();
                                dialog_benutzer_weg.setVisible(true);
                                //Aktion bei zurück
                                btn_benutzer_back.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dialog_benutzer_weg.dispose();
                                        dialog_menu_admin.setVisible(true);
                                    }
                                });
                                //Aktion bei OK:
                                btn_benutzer_weg.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {


                                        try {
                                            benutzerliste.benutzerLoeschen(split_ohne_admin[cb_namensliste.getSelectedIndex()]);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }

                                        JDialog end_benutzer_weg = new JDialog();
                                        end_benutzer_weg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_benutzer_weg.setLocation(800, 100);
                                        JPanel panel_end_benutzer_weg = new JPanel();
                                        end_benutzer_weg.add(panel_end_benutzer_weg);
                                        JLabel lbl_end_benutzer_weg = new JLabel("Benutzer " + split_ohne_admin[cb_namensliste.getSelectedIndex()] + " gelöscht.");
                                        lbl_end_benutzer_weg.setVisible(true);
                                        panel_end_benutzer_weg.add(lbl_end_benutzer_weg);
                                        end_benutzer_weg.pack();
                                        end_benutzer_weg.setVisible(true);
                                        dialog_benutzer_weg.dispose();
                                        dialog_menu_admin.setVisible(true);


                                    }
                                });
                            }
                        }
                    });
                    alle_benutzer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog_alle_benutzer=new JDialog();
                            dialog_alle_benutzer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog_alle_benutzer.setLocation(800, 100);
                            String[] split = benutzerliste.benutzerlisteAusgeben().split("-x-");
                            JPanel panel_alle_benutzer = new JPanel(new GridLayout(split.length+2,1));
                            JLabel lbl_alle_benutzer = new JLabel("Liste aller Benutzer:");
                            panel_alle_benutzer.add(lbl_alle_benutzer);
                            String[] split_1 = benutzerliste.benutzerlisteAusgeben().split("-x-");
                            for(int i = 0; i<split_1.length;i++){
                                JLabel name_i = new JLabel("- " +split_1[i]);
                                panel_alle_benutzer.add(name_i);
                            }

                            dialog_alle_benutzer.add(panel_alle_benutzer);
                            dialog_alle_benutzer.pack();
                            dialog_alle_benutzer.setVisible(true);


                        }
                    });
                    koffer_neu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Fenster für Neuen Koffer
                            JDialog dialog_koffer_neu = new JDialog();
                            dialog_koffer_neu.setTitle("Neuen Koffer anlegen");
                            //bei x: Schließen und Admin-Menu öffnen
                            dialog_koffer_neu.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_koffer_neu.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_koffer_neu = new JPanel(new BorderLayout());
                            //Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_logo_new = new ImageIcon("koffer.png");
                            koffer_logo_new.setImage(koffer_logo_new.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                            panel_koffer_neu.add(new JLabel(koffer_logo_new), BorderLayout.WEST);
                            panel_koffer_neu.setVisible(true);

                            JPanel panel_koffer_neu_menu = new JPanel();

                            JLabel lbl_koffer_neu = new JLabel("Nummer des neuen Koffers eintragen:");
                            JTextField tf_koffer_neu = new JTextField(2);
                            JButton btn_koffer_neu = new JButton("OK");
                            JButton btn_koffer_neu_back = new JButton("zurück");
                            panel_koffer_neu_menu.add(lbl_koffer_neu);
                            panel_koffer_neu_menu.add(tf_koffer_neu);
                            panel_koffer_neu_menu.add(btn_koffer_neu);
                            panel_koffer_neu_menu.add(btn_koffer_neu_back);
                            panel_koffer_neu.add(panel_koffer_neu_menu, BorderLayout.EAST);

                            dialog_koffer_neu.add(panel_koffer_neu);
                            dialog_koffer_neu.pack();
                            dialog_koffer_neu.setVisible(true);

                            //Aktion bei zurück
                            btn_koffer_neu_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_koffer_neu.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            //Aktion bei OK
                            btn_koffer_neu.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (tf_koffer_neu != null) {

                                        if (tf_koffer_neu.getText().matches("[0-9]+") == false) {
                                            //Pop Up Fenster falsche Nummer
                                            JDialog end_koffer_neu_nummer_falsch = new JDialog();
                                            end_koffer_neu_nummer_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                            end_koffer_neu_nummer_falsch.setLocation(800, 100);
                                            JPanel panel_end_koffer_neu_nummer_falsch = new JPanel();
                                            end_koffer_neu_nummer_falsch.add(panel_end_koffer_neu_nummer_falsch);
                                            JLabel lbl_end_koffer_neu = new JLabel("Bitte nur natürliche Zahlen eintragen");
                                            lbl_end_koffer_neu.setVisible(true);
                                            panel_end_koffer_neu_nummer_falsch.add(lbl_end_koffer_neu);
                                            end_koffer_neu_nummer_falsch.pack();
                                            end_koffer_neu_nummer_falsch.setVisible(true);


                                        } else {
                                            //Wenn Koffernummer schon vorhanden, Pop-Up
                                            boolean nummer_da = false;
                                            for (int i = 0; i < reservierungsliste.gibKofferListe().length; i++) {
                                                int neue_Nummer = Integer.parseInt(tf_koffer_neu.getText());
                                                if (neue_Nummer == reservierungsliste.gibKofferListe()[i].gibNummer()) {
                                                    JDialog end_koffer_neu = new JDialog();
                                                    end_koffer_neu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end_koffer_neu.setLocation(800, 100);
                                                    JPanel panel_end_koffer_neu = new JPanel();
                                                    end_koffer_neu.add(panel_end_koffer_neu);
                                                    JLabel lbl_end_koffer_neu = new JLabel("Koffer mit der Nummer " + tf_koffer_neu.getText() + " existiert bereits.");
                                                    lbl_end_koffer_neu.setVisible(true);
                                                    panel_end_koffer_neu.add(lbl_end_koffer_neu);
                                                    end_koffer_neu.pack();
                                                    end_koffer_neu.setVisible(true);
                                                    nummer_da = true;
                                                }
                                            }
                                            if (nummer_da == false) {
                                                try {
                                                    reservierungsliste.neuerKoffer(Integer.parseInt(tf_koffer_neu.getText()));
                                                    JDialog end_koffer_neu = new JDialog();
                                                    end_koffer_neu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end_koffer_neu.setLocation(800, 100);
                                                    JPanel panel_end_koffer_neu = new JPanel();
                                                    end_koffer_neu.add(panel_end_koffer_neu);
                                                    JLabel lbl_end_koffer_neu = new JLabel("Koffer " + tf_koffer_neu.getText() + " erstellt");
                                                    lbl_end_koffer_neu.setVisible(true);
                                                    panel_end_koffer_neu.add(lbl_end_koffer_neu);
                                                    end_koffer_neu.pack();
                                                    end_koffer_neu.setVisible(true);
                                                } catch (IOException ex) {
                                                    throw new RuntimeException(ex);
                                                }

                                            }
                                        }
                                        //Neuer Koffer Fenster verschrotten, Admin-Fenster wieder sichtbar machen
                                        dialog_koffer_neu.dispose();
                                        dialog_menu_admin.setVisible(true);
                                    }

                                }


                            });
                        }

                    });
                    koffer_weg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Admin-Menu unsichtbar machen
                            dialog_menu_admin.setVisible(false);
                            //Fenster für Koffer löschen
                            JDialog dialog_koffer_weg = new JDialog();
                            dialog_koffer_weg.setTitle("Koffer löschen");
                            //bei x: Schließen und Admin-Menu öffnen
                            dialog_koffer_weg.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_koffer_weg.dispose();
                                    dialog_menu_admin.setVisible(true);
                                }
                            });
                            JPanel panel_koffer_weg = new JPanel(new BorderLayout());
                            //Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_logo_cross_new = new ImageIcon("koffer_cross.png");
                            koffer_logo_cross_new.setImage(koffer_logo_cross_new.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                            panel_koffer_weg.add(new JLabel(koffer_logo_cross_new),BorderLayout.WEST);
                            panel_koffer_weg.setVisible(true);

                            //Drop Down Menu mit vorhandenen Koffern. Wenn keine Koffer vorhanden, zurück zum Hauptmenu
                            if (reservierungsliste.gibKofferListe() == null) {
                                JDialog end_no_koffer = new JDialog();
                                end_no_koffer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_koffer.setLocation(800, 100);
                                JPanel panel_end_no_koffer = new JPanel();
                                end_no_koffer.add(panel_end_no_koffer);
                                JLabel lbl_end_no_koffer = new JLabel("Es sind keine Koffer vorhanden, die gelöscht werden könnten.");
                                lbl_end_no_koffer.setVisible(true);
                                panel_end_no_koffer.add(lbl_end_no_koffer);
                                end_no_koffer.pack();
                                end_no_koffer.setVisible(true);

                                //Koffer löschen Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                dialog_koffer_weg.dispose();
                                dialog_menu_admin.setVisible(true);
                            } else {
                                JPanel panel_koffer_weg_menu=new JPanel();
                                JLabel lbl_koffer_weg = new JLabel("Zu löschenden Koffer wählen: ");
                                String[] koffer_weg = new String[Main.reservierungsliste.gibKofferListe().length];
                                int[] koffer_nummer_auswahl = new int[Main.reservierungsliste.gibKofferListe().length];
                                for (int j = 0; j < reservierungsliste.gibKofferListe().length; j++) {
                                    koffer_weg[j] = "Koffer " + Main.reservierungsliste.gibKofferListe()[j].gibNummer();
                                    koffer_nummer_auswahl[j]=Main.reservierungsliste.gibKofferListe()[j].gibNummer();
                                }
                                //Drop-Dowm-Menü für Kofferliste
                                final JComboBox<String> cb_koffer_weg = new JComboBox<String>(koffer_weg);

                                JButton btn_koffer_weg = new JButton("Löschen");
                                JButton btn_koffer_weg_back=new JButton("zurück");
                                panel_koffer_weg_menu.add(lbl_koffer_weg);
                                panel_koffer_weg_menu.add(cb_koffer_weg);
                                panel_koffer_weg_menu.add(btn_koffer_weg);
                                panel_koffer_weg_menu.add(btn_koffer_weg_back);

                                panel_koffer_weg.add(panel_koffer_weg_menu,BorderLayout.EAST);
                                dialog_koffer_weg.add(panel_koffer_weg);
                                dialog_koffer_weg.pack();
                                dialog_koffer_weg.setVisible(true);
                                //Aktion bei zurück
                                btn_koffer_weg_back.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dialog_koffer_weg.dispose();
                                        dialog_menu_admin.setVisible(true);
                                    }
                                });
                                //Bei Klick auf Löschen
                                btn_koffer_weg.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            reservierungsliste.kofferEntfernen(koffer_nummer_auswahl[cb_koffer_weg.getSelectedIndex()]);
                                            JDialog end_koffer_weg = new JDialog();
                                            end_koffer_weg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                            end_koffer_weg.setLocation(800, 100);
                                            JPanel panel_end_koffer_weg = new JPanel();
                                            end_koffer_weg.add(panel_end_koffer_weg);
                                            JLabel lbl_end_koffer_weg = new JLabel("Koffer "+koffer_nummer_auswahl[cb_koffer_weg.getSelectedIndex()]+" wurde entfernt.");
                                            lbl_end_koffer_weg.setVisible(true);
                                            panel_end_koffer_weg.add(lbl_end_koffer_weg);
                                            end_koffer_weg.pack();
                                            end_koffer_weg.setVisible(true);


                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        //Koffer löschen Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                        dialog_koffer_weg.dispose();
                                        dialog_menu_admin.setVisible(true);
                                    }
                                });

                            }

                        }
                    });
                    alle_koffer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog_alle_koffer = new JDialog();
                            dialog_alle_koffer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog_alle_koffer.setLocation(800, 100);
                            JPanel panel_alle_koffer = new JPanel(new GridLayout(reservierungsliste.gibKofferListe().length+2,1));
                            JLabel lbl_alle_koffer = new JLabel("Liste aller Koffer:");
                            panel_alle_koffer.add(lbl_alle_koffer);
                            for(int i = 0; i<reservierungsliste.gibKofferListe().length;i++){
                                JLabel koffer_i=new JLabel("- Koffer " + reservierungsliste.gibKofferListe()[i].gibNummer() );
                                panel_alle_koffer.add(koffer_i);
                            }
                            dialog_alle_koffer.add(panel_alle_koffer);
                            dialog_alle_koffer.pack();
                            dialog_alle_koffer.setVisible(true);

                        }
                    });


                }
                //Wenn Nutzer nicht admin und in der Liste, Nutzer-Fenster öffnen
                else if (benutzerliste.gibAngemeldeterBenutzer()!=null){
                    //Start-Fenster unsichtbar, neues Fenster User-Menu
                    dialog_start.setVisible(false);
                    JDialog dialog_user_menu = new JDialog();
                    dialog_user_menu.setTitle("IPad-Koffer-Leihsystem: Hauptmenu");
                    //Bei Klick auf x: Abmelden und Start-Menu öffnen
                    dialog_user_menu.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                            benutzerliste.benutzerAbmelden();
                            dialog_user_menu.dispose();
                            dialog_start.setVisible(true);
                        }
                    });
                    dialog_user_menu.setSize(300,300);
                    JPanel panel_user_menu=new JPanel(new BorderLayout());
                    panel_user_menu.add(new JLabel(logo),BorderLayout.WEST);

                    JPanel panel_user_action = new JPanel(new GridLayout(6,1));

                    JLabel lbl_menu = new JLabel("Hallo "+ benutzerliste.gibAngemeldeterBenutzer().gibVorname()+". Was willst du tun?");
                    JButton reservieren = new JButton("Reservieren");
                    JButton stornieren = new JButton("Stornieren");
                    JButton passwort = new JButton("Passwort ändern");
                    JButton abmelden = new JButton("Abmelden");
                    panel_user_action.add(lbl_menu);
                    panel_user_action.add(reservieren);
                    panel_user_action.add(stornieren);
                    panel_user_action.add(passwort);
                    panel_user_action.add(abmelden);

                    panel_user_menu.add(panel_user_action,BorderLayout.EAST);

                    dialog_user_menu.add(panel_user_menu);
                    dialog_user_menu.pack();
                    dialog_user_menu.setVisible(true);
                    //Aktionen bei Buttons
                    reservieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //User-Menu unsichtbar machen
                            dialog_user_menu.setVisible(false);
                            //Fenster für Reservieren
                            JDialog dialog_reservieren = new JDialog();
                            dialog_reservieren.setTitle("IPad-Koffer reservieren");
                            //Bei x: Fenster schließen, User-Menu öffnen
                            dialog_reservieren.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_reservieren.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });
                            JPanel panel_reservieren=new JPanel(new BorderLayout());
                            panel_reservieren.setVisible(true);
                            //Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_logo = new ImageIcon("koffer.png");
                            koffer_logo.setImage(koffer_logo.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                            panel_reservieren.add(new JLabel(koffer_logo),BorderLayout.WEST);

                            JPanel panel_reservieren_menu = new JPanel();
                            JLabel lbl_reservieren = new JLabel("Für Reservieren Koffer wählen");
                            //Kofferliste aus Reservierungsliste
                            String[] koffers= new String[Main.reservierungsliste.gibKofferListe().length];
                            for (int j = 0;j<reservierungsliste.gibKofferListe().length;j++){
                                koffers[j] = "Koffer " + Main.reservierungsliste.gibKofferListe()[j].gibNummer();
                            }
                            //Drop-Dowm-Menü für Kofferliste
                            final JComboBox<String> cb = new JComboBox<String>(koffers);
                            JButton btn_reservieren = new JButton("OK");
                            JButton btn_reservieren_back = new JButton("zurück");

                            panel_reservieren_menu.add(lbl_reservieren);
                            panel_reservieren_menu.add(cb);
                            panel_reservieren_menu.add(btn_reservieren);
                            panel_reservieren_menu.add(btn_reservieren_back);

                            panel_reservieren.add(panel_reservieren_menu,BorderLayout.CENTER);
                            dialog_reservieren.add(panel_reservieren);
                            dialog_reservieren.pack();
                            dialog_reservieren.setVisible(true);
                            //Aktion bei zurück: Fenster verschrotten, Admin-Menu wieder sichtbar machen
                            btn_reservieren_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_reservieren.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });

                            //Aktion bei OK: Kalender öffnen und Datum wählen, Schulstunden-Pop-up-Fenster öffnen
                            btn_reservieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String gewaehltesDatum;
                                    gewaehltesDatum = new Kalender(dialog_user_menu, reservierungsliste, Main.reservierungsliste.gibKofferListe()[cb.getSelectedIndex()]).setPickedDate();
                                    //Aus Datum-String mache int-Datum
                                    int year = (1000 * (Character.getNumericValue(gewaehltesDatum.charAt(6)))) + (100 * Character.getNumericValue(gewaehltesDatum.charAt(7))) + (10 * Character.getNumericValue(gewaehltesDatum.charAt(8))) + (Character.getNumericValue(gewaehltesDatum.charAt(9)));
                                    int month = (10 * (Character.getNumericValue(gewaehltesDatum.charAt(3)))) + (Character.getNumericValue(gewaehltesDatum.charAt(4)));
                                    int day = (10 * Character.getNumericValue(gewaehltesDatum.charAt(0))) + Character.getNumericValue(gewaehltesDatum.charAt(1));

                                    LocalDate pickedDate = LocalDate.of(year, month, day);

                                    //Fenster für Schulstunde erzeugen und öffnen
                                    JDialog dialog_schulstunde = new JDialog();
                                    dialog_schulstunde.setLocation(dialog_user_menu.getX(),dialog_user_menu.getY()+200);
                                    dialog_schulstunde.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosing(WindowEvent e) {
                                            super.windowClosing(e);
                                            //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                            dialog_reservieren.dispose();
                                            dialog_schulstunde.dispose();
                                            dialog_user_menu.setVisible(true);
                                        }
                                    });
                                    JPanel panel_schulstunde=new JPanel();
                                    panel_schulstunde.setVisible(true);
                                    JLabel lbl_schulstunde = new JLabel("Schuldstunde am "+gewaehltesDatum+" wählen");
                                    panel_schulstunde.add(lbl_schulstunde);
                                    for(int i =0;i<10;i++){
                                        final int gewaehlte_stunde = i;
                                        JButton btn_i=new JButton("Stunde "+i );
                                        btn_i.setBackground(Color.white);
                                        //wenn eine Reservierung vorliegt, mache den Button rot, schreibe Namen der Reservierung rein
                                        if(reservierungsliste.istReserviert(pickedDate,gewaehlte_stunde,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()])){
                                            btn_i.setBackground(Color.red);
                                        }
                                        panel_schulstunde.add(btn_i);
                                        btn_i.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                //Prüfe, ob eine Reservierung vorliegt: Wenn ja: Pop-Up: "Keine Reservierung, sonst: Mit Datum und Namen reservieren
                                                if(reservierungsliste.istReserviert(pickedDate,gewaehlte_stunde,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()])) {
                                                    dialog_schulstunde.dispose();
                                                    JDialog end = new JDialog();
                                                    end.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end.setLocation(800, 100);
                                                    JPanel panel_end = new JPanel();
                                                    end.add(panel_end);
                                                    JLabel lbl_end = new JLabel("Keine Reservierung in dieser Stunde möglich");
                                                    lbl_end.setVisible(true);
                                                    panel_end.add(lbl_end);
                                                    end.pack();
                                                    end.setVisible(true);
                                                    dialog_reservieren.setVisible(true);

                                                }
                                                else {
                                                    try {
                                                        reservierungsliste.reservieren(pickedDate,gewaehlte_stunde,nutzer,reservierungsliste.gibKofferListe()[cb.getSelectedIndex()]);

                                                    } catch (IOException ex) {
                                                        throw new RuntimeException(ex);
                                                    }
                                                    JDialog end_reservieren = new JDialog();
                                                    end_reservieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                                    end_reservieren.setLocation(900, 100);
                                                    JPanel panel_end_reservieren = new JPanel();
                                                    end_reservieren.add(panel_end_reservieren);
                                                    JLabel lbl_end_reservieren = new JLabel("Koffer " + reservierungsliste.gibKofferListe()[cb.getSelectedIndex()].gibNummer() + " für " + nutzer + " am Datum " + gewaehltesDatum + " in Stunde "+ gewaehlte_stunde+" reserviert.");
                                                    lbl_end_reservieren.setVisible(true);
                                                    panel_end_reservieren.add(lbl_end_reservieren);
                                                    end_reservieren.pack();
                                                    end_reservieren.setVisible(true);
                                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Hauptframe wieder sichtbar machen
                                                    dialog_reservieren.dispose();
                                                    dialog_schulstunde.dispose();
                                                    dialog_user_menu.setVisible(true);

                                                }

                                            }
                                        });
                                    }
                                    JButton btn_stunde_back=new JButton("Zurück");
                                    btn_stunde_back.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            //Stunden Fenster schließen, Admin-Menu wieder sichtbar machen
                                            dialog_schulstunde.dispose();

                                        }
                                    });
                                    panel_schulstunde.add(btn_stunde_back);
                                    dialog_schulstunde.add(panel_schulstunde);
                                    dialog_schulstunde.pack();
                                    dialog_schulstunde.setVisible(true);


                                }
                            });

                        }
                    });

                    stornieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //User-Menu unsichtbar machen
                            dialog_user_menu.setVisible(false);
                            //Fenster für Stornieren
                            JDialog dialog_stornieren = new JDialog();
                            dialog_stornieren.setTitle("Reservierung stornieren");
                            //Bei x: Fenster schließen, User-Menu öffnen
                            dialog_stornieren.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_stornieren.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });
                            JPanel panel_stornieren = new JPanel(new BorderLayout());
                            //Durchgestrichener Koffer Symbol rechts ins Fenster
                            ImageIcon koffer_cross_logo = new ImageIcon("koffer_cross.png");
                            koffer_cross_logo.setImage(koffer_cross_logo.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                            panel_stornieren.add(new JLabel(koffer_cross_logo),BorderLayout.WEST);
                            panel_stornieren.setVisible(true);
                            JPanel panel_stornieren_menu = new JPanel();

                            JLabel lbl_stornieren = new JLabel("Koffer und Datum zum Stornieren wählen, mit OK bestätigen");

                            //Drop Down Menu für getätigte Reservierungen, wenn keine Reservierungen vorhanden, Pop-Up und zurück zu Menu
                            if (reservierungsliste.reservierungenBenutzerAnzeigen(nutzer) == null) {
                                JDialog end_no_res = new JDialog();
                                end_no_res.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_res.setLocation(800, 100);
                                JPanel panel_end_stornieren = new JPanel();
                                end_no_res.add(panel_end_stornieren);
                                JLabel lbl_end_stornieren = new JLabel("Es sind keine Reservierungen auf deinen Namen vorhanden");
                                lbl_end_stornieren.setVisible(true);
                                panel_end_stornieren.add(lbl_end_stornieren);
                                end_no_res.pack();
                                end_no_res.setVisible(true);

                                //Stornieren Fenster verschrotten, Admin-Menu wieder sichtbar machen
                                dialog_stornieren.dispose();
                                dialog_user_menu.setVisible(true);
                            }
                            else{
                                String[] reservierungen = new String[reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length];

                                for (int i = 0; i < reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length; i++) {
                                    reservierungen[i] = "Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibKoffer().gibNummer() + ", Datum: " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibDatum()+" Stunde " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibStunde();
                                }
                                final JComboBox<String> cb_reservierungen = new JComboBox<String>(reservierungen);
                                JButton btn_stornieren = new JButton("OK");
                                JButton btn_back=new JButton("Zurück");


                                panel_stornieren_menu.add(lbl_stornieren);
                                panel_stornieren_menu.add(cb_reservierungen);
                                panel_stornieren_menu.add(btn_stornieren);
                                panel_stornieren_menu.add(btn_back);
                                panel_stornieren.add(panel_stornieren_menu,BorderLayout.EAST);
                                dialog_stornieren.add(panel_stornieren);
                                dialog_stornieren.pack();
                                dialog_stornieren.setVisible(true);

                                btn_back.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dialog_stornieren.dispose();
                                        dialog_user_menu.setVisible(true);
                                    }
                                });
                                btn_stornieren.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        //Stornieren
                                        String storniert = new String("Reservierung von Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer().gibNummer() + " am " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum() + " in der Stunde" + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibStunde() + " storniert.");
                                        try {
                                            reservierungsliste.stornieren(reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum(), reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibStunde(), reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer());
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        //Pop-up Bestätigung, Stornieren Fenster plattmachen, Menu-Frame wieder sichtbar machen
                                        JDialog end_stornieren = new JDialog();
                                        end_stornieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_stornieren.setLocation(800, 100);
                                        JPanel panel_end_stornieren = new JPanel();
                                        end_stornieren.add(panel_end_stornieren);
                                        JLabel lbl_end_stornieren = new JLabel(storniert);
                                        panel_end_stornieren.add(lbl_end_stornieren);
                                        end_stornieren.pack();
                                        end_stornieren.setVisible(true);

                                        //Stornieren Fenster verschrotten, User-Menu wieder sichtbar machen
                                        dialog_stornieren.dispose();
                                        dialog_user_menu.setVisible(true);
                                    }

                                });


                            }
                        }
                    });

                    passwort.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //User-Menu unsichtbar machen
                            dialog_user_menu.setVisible(false);
                            //Neues Fenster zur neuen Passworteingabe öffnen
                            JDialog dialog_passwort=new JDialog();
                            //Bei x: Fenster schließen, User-Menu öffnen
                            dialog_passwort.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_passwort.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });
                            dialog_passwort.setTitle("Passwort ändern");
                            JPanel panel_passwort = new JPanel();
                            panel_passwort.setVisible(true);
                            JLabel lbl_pw_alt = new JLabel("Altes Passwort eingeben");
                            JPasswordField tf_pw_alt = new JPasswordField(20);
                            JLabel lbl_pw_neu = new JLabel("Neues Passwort eingeben");
                            JPasswordField tf_pw_neu = new JPasswordField(20);
                            JLabel lbl_pw_neu_2 = new JLabel("Neues Passwort wiederholen");
                            JPasswordField tf_pw_neu_2 = new JPasswordField(20);
                            JButton btn_passwort = new JButton("OK");
                            JButton btn_passwort_back=new JButton("zurück");
                            panel_passwort.add(lbl_pw_alt);
                            panel_passwort.add(tf_pw_alt);
                            panel_passwort.add(lbl_pw_neu);
                            panel_passwort.add(tf_pw_neu);
                            panel_passwort.add(lbl_pw_neu_2);
                            panel_passwort.add(tf_pw_neu_2);
                            panel_passwort.add(btn_passwort);
                            panel_passwort.add(btn_passwort_back);

                            dialog_passwort.add(panel_passwort);
                            dialog_passwort.pack();
                            dialog_passwort.setVisible(true);
                            //Aktion bei zurück
                            btn_passwort_back.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog_passwort.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });
                            //Aktion bei OK
                            btn_passwort.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Passwort prüfen: passt -> neues Passwort setzen, passt nicht -> neuer Versuch
                                    if(tf_pw_alt.getText().equals(benutzerliste.gibAngemeldeterBenutzer().gibPasswort())&&tf_pw_neu.getText().equals(tf_pw_neu_2.getText())){
                                        benutzerliste.gibAngemeldeterBenutzer().setzePasswort(tf_pw_neu.getText());
                                        JDialog end_passwort = new JDialog();
                                        end_passwort.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_passwort.setLocation(800, 100);
                                        JPanel panel_end_passwort = new JPanel();
                                        end_passwort.add(panel_end_passwort);
                                        JLabel lbl_end_passwort = new JLabel("Passwort erfolgreich geändert");
                                        lbl_end_passwort.setVisible(true);
                                        panel_end_passwort.add(lbl_end_passwort);
                                        end_passwort.pack();
                                        end_passwort.setVisible(true);


                                    }
                                    //Passwort Fenster verschrotten, Hauptframe wieder sichtbar machen


                                    else{JDialog end_pw_falsch = new JDialog();
                                        end_pw_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_pw_falsch.setLocation(800, 100);
                                        JPanel panel_end_pw_falsch = new JPanel();
                                        end_pw_falsch.add(panel_end_pw_falsch);
                                        JLabel lbl_end_pw_falsch = new JLabel("Passwort falsch oder neue Passwörter passen nicht zusammen.");
                                        lbl_end_pw_falsch.setVisible(true);
                                        panel_end_pw_falsch.add(lbl_end_pw_falsch);
                                        end_pw_falsch.pack();
                                        end_pw_falsch.setVisible(true);

                                    }
                                    dialog_passwort.dispose();
                                    dialog_user_menu.setVisible(true);

                                }
                            });

                        }
                    });

                    abmelden.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //User-Menu unsichtbar machen
                            dialog_user_menu.setVisible(false);
                            //Neues Fenster zum Abmelden
                            JDialog dialog_abmelden=new JDialog();
                            //Bei x: Fenster schließen, User-Menu öffnen
                            dialog_abmelden.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    //Reservieren Fenster verschrotten, Stunden Fenster verschrotten, Menu Dialog wieder sichtbar machen
                                    dialog_abmelden.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });
                            JPanel panel_abmelden = new JPanel();
                            panel_abmelden.setVisible(true);
                            JLabel lbl_abmelden = new JLabel("Sicher, dass Du dich abmelden willst, "+benutzerliste.gibAngemeldeterBenutzer().gibVorname()+"?");
                            JButton btn_abmelden_ja = new JButton("Ja");
                            JButton btn_abmelden_nein = new JButton("Nein");
                            lbl_abmelden.setVisible(true);
                            btn_abmelden_ja.setVisible(true);
                            btn_abmelden_nein.setVisible(true);
                            panel_abmelden.add(lbl_abmelden);
                            panel_abmelden.add(btn_abmelden_ja);
                            panel_abmelden.add(btn_abmelden_nein);

                            dialog_abmelden.add(panel_abmelden);
                            dialog_abmelden.pack();
                            dialog_abmelden.setVisible(true);

                            btn_abmelden_ja.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                 benutzerliste.benutzerAbmelden();
                                    //Fenster "abmelden" verschrotten, Start-Menu wieder sichtbar machen
                                    dialog_abmelden.dispose();

                                    tf_nutzer.setText("");
                                    tf_passwort.setText("");

                                    dialog_start.setVisible(true);
                                }
                            });
                            btn_abmelden_nein.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Fenster "abmelden" verschrotten, User Menu wieder sichtbar machen
                                    dialog_abmelden.dispose();
                                    dialog_user_menu.setVisible(true);
                                }
                            });

                        }
                    });












                }
            }
        });
    }
}
