import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ShowOrders {
    void show() {
        JFrame f = new JFrame("Show Orders");
        f.setBounds(300, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Show Orders");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 30);
        container.add(title);

        JLabel ID = new JLabel("Transaction ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setSize(150, 20);
        ID.setLocation(300, 100);
        container.add(ID);

        JComboBox<String> transactionList = new JComboBox<String>();
        try {
            Conn c = new Conn();
            CallableStatement cs = null;
            if(User.getInstance().getRole() == 2) {
                cs = c.con.prepareCall("{call get_all_transactions()}");
            } else if(User.getInstance().getRole() == 1) {
                cs = c.con.prepareCall("{call get_all_transactions_made_by_vendor(?)}");
                cs.setString(1, User.getInstance().getId());
            } else {
                cs = c.con.prepareCall("{call get_all_payments_made_by_student(?)}");
                cs.setString(1, User.getInstance().getId());
            }
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                transactionList.addItem(rs.getString("ID") + " - " + rs.getString("date_time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        transactionList.setSize(190, 20);
        transactionList.setLocation(500, 100);
        container.add(transactionList);

        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 250);
        table.setLocation(100, 150);
        container.add(table);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        table.setModel(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(800, 250);
        scroll.setLocation(50, 150);
        container.add(scroll);

        JButton show = new JButton("Show Orders");
        configureButton(show, 375, 425, 150, 30);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // clear previous data
                model.setRowCount(0);
                String transaction = transactionList.getSelectedItem().toString();
                String[] parts = transaction.split(" - ");
                String transactionID = parts[0];

                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call get_all_orders_of_transaction(?)}");
                    cs.setString(1, transactionID);
                    ResultSet rs = cs.executeQuery();

                    while (rs.next()) {
                        model.addRow(new Object[] {rs.getString("ID"), rs.getString("item_name"), rs.getString("quantity"), rs.getString("price")});
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Error: " + ex);
                    System.out.println(ex);
                }
            }
        });
        container.add(show);

        JButton back = new JButton("Back");
        configureButton(back, 400, 475, 100, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(User.getInstance().getRole() == 2) {
                    AdminHome home = new AdminHome();
                    home.show();
                } else if(User.getInstance().getRole() == 1) {
                    VendorHome home = new VendorHome();
                    home.show();
                } else {
                    StudentHome home = new StudentHome();
                    home.show();
                }
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
        button.setBackground(new Color(176, 166, 149)); // B0A695
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
    }

    public static void main(String[] args) {
        ShowOrders showOrders = new ShowOrders();
        showOrders.show();
    }
}