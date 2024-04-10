import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ShowTransactionByVendor {
    public void show(String v_id) {
        JFrame f = new JFrame("Show Transactions");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setLayout(null);

        JLabel title = new JLabel("Transactions");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(200, 20);
        title.setLocation(200, 50);
        f.add(title);

        String[] columns = {"ID", "vendor_id", "student_id", "total_amount", "date_time"};
        String[][] data = new String[100][5];
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM transactions WHERE vendor_id = " + v_id);

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString(columns[0]);
                data[i][1] = rs.getString(columns[1]);
                data[i][2] = rs.getString(columns[2]);
                data[i][3] = rs.getString(columns[3]);
                data[i][4] = rs.getString(columns[4]);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTable table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(300, 200);
        table.setLocation(100, 100);
        f.add(table);

        JButton add = new JButton("Add Transaction");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(200, 350);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                AddTransaction addTransaction = new AddTransaction();
//                addTransaction.show();
//                f.dispose();

            }
        });
        f.add(add);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        ShowTransactionByVendor showTransactionByVendor = new ShowTransactionByVendor();
        showTransactionByVendor.show("ritv1234");
    }
}
