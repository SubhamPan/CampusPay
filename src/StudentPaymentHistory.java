import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class StudentPaymentHistory {
    public void show() {
        JFrame frame = new JFrame("Payment History");
        frame.setBounds(300, 90, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Container container = frame.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Payment History");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(350, 30, 300, 30);
        container.add(title);

        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 325);
        table.setLocation(100, 100);
        container.add(table);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vendor");
        model.addColumn("Amount");
        model.addColumn("Date");
        table.setModel(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(800, 325);
        scroll.setLocation(50, 100);
        container.add(scroll);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_payments_made_by_student(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[] {rs.getString("v_name"), rs.getString("total_amount"), rs.getString("date_time")});
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backButton.setBounds(400, 475, 100, 30);
        backButton.setBackground(new Color(176, 166, 149));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        backButton.addActionListener(e -> {
            StudentHome home = new StudentHome();
            home.show();
            frame.dispose();
        });
        container.add(backButton);

        frame.setVisible(true);


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }
    public static void main(String[] args) {
        StudentPaymentHistory studentPaymentHistory = new StudentPaymentHistory();
        studentPaymentHistory.show();
    }
}
