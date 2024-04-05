import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Vendors {
    // this will show the list of vendors
    // vendors are stored in the database in the vendors table
    public void show() {
        // create a new frame to store the list of vendors
        JFrame f = new JFrame("Vendors");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        JLabel title = new JLabel("Vendors");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a text area to store the list of vendors
        JTextArea vendors = new JTextArea();
        vendors.setFont(new Font("Arial", Font.PLAIN, 15));
        vendors.setSize(400, 200);
        vendors.setLocation(100, 100);
        f.add(vendors);

        // connect to the database and get the list of vendors
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");

            // display the list of vendors
            while (rs.next()) {
                vendors.append("ID: " + rs.getString("ID") + "\n");
                vendors.append("Name: " + rs.getString("v_name") + "\n");
                vendors.append("Contact: " + rs.getString("contact") + "\n\n");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 300);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                Home home = new Home();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
    }
}
