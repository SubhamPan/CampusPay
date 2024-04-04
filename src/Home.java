import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home {
    // show the home page
    public void show() {
        // create a new frame to store text field and button
        JFrame f = new JFrame("Home");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        JLabel title = new JLabel("Home Page");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // vendors page button
        JButton vendors = new JButton("Vendors");
        vendors.setFont(new Font("Arial", Font.PLAIN, 15));
        vendors.setSize(100, 20);
        vendors.setLocation(250, 100);
        vendors.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // show the list of vendors
                Vendors vendors = new Vendors();
                vendors.show();
            }
        });
        f.add(vendors);

        // create a button
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setSize(100, 20);
        logout.setLocation(250, 200);
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the login page
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        f.add(logout);

        // set the frame visibility
        f.setVisible(true);
    }
}
