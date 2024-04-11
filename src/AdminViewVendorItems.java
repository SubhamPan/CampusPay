import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminViewVendorItems {
    // this class is used to display, add and change prices of items sold by a vendor
    private Container c;
    private JLabel title;
    private JTextArea items;
    private JScrollPane scroll;
    private JButton add;
    private JButton edit;

    public void show(String Vid) {
        // create a new frame to store the items sold by the vendor
        JFrame f = new JFrame("Vendor Items");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Vendor Items");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a scrollable area to store the items sold by the vendor
        items = new JTextArea(30, 30);
        items.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(items);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 100);
        f.add(scroll);

        // connect to the database and get the items sold by the vendor
        try {
            Conn c = new Conn();
            // use procedure get_all_items_sold_by_vendor(IN vendor_id varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, Vid);
            ResultSet rs = cs.executeQuery();

            // display the items sold by the vendor
            while (rs.next()) {
                items.append("Item ID: " + rs.getString("ID") + "\n");
                items.append("Item Name: " + rs.getString("item_name") + "\n");
                items.append("Price: " + rs.getString("price") + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 300);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                VendorHome home = new VendorHome();
//                home.show();
                ViewVendors home = new ViewVendors();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);
    }
}
