import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class VendorTransactionHistory {
    private JLabel title;

    public void show() {
        JFrame f = new JFrame("Transaction History");
        f.setBounds(300, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        title = new JLabel("Transaction History");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        container.add(title);

        // create a table to display all the transactions
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 350);
        table.setLocation(50, 100);
        container.add(table);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Student");
        model.addColumn("Amount");
        model.addColumn("Date&Time");
        table.setModel(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(800, 350);
        scroll.setLocation(50, 100);
        container.add(scroll);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions_made_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("student_id") + " - " + rs.getString("s_name"), rs.getString("total_amount"), rs.getString("date_time")});
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JButton back = new JButton("Back");
        configureButton(back, 400, 500, 100, 30);
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
        VendorTransactionHistory transactionHistory = new VendorTransactionHistory();
        transactionHistory.show();
    }
}
