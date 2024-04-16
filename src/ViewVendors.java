import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewVendors {

    private JLabel title;
    private JScrollPane scroll;
    private JButton add;
    private JButton edit;
    private String[] columns = {"ID", "v_name", "account_no", "contact"};
    private JTable table;
    //   table to display the list of students
    // this class will be used to view the list of students
    // display table of students with columns ID, account_no, s_name, Contact and buttons to add and edit students
    public void show() {
        // create a frame
        JFrame f = new JFrame("View Vendors");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 600);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(243, 238, 234));
        f.setResizable(false);

        // create a label for the title
        title = new JLabel("Vendors");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(200, 30);
        title.setLocation(400, 50);
        f.add(title);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(columns[0]);
        model.addColumn(columns[1]);
        model.addColumn(columns[2]);
        model.addColumn(columns[3]);
        table = new JTable();
        table.setModel(model);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 300);
        table.setLocation(100, 100);
        f.add(table);
//        String[][] data = new String[100][4];
        // data taken from sql query SELECT * FROM students

        try {
            Conn c = new Conn();
//            ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");
            CallableStatement cs = c.con.prepareCall("{call get_all_vendors()}");
            ResultSet rs = cs.executeQuery();
            int i = 0;
            while (rs.next()) {
//                data[i][0] = rs.getString(columns[0]);
//                data[i][1] = rs.getString(columns[1]);
//                data[i][2] = rs.getString(columns[2]);
//                data[i][3] = rs.getString(columns[3]);
                model.addRow(new Object[]{rs.getString(columns[0]), rs.getString(columns[1]), rs.getString(columns[2]), rs.getString(columns[3])});
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e);
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(800, 300);
        scroll.setLocation(50, 100);

        f.add(scroll);


        // create a button to add a student
        add = new JButton("Add Vendor");
//        add.setFont(new Font("Arial", Font.PLAIN, 15));
//        add.setSize(200, 20);
//        add.setLocation(200, 350);
        configureButton(add, 200, 425, 200, 20);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the vendor add page
                VendorRegistration vendorRegistration = new VendorRegistration();
                vendorRegistration.show();
            }
        });
        f.add(add);

        // create a button to edit a student
        edit = new JButton("Edit Vendor");
//        edit.setFont(new Font("Arial", Font.PLAIN, 15));
//        edit.setSize(200, 20);
//        edit.setLocation(200, 400);
        configureButton(edit, 500, 425, 200, 20);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the vendor edit page
                EditVendor editVendor = new EditVendor();
                editVendor.show();
            }
        });
        f.add(edit);

//        Create a button to view vendor transactions for selected vendor from table
        JButton viewTransactions = new JButton("View Transactions");
//        viewTransactions.setFont(new Font("Arial", Font.PLAIN, 15));
//        viewTransactions.setSize(200, 20);
//        viewTransactions.setLocation(200, 450);
        configureButton(viewTransactions, 200, 475, 200, 20);
        viewTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String vendor_id = table.getModel().getValueAt(row, 0).toString();
                AdminViewVendorTransaction vendorTransactions = new AdminViewVendorTransaction();
                vendorTransactions.show(vendor_id);
                f.dispose();
            }
        });
        f.add(viewTransactions);

        JButton viewItems = new JButton("View Items");
//        viewItems.setFont(new Font("Arial", Font.PLAIN, 15));
//        viewItems.setSize(200, 20);
//        viewItems.setLocation(200, 500);
        configureButton(viewItems, 500, 475, 200, 20);
        viewItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String vendor_id = table.getModel().getValueAt(row, 0).toString();
                AdminViewVendorItems vendorTransactions = new AdminViewVendorItems();
                vendorTransactions.show(vendor_id);
                f.dispose();
            }
        });
        f.add(viewItems);



        // create a button to go back
        JButton back = new JButton("Back");
//        back.setFont(new Font("Arial", Font.PLAIN, 15));
//        back.setSize(100, 20);
//        back.setLocation(250, 550);
        configureButton(back, 400, 525, 100, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                 go back to the admin home page
                AdminHome adminHome = new AdminHome();
                adminHome.show();
                f.dispose();
            }
        });
        f.add(back);
        f.setVisible(true);


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
        ViewVendors viewVendors = new ViewVendors();
        viewVendors.show();
    }
}
