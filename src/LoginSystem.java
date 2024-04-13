import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class LoginSystem {
    public void show() {
        // if User not null, show the respective home page
        if (User.getInstance().getId() != null) {
            if (User.getInstance().getRole() == 0) {
                StudentHome home = new StudentHome();
                home.show();
            } else if (User.getInstance().getRole() == 1) {
                VendorHome home = new VendorHome();
                home.show();
            } else {
                AdminHome home = new AdminHome();
                home.show();
            }
            return;
        }

        JFrame frame = new JFrame("Login");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        ImageIcon image = new ImageIcon("images/login.jpg");
        Image img = image.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT);
        image = new ImageIcon(img);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(500, 100, 300, 400);
        frame.add(imageLabel);

        Container container = frame.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("CAMPUSPAY");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(350, 30, 300, 30);
        container.add(title);

        JLabel userLabel = new JLabel("ID");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        userLabel.setBounds(100, 100, 100, 20);
        container.add(userLabel);

        JTextField userText = new JTextField();
        userText.setFont(new Font("Arial", Font.PLAIN, 15));
        userText.setBounds(200, 100, 190, 20);
        userText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(userText);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        roleLabel.setBounds(100, 130, 100, 20);
        container.add(roleLabel);

        JTextField roleText = new JTextField();
        roleText.setFont(new Font("Arial", Font.PLAIN, 15));
        roleText.setBounds(200, 130, 190, 20);
        roleText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(roleText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setBounds(100, 160, 100, 20);
        container.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setBounds(200, 160, 190, 20);
        passwordText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(passwordText);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setBounds(150, 200, 100, 30);
        submitButton.setBackground(new Color(176, 166, 149)); // B0A695
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
        submitButton.addActionListener(e -> {
            String ID = userText.getText();
            int role = -1;
            String roleTxt = roleText.getText();
            if (roleTxt.toLowerCase().equals("student")) {
                role = 0;
            } else if (roleTxt.toLowerCase().equals("vendor")) {
                role = 1;
            } else if (roleTxt.toLowerCase().equals("admin")) {
                role = 2;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Role");
                return;
            }
            String password = new String(passwordText.getPassword());
            // Hash the password using sha256
            password = Hash.hash(password);
            try {
                Conn c = new Conn();
                // Use procedure verify_login(IN ID varchar(50), IN password varchar(256), IN role int)
                CallableStatement cs = c.con.prepareCall("{call verify_login(?, ?, ?)}");
                cs.setString(1, ID);
                cs.setString(2, password);
                cs.setInt(3, role);
                ResultSet rs = cs.executeQuery();

                if (rs.next()) {
                    // Instantiate the user
                    User user = User.getInstance();
                    user.setId(ID);
                    user.setRole(role);
                    user.setPassword(password);
                    frame.dispose();
                    if (role == 0) {
                        StudentHome home = new StudentHome();
                        home.show();
                    } else if (role == 1) {
                        VendorHome home = new VendorHome();
                        home.show();
                    } else {
                        AdminHome home = new AdminHome();
                        home.show();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid ID or Password or Role");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        container.add(submitButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 15));
        resetButton.setBounds(270, 200, 100, 30);
        resetButton.setBackground(new Color(176, 166, 149)); // B0A695 (changed to match theme)
        resetButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695 (changed to match theme)
        resetButton.addActionListener(e -> {
            userText.setText("");
            passwordText.setText("");
        });
        container.add(resetButton);

        JButton registerButton = new JButton("Student Registration");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setBounds(175, 250, 175, 30);
        registerButton.setBackground(new Color(176, 166, 149)); // B0A695
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
        registerButton.addActionListener(e -> {
            // Show the student registration form
            StudentRegistration registration = new StudentRegistration();
            // Dispose the current frame
            frame.dispose();
            registration.show();
        });
        container.add(registerButton);

        JButton vendorRegisterButton = new JButton("Vendor Registration");
        vendorRegisterButton.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorRegisterButton.setBounds(175, 300, 175, 30);
        vendorRegisterButton.setBackground(new Color(176, 166, 149)); // B0A695
        vendorRegisterButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
        vendorRegisterButton.addActionListener(e -> {
            // Show the vendor registration form
            VendorRegistration registration = new VendorRegistration();
            // Dispose the current frame
            frame.dispose();
            registration.show();
        });
        container.add(vendorRegisterButton);

        frame.setVisible(true);

        // Centering the form within the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.show();
    }
}
