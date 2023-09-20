import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class GUI {

    public GUI(Reservierungsliste reservierungsliste, Benutzerliste benutzerliste) {
        final String pickedDate;
        //Haupt-Fenster JDialog
        JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //Fenster 1 mit Nutzername und Passworteingabe
        JPanel panel_1 = new JPanel();
        JLabel lbl_1 = new JLabel("Nutzername und Passwort eingeben");
      JTextField  tf_nutzer = new JTextField(20);
        JPasswordField tf_passwort = new JPasswordField(20);
        JButton btn_1 = new JButton("OK");

        panel_1.add(lbl_1);
        panel_1.add(tf_nutzer);
        panel_1.add(tf_passwort);
        panel_1.add(btn_1);


        frame.add(panel_1);
        frame.pack();
        frame.setVisible(true);
//Aktion bei OK
        btn_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nutzer = tf_nutzer.getText();
                String pw = tf_passwort.getText();
                //benutzerliste.benutzerAnmelden(tf_nutzer.getText(), tf_passwort.getText());
                benutzerliste.baRekursiv(tf_nutzer.getText(), tf_passwort.getText());
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
                }
                //Wenn Nutzer Admin ist: Admin Fenster öffnen
                if(tf_nutzer.getText().equals("admin")){
                    //Hauptframe leeren, mit Menu-Inhalt füllen
                    frame.remove(panel_1);
                    frame.setVisible(false);
                    JPanel menu=new JPanel();
                    JLabel lbl_menu = new JLabel("Hallo "+ benutzerliste.gibAngemeldeterBenutzer().gibVorname()+". Was willst du tun?");
                    JButton reservieren = new JButton("Reservieren");
                    JButton stornieren = new JButton("Stornieren");
                    JButton passwort = new JButton("Passwort ändern");
                    JButton abmelden = new JButton("Abmelden");
                    JButton benutzer_neu= new JButton("Neuen Benutzer anlegen");
                    JButton benutzer_weg=new JButton("Benutzer löschen");
                    JButton koffer_neu = new JButton("Neuen Koffer anlegen");
                    JButton koffer_weg = new JButton("Koffer löschen");

                    menu.add(lbl_menu);
                    menu.add(reservieren);
                    menu.add(stornieren);
                    menu.add(passwort);
                    menu.add(benutzer_neu);
                    menu.add(benutzer_weg);
                    menu.add(koffer_neu);
                    menu.add(koffer_weg);
                    menu.add(abmelden);


                    frame.add(menu);
                    frame.pack();
                    frame.setVisible(true);
                    //Aktionen bei Buttons
                    reservieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Reservieren
                            JDialog frame_reservieren = new JDialog();
                            JPanel panel_reservieren=new JPanel();
                            panel_reservieren.setVisible(true);
                            JLabel lbl_reservieren = new JLabel("Für Reservieren Koffer wählen");
                            //Kofferliste aus Reservierungsliste
                            String[] koffers= new String[Main.reservierungsliste.kofferliste.length];
                            for (int j = 0;j<reservierungsliste.kofferliste.length;j++){
                                koffers[j] = "Koffer " + Main.reservierungsliste.kofferliste[j].gibNummer();
                            }
                            //Drop-Dowm-Menü für Kofferliste
                            final JComboBox<String> cb = new JComboBox<String>(koffers);
                            JButton btn_reservieren = new JButton("OK");
                            lbl_reservieren.setVisible(true);
                            cb.setVisible(true);
                            btn_reservieren.setVisible(true);
                            panel_reservieren.add(lbl_reservieren);
                            panel_reservieren.add(cb);
                            panel_reservieren.add(btn_reservieren);

                            frame_reservieren.add(panel_reservieren);
                            frame_reservieren.pack();
                            frame_reservieren.setVisible(true);
                            //Aktion bei OK
                            btn_reservieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    reservierungsliste.gewaehltesDatum = new Kalender(frame, reservierungsliste, Main.reservierungsliste.kofferliste[cb.getSelectedIndex()]).setPickedDate();
                                    //Aus Datum-String mache int-Datum
                                    int year = (1000 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(6)))) + (100 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(7))) + (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(8))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(9)));
                                    int month = (10 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(3)))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(4)));
                                    int day = (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(0))) + Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(1));

                                    LocalDate pickedDate = LocalDate.of(year, month, day);

                                    //Prüfe, ob eine Reservierung vorliegt: Wenn ja: Pop-Up: "Keine Reservierung, sonst: Mit Datum und Namen reservieren
                                    if (reservierungsliste.istReserviert(pickedDate, reservierungsliste.kofferliste[cb.getSelectedIndex()])) {
                                        frame.dispose();
                                        JDialog end = new JDialog();
                                        end.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end.setLocation(430, 100);
                                        JPanel panel_end = new JPanel();
                                        end.add(panel_end);
                                        JLabel lbl_end = new JLabel("Keine Reservierung an diesem Datum möglich");
                                        lbl_end.setVisible(true);
                                        panel_end.add(lbl_end);
                                        end.pack();
                                        end.setVisible(true);


                                    } else {
                                        try {
                                            reservierungsliste.reservieren(pickedDate, nutzer, Main.reservierungsliste.kofferliste[cb.getSelectedIndex()]);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        JDialog end_reservieren = new JDialog();
                                        end_reservieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_reservieren.setLocation(430, 100);
                                        JPanel panel_end_reservieren = new JPanel();
                                        end_reservieren.add(panel_end_reservieren);
                                        JLabel lbl_end_reservieren = new JLabel("Koffer " + reservierungsliste.kofferliste[cb.getSelectedIndex()].gibNummer() + " für " + nutzer + " am Datum " + reservierungsliste.gewaehltesDatum + " reserviert.");
                                        lbl_end_reservieren.setVisible(true);
                                        panel_end_reservieren.add(lbl_end_reservieren);
                                        end_reservieren.pack();
                                        end_reservieren.setVisible(true);

                                    }
                                    //Reservieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_reservieren.dispose();
                                    frame.setVisible(true);
                                }
                            });


                        }
                    });
                    stornieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Stornieren
                            JDialog frame_stornieren = new JDialog();
                            JPanel panel_stornieren = new JPanel();
                            panel_stornieren.setVisible(true);
                            JLabel lbl_stornieren = new JLabel("Koffer und Datum zum Stornieren wählen, mit OK bestätigen");

                            //Drop Down Menu für getätigte Reservierungen, wenn keine Reservierungen vorhanden, Pop-Up und zurück zu Menu
                            if (reservierungsliste.reservierungenBenutzerAnzeigen(nutzer) == null) {
                                JDialog end_no_res = new JDialog();
                                end_no_res.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_res.setLocation(430, 100);
                                JPanel panel_end_stornieren = new JPanel();
                                end_no_res.add(panel_end_stornieren);
                                JLabel lbl_end_stornieren = new JLabel("Es sind keine Reservierungen auf deinen Namen vorhanden");
                                lbl_end_stornieren.setVisible(true);
                                panel_end_stornieren.add(lbl_end_stornieren);
                                end_no_res.pack();
                                end_no_res.setVisible(true);

                                //Stornieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                frame_stornieren.dispose();
                                frame.setVisible(true);
                            }
                            else{
                            String[] reservierungen = new String[reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length];

                            for (int i = 0; i < reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length; i++) {
                                reservierungen[i] = "Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibKoffer().gibNummer() + ", Datum: " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibDatum();
                            }
                            final JComboBox<String> cb_reservierungen = new JComboBox<String>(reservierungen);
                            JButton btn_stornieren = new JButton("OK");

                            lbl_stornieren.setVisible(true);
                            cb_reservierungen.setVisible(true);
                            btn_stornieren.setVisible(true);
                            panel_stornieren.add(lbl_stornieren);
                            panel_stornieren.add(cb_reservierungen);
                            panel_stornieren.add(btn_stornieren);
                            frame_stornieren.add(panel_stornieren);
                            frame_stornieren.pack();
                            frame_stornieren.setVisible(true);

                            btn_stornieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Stornieren
                                    String storniert = new String("Reservierung von Koffer " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer().gibNummer() + " am " + reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum() + " storniert.");
                                    try {
                                        reservierungsliste.stornieren(reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].datum, reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].koffer);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    //Pop-up Bestätigung, Stornieren Fenster plattmachen, Menu-Frame wieder sichtbar machen
                                    JDialog end_stornieren = new JDialog();
                                    end_stornieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                    end_stornieren.setLocation(430, 100);
                                    JPanel panel_end_stornieren = new JPanel();
                                    end_stornieren.add(panel_end_stornieren);
                                    JLabel lbl_end_stornieren = new JLabel(storniert);
                                    lbl_end_stornieren.setVisible(true);
                                    panel_end_stornieren.add(lbl_end_stornieren);
                                    end_stornieren.pack();
                                    end_stornieren.setVisible(true);

                                    //Stornieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_stornieren.dispose();
                                    frame.setVisible(true);
                                }

                            });


                        }
                    }
                    });
                    passwort.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Neues Fenster zur neuen Passworteingabe öffnen
                            JDialog frame_passwort=new JDialog();
                            JPanel panel_passwort = new JPanel();
                            panel_passwort.setVisible(true);
                            JLabel lbl_pw_alt = new JLabel("Altes Passwort eingeben");
                            JPasswordField tf_pw_alt = new JPasswordField(20);
                            JLabel lbl_pw_neu = new JLabel("Neues Passwort eingeben");
                            JPasswordField tf_pw_neu = new JPasswordField(20);
                            JLabel lbl_pw_neu_2 = new JLabel("Neues Passwort wiederholen");
                            JPasswordField tf_pw_neu_2 = new JPasswordField(20);
                            JButton btn_passwort = new JButton("OK");
                            panel_passwort.add(lbl_pw_alt);
                            panel_passwort.add(tf_pw_alt);
                            panel_passwort.add(lbl_pw_neu);
                            panel_passwort.add(tf_pw_neu);
                            panel_passwort.add(lbl_pw_neu_2);
                            panel_passwort.add(tf_pw_neu_2);
                            panel_passwort.add(btn_passwort);

                            frame_passwort.add(panel_passwort);
                            frame_passwort.pack();
                            frame_passwort.setVisible(true);

                            //Aktion bei OK
                            btn_passwort.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Passwort prüfen: passt -> neues Passwort setzen, passt nicht -> neuer Versuch
                                    if(tf_pw_alt.getText().equals(benutzerliste.gibAngemeldeterBenutzer().gibPasswort())&&tf_pw_neu.getText().equals(tf_pw_neu_2.getText())){
                                        benutzerliste.gibAngemeldeterBenutzer().setzePasswort(tf_pw_neu.getText());
                                        JDialog end_passwort = new JDialog();
                                        end_passwort.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_passwort.setLocation(430, 100);
                                        JPanel panel_end_passwort = new JPanel();
                                        end_passwort.add(panel_end_passwort);
                                        JLabel lbl_end_passwort = new JLabel("Passwort geändert");
                                        lbl_end_passwort.setVisible(true);
                                        panel_end_passwort.add(lbl_end_passwort);
                                        end_passwort.pack();
                                        end_passwort.setVisible(true);


                                    }
                                    //Passwort Fenster verschrotten, Hauptframe wieder sichtbar machen


                                    else{JDialog end_pw_falsch = new JDialog();
                                        end_pw_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_pw_falsch.setLocation(430, 100);
                                        JPanel panel_end_pw_falsch = new JPanel();
                                        end_pw_falsch.add(panel_end_pw_falsch);
                                        JLabel lbl_end_pw_falsch = new JLabel("Passwort falsch.");
                                        lbl_end_pw_falsch.setVisible(true);
                                        panel_end_pw_falsch.add(lbl_end_pw_falsch);
                                        end_pw_falsch.pack();
                                        end_pw_falsch.setVisible(true);

                                    }
                                    frame_passwort.dispose();
                                    frame.setVisible(true);

                                }
                            });

                        }
                    });
                    abmelden.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Neues Fenster zum Abmelden
                            JDialog frame_abmelden=new JDialog();
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

                            frame_abmelden.add(panel_abmelden);
                            frame_abmelden.pack();
                            frame_abmelden.setVisible(true);

                            btn_abmelden_ja.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Frame "abmelden" verschrotten, Frame 1 wieder aufbauen
                                    frame_abmelden.dispose();
                                    frame.remove(menu);
                                    tf_nutzer.setText("");
                                    tf_passwort.setText("");

                                    frame.add(panel_1);
                                    frame.pack();
                                    frame.setVisible(true);
                                }
                            });
                            btn_abmelden_nein.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Frame "abmelden" verschrotten, Frame Menu wieder aufbauen
                                    frame_abmelden.dispose();
                                    frame.setVisible(true);
                                }
                            });

                        }
                    });
                    benutzer_neu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Neuen Benutzer
                            JDialog frame_benutzer_neu = new JDialog();
                            JPanel panel_benutzer_neu=new JPanel();
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

                            panel_benutzer_neu.add(lbl_benutzer_neu);
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

                            frame_benutzer_neu.add(panel_benutzer_neu);
                            frame_benutzer_neu.pack();
                            frame_benutzer_neu.setVisible(true);

                            //Bei OK neuen Benutzer anlegen
                            btn_benutzer_neu.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Passwörter vergleichen, wenn gleich, Benutzer anlegen. Ansonsten: Zurück zu Eingabefeld
                                    if (tf_passwort.getText().equals(tf_passwort_wdh.getText())) {
                                        try {
                                            benutzerliste.benutzerEinfuegen(tf_vorname.getText(), tf_nachname.getText(), tf_nutzername.getText(), tf_passwort.getText());
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        JDialog end_benutzer_neu = new JDialog();
                                        end_benutzer_neu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_benutzer_neu.setLocation(430, 100);
                                        JPanel panel_end_benutzer_neu = new JPanel();
                                        end_benutzer_neu.add(panel_end_benutzer_neu);
                                        JLabel lbl_end_benutzer_neu = new JLabel("Benutzer "+ tf_nutzername.getText()+ " angelegt.");
                                        lbl_end_benutzer_neu.setVisible(true);
                                        panel_end_benutzer_neu.add(lbl_end_benutzer_neu);
                                        end_benutzer_neu.pack();
                                        end_benutzer_neu.setVisible(true);

                                    }
                                    else{
                                        JDialog end_benutzer_neu_falsch = new JDialog();
                                        end_benutzer_neu_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_benutzer_neu_falsch.setLocation(430, 100);
                                        JPanel panel_end_benutzer_neu_falsch = new JPanel();
                                        end_benutzer_neu_falsch.add(panel_end_benutzer_neu_falsch);
                                        JLabel lbl_end_benutzer_neu_falsch= new JLabel("Passwörter stimmen nicht überein");
                                       lbl_end_benutzer_neu_falsch.setVisible(true);
                                        panel_end_benutzer_neu_falsch.add(lbl_end_benutzer_neu_falsch);
                                        end_benutzer_neu_falsch.pack();
                                        end_benutzer_neu_falsch.setVisible(true);
                                    }
                                    //Neuer Benutzer Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_benutzer_neu.dispose();
                                    frame.setVisible(true);
                                }
                            });
                        }
                    });
                    benutzer_weg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für zu löschenden Benutzer
                            JDialog frame_benutzer_weg = new JDialog();
                            JPanel panel_benutzer_weg=new JPanel();
                            panel_benutzer_weg.setVisible(true);
                            JLabel lbl_benutzer_weg=new JLabel("Benutzernamen, der zu löschen ist, eingeben:");
                            JTextField tf_benutzer_weg=new JTextField(20);
                            JButton btn_benutzer_weg=new JButton("OK");
                            panel_benutzer_weg.add(lbl_benutzer_weg);
                            panel_benutzer_weg.add(tf_benutzer_weg);
                            panel_benutzer_weg.add(btn_benutzer_weg);

                            frame_benutzer_weg.add(panel_benutzer_weg);
                            frame_benutzer_weg.pack();
                            frame_benutzer_weg.setVisible(true);

                            //Aktion bei OK:
                            btn_benutzer_weg.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(tf_benutzer_weg.getText()!=null){
                                        System.out.println("Lösche Benutzer");
                                        try {
                                            benutzerliste.benutzerLoeschen(tf_benutzer_weg.getText());
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        JDialog end_benutzer_weg = new JDialog();
                                        end_benutzer_weg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_benutzer_weg.setLocation(430, 100);
                                        JPanel panel_end_benutzer_weg = new JPanel();
                                        end_benutzer_weg.add(panel_end_benutzer_weg);
                                        JLabel lbl_end_benutzer_weg = new JLabel("Benutzer "+ tf_benutzer_weg.getText()+ " gelöscht.");
                                        lbl_end_benutzer_weg.setVisible(true);
                                        panel_end_benutzer_weg.add(lbl_end_benutzer_weg);
                                        end_benutzer_weg.pack();
                                        end_benutzer_weg.setVisible(true);

                                    }
                                    //Benutzer löschen Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_benutzer_weg.dispose();
                                    frame.setVisible(true);


                                }
                            });
                        }
                    });
                    koffer_neu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Neuen Koffer
                            JDialog frame_koffer_neu = new JDialog();
                            JPanel panel_koffer_neu=new JPanel();
                            panel_koffer_neu.setVisible(true);
                            JLabel lbl_koffer_neu = new JLabel("Nummer des neuen Koffers eintragen:");
                            JTextField tf_koffer_neu=new JTextField(2);
                            JButton btn_koffer_neu=new JButton("OK");
                            panel_koffer_neu.add(lbl_koffer_neu);
                            panel_koffer_neu.add(tf_koffer_neu);
                            panel_koffer_neu.add(btn_koffer_neu);

                            frame_koffer_neu.add(panel_koffer_neu);
                            frame_koffer_neu.pack();
                            frame_koffer_neu.setVisible(true);

                            //Aktion bei OK
                            btn_koffer_neu.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(tf_koffer_neu!=null){
                                        try {
                                            reservierungsliste.neuerKoffer(Integer.parseInt(tf_koffer_neu.getText()));
                                            JDialog end_koffer_neu = new JDialog();
                                            end_koffer_neu.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                            end_koffer_neu.setLocation(430, 100);
                                            JPanel panel_end_koffer_neu = new JPanel();
                                            end_koffer_neu.add(panel_end_koffer_neu);
                                            JLabel lbl_end_koffer_neu= new JLabel("Koffer "+tf_koffer_neu.getText()+"erstellt");
                                            lbl_end_koffer_neu.setVisible(true);
                                            panel_end_koffer_neu.add(lbl_end_koffer_neu);
                                            end_koffer_neu.pack();
                                            end_koffer_neu.setVisible(true);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }

                                    }
                                    //Neuer Koffer Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_koffer_neu.dispose();
                                    frame.setVisible(true);
                                }

                            });
                        }
                    });
                    koffer_weg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Koffer löschen
                            JDialog frame_koffer_weg = new JDialog();
                            JPanel panel_koffer_weg = new JPanel();
                            panel_koffer_weg.setVisible(true);

                            //Drop Down Menu mit vorhandenen Koffern. Wenn keine Koffer vorhanden, zurück zum Hauptmenu
                            if (reservierungsliste.kofferliste == null) {
                                JDialog end_no_koffer = new JDialog();
                                end_no_koffer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_koffer.setLocation(430, 100);
                                JPanel panel_end_no_koffer = new JPanel();
                                end_no_koffer.add(panel_end_no_koffer);
                                JLabel lbl_end_no_koffer = new JLabel("Es sind keine Koffer vorhanden, die gelöscht werden könnten.");
                                lbl_end_no_koffer.setVisible(true);
                                panel_end_no_koffer.add(lbl_end_no_koffer);
                                end_no_koffer.pack();
                                end_no_koffer.setVisible(true);

                                //Koffer löschen Fenster verschrotten, Hauptframe wieder sichtbar machen
                                frame_koffer_weg.dispose();
                                frame.setVisible(true);
                            } else {
                                JLabel lbl_koffer_weg = new JLabel("Zu löschenden Koffer wählen: ");
                                String[] koffer_weg = new String[Main.reservierungsliste.kofferliste.length];
                                for (int j = 0; j < reservierungsliste.kofferliste.length; j++) {
                                    koffer_weg[j] = "Koffer " + Main.reservierungsliste.kofferliste[j].gibNummer();
                                }
                                //Drop-Dowm-Menü für Kofferliste
                                final JComboBox<String> cb_koffer_weg = new JComboBox<String>(koffer_weg);

                                JButton btn_koffer_weg = new JButton("Löschen");
                                panel_koffer_weg.add(lbl_koffer_weg);
                                panel_koffer_weg.add(cb_koffer_weg);
                                panel_koffer_weg.add(btn_koffer_weg);

                                frame_koffer_weg.add(panel_koffer_weg);
                                frame_koffer_weg.pack();
                                frame_koffer_weg.setVisible(true);

                                //Bei Klick auf Löschen
                                btn_koffer_weg.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            reservierungsliste.kofferEntfernen(cb_koffer_weg.getSelectedIndex()+1);
                                            JDialog end_koffer_weg = new JDialog();
                                            end_koffer_weg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                            end_koffer_weg.setLocation(430, 100);
                                            JPanel panel_end_koffer_weg = new JPanel();
                                            end_koffer_weg.add(panel_end_koffer_weg);
                                            JLabel lbl_end_koffer_weg = new JLabel("Koffer "+(cb_koffer_weg.getSelectedIndex()+1)+" wurde entfernt.");
                                            lbl_end_koffer_weg.setVisible(true);
                                            panel_end_koffer_weg.add(lbl_end_koffer_weg);
                                            end_koffer_weg.pack();
                                            end_koffer_weg.setVisible(true);


                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        //Koffer löschen Fenster verschrotten, Hauptframe wieder sichtbar machen
                                        frame_koffer_weg.dispose();
                                        frame.setVisible(true);
                                    }
                                });

                            }

                        }
                    });













                }
                //Nutzer-Fenster öffner
                else{
                    //Hauptframe leeren, mit Menu-Inhalt füllen
                    frame.remove(panel_1);
                    frame.setVisible(false);
                    JPanel menu=new JPanel();
                    JLabel lbl_menu = new JLabel("Hallo "+ benutzerliste.gibAngemeldeterBenutzer().gibVorname()+". Was willst du tun?");
                    JButton reservieren = new JButton("Reservieren");
                    JButton stornieren = new JButton("Stornieren");
                    JButton passwort = new JButton("Passwort ändern");
                    JButton abmelden = new JButton("Abmelden");

                    menu.add(lbl_menu);
                    menu.add(reservieren);
                    menu.add(stornieren);
                    menu.add(passwort);
                    menu.add(abmelden);


                    frame.add(menu);
                    frame.pack();
                    frame.setVisible(true);
                    //Aktionen bei Buttons
                    reservieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Reservieren
                            JDialog frame_reservieren = new JDialog();
                            JPanel panel_reservieren=new JPanel();
                            panel_reservieren.setVisible(true);
                            JLabel lbl_reservieren = new JLabel("Für Reservieren Koffer wählen");
                            //Kofferliste aus Reservierungsliste
                            String[] koffers= new String[Main.reservierungsliste.kofferliste.length];
                            for (int j = 0;j<reservierungsliste.kofferliste.length;j++){
                                koffers[j] = "Koffer " + Main.reservierungsliste.kofferliste[j].gibNummer();
                            }
                            //Drop-Dowm-Menü für Kofferliste
                            final JComboBox<String> cb = new JComboBox<String>(koffers);
                            JButton btn_reservieren = new JButton("OK");

                            panel_reservieren.add(lbl_reservieren);
                            panel_reservieren.add(cb);
                            panel_reservieren.add(btn_reservieren);

                            frame_reservieren.add(panel_reservieren);
                            frame_reservieren.pack();
                            frame_reservieren.setVisible(true);
                            //Aktion bei OK
                            btn_reservieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    reservierungsliste.gewaehltesDatum = new Kalender(frame, reservierungsliste, Main.reservierungsliste.kofferliste[cb.getSelectedIndex()]).setPickedDate();
                                    //Aus Datum-String mache int-Datum
                                    int year = (1000 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(6)))) + (100 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(7))) + (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(8))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(9)));
                                    int month = (10 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(3)))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(4)));
                                    int day = (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(0))) + Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(1));

                                    LocalDate pickedDate = LocalDate.of(year, month, day);

                                    //Prüfe, ob eine Reservierung vorliegt: Wenn ja: Pop-Up: "Keine Reservierung, sonst: Mit Datum und Namen reservieren
                                    if (reservierungsliste.istReserviert(pickedDate, reservierungsliste.kofferliste[cb.getSelectedIndex()]) == true) {
                                        frame.dispose();
                                        JDialog end = new JDialog();
                                        end.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end.setLocation(430, 100);
                                        JPanel panel_end = new JPanel();
                                        end.add(panel_end);
                                        JLabel lbl_end = new JLabel("Keine Reservierung an diesem Datum möglich");
                                        panel_end.add(lbl_end);
                                        end.pack();
                                        end.setVisible(true);


                                    } else {
                                        try {
                                            reservierungsliste.reservieren(pickedDate, nutzer, Main.reservierungsliste.kofferliste[cb.getSelectedIndex()]);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        JDialog end_reservieren = new JDialog();
                                        end_reservieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_reservieren.setLocation(430, 100);
                                        JPanel panel_end_reservieren = new JPanel();
                                        end_reservieren.add(panel_end_reservieren);
                                        JLabel lbl_end_reservieren = new JLabel("Koffer " + reservierungsliste.kofferliste[cb.getSelectedIndex()].gibNummer() + " für " + nutzer + " am Datum " + reservierungsliste.gewaehltesDatum + " reserviert.");
                                       panel_end_reservieren.add(lbl_end_reservieren);
                                        end_reservieren.pack();
                                        end_reservieren.setVisible(true);

                                    }
                                    //Reservieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_reservieren.dispose();
                                    frame.setVisible(true);
                                }
                            });


                        }
                    });
                    stornieren.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Fenster für Stornieren
                            JDialog frame_stornieren = new JDialog();
                            JPanel panel_stornieren=new JPanel();
                            panel_stornieren.setVisible(true);
                            JLabel lbl_stornieren = new JLabel("Koffer und Datum zum Stornieren wählen, mit OK bestätigen");
                            //Drop Down Menu für getätigte Reservierungen, wenn keine Reservierungen vorhanden, Pop-Up und zurück zu Menu
                            if (reservierungsliste.reservierungenBenutzerAnzeigen(nutzer) == null) {
                                JDialog end_no_res = new JDialog();
                                end_no_res.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                end_no_res.setLocation(430, 100);
                                JPanel panel_end_stornieren = new JPanel();
                                end_no_res.add(panel_end_stornieren);
                                JLabel lbl_end_stornieren = new JLabel("Es sind keine Reservierungen auf deinen Namen vorhanden");
                                panel_end_stornieren.add(lbl_end_stornieren);
                                end_no_res.pack();
                                end_no_res.setVisible(true);

                                //Stornieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                frame_stornieren.dispose();
                                frame.setVisible(true);
                            }
                            //Drop Down Menu für getätigte Reservierungen
                            String[] reservierungen = new String[reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length];

                            for(int i =0;i<reservierungsliste.reservierungenBenutzerAnzeigen(nutzer).length;i++){
                                reservierungen[i]="Koffer "+  reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibKoffer().gibNummer()+ ", Datum: "+reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[i].gibDatum();
                            }
                            final JComboBox<String> cb_reservierungen = new JComboBox<String>(reservierungen);
                            JButton btn_stornieren = new JButton("OK");

                            panel_stornieren.add(lbl_stornieren);
                            panel_stornieren.add(cb_reservierungen);
                            panel_stornieren.add(btn_stornieren);
                            frame_stornieren.add(panel_stornieren);
                            frame_stornieren.pack();
                            frame_stornieren.setVisible(true);

                            btn_stornieren.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Stornieren
                                    String storniert = new String("Reservierung von Koffer "+reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer().gibNummer()+" am "+reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum() + " storniert.");
                                    try {
                                        reservierungsliste.stornieren(reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibDatum(),reservierungsliste.reservierungenBenutzerAnzeigen(nutzer)[cb_reservierungen.getSelectedIndex()].gibKoffer());
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    //Pop-up Bestätigung, Stornieren Fenster plattmachen, Menu-Frame wieder sichtbar machen
                                    JDialog end_stornieren = new JDialog();
                                    end_stornieren.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                    end_stornieren.setLocation(430, 100);
                                    JPanel panel_end_stornieren = new JPanel();
                                    end_stornieren.add(panel_end_stornieren);
                                    JLabel lbl_end_stornieren = new JLabel(storniert);
                                    panel_end_stornieren.add(lbl_end_stornieren);
                                    end_stornieren.pack();
                                    end_stornieren.setVisible(true);

                                    //Stornieren Fenster verschrotten, Hauptframe wieder sichtbar machen
                                    frame_stornieren.dispose();
                                    frame.setVisible(true);
                                }

                            });



                        }
                    });

                    passwort.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Neues Fenster zur neuen Passworteingabe öffnen
                            JDialog frame_passwort=new JDialog();
                            JPanel panel_passwort = new JPanel();
                            panel_passwort.setVisible(true);
                            JLabel lbl_pw_alt = new JLabel("Altes Passwort eingeben");
                            JPasswordField tf_pw_alt = new JPasswordField(20);
                            JLabel lbl_pw_neu = new JLabel("Neues Passwort eingeben");
                            JPasswordField tf_pw_neu = new JPasswordField(20);
                            JLabel lbl_pw_neu_2 = new JLabel("Neues Passwort wiederholen");
                            JPasswordField tf_pw_neu_2 = new JPasswordField(20);
                            JButton btn_passwort = new JButton("OK");
                            panel_passwort.add(lbl_pw_alt);
                            panel_passwort.add(tf_pw_alt);
                            panel_passwort.add(lbl_pw_neu);
                            panel_passwort.add(tf_pw_neu);
                            panel_passwort.add(lbl_pw_neu_2);
                            panel_passwort.add(tf_pw_neu_2);
                            panel_passwort.add(btn_passwort);

                            frame_passwort.add(panel_passwort);
                            frame_passwort.pack();
                            frame_passwort.setVisible(true);

                            //Aktion bei OK
                            btn_passwort.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Passwort prüfen: passt -> neues Passwort setzen, passt nicht -> neuer Versuch
                                    if(tf_pw_alt.getText().equals(benutzerliste.gibAngemeldeterBenutzer().gibPasswort())&&tf_pw_neu.getText().equals(tf_pw_neu_2.getText())){
                                        benutzerliste.gibAngemeldeterBenutzer().setzePasswort(tf_pw_neu.getText());
                                        JDialog end_passwort = new JDialog();
                                        end_passwort.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_passwort.setLocation(430, 100);
                                        JPanel panel_end_passwort = new JPanel();
                                        end_passwort.add(panel_end_passwort);
                                        JLabel lbl_end_passwort = new JLabel("Passwort geändert");
                                        lbl_end_passwort.setVisible(true);
                                        panel_end_passwort.add(lbl_end_passwort);
                                        end_passwort.pack();
                                        end_passwort.setVisible(true);


                                    }
                                    //Passwort Fenster verschrotten, Hauptframe wieder sichtbar machen


                                    else{JDialog end_pw_falsch = new JDialog();
                                        end_pw_falsch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                        end_pw_falsch.setLocation(430, 100);
                                        JPanel panel_end_pw_falsch = new JPanel();
                                        end_pw_falsch.add(panel_end_pw_falsch);
                                        JLabel lbl_end_pw_falsch = new JLabel("Passwort falsch.");
                                        lbl_end_pw_falsch.setVisible(true);
                                        panel_end_pw_falsch.add(lbl_end_pw_falsch);
                                        end_pw_falsch.pack();
                                        end_pw_falsch.setVisible(true);

                                    }
                                    frame_passwort.dispose();
                                    frame.setVisible(true);

                                }
                            });

                        }
                    });

                    abmelden.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Hauptframe unsichtbar machen
                            frame.setVisible(false);
                            //Neues Fenster zum Abmelden
                            JDialog frame_abmelden=new JDialog();
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

                            frame_abmelden.add(panel_abmelden);
                            frame_abmelden.pack();
                            frame_abmelden.setVisible(true);

                            btn_abmelden_ja.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Frame "abmelden" verschrotten, Frame 1 wieder aufbauen
                                    frame_abmelden.dispose();
                                    frame.remove(menu);
                                    tf_nutzer.setText("");
                                    tf_passwort.setText("");

                                    frame.add(panel_1);
                                    frame.pack();
                                    frame.setVisible(true);
                                }
                            });
                            btn_abmelden_nein.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Frame "abmelden" verschrotten, Frame Menu wieder aufbauen
                                    frame_abmelden.dispose();
                                    frame.setVisible(true);
                                }
                            });

                        }
                    });












                }
            }
        });
    }
}
