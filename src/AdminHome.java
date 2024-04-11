import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminHome {
    public void show() {
        // create a frame
        JFrame f = new JFrame("Admin Home");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setLayout(null);

        // create a label for the title
        JLabel title = new JLabel("Admin Home");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(200, 50);
        f.add(title);

        // create a button to view the list of vendors
        JButton vendors = new JButton("View Vendors");
        vendors.setFont(new Font("Arial", Font.PLAIN, 15));
        vendors.setSize(200, 20);
        vendors.setLocation(200, 150);
        vendors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AdminVendors vendor = new AdminVendors();
//                vendor.show();
//                f.dispose();
                ViewVendors viewVendors = new ViewVendors();
                viewVendors.show();
                f.dispose();
            }
        });
        f.add(vendors);

        // create a button to view the list of students
        JButton students = new JButton("View Students");
        students.setFont(new Font("Arial", Font.PLAIN, 15));
        students.setSize(200, 20);
        students.setLocation(200, 200);
        students.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AdminStudents student = new AdminStudents();
//                student.show();
//                f.dispose();
                ViewStudents viewStudents = new ViewStudents();
                viewStudents.show();
                f.dispose();
            }
        });
        f.add(students);

        // create a button to view the list of transactions
        JButton transactions = new JButton("Show Transactions");
        transactions.setFont(new Font("Arial", Font.PLAIN, 15));
        transactions.setSize(200, 20);
        transactions.setLocation(200, 250);
        transactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTransactions showTransactions = new ShowTransactions();
                showTransactions.show();
                f.dispose();
            }
        });
        f.add(transactions);

        // create a button to logout
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setSize(100, 20);
        logout.setLocation(250, 500);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.getInstance().setId(null);
                User.getInstance().setRole(-1);
                User.getInstance().setPassword(null);
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        f.add(logout);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        AdminHome adminHome = new AdminHome();
        adminHome.show();
    }
}
