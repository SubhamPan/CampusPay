import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminRegistration {
    // this class is used to register a new admin
    void show() {
        // create a new frame to store the registration form
        JFrame f = new JFrame("Register Admin");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(new Color(243, 238, 234)); // Set background color
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        // create a label
        JLabel title = new JLabel("Register Admin");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 130);
        container.add(title);

        // admin has ID, password
        // create a label for the ID
        JLabel ID = new JLabel("ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(100, 20);
        ID.setLocation(350, 200);
        container.add(ID);

        // create a text field for the ID
        JTextField IDText = new JTextField();
        IDText.setFont(new Font("Arial", Font.PLAIN, 15));
        IDText.setSize(190, 20);
        IDText.setLocation(400, 200);
        IDText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(IDText);

        // create a label for the password
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100, 20);
        password.setLocation(300, 250);
        container.add(password);

        // create a password field for the password
        JPasswordField passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setSize(190, 20);
        passwordText.setLocation(400, 250);
        passwordText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(passwordText);

        // create a button to submit the form
        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(350, 300);
        submit.setBackground(new Color(176, 166, 149)); // Set background color
        submit.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        container.add(submit);

        // create a button to reset the form
        JButton reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(500, 300);
        reset.setBackground(new Color(176, 166, 149)); // Set background color
        reset.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        container.add(reset);

        // clear the text fields when reset is clicked
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IDText.setText("");
                passwordText.setText("");
            }
        });

        // listen for the submit button to be clicked
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the ID and password from the text fields
                String ID = IDText.getText();
                String password = new String(passwordText.getPassword());
                password = Hash.hash(password);

                // use procedure register_admin(IN ID varchar(10), IN password varchar(20))
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call register_admin(?, ?)}");
                    cs.setString(1, ID);
                    cs.setString(2, password);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Admin registered successfully");
                    f.dispose();
                    // show the login page
                    LoginSystem login = new LoginSystem();
                    login.show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        // make the frame visible
        f.setVisible(true);

        // Centering the frame on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

    public static void main(String[] args) {
        AdminRegistration admin = new AdminRegistration();
        admin.show();
    }
}
