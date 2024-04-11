import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ShowTransactions {
    private JLabel title;
    private JScrollPane scroll;
    private JButton add;
    private JButton edit;
    private String[] columns = {"ID", "vendor_id", "student_id", "total_amount", "date_time"};
    private JTable table;

    public void show() {
        JFrame f = new JFrame("Show Transactions");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        f.setLayout(null);

        title = new JLabel("Transactions");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(200, 20);
        title.setLocation(200, 50);
        f.add(title);



        String[][] data = new String[100][5];
        try {
            Conn c = new Conn();
//            ResultSet rs = c.stmt.executeQuery("SELECT * FROM transactions");
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions()}");
            ResultSet rs = cs.executeQuery();
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


        table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(300, 200);
        table.setLocation(100, 100);
        f.add(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 100);
        f.add(scroll);

        add = new JButton("Add Transaction");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(200, 350);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddTransaction adminAddTransaction = new AdminAddTransaction();
                adminAddTransaction.show();
                f.dispose();
            }
        });
        f.add(add);

//        Edit selected transaction from table
        edit = new JButton("Edit Transaction");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(200, 20);
        edit.setLocation(200, 400);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String transaction_id = table.getModel().getValueAt(row, 0).toString();
                String vendor_id = table.getModel().getValueAt(row, 1).toString();
                String student_id = table.getModel().getValueAt(row, 2).toString();
                String total_amount = table.getModel().getValueAt(row, 3).toString();
                String date_time = table.getModel().getValueAt(row, 4).toString();
                AdminEditTransaction adminEditTransaction = new AdminEditTransaction(transaction_id, vendor_id, student_id, total_amount, date_time);
                adminEditTransaction.show();
                f.dispose();
            }
        });
        f.add(edit);


//        back
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(200, 450);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AdminHome adminHome = new AdminHome();
                adminHome.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        ShowTransactions showTransactions = new ShowTransactions();
        showTransactions.show();
    }
}
