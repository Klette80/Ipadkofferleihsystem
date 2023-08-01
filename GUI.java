import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class GUI {

    public GUI(Reservierungsliste reservierungsliste){
        final String pickedDate;
        JDialog frame = new JDialog();
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setLocation(430, 100);
        JPanel panel = new JPanel();
        frame.add(panel);
        JLabel lbl = new JLabel("Name eingeben, Koffer wählen und Ok klicken");
        lbl.setVisible(true);
        panel.add(lbl);
        JTextField tf = new JTextField(20);
        tf.setVisible(true);
        panel.add(tf);

        //Anzahl der Koffer ermitteln
        //int kofferanzahl = Main.reservierungsliste.gibKofferzanzahl();
        String[] koffers= new String[Main.reservierungsliste.kofferliste.length];
        for (int j = 0;j<reservierungsliste.kofferliste.length;j++){

            koffers[j]= "Koffer "+ Main.reservierungsliste.kofferliste[j].gibNummer();
        }

        final JComboBox<String> cb = new JComboBox<String>(koffers);
        cb.setVisible(true);
        panel.add(cb);
        JButton btn = new JButton("OK");
        panel.add(btn);
        frame.pack();
        frame.setVisible(true);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int koffernr = cb.getSelectedIndex();
                if (Objects.equals(tf.getText(), "")) {
                    System.out.println("Kein Name eingegeben");

                }
                reservierungsliste.gewaehltesDatum = new Kalender(frame, reservierungsliste, Main.reservierungsliste.kofferliste[koffernr]).setPickedDate();
                System.out.println("GUI: Ende");
                System.out.println(reservierungsliste.gewaehltesDatum);

                int year = (1000 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(6)))) + (100 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(7))) + (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(8))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(9)));
                int month = (10 * (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(3)))) + (Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(4)));
                int day = (10 * Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(0))) + Character.getNumericValue(reservierungsliste.gewaehltesDatum.charAt(1));

                LocalDate pickedDate = LocalDate.of(year, month, day);
                System.out.println("GUI:ActionListener Datum :" + pickedDate);
                if (reservierungsliste.istReserviert(pickedDate, reservierungsliste.kofferliste[cb.getSelectedIndex()]) == true) {
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
                        reservierungsliste.reservieren(pickedDate, tf.getText(), Main.reservierungsliste.kofferliste[koffernr]);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    frame.dispose();
                    JDialog end = new JDialog();
                    end.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    end.setLocation(430, 100);
                    JPanel panel_end = new JPanel();
                    end.add(panel_end);
                    JLabel lbl_end = new JLabel("Koffer " + Main.reservierungsliste.kofferliste[koffernr].gibNummer() + " für " + tf.getText() + " am Datum " + reservierungsliste.gewaehltesDatum + " reserviert.");
                    lbl_end.setVisible(true);
                    panel_end.add(lbl_end);
                    end.pack();
                    end.setVisible(true);
                }
            }
        });

    }

}
