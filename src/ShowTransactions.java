import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

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
        f.setSize(900, 600);
        f.setLayout(null);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(243, 238, 234));

        title = new JLabel("Transactions");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        title.setSize(200, 20);
        title.setLocation(375, 25);
        f.add(title);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 250);
        table.setLocation(100, 100);
        f.add(table);


        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions()}");
            ResultSet rs = cs.executeQuery();
            int i = 0;
            while (rs.next()) {
//                data[i][0] = rs.getString(columns[0]);
//                data[i][1] = rs.getString(columns[1]);
//                data[i][2] = rs.getString(columns[2]);
//                data[i][3] = rs.getString(columns[3]);
//                data[i][4] = rs.getString(columns[4]);
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("vendor_id"), rs.getString("student_id"), rs.getString("total_amount"), rs.getString("date_time")});
                i++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error: " + e);
            e.printStackTrace();
        }

//        table = new JTable(data, columns);
//        table.setFont(new Font("Arial", Font.PLAIN, 15));
//        table.setSize(700, 300);
//        table.setLocation(100, 100);
//        f.add(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(800, 300);
        scroll.setLocation(50, 75);
        f.add(scroll);

        add = new JButton("Add Transaction");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(350, 400);
        add.setBackground(new Color(176,166,149));
        add.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddTransaction adminAddTransaction = new AdminAddTransaction();
                adminAddTransaction.show();
                f.dispose();
            }
        });
        f.add(add);

        edit = new JButton("Edit Transaction");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(200, 20);
        edit.setLocation(350, 440);
        edit.setBackground(new Color(176,166,149));
        edit.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
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

        JButton refund = new JButton("Refund");
        refund.setFont(new Font("Arial", Font.PLAIN, 15));
        refund.setSize(200, 20);
        refund.setLocation(350, 480);
        refund.setBackground(new Color(176,166,149));
        refund.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
        refund.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String transaction_id = table.getModel().getValueAt(row, 0).toString();
                RefundTransaction refundTransaction = new RefundTransaction();
                refundTransaction.show(transaction_id);
            }
        });
        f.add(refund);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 520);
        back.setBackground(new Color(176,166,149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    public static void main(String[] args) {
        ShowTransactions showTransactions = new ShowTransactions();
        showTransactions.show();
    }
}
