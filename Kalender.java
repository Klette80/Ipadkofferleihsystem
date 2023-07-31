
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;

public class Kalender {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] button = new JButton[49];







    public Kalender(JFrame parent,Reservierungsliste reservierungsliste) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<<");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate(reservierungsliste);
            }
        });
        p2.add(previous);

        p2.add(l);

        JButton next = new JButton(">>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate(reservierungsliste);
            }
        });
        p2.add(next);

        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6) {
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                    }
                });
            }
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
            }
            p1.add(button[x]);
        }


        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate(reservierungsliste);
            }
        });
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate(reservierungsliste);
        d.setVisible(true);
    }

    public void displayDate(Reservierungsliste reservierungsliste) {

        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int i = 6 + dayOfWeek, day = 1; day <= daysInMonth; i++, day++) {
            button[i].setText("" + day);
            button[i].setBackground(Color.white);
            //Datum abgleichen und Feld rot hinterlegen
            int jahr=year;
            int monat=month+1;
            int tag=day;
           Date datum_1 = new Date(jahr, monat, tag);
            System.out.println("Year Input:"+year+"Datum in Date:"+datum_1.getYear());
            System.out.println("Month Input:"+month+"Datum in Date:"+datum_1.getMonth());
            System.out.println("Day Input:"+day+"Datum in Date:"+datum_1.getDate());
            if (reservierungsliste.istReserviert(datum_1, reservierungsliste.kofferliste[1])==true) {
System.out.println("reservierung gefunden");
            button[i].setBackground(Color.red);
        }

        }

        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals("")){
            System.out.println("day ist Null");
            return day;}
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}



