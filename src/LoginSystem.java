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

        // register button
        JButton register = new JButton("Register");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setSize(100, 20);
        register.setLocation(200, 250);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
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

    // register option
    public void register() {
        JFrame f = new JFrame("Register");
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l1, l2;
        JTextField tf1;
        JPasswordField p1;
        JButton b1, b2;

        l1 = new JLabel("ID");
        l1.setBounds(50, 50, 150, 30);
        f.add(l1);

        tf1 = new JTextField();
        tf1.setBounds(150, 50, 150, 30);
        f.add(tf1);

        l2 = new JLabel("Password");
        l2.setBounds(50, 100, 150, 30);
        f.add(l2);

        p1 = new JPasswordField();
        p1.setBounds(150, 100, 150, 30);
        f.add(p1);

        b1 = new JButton("Submit");
        b1.setBounds(50, 150, 100, 30);
        f.add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(200, 150, 100, 30);
        f.add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = tf1.getText();
                String password = p1.getText();
                // make the User object
                User user = User.getInstance();
                user.setId(ID);
                user.setPassword(password);
                try {
                    Conn con = new Conn();
                    con.stmt.executeUpdate("insert into login values('" + ID + "', '" + password + "')");
                    JOptionPane.showMessageDialog(f, "Data Saved Successfully");
                    f.dispose();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data1;
            String data = "ID: " + userText.getText();
            try {
                Conn con = new Conn();
                ResultSet rs = con.stmt.executeQuery("select * from login where ID='" + userText.getText() + "' and password='" + passwordText.getText() + "'");
                if (rs.next()) {
                    // make the User object
                    User user = User.getInstance();
                    user.setId(userText.getText());
                    user.setPassword(passwordText.getText());
                    data1 = "Login Successful";
                    Home a = new Home();
                    a.show();
                    this.dispose();
                } else {
                    data1 = "Login Failed";
                }
                tout.setText(data + "\n" + data1);
                tout.setEditable(false);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == reset) {
            String def = "";
            userText.setText(def);
            passwordText.setText(def);
            tout.setText(def);
        }
    }

    public static void main(String[] args) {
        LoginSystem f = new LoginSystem();
        f.setVisible(true);
    }
}
