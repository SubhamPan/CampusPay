import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VendorsMenu {
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

        // create a scrollable area to store the list of vendors
        JTextArea vendors = new JTextArea(30, 30);
        vendors.setFont(new Font("Arial", Font.PLAIN, 10));
        JScrollPane scroll = new JScrollPane(vendors);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 100);
        f.add(scroll);

        // connect to the database and get the list of vendors
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");

            // display the list of vendors
            while (rs.next()) {
                vendors.append("Vendor Name: " + rs.getString("v_name") + "\n");
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
                StudentHome home = new StudentHome();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
    }
}
