import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EditVendor {
    // this class will be used to edit a vendor
    private Container c;
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
        // create a new frame to store the edit vendor form
        JFrame f = new JFrame("Edit Vendor");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Edit Vendor");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a label for the vendor ID
        ID = new JLabel("Vendor ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(100, 20);
        ID.setLocation(100, 50);
        f.add(ID);

        // create a drop down list for the vendors, this will be populated from the database
        JComboBox<String> vendorList = new JComboBox<String>();

        if(User.getInstance().getRole() == 2) {
            // user is admin, get all vendors
            try {
                Conn c = new Conn();
                ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");
                while (rs.next()) {
                    // ID - vname
                    vendorList.addItem(rs.getString("ID") + " - " + rs.getString("v_name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // user is vendor, make the drop down list uneditable and show only the vendor's details
            vendorList.setEnabled(false);
            vendorList.addItem(User.getInstance().getId());
        }
        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorList.setSize(190, 20);
        vendorList.setLocation(200, 50);
        f.add(vendorList);

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
        account = new JLabel("Account Number");
        account.setFont(new Font("Arial", Font.PLAIN, 20));
        account.setSize(200, 20);
        account.setLocation(100, 150);
        f.add(account);

        // create a text field for the account number
        accountText = new JTextField();
        accountText.setFont(new Font("Arial", Font.PLAIN, 15));
        accountText.setSize(190, 20);
        accountText.setLocation(200, 150);
        f.add(accountText);

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

        // create a button to edit the vendor
        edit = new JButton("Edit");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(100, 20);
        edit.setLocation(250, 300);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the values from the form
                String v_name = nameText.getText();
                String v_account = accountText.getText();
                String v_contact = contactText.getText();
                String v_password = passwordText.getText();

                // use procedure update_vendor_details(IN ID varchar(50), IN v_name varchar(50), IN account_no varchar(50), IN contact char(10), IN password varchar(256))
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
        f.add(edit);

        // create a button to go back to the home page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 350);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);
    }
}
