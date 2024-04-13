import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorItems {
    private JLabel title;
    private JTextArea items;
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
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        container.add(title);

        items = new JTextArea(30, 30);
        items.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(items);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(600, 200);
        scroll.setLocation(150, 100);
        container.add(scroll);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_items_sold_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                items.append("Item ID: " + rs.getString("ID") + "\n");
                items.append("Item Name: " + rs.getString("item_name") + "\n");
                items.append("Price: " + rs.getString("price") + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        int buttonWidth = 100;
        int buttonHeight = 30;
        int buttonX = 300;

        add = new JButton("Add Item");
        configureButton(add, buttonX - 50, 350, buttonWidth, buttonHeight);
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
        configureButton(edit, buttonX + 100, 350, buttonWidth, buttonHeight);
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
        configureButton(back, buttonX + 250, 350, buttonWidth, buttonHeight);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendorHome home = new VendorHome();
                home.show();
                f.dispose();
            }
        });
        container.add(back);

        f.setVisible(true);
    }

    private void configureButton(JButton button, int x, int y, int width, int height) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(176, 166, 149)); // B0A695
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
    }
}
