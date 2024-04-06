import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorRegistration {
    // this class is used to register a vendor, this has - name, account_no, contact, password
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField nameText;
    private JLabel accountNo;
    private JTextField accountNoText;
    private JLabel contact;
    private JTextField contactText;
    private JLabel password;
    private JPasswordField passwordText;
    private JButton register;
    private JButton back;
    private JTextArea tout;

    public void show() {
        // create a new frame to store the vendor registration form
        JFrame f = new JFrame("Vendor Registration");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Vendor Registration");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a label for the name
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        f.add(name);

        // create a text field for the name
        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setSize(190, 20);
        nameText.setLocation(200, 100);
        f.add(nameText);

        // create a label for the account number
        accountNo = new JLabel("Account Number");
        accountNo.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNo.setSize(150, 20);
        accountNo.setLocation(100, 150);
        f.add(accountNo);

        // create a text field for the account number
        accountNoText = new JTextField();
        accountNoText.setFont(new Font("Arial", Font.PLAIN, 15));
        accountNoText.setSize(190, 20);
        accountNoText.setLocation(250, 150);
        f.add(accountNoText);

        // create a label for the contact
        contact = new JLabel("Contact");
        contact.setFont(new Font("Arial", Font.PLAIN, 20));
        contact.setSize(100, 20);
        contact.setLocation(100, 200);
        f.add(contact);

        // create a text field for the contact
        contactText = new JTextField();
        contactText.setFont(new Font("Arial", Font.PLAIN, 15));
        contactText.setSize(190, 20);
        contactText.setLocation(200, 200);
        f.add(contactText);

        // create a label for the password
        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100, 20);
        password.setLocation(100, 250);
        f.add(password);

        // create a text field for the password
        passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setSize(190, 20);
        passwordText.setLocation(200, 250);
        f.add(passwordText);

        // create a button to register the vendor
        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setSize(100, 20);
        register.setLocation(250, 300);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // register the vendor
                try {
                    Conn c = new Conn();
                    // use procedure register_vendor(IN ID varchar(50), IN v_name varchar(50), IN account_no varchar(50), IN contact varchar(50), IN password varchar(50))
                    CallableStatement cs = c.con.prepareCall("{call register_vendor(?, ?, ?, ?, ?)}");
                    String password = new String(passwordText.getPassword());
                    password = Hash.hash(password);
                    String ID = nameText.getText() + accountNoText.getText().substring(0, 4);
                    cs.setString(1, ID);
                    cs.setString(2, nameText.getText());
                    cs.setString(3, accountNoText.getText());
                    cs.setString(4, contactText.getText());
                    cs.setString(5, password);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Vendor registered successfully, your ID is " + ID + ". Please remember this ID.");
                    f.dispose();
                    // go back to the login system
                    LoginSystem login = new LoginSystem();
                    login.show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        f.add(register);

        // create a button to go back to the LoginSystem
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 350);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the login system
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);
    }
}
