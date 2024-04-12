import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ShowOrders {
    // this class is used to show the orders of a transaction
    void show() {
        // create a frame
        JFrame f = new JFrame("Show Orders");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setLayout(null);

        // create a label for the title
        JLabel title = new JLabel("Show Orders");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a label for the transaction ID
        JLabel ID = new JLabel("Transaction ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(150, 20);
        ID.setLocation(100, 100);
        f.add(ID);

        // create a drop down list for the transactions, this will be populated from the database
        JComboBox<String> transactionList = new JComboBox<String>();

        // get all transactions
        try {
            Conn c = new Conn();
            CallableStatement cs = null;
            if(User.getInstance().getRole() == 2) {
                // user is admin, get all transactions
                cs = c.con.prepareCall("{call get_all_transactions()}");
            } else if(User.getInstance().getRole() == 1) {
                // user is vendor, get transactions of the vendor
                cs = c.con.prepareCall("{call get_transactions_made_by_vendor(?)}");
                cs.setString(1, User.getInstance().getId());
            } else {
                // user is student, get transactions of the student
                cs = c.con.prepareCall("{call get_all_payments_made_by_student(?)}");
                cs.setString(1, User.getInstance().getId());
            }
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                transactionList.addItem(rs.getString("ID") + " - " + rs.getString("date_time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        transactionList.setSize(190, 20);
        transactionList.setLocation(300, 100);
        f.add(transactionList);

        // create a Jtable to show the orders
        // use DefaultTableModel to add rows dynamically
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(400, 200);
        table.setLocation(100, 150);
        f.add(table);

        // create a model for the table
        DefaultTableModel model = new DefaultTableModel();
        // item_id, item_name, quantity, price
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        table.setModel(model);

        // create a scroll pane for the table
        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 150);
        f.add(scroll);

        // create a button to show the orders of selected transaction
        JButton show = new JButton("Show Orders");
        show.setFont(new Font("Arial", Font.PLAIN, 15));
        show.setSize(150, 20);
        show.setLocation(250, 400);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected transaction
                String transaction = transactionList.getSelectedItem().toString();
                String[] parts = transaction.split(" - ");
                String transactionID = parts[0];

                // get the orders of the transaction
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call get_all_orders_of_transaction(?)}");
                    cs.setString(1, transactionID);
                    ResultSet rs = cs.executeQuery();

                    // display the orders
                    while (rs.next()) {
                        model.addRow(new Object[] {rs.getString("ID"), rs.getString("item_name"), rs.getString("quantity"), rs.getString("price")});
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        f.add(show);

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 450);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                if(User.getInstance().getRole() == 2) {
                    AdminHome home = new AdminHome();
                    home.show();
                } else if(User.getInstance().getRole() == 1) {
                    VendorHome home = new VendorHome();
                    home.show();
                } else {
                    StudentHome home = new StudentHome();
                    home.show();
                }
                f.dispose();
            }
        });
        f.add(back);

        // display the frame
        f.setVisible(true);
    }
}
