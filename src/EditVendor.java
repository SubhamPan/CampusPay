import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EditVendor {
    private JLabel title;
    private JLabel ID;
    private JLabel name;
    private JTextField nameText;
    private JLabel account;
    private JTextField accountText;
    private JLabel contact;
    private JTextField contactText;
    private JLabel password;
    private JPasswordField passwordText;
    private JButton edit;
    private JButton back;

    public void show() {
        JFrame f = new JFrame("Edit Vendor");
        f.setBounds(300, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        title = new JLabel("Edit Vendor");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(300, 30, 300, 30);
        container.add(title);

        ID = new JLabel("Vendor ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setBounds(100, 50, 100, 20);
        container.add(ID);

        JComboBox<String> vendorList = new JComboBox<String>();
        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorList.setBounds(200, 50, 190, 20);
        container.add(vendorList);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setBounds(100, 100, 100, 20);
        container.add(name);

        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setBounds(200, 100, 190, 20);
        container.add(nameText);

        account = new JLabel("Acc No.");
        account.setFont(new Font("Arial", Font.PLAIN, 20));
        account.setBounds(100, 150, 200, 20);
        container.add(account);

        accountText = new JTextField();
        accountText.setFont(new Font("Arial", Font.PLAIN, 15));
        accountText.setBounds(200, 150, 190, 20);
        container.add(accountText);

        contact = new JLabel("Contact");
        contact.setFont(new Font("Arial", Font.PLAIN, 20));
        contact.setBounds(100, 200, 100, 20);
        container.add(contact);

        contactText = new JTextField();
        contactText.setFont(new Font("Arial", Font.PLAIN, 15));
        contactText.setBounds(200, 200, 190, 20);
        container.add(contactText);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(100, 250, 100, 20);
        container.add(password);

        passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setBounds(200, 250, 190, 20);
        container.add(passwordText);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_vendor_details(?)}");
            if(User.getInstance().getRole() == 1) {
                cs.setString(1, User.getInstance().getId());
            } else {
                String[] parts = vendorList.getSelectedItem().toString().split(" - ");
                cs.setString(1, parts[0]);
            }
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                nameText.setText(rs.getString("v_name"));
                accountText.setText(rs.getString("account_no"));
                contactText.setText(rs.getString("contact"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        edit = new JButton("Edit");
        configureButton(edit, 250, 300, 100, 30);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String v_name = nameText.getText();
                String v_account = accountText.getText();
                String v_contact = contactText.getText();
                String v_password = passwordText.getText();

                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call update_vendor_details(?, ?, ?, ?, ?)}");
                    if(User.getInstance().getRole() == 1) {
                        cs.setString(1, User.getInstance().getId());
                    } else {
                        String[] parts = vendorList.getSelectedItem().toString().split(" - ");
                        cs.setString(1, parts[0]);
                    }
                    cs.setString(2, v_name);
                    cs.setString(3, v_account);
                    cs.setString(4, v_contact);
                    v_password = Hash.hash(v_password);
                    cs.setString(5, v_password);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Vendor details updated successfully");
                    f.dispose();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        container.add(edit);

        back = new JButton("Back");
        configureButton(back, 250, 350, 100, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        container.add(back);

        f.setVisible(true);
    }

    private void configureButton(JButton button, int x, int y, int width, int height) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(176, 166, 149)); // B0A695
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
    }

    public static void main(String[] args) {
        EditVendor ev = new EditVendor();
        ev.show();
    }
}
