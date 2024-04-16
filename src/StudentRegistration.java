import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class StudentRegistration {
    public void show() {
        JFrame frame = new JFrame("Register Student");
        frame.setBounds(300, 90, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Container container = frame.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Register Student");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(325, 100, 300, 30);
        container.add(title);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setBounds(300, 170, 100, 20);
        container.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setBounds(400, 170, 190, 20);
        nameText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(nameText);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        idLabel.setBounds(300, 220, 100, 20);
        container.add(idLabel);

        JTextField idText = new JTextField();
        idText.setFont(new Font("Arial", Font.PLAIN, 15));
        idText.setBounds(400, 220, 190, 20);
        idText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(idText);

        JLabel contactLabel = new JLabel("Contact");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contactLabel.setBounds(300, 270, 100, 20);
        container.add(contactLabel);

        JTextField contactText = new JTextField();
        contactText.setFont(new Font("Arial", Font.PLAIN, 15));
        contactText.setBounds(400, 270, 190, 20);
        contactText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(contactText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setBounds(300, 320, 100, 20);
        container.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setBounds(400, 320, 190, 20);
        passwordText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(passwordText);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setBounds(325, 370, 100, 30);
        submitButton.setBackground(new Color(176, 166, 149));
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // make a connection to the database
                Conn c = new Conn();
                try {
                    // use procedure register_student(IN ID varchar(50), IN account_no varchar(50), IN s_name varchar(50), IN contact char(10), IN password varchar(256))
                    CallableStatement cs = c.con.prepareCall("{call register_student(?, ?, ?, ?, ?)}");
                    cs.setString(1, idText.getText());
                    cs.setString(2, idText.getText());
                    cs.setString(3, nameText.getText());
                    cs.setString(4, contactText.getText());
                    String password = new String(passwordText.getPassword());
                    password = Hash.hash(password);
                    cs.setString(5, password);
                    cs.execute();

                    // show a success message
                    JOptionPane.showMessageDialog(frame, "Student registered successfully");

                    if(User.getInstance().getRole() != 2) {
                        LoginSystem login = new LoginSystem();
                        login.show();
                    }
                    frame.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex);
                    System.out.println(ex);
                }
            }
        });
        container.add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setBounds(475, 370, 100, 30);
        backButton.setBackground(new Color(176, 166, 149));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        backButton.addActionListener(new ActionListener() {
            // Add your back button logic here
            @Override
            public void actionPerformed(ActionEvent e) {
                if(User.getInstance().getRole() != 2) {
                    LoginSystem login = new LoginSystem();
                    login.show();
                }
                frame.dispose();
            }
        });
        container.add(backButton);

        frame.setVisible(true);

        // Centering the form within the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }

    public static void main(String[] args) {
        StudentRegistration studentRegistration = new StudentRegistration();
        studentRegistration.show();
    }
}
