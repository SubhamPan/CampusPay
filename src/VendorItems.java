import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VendorItems {
    private JLabel title;
    private JScrollPane scroll;
    private JButton add;
    private JButton edit;

    public void show() {
        JFrame f = new JFrame("Vendor Items");
        f.setBounds(350, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        title = new JLabel("Vendor Items");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 30);
        container.add(title);

        // create a table to display all the items
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 300);
        table.setLocation(50, 100);
        container.add(table);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        table.setModel(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(800, 300);
        scroll.setLocation(50, 100);
        container.add(scroll);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("item_name"), rs.getString("price")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e);
            System.out.println(e);
        }

        int buttonWidth = 100;
        int buttonHeight = 30;
//        int buttonX = 300;

        add = new JButton("Add Item");
        configureButton(add, 250, 450, buttonWidth, buttonHeight);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddItem item = new AddItem();
                item.show();
                f.dispose();
            }
        });
        container.add(add);

        edit = new JButton("Edit Item");
        configureButton(edit, 400, 450, buttonWidth, buttonHeight);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditItem item = new EditItem();
                item.show();
                f.dispose();
            }
        });
        container.add(edit);

        JButton back = new JButton("Back");
        configureButton(back, 400, 500, buttonWidth, buttonHeight);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorHome home = new VendorHome();
                home.show();
                f.dispose();
            }
        });
        container.add(back);

        //  add button to delete item
        JButton delete = new JButton("Delete Item");
        configureButton(delete, 550, 450, buttonWidth, buttonHeight);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteItem item = new DeleteItem();
                item.show();
            }
        });
        container.add(delete);

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
        button.setBackground(new Color(176, 166, 149)); // B0A695
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
    }

//    public static void main(String[] args) {
//        VendorItems items = new VendorItems();
//        items.show();
//    }
}
