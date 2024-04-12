// Main login system class
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
        frame.setBounds(300, 90, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Add login.jpg image to the frame from the images folder in the project directory
        ImageIcon image = new ImageIcon("images/login.jpg");
        // Make the image fit the frame
        Image img = image.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT);
        image = new ImageIcon(img);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBounds(500, 100, 300, 400);
        frame.add(imageLabel);

        Container container = frame.getContentPane();
        container.setLayout(null);
        // Set background color to C5FFF8
        container.setBackground(new Color(197, 255, 248));

        JLabel title = new JLabel("Login System");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        container.add(title);

        JLabel userLabel = new JLabel("ID");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        userLabel.setSize(100, 20);
        userLabel.setLocation(100, 100);
        container.add(userLabel);

        JTextField userText = new JTextField();
        userText.setFont(new Font("Arial", Font.PLAIN, 15));
        userText.setSize(190, 20);
        userText.setLocation(200, 100);
        container.add(userText);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        roleLabel.setSize(100, 20);
        roleLabel.setLocation(100, 125);
        container.add(roleLabel);

        JTextField roleText = new JTextField();
        roleText.setFont(new Font("Arial", Font.PLAIN, 15));
        roleText.setSize(190, 20);
        roleText.setLocation(200, 125);
        container.add(roleText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setSize(100, 20);
        passwordLabel.setLocation(100, 150);
        container.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setSize(190, 20);
        passwordText.setLocation(200, 150);
        container.add(passwordText);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setSize(100, 20);
        submitButton.setLocation(150, 200);
        submitButton.setBackground(new Color(123, 102, 255));
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
        resetButton.setSize(100, 20);
        resetButton.setLocation(270, 200);
        resetButton.setBackground(Color.RED);
        resetButton.addActionListener(e -> {
            userText.setText("");
            passwordText.setText("");
        });
        container.add(resetButton);

        // Student registration option
        JButton registerButton = new JButton("Student Registration");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setBounds(175, 250, 175, 20);
        registerButton.setBackground(Color.ORANGE);
        registerButton.addActionListener(e -> {
            // Show the student registration form
            StudentRegistration registration = new StudentRegistration();
            // Dispose the current frame
            frame.dispose();
            registration.show();
        });
        container.add(registerButton);

        // Vendor registration option
        JButton vendorRegisterButton = new JButton("Vendor Registration");
        vendorRegisterButton.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorRegisterButton.setBounds(175, 300, 175, 20);
        vendorRegisterButton.setBackground(Color.ORANGE);
        vendorRegisterButton.addActionListener(e -> {
            // Show the vendor registration form
            VendorRegistration registration = new VendorRegistration();
            // Dispose the current frame
            frame.dispose();
            registration.show();
        });
        container.add(vendorRegisterButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.show();
    }
}

