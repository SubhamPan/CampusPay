import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterStudent {
    // this class is used to register a student
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField nameText;
    private JLabel ID;
    private JTextField IDText;
    private JLabel Contact;
    private JTextField ContactText;
    private JLabel password;
    private JPasswordField passwordText;
    private JButton submit;
    private JButton back;
    private JTextArea tout;

    public void show() {
        // create a new frame to store the registration form
        JFrame f = new JFrame("Register Student");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Register Student");
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

        // create a label for the ID
        ID = new JLabel("ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(100, 20);
        ID.setLocation(100, 150);
        f.add(ID);

        // create a text field for the ID
        IDText = new JTextField();
        IDText.setFont(new Font("Arial", Font.PLAIN, 15));
        IDText.setSize(190, 20);
        IDText.setLocation(200, 150);
        f.add(IDText);

        // create a label for the contact
        Contact = new JLabel("Contact");
        Contact.setFont(new Font("Arial", Font.PLAIN, 20));
        Contact.setSize(100, 20);
        Contact.setLocation(100, 200);
        f.add(Contact);

        // create a text field for the contact
        ContactText = new JTextField();
        ContactText.setFont(new Font("Arial", Font.PLAIN, 15));
        ContactText.setSize(190, 20);
        ContactText.setLocation(200, 200);
        f.add(ContactText);

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

        // create a button to submit the form
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(150, 300);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // make a connection to the database
                Conn c = new Conn();
                try {
                    // get the values from the text fields
                    String name = nameText.getText();
                    String ID = IDText.getText();
                    String contact = ContactText.getText();
                    String password = passwordText.getText();
                    // hash the password using sha256
                    password = Hash.hash(password);
                    String BITS_account = ID;

                    // insert the values into the database
                    c.stmt.executeUpdate("INSERT INTO student VALUES ('" + ID + "', '" + BITS_account + "', '" + name + "', '" + contact + "', '" + password + "')");

                    // add the login details to the login table
                    c.stmt.executeUpdate("INSERT INTO login VALUES ('" + ID + "', '" + password + "')");

                    // show a success message
                    JOptionPane.showMessageDialog(f, "Student registered successfully");

                    // go back to the login page
                    LoginSystem login = new LoginSystem();
                    login.show();
                    f.dispose();

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        f.add(submit);

        // create a button to go back to the LoginSystem
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(270, 300);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the login page
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
    }
}
