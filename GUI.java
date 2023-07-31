//import swing_1.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    public GUI(Reservierungsliste reservierungsliste){
        JLabel label = new JLabel("Selected Date:");
        final JTextField text = new JTextField(20);
        JButton b_1 = new JButton("Koffer 1");
        JButton b_2 = new JButton("Koffer 2");
        JPanel p = new JPanel();
        p.add(label);
        p.add(text);
        p.add(b_1);
        p.add(b_2);
        final JFrame f = new JFrame();
        f.getContentPane().add(p);
        f.pack();
        f.setVisible(true);
        b_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                text.setText(new Kalender(f,reservierungsliste).setPickedDate());
                //Reservierungsliste.reservieren(year,month,day);
            }
        });
    }
}
