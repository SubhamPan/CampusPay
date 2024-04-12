import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class AdminRegistration {
    // this class is used to register a new admin
    void show() {
        // create a new frame to store the registration form
        JFrame f = new JFrame("Register Admin");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        JLabel title = new JLabel("Register Admin");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // admin has ID, password
        // create a label for the ID
        JLabel ID = new JLabel("ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(100, 20);
        ID.setLocation(100, 100);
        f.add(ID);

        // create a text field for the ID
        JTextField IDText = new JTextField();
        IDText.setFont(new Font("Arial", Font.PLAIN, 15));
        IDText.setSize(190, 20);
        IDText.setLocation(200, 100);
        f.add(IDText);

        // create a label for the password
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100, 20);
        password.setLocation(100, 150);
        f.add(password);

        // create a password field for the password
        JPasswordField passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setSize(190, 20);
        passwordText.setLocation(200, 150);
        f.add(passwordText);

        // create a button to submit the form
        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(250, 200);
        f.add(submit);

        // create a button to reset the form
        JButton reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(150, 200);
        f.add(reset);

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
                String password = passwordText.getText();
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
    }

    public static void main(String[] args) {
        AdminRegistration admin = new AdminRegistration();
        admin.show();
    }
}
