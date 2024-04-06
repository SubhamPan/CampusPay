import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginSystem extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Container c;
    private JLabel title;
    private JLabel user;
    private JTextField userText;
    private JLabel password;

    private JLabel role;

    private JTextField roleText;
    private JPasswordField passwordText;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;

    public LoginSystem() {
        setTitle("Login");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // add login.jpg image to the frame from the images folder in the project directory
        ImageIcon image = new ImageIcon("images/login.jpg");
        // make the image fit the frame
        Image img = image.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT);
        image = new ImageIcon(img);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(500, 100, 300, 400);
        add(imageLabel);

        c = getContentPane();
        c.setLayout(null);
        // set backgroung color to C5FFF8
        c.setBackground(new Color(197, 255, 248));

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

        role = new JLabel("Role");
        role.setFont(new Font("Arial", Font.PLAIN, 20));
        role.setSize(100, 20);
        role.setLocation(100, 125);
        c.add(role);

        roleText = new JTextField();
        roleText.setFont(new Font("Arial", Font.PLAIN, 15));
        roleText.setSize(190, 20);
        roleText.setLocation(200, 125);
        c.add(roleText);

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
        // set color to 7B66FF
        sub.setBackground(new Color(123, 102, 255));
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 200);
        reset.addActionListener(this);
        reset.setBackground(Color.RED);
        c.add(reset);

        // student registration option
        JButton register = new JButton("Student Registration");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setBounds(175, 250, 175, 20);
        register.setBackground(Color.ORANGE);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the student registration form
                StudentRegistration r = new StudentRegistration();
                // dispose the current frame
                dispose();
                r.show();
            }
        });
        c.add(register);

        // vendor registration option
        JButton vendorRegister = new JButton("Vendor Registration");
        vendorRegister.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorRegister.setBounds(175, 300, 175, 20);
        vendorRegister.setBackground(Color.ORANGE);
        vendorRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the vendor registration form
                VendorRegistration v = new VendorRegistration();
                // dispose the current frame
                dispose();
                v.show();
            }
        });
        c.add(vendorRegister);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data1;
            String data = "ID: " + userText.getText();
            String ID = userText.getText();
            String roletxt = roleText.getText();
            int role = -1;
            if (roletxt.toLowerCase().equals("student")) {
                role = 0;
            } else if (roletxt.toLowerCase().equals("vendor")) {
                role = 1;
            }
            else if (roletxt.toLowerCase().equals("admin")) {
                role = 2;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Role");
                return;
            }
            String password = passwordText.getText();
            // hash the password using sha256
            password = Hash.hash(password);
            try {
                Conn c = new Conn();
                ResultSet rs = c.stmt.executeQuery("SELECT * FROM login WHERE ID = '" + ID + "' AND password = '" + password + "'");
                if (rs.next()) {
                    // instantiate the user
                    User user = User.getInstance();
                    user.setId(ID);
                    user.setRole(role);
                    user.setPassword(password);
                    this.dispose();
                    if (role == 0) {
                        StudentHome home = new StudentHome();
                        home.show();
                    } else if (role == 1) {
                        VendorHome home = new VendorHome();
                        home.show();
                    } else if (role == 2) {
                        // temporary admin home is the student home
                        StudentHome home = new StudentHome();
                        home.show();
                    }
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
