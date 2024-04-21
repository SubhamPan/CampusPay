import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class AddItem {
    // this class is used to add an item to the database
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField nameText;
    private JLabel price;
    private JTextField priceText;
    private JButton submit;
    private JButton back;

    public void show() {
        // create a new frame to store the add item form
        JFrame f = new JFrame("Add Item");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        // create a label
        title = new JLabel("Add Item");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(390, 130);
        container.add(title);

        // create a label for the name
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(300, 200);
        container.add(name);

        // create a text field for the name
        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setSize(190, 20);
        nameText.setLocation(375, 200);
        nameText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(nameText);

        // create a label for the price
        price = new JLabel("Price");
        price.setFont(new Font("Arial", Font.PLAIN, 20));
        price.setSize(100, 20);
        price.setLocation(300, 250);
        container.add(price);

        // create a text field for the price
        priceText = new JTextField();
        priceText.setFont(new Font("Arial", Font.PLAIN, 15));
        priceText.setSize(190, 20);
        priceText.setLocation(375, 250);
        priceText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(priceText);

        // create a button to submit the form
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(325, 300);
        submit.setBackground(new Color(176, 166, 149));
        submit.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        container.add(submit);

        // create a button to go back to the home page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(475, 300);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));

        container.add(back);

        // add an action listener to the submit button
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the name and price of the item
                String name = nameText.getText();
                String price = priceText.getText();

                // connect to the database and add the item
                try {
                    Conn c = new Conn();
                    // use procedure add_item_to_menu(IN item_name varchar(50), IN price int, IN vendor_id varchar(50))
                    CallableStatement cs = c.con.prepareCall("{call add_item_to_menu(?, ?, ?)}");
                    cs.setString(1, name);
                    cs.setInt(2, Integer.parseInt(price));
                    cs.setString(3, User.getInstance().getId());
                    cs.executeQuery();
                    JOptionPane.showMessageDialog(f, "Item Added Successfully");
                    nameText.setText("");
                    priceText.setText("");
                } catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(f, "Error Adding Item");
                }
            }
        });

        // add an action listener to the back button
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // go back to the vendor items page
                VendorItems items = new VendorItems();
                items.show();
                f.dispose();
            }
        });

        // display the frame
        f.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }
//    public static void main(String[] args) {
//        AddItem item = new AddItem();
//        item.show();
//    }
}
