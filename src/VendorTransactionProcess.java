import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VendorTransactionProcess {
    // this class is used to generate and process transactions by the vendor
    void show() {
        // create a new frame
        JFrame f = new JFrame("Vendor Transaction Process");
        f.setSize(600, 600);
        f.setLocation(300, 100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label for the title
        JLabel title = new JLabel("Vendor Transaction Process");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(400, 30);
        title.setLocation(150, 50);
        f.add(title);

        // create a label for the amount
        JLabel amount = new JLabel("Amount");
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        amount.setSize(100, 20);
        amount.setLocation(100, 100);
        f.add(amount);

        // create a non editable text field for the amount
        // this will be calculated using the quantity and price
        JTextField amountText = new JTextField();
        amountText.setFont(new Font("Arial", Font.PLAIN, 15));
        amountText.setSize(190, 20);
        amountText.setLocation(200, 100);
        amountText.setEditable(false);
        // set the initial amount to 0
        amountText.setText("0");
        f.add(amountText);

        // create a label for the student_id
        JLabel student = new JLabel("Student ID");
        student.setFont(new Font("Arial", Font.PLAIN, 20));
        student.setSize(100, 20);
        student.setLocation(100, 125);
        f.add(student);

        // create a text field for the student_id
        JTextField studentText = new JTextField();
        studentText.setFont(new Font("Arial", Font.PLAIN, 15));
        studentText.setSize(190, 20);
        studentText.setLocation(200, 125);
        f.add(studentText);

        // there will be a drop down list for the products, this will be populated from the database
        JLabel product = new JLabel("Product");
        product.setFont(new Font("Arial", Font.PLAIN, 20));
        product.setSize(100, 20);
        product.setLocation(100, 150);
        f.add(product);

        JComboBox<String> productList = new JComboBox<String>();

        // use get_all_items_sold_by_vendor(IN vendor_id varchar(50)) to get the list of products
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                // ID, item_name
                productList.addItem("ID: " + rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        productList.setFont(new Font("Arial", Font.PLAIN, 15));
        productList.setSize(190, 20);
        productList.setLocation(200, 150);
        f.add(productList);

        // create a label for the quantity of the product
        JLabel quantity = new JLabel("Quantity");
        quantity.setFont(new Font("Arial", Font.PLAIN, 20));
        quantity.setSize(100, 20);
        quantity.setLocation(100, 200);
        f.add(quantity);

        // create a text field for the quantity
        JTextField quantityText = new JTextField();
        quantityText.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityText.setSize(190, 20);
        quantityText.setLocation(200, 200);
        f.add(quantityText);

        // create a table to show the selected products and their quantity
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(400, 200);
        table.setLocation(100, 250);
        f.add(table);

        // create a table model to add the data to the table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Price");
        table.setModel(model);

        // create a scroll pane for the table
        JScrollPane sp = new JScrollPane(table);
        sp.setSize(400, 200);
        sp.setLocation(100, 250);
        f.add(sp);

        // create a button to add the product to the table
        // the table and amount will be updated when the product is added
        JButton add = new JButton("Add");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(100, 20);
        add.setLocation(250, 470);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the product name and quantity
                String product = (String) productList.getSelectedItem();
                String quantity = quantityText.getText();

                // add the product and quantity to the table
                // use the DefaultTableModel to add the data to the table
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                // product, quantity, amount
                int price = 0;
                // update the amount
                // use procedure get_price_of_item(IN item_id int, OUT price int) to get the price of the item
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call get_price_of_item(?, ?)}");
                    cs.setInt(1, Integer.parseInt(product.split(" ")[1]));
                    cs.registerOutParameter(2, Types.INTEGER);
                    cs.execute();
                    price = cs.getInt(2);

                    // calculate the amount
                    int total = Integer.parseInt(amountText.getText());
                    total += price * Integer.parseInt(quantity);
                    amountText.setText(Integer.toString(total));
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                model.addRow(new Object[] {product, quantity, price});

                // clear the quantity text field
                quantityText.setText("");
            }
        });
        f.add(add);

        // create a button to remove the selected product from the table
        // the table and amount will be updated when the product is removed
        JButton remove = new JButton("Remove");
        remove.setFont(new Font("Arial", Font.PLAIN, 15));
        remove.setSize(100, 20);
        remove.setLocation(350, 470);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected row
                int row = table.getSelectedRow();
                if (row != -1) {
                    // get the amount
                    int total = Integer.parseInt(amountText.getText());
                    // get the price of the item
                    int price = (int) table.getValueAt(row, 2);
                    // get the quantity
                    int quantity = Integer.parseInt((String) table.getValueAt(row, 1));
                    // update the amount
                    total -= price * quantity;
                    amountText.setText(Integer.toString(total));
                    // remove the row
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                }
            }
        });
        f.add(remove);

        // create a button to confirm the transaction
        JButton confirm = new JButton("Confirm");
        confirm.setFont(new Font("Arial", Font.PLAIN, 15));
        confirm.setSize(100, 20);
        confirm.setLocation(250, 500);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the amount
                String amount = amountText.getText();

                // make the transaction using procedure make_transaction(IN vendor_id varchar(50), IN student_id varchar(50), IN total_amount int, IN date_time datetime)
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call make_transaction(?, ?, ?, ?)}");
                    cs.setString(1, User.getInstance().getId());
                    cs.setString(2, studentText.getText());
                    cs.setInt(3, Integer.parseInt(amount));
                    cs.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    cs.execute();
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                // add orders for the transaction
                // use procedure make_order(IN transaction_id int, IN item_id int, IN price int, IN quantity int)
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call make_order(?, ?, ?, ?)}");
                    // get the transaction_id
                    ResultSet rs = c.stmt.executeQuery("SELECT MAX(ID) FROM transactions");
                    int transaction_id = 0;
                    if (rs.next()) {
                        transaction_id = rs.getInt(1);
                    }
                    // get the orders
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String product = (String) model.getValueAt(i, 0);
                        int item_id = Integer.parseInt(product.split(" ")[1]);
                        int price = (int) model.getValueAt(i, 2);
                        int quantity = Integer.parseInt((String) model.getValueAt(i, 1));
                        cs.setInt(1, transaction_id);
                        cs.setInt(2, item_id);
                        cs.setInt(3, price);
                        cs.setInt(4, quantity);
                        cs.execute();
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                JOptionPane.showMessageDialog(f, "Transaction successful");

                // dispose the frame
                f.dispose();

                // show the vendor home page
                VendorHome h = new VendorHome();
                h.show();
            }
        });
        f.add(confirm);

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(350, 500);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                VendorHome h = new VendorHome();
                h.show();
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
    }
}
