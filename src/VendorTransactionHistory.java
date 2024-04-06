import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorTransactionHistory {
    // this class is used to display the transaction history of a vendor
    private Container c;
    private JLabel title;
    private JTextArea transactions;
    private JScrollPane scroll;

    public void show() {
        // create a new frame to store the transaction history
        JFrame f = new JFrame("Transaction History");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Transaction History");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a scrollable area to store the transaction history
        transactions = new JTextArea(30, 30);
        transactions.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(transactions);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 100);
        f.add(scroll);

        // connect to the database and get the transaction history
        try {
            Conn c = new Conn();
            // use procedure get_all_transactions_made_by_vendor(IN vendor_id varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions_made_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            // display the transaction history
            while (rs.next()) {
                transactions.append("Transaction ID: " + rs.getString("ID") + "\n");
                transactions.append("Student ID: " + rs.getString("student_id") + "\n");
                transactions.append("Amount: " + rs.getString("total_amount") + "\n");
                transactions.append("Date and Time: " + rs.getString("date_time") + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 300);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorHome home = new VendorHome();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);
    }
}
