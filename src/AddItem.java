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
    private JTextArea tout;

    public void show() {
        // create a new frame to store the add item form
        JFrame f = new JFrame("Add Item");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Add Item");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a label for the name
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        f.add(name);

        // create a text field for the name
        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setSize(190, 20);
        nameText.setLocation(200, 100);
        f.add(nameText);

        // create a label for the price
        price = new JLabel("Price");
        price.setFont(new Font("Arial", Font.PLAIN, 20));
        price.setSize(100, 20);
        price.setLocation(100, 150);
        f.add(price);

        // create a text field for the price
        priceText = new JTextField();
        priceText.setFont(new Font("Arial", Font.PLAIN, 15));
        priceText.setSize(190, 20);
        priceText.setLocation(200, 150);
        f.add(priceText);

        // create a button to submit the form
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(100, 200);
        f.add(submit);

        // create a button to go back to the home page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 200);
        f.add(back);

        // create a text area to display the result of the form submission
        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 200);
        tout.setLocation(100, 250);
        tout.setLineWrap(true);
        tout.setEditable(false);
        f.add(tout);

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
                    tout.setText("Item added successfully");
                } catch (Exception ex) {
                    System.out.println(ex);
                    tout.setText("Error adding item");
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
    }
}
