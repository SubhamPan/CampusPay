import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VendorTransactionProcess {
    void show() {
        JFrame f = new JFrame("Vendor Transaction Process");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Vendor Transaction Process");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(400, 30);
        title.setLocation(150, 50);
        container.add(title);

        JLabel amount = new JLabel("Amount");
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        amount.setSize(100, 20);
        amount.setLocation(100, 100);
        container.add(amount);

        JTextField amountText = new JTextField();
        amountText.setFont(new Font("Arial", Font.PLAIN, 15));
        amountText.setSize(190, 20);
        amountText.setLocation(200, 100);
        amountText.setEditable(false);
        amountText.setText("0");
        container.add(amountText);

        JLabel student = new JLabel("Student ID");
        student.setFont(new Font("Arial", Font.PLAIN, 20));
        student.setSize(100, 20);
        student.setLocation(100, 125);
        container.add(student);

        JTextField studentText = new JTextField();
        studentText.setFont(new Font("Arial", Font.PLAIN, 15));
        studentText.setSize(190, 20);
        studentText.setLocation(200, 125);
        container.add(studentText);

        JLabel product = new JLabel("Product");
        product.setFont(new Font("Arial", Font.PLAIN, 20));
        product.setSize(100, 20);
        product.setLocation(100, 150);
        container.add(product);

        JComboBox<String> productList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                productList.addItem("ID: " + rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productList.setFont(new Font("Arial", Font.PLAIN, 15));
        productList.setSize(190, 20);
        productList.setLocation(200, 150);
        container.add(productList);

        JLabel quantity = new JLabel("Quantity");
        quantity.setFont(new Font("Arial", Font.PLAIN, 20));
        quantity.setSize(100, 20);
        quantity.setLocation(100, 200);
        container.add(quantity);

        JTextField quantityText = new JTextField();
        quantityText.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityText.setSize(190, 20);
        quantityText.setLocation(200, 200);
        container.add(quantityText);

        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(400, 200);
        table.setLocation(100, 250);
        container.add(table);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Price");
        table.setModel(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setSize(400, 200);
        sp.setLocation(100, 250);
        container.add(sp);

        JButton add = new JButton("Add");
        configureButton(add, 250, 470, 100, 30);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product = (String) productList.getSelectedItem();
                String quantity = quantityText.getText();

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int price = 0;
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call get_item_details(?)}");
                    cs.setInt(1, Integer.parseInt(product.split(" ")[1]));
                    ResultSet rs = cs.executeQuery();
                    if (rs.next()) {
                        price = rs.getInt(3);
                    }

                    int total = Integer.parseInt(amountText.getText());
                    total += price * Integer.parseInt(quantity);
                    amountText.setText(Integer.toString(total));
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                model.addRow(new Object[] {product, quantity, price});
                quantityText.setText("");
            }
        });
        container.add(add);

        JButton remove = new JButton("Remove");
        configureButton(remove, 350, 470, 100, 30);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int total = Integer.parseInt(amountText.getText());
                    int price = (int) table.getValueAt(row, 2);
                    int quantity = Integer.parseInt((String) table.getValueAt(row, 1));
                    total -= price * quantity;
                    amountText.setText(Integer.toString(total));
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                }
            }
        });
        container.add(remove);

        JButton confirm = new JButton("Confirm");
        configureButton(confirm, 250, 500, 100, 30);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountText.getText();
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

                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call make_order(?, ?, ?, ?)}");
                    ResultSet rs = c.stmt.executeQuery("SELECT MAX(ID) FROM transactions");
                    int transaction_id = 0;
                    if (rs.next()) {
                        transaction_id = rs.getInt(1);
                    }
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
                f.dispose();
                VendorHome h = new VendorHome();
                h.show();
            }
        });
        container.add(confirm);

        JButton back = new JButton("Back");
        configureButton(back, 350, 500, 100, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorHome h = new VendorHome();
                h.show();
                f.dispose();
            }
        });
        container.add(back);

        f.setVisible(true);

        // Centering the form within the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

    private void configureButton(JButton button, int x, int y, int width, int height) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(176, 166, 149));
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
    }
}
