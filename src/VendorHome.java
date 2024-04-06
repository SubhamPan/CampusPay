import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorHome {
    // this will be the home page for the vendor
    public void show() {
        // create a new frame to store the text fields and buttons
        JFrame f = new JFrame("Home");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        JLabel title = new JLabel("Home Page");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // show the total amount earned by querying the database
        JLabel total = new JLabel("Total Amount Earned: ");
        total.setFont(new Font("Arial", Font.PLAIN, 20));
        total.setSize(150, 20);
        total.setLocation(100, 100);
        f.add(total);

        // connect to the database and get the total amount earned
        // use procedure get_total_amount_earned_by_vendor(IN vendor_id varchar(50), OUT total_amount_earned int)
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_total_amount_earned_by_vendor(?, ?)}");
            cs.setString(1, User.getInstance().getId());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            int total_amount_earned = cs.getInt(2);
            JLabel totalAmount = new JLabel(Integer.toString(total_amount_earned));
            totalAmount.setFont(new Font("Arial", Font.PLAIN, 20));
            totalAmount.setSize(150, 20);
            totalAmount.setLocation(250, 100);
            f.add(totalAmount);
        } catch (Exception e) {
            System.out.println(e);
        }

        // create a button to view the transaction history
        JButton transactionHistory = new JButton("Transaction History");
        transactionHistory.setFont(new Font("Arial", Font.PLAIN, 15));
        transactionHistory.setSize(200, 20);
        transactionHistory.setLocation(250, 140);
        transactionHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorTransactionHistory transaction = new VendorTransactionHistory();
                transaction.show();
                f.dispose();
            }
        });
        f.add(transactionHistory);

        // create a button to view the items sold by vendor
        JButton itemsSold = new JButton("My Items");
        itemsSold.setFont(new Font("Arial", Font.PLAIN, 15));
        itemsSold.setSize(200, 20);
        itemsSold.setLocation(250, 180);
        itemsSold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorItems items = new VendorItems();
                items.show();
                f.dispose();
            }
        });
        f.add(itemsSold);


        // add logout button
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
}
