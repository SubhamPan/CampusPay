import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSystem extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Container c;
    private JLabel title;
    private JLabel user;
    private JTextField userText;
    private JLabel password;
    private JPasswordField passwordText;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;

    public LoginSystem() {
        setTitle("Login");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Login System");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        user = new JLabel("ID");
        user.setFont(new Font("Arial", Font.PLAIN, 20));
        user.setSize(100, 20);
        user.setLocation(100, 100);
        c.add(user);

        userText = new JTextField();
        userText.setFont(new Font("Arial", Font.PLAIN, 15));
        userText.setSize(190, 20);
        userText.setLocation(200, 100);
        c.add(userText);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100, 20);
        password.setLocation(100, 150);
        c.add(password);

        passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setSize(190, 20);
        passwordText.setLocation(200, 150);
        c.add(passwordText);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 200);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 200);
        reset.addActionListener(this);
        c.add(reset);

        // student registration option
        JButton register = new JButton("Student Registration");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setSize(200, 20);
        register.setLocation(200, 250);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the student registration form
                RegisterStudent r = new RegisterStudent();
                // dispose the current frame
                dispose();
                r.show();
            }
        });
        c.add(register);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data1;
            String data = "ID: " + userText.getText();
            String ID = userText.getText();
            String password = passwordText.getText();
            // hash the password using sha256
            password = Hash.hash(password);
            try {
                Conn c = new Conn();
                ResultSet rs = c.stmt.executeQuery("SELECT * FROM login WHERE ID = '" + ID + "' AND password = '" + password + "'");
                if (rs.next()) {
                    this.dispose();
                    Home h = new Home();
                    h.show();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid ID or Password");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == reset) {
            userText.setText("");
            passwordText.setText("");
        }
    }

    public static void main(String[] args) {
        LoginSystem f = new LoginSystem();
        f.setVisible(true);
    }
}
