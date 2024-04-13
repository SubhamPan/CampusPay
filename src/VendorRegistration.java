import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorRegistration {
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
        JFrame f = new JFrame("Vendor Registration");
        f.setBounds(300, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // Setting background color
        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        title = new JLabel("Vendor Registration");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(300, 30, 300, 40); // Centered horizontally
        container.add(title);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setBounds(250, 100, 100, 20); // Centered horizontally
        container.add(name);

        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setBounds(350, 100, 190, 20); // Centered horizontally
        nameText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(nameText);

        accountNo = new JLabel("Acc No.");
        accountNo.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNo.setBounds(250, 150, 150, 20); // Centered horizontally
        container.add(accountNo);

        accountNoText = new JTextField();
        accountNoText.setFont(new Font("Arial", Font.PLAIN, 15));
        accountNoText.setBounds(350, 150, 190, 20); // Centered horizontally
        accountNoText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(accountNoText);

        contact = new JLabel("Contact");
        contact.setFont(new Font("Arial", Font.PLAIN, 20));
        contact.setBounds(250, 200, 100, 20); // Centered horizontally
        container.add(contact);

        contactText = new JTextField();
        contactText.setFont(new Font("Arial", Font.PLAIN, 15));
        contactText.setBounds(350, 200, 190, 20); // Centered horizontally
        contactText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(contactText);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(250, 250, 100, 20); // Centered horizontally
        container.add(password);

        passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setBounds(350, 250, 190, 20); // Centered horizontally
        passwordText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // EBE3D5
        container.add(passwordText);

        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.PLAIN, 15));
        register.setBounds(300, 300, 100, 30); // Centered horizontally
        register.setBackground(new Color(176, 166, 149)); // B0A695
        register.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
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
        container.add(register);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setBounds(300, 350, 100, 30); // Centered horizontally
        back.setBackground(new Color(176, 166, 149)); // B0A695
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the login system
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        container.add(back);

        // Centering the form within the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);

        f.setVisible(true);
    }
}
