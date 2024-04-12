import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EditItem {
    // this is used to change the details of an item
    private Container c;
    private JLabel title;
    private JLabel price;
    private JTextField priceText;
    private JButton submit;
    private JButton back;
    private JTextArea tout;

    private JComboBox selectItem;

    public void show() {
        // create a new frame to store the edit item form
        JFrame f = new JFrame("Edit Item");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Edit Item");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a drop down menu to select the item to edit
        selectItem = new JComboBox();
        selectItem.setFont(new Font("Arial", Font.PLAIN, 15));
        selectItem.setSize(190, 20);
        selectItem.setLocation(200, 100);
        f.add(selectItem);

        // connect to the database and get the items sold by the vendor
        try {
            Conn c = new Conn();
            // use procedure get_all_items_sold_by_vendor(IN vendor_id varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            // display the items sold by the vendor
            while (rs.next()) {
                selectItem.addItem(rs.getString("ID") + " - " + rs.getString("item_name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // create a label for the item_name
        JLabel item_name = new JLabel("Item Name");
        item_name.setFont(new Font("Arial", Font.PLAIN, 20));
        item_name.setSize(100, 20);
        item_name.setLocation(100, 150);
        f.add(item_name);

        // create a text field for the item_name
        JTextField item_nameText = new JTextField();
        item_nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        item_nameText.setSize(190, 20);
        item_nameText.setLocation(200, 150);
        f.add(item_nameText);

        // create a label for the price
        price = new JLabel("Price");
        price.setFont(new Font("Arial", Font.PLAIN, 20));
        price.setSize(100, 20);
        price.setLocation(100, 200);
        f.add(price);

        // create a text field for the price
        priceText = new JTextField();
        priceText.setFont(new Font("Arial", Font.PLAIN, 15));
        priceText.setSize(190, 20);
        priceText.setLocation(200, 200);
        f.add(priceText);

        // create a button to submit the form
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(200, 250);
        f.add(submit);

        // set the item_name and price to the current price of the item
        // use procedure get_item_details(IN ID int)
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_item_details(?)}");
            String item = selectItem.getSelectedItem().toString();
            String[] parts = item.split(" - ");
            cs.setInt(1, Integer.parseInt(parts[0]));
            ResultSet rs = cs.executeQuery();
            rs.next();
            priceText.setText(rs.getString("price"));
            item_nameText.setText(rs.getString("item_name"));
        } catch (Exception e) {
            System.out.println(e);
        }

        // listen for the submit button to be clicked
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // connect to the database and update the details of the item
                try {
                    Conn c = new Conn();
                    // use procedure update_item_details(IN ID int, IN item_name varchar(50), IN price int)
                    CallableStatement cs = c.con.prepareCall("{call update_item_details(?, ?, ?)}");
                    String item = selectItem.getSelectedItem().toString();
                    String[] parts = item.split(" - ");
                    cs.setInt(1, Integer.parseInt(parts[0]));
                    cs.setString(2, item_nameText.getText());
                    cs.setInt(3, Integer.parseInt(priceText.getText()));
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Price updated successfully");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });


        // create a button to go back to the vendor items page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 300);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorItems items = new VendorItems();
                items.show();
                f.dispose();
            }
        });
        f.add(back);

        // display the frame
        f.setVisible(true);
    }
}
