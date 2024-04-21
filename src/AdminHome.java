import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminHome {
    public void show() {
        // create a frame
        JFrame f = new JFrame("Admin Home");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 600);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        // create a label for the title
        JLabel title = new JLabel("Admin Home");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 50);
        container.add(title);

        // create a button to view the list of vendors
        JButton vendors = new JButton("View Vendors");
        vendors.setFont(new Font("Arial", Font.PLAIN, 15));
        vendors.setSize(200, 20);
        vendors.setLocation(350, 150);
        vendors.setBackground(new Color(176, 166, 149)); // Set background color
        vendors.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        vendors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewVendors viewVendors = new ViewVendors();
                viewVendors.show();
                f.dispose();
            }
        });
        container.add(vendors);

        // create a button to view the list of students
        JButton students = new JButton("View Students");
        students.setFont(new Font("Arial", Font.PLAIN, 15));
        students.setSize(200, 20);
        students.setLocation(350, 200);
        students.setBackground(new Color(176, 166, 149)); // Set background color
        students.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        students.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewStudents viewStudents = new ViewStudents();
                viewStudents.show();
                f.dispose();
            }
        });
        container.add(students);

        // create a button to view the list of transactions
        JButton transactions = new JButton("Show Transactions");
        transactions.setFont(new Font("Arial", Font.PLAIN, 15));
        transactions.setSize(200, 20);
        transactions.setLocation(350, 250);
        transactions.setBackground(new Color(176, 166, 149)); // Set background color
        transactions.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        transactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTransactions showTransactions = new ShowTransactions();
                showTransactions.show();
                f.dispose();
            }
        });
        container.add(transactions);

        // create a button to show orders
        JButton orders = new JButton("Show Orders");
        orders.setFont(new Font("Arial", Font.PLAIN, 15));
        orders.setSize(200, 20);
        orders.setLocation(350, 300);
        orders.setBackground(new Color(176, 166, 149)); // Set background color
        orders.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        orders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowOrders showOrders = new ShowOrders();
                showOrders.show();
                f.dispose();
            }
        });
        container.add(orders);

        // create a button to logout
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setSize(100, 20);
        logout.setLocation(400, 500);
        logout.setBackground(new Color(176, 166, 149)); // Set background color
        logout.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.getInstance().clear();
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        container.add(logout);
        f.setVisible(true);

        // Centering the frame on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

//    public static void main(String[] args) {
//        AdminHome adminHome = new AdminHome();
//        adminHome.show();
//    }
}
