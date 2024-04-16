import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteItem {
    // this class is used to delete an item from the database
    void show() {
        // create a 900x600 frame to store the delete item form
        JFrame f = new JFrame("Delete Item");
        f.setBounds(350, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a container to store the components
        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        // create a label for the title
        JLabel title = new JLabel("Delete Item");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(375, 130, 300, 30);
        container.add(title);

        // create a label for vendor
        JLabel vendor = new JLabel("Vendor");
        vendor.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor.setBounds(350, 200, 100, 20);
        container.add(vendor);

        // create a drop down list for the vendors, this will be populated from the database
        JComboBox<String> vendorList = new JComboBox<String>();

        // if the user is a vendor, only show their id and name in the drop down list
        if (User.getInstance().getRole() == 1) {
            // get vendor details
            try {
                Conn c = new Conn();
                CallableStatement cs = c.con.prepareCall("{call get_vendor_details(?)}");
                cs.setString(1, User.getInstance().getId());
                ResultSet rs = cs.executeQuery();

                // display the vendor details
                while (rs.next()) {
                    vendorList.addItem(User.getInstance().getId() + " - " + rs.getString("v_name"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            // make the drop down list uneditable
            vendorList.setEditable(false);
        } else {
            // connect to the database and get the list of vendors
            try {
                Conn c = new Conn();
                ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");

                // display the list of vendors
                while (rs.next()) {
                    vendorList.addItem(rs.getString("ID") + " - " + rs.getString("v_name"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorList.setBounds(450, 200, 190, 20);
        vendorList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(vendorList);

        // create a label for the item
        JLabel item = new JLabel("Item");
        item.setFont(new Font("Arial", Font.PLAIN, 20));
        item.setBounds(350, 250, 100, 20);
        container.add(item);

        // create a drop down list for the items, this will be populated from the database
        JComboBox<String> itemList = new JComboBox<String>();

        // connect to the database and get the list of items of the selected vendor
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            String id = vendorList.getSelectedItem().toString().split(" - ")[0];
            cs.setString(1, id);
            ResultSet rs = cs.executeQuery();

            // display the list of items
            while (rs.next()) {
                itemList.addItem(rs.getString("ID") + " - " + rs.getString("item_name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        itemList.setFont(new Font("Arial", Font.PLAIN, 15));
        itemList.setBounds(450, 250, 190, 20);
        itemList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(itemList);

        // create a button to delete the item
        JButton delete = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setBounds(400, 300, 100, 20);
        delete.setBackground(new Color(176, 166, 149));
        delete.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the id of the item
                String id = itemList.getSelectedItem().toString().split(" - ")[0];

                // connect to the database and delete the item
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call delete_item(?)}");
                    cs.setString(1, id);
                    cs.executeQuery();
                    JOptionPane.showMessageDialog(f, "Item Deleted Successfully");
                } catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(f, "Error Deleting Item");
                }
            }
        });
        container.add(delete);

        // create a button to go back
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setBounds(400, 350, 100, 20);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        container.add(back);

        // display the frame
        f.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

    public static void main(String[] args) {
        DeleteItem deleteItem = new DeleteItem();
        deleteItem.show();
    }
}
