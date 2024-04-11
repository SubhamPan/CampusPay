import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminAddTransaction {
    public void show()
    {
//    Reguired Fields = Student Id (Droplist)
//             Vendor Id (Droplist)
//        Transaction Date = Current Date
//        Transaction Amount = Number Field
        JFrame f = new JFrame("Add Transaction");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setLayout(null);

        JLabel title = new JLabel("Add Transaction");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(200, 50);
        f.add(title);

        JLabel student_id = new JLabel("Student ID");
        student_id.setFont(new Font("Arial", Font.PLAIN, 20));
        student_id.setSize(100, 20);
        student_id.setLocation(100, 100);
        f.add(student_id);

//        Create drop down list from database by queriying the student table
        JComboBox<String> studentList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                studentList.addItem(rs.getString("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
}

        studentList.setFont(new Font("Arial", Font.PLAIN, 15));
        studentList.setSize(100, 20);
        studentList.setLocation(250, 100);
        f.add(studentList);

        JLabel vendor_id = new JLabel("Vendor ID");
        vendor_id.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor_id.setSize(100, 20);
        vendor_id.setLocation(100, 150);
        f.add(vendor_id);

//        Create drop down list from database by queriying the vendor table
        JComboBox<String> vendorList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");
            while (rs.next()) {
                vendorList.addItem(rs.getString("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
}

        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));

        vendorList.setSize(100, 20);
        vendorList.setLocation(250, 150);
        f.add(vendorList);




        JLabel transaction_amount = new JLabel("Transaction Amount");
        transaction_amount.setFont(new Font("Arial", Font.PLAIN, 20));
        transaction_amount.setSize(100, 20);
        transaction_amount.setLocation(100, 250);
        f.add(transaction_amount);

// Transaction Amount = Number Field
        JTextField transaction_amount_text = new JTextField();
        transaction_amount_text.setFont(new Font("Arial", Font.PLAIN, 15));
        transaction_amount_text.setSize(190, 20);
        transaction_amount_text.setLocation(250, 250);
        f.add(transaction_amount_text);

        JButton add = new JButton("Add Transaction");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(200, 350);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String student_id = studentList.getSelectedItem().toString();
                String vendor_id = vendorList.getSelectedItem().toString();
                String transaction_amount = transaction_amount_text.getText();
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call add_transaction(?,?,?)}");
                    cs.setString(1, student_id);
                    cs.setString(2, vendor_id);
                    cs.setString(3, transaction_amount);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Transaction Added Successfully");
//                    Go back to admin home
                    AdminHome adminHome = new AdminHome();
                    adminHome.show();
                    f.dispose();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Error Adding Transaction");

                }
            }
        });
        f.add(add);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 400);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminHome adminHome = new AdminHome();
                adminHome.show();
                f.dispose();
            }
        });
f.add(back);

        f.setVisible(true);

    }
    public static void main(String[] args) {
        AdminAddTransaction adminAddTransaction = new AdminAddTransaction();
        adminAddTransaction.show();
    }
}
