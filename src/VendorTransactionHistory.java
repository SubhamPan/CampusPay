import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VendorTransactionHistory {
    private JLabel title;
    private JTextArea transactions;
    private JScrollPane scroll;

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

        transactions = new JTextArea(30, 30);
        transactions.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(transactions);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(600, 200);
        scroll.setLocation(150, 100);
        container.add(scroll);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions_made_by_vendor(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                transactions.append("Transaction ID: " + rs.getString("ID") + "\n");
                transactions.append("Student ID: " + rs.getString("student_id") + "\n");
                transactions.append("Amount: " + rs.getString("total_amount") + "\n");
                transactions.append("Date and Time: " + rs.getString("date_time") + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JButton back = new JButton("Back");
        configureButton(back, 400, 350, 100, 30);
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
