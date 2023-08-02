import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;

public class Kalender {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);

    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] button = new JButton[49];

    public Kalender(JDialog parent, Reservierungsliste reservierungsliste, Koffer koffer) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<<");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                System.out.println(month);
                if(month<0){
                    month = 11;
                    year=year-1;
                }
                displayDate(reservierungsliste, koffer);
            }
        });
        p2.add(previous);
        p2.add(l);

        JButton next = new JButton(">>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month=month+1;
                System.out.println(month);
                if(month>11){
                    month=0;
                    year=year+1;
                }
                displayDate(reservierungsliste, koffer);
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
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate(reservierungsliste, koffer);
        d.setVisible(true);
    }

    public void displayDate(Reservierungsliste reservierungsliste, Koffer koffer) {

        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for(int i =7;i<13;i++){
            button[i].setText("");
            button[i].setBackground(Color.white);
        }
        for (int i = 6 + dayOfWeek, day = 1; day <= daysInMonth; i++, day++) {
            button[i].setText("" + day);
            button[i].setBackground(Color.white);
            //Datum abgleichen und Feld rot hinterlegen
            int jahr=year;
            int monat=month+1;
            int tag=day;
            LocalDate datum_1 = LocalDate.of(jahr, monat, tag);

            if (reservierungsliste.istReserviert(datum_1, koffer)==true) {
                System.out.println("Kalender: Reservierung gefunden");
                button[i].setBackground(Color.red);
            }

        }

        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals("")){
            System.out.println("Kalender: day ist Null");
            return day;}
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}



