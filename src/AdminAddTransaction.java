import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminAddTransaction {
    public void show()
    {
        JFrame f = new JFrame("Add Transaction");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 600);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Add Transaction");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 100);
        container.add(title);

        JLabel student_id = new JLabel("Student ID");
        student_id.setFont(new Font("Arial", Font.PLAIN, 20));
        student_id.setSize(100, 20);
        student_id.setLocation(300, 150);
        f.add(student_id);

//        Create drop down list from database by queriying the student table
        JComboBox<String> studentList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_students()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                studentList.addItem(rs.getString("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
}

        studentList.setFont(new Font("Arial", Font.PLAIN, 15));
        studentList.setSize(150, 20);
        studentList.setLocation(450, 150);
        studentList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        f.add(studentList);

        JLabel vendor_id = new JLabel("Vendor ID");
        vendor_id.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor_id.setSize(100, 20);
        vendor_id.setLocation(300, 200);
        f.add(vendor_id);

//        Create drop down list from database by queriying the vendor table
        JComboBox<String> vendorList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_vendors()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                vendorList.addItem(rs.getString("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
}

        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));

        vendorList.setSize(150, 20);
        vendorList.setLocation(450, 200);
        vendorList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        f.add(vendorList);




        JLabel transaction_amount = new JLabel("Transaction Amount");
        transaction_amount.setFont(new Font("Arial", Font.PLAIN, 20));
        transaction_amount.setSize(200, 20);
        transaction_amount.setLocation(250, 250);
        f.add(transaction_amount);

// Transaction Amount = Number Field
        JTextField transaction_amount_text = new JTextField();
        transaction_amount_text.setFont(new Font("Arial", Font.PLAIN, 15));
        transaction_amount_text.setSize(150, 20);
        transaction_amount_text.setLocation(450, 250);
        transaction_amount_text.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        f.add(transaction_amount_text);

        JButton add = new JButton("Add Transaction");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(350, 300);
        add.setBackground(new Color(176, 166, 149));
        add.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String student_id = studentList.getSelectedItem().toString();
                String vendor_id = vendorList.getSelectedItem().toString();
                String transaction_amount = transaction_amount_text.getText();
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("call make_transaction(?, ?, ?)");
                    cs.setString(1, vendor_id);
                    cs.setString(2, student_id);
                    cs.setString(3, transaction_amount);
                    cs.execute();

                    JOptionPane.showMessageDialog(f, "Transaction Added Successfully");
//                    Go back to show transactions
            ShowTransactions showTransactions = new ShowTransactions();
            showTransactions.show();
                    f.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Error Adding Transaction");

                }
            }
        });
        f.add(add);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 350);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AdminHome adminHome = new AdminHome();
//                adminHome.show();
                ShowTransactions showTransactions = new ShowTransactions();
                showTransactions.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);

    }
    public static void main(String[] args) {
        AdminAddTransaction adminAddTransaction = new AdminAddTransaction();
        adminAddTransaction.show();
    }
}
