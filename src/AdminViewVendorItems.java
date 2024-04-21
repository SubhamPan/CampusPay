import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AdminViewVendorItems {
    // this class is used to display, add and change prices of items sold by a vendor
    private Container c;
    private JLabel title;
//    private JTextArea items;
    private JScrollPane scroll;

    public void show(String Vid) {
        // create a new frame to store the items sold by the vendor
        JFrame f = new JFrame("Vendor Items");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(243, 238, 234)); // Set background color

        // create a label
        title = new JLabel("Vendor Items");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 30);
        f.add(title);


        // create a scrollable area to store the items sold by the vendor
//        items = new JTextArea(30, 30);
//        items.setFont(new Font("Arial", Font.PLAIN, 15));
//        items.setEditable(false); // Disable editing
//        scroll = new JScrollPane(items);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        JTable table = new JTable();
        table.setModel(model);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 300);
        table.setLocation(50, 100);
        f.add(table);
        scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(800, 350);
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
//                items.append("Item ID: " + rs.getString("ID") + "\n");
//                items.append("Item Name: " + rs.getString("item_name") + "\n");
//                items.append("Price: " + rs.getString("price") + "\n\n");
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("item_name"), rs.getString("price")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "an error occurred, please contact the admin");
            System.out.println(e);
        }

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 470);
        back.setBackground(new Color(176, 166, 149)); // Set background color
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewVendors home = new ViewVendors();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        // Centering the frame on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);

        f.setVisible(true);
    }

//    public static void main(String[] args) {
//        AdminViewVendorItems items = new AdminViewVendorItems();
//        items.show("V1");
//    }
}
