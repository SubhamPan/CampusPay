import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
public class StudentPaymentHistory {
    // this class is used to display the payment history of a student
    private Container c;
    private JLabel title;

    public void show() {
        // create a new frame to store the payment history
        JFrame f = new JFrame("Payment History");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Payment History");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a Jtable to display the payment history
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(400, 200);
        table.setLocation(100, 100);
        f.add(table);

        // create a model for the table
        DefaultTableModel model = new DefaultTableModel();
        // vendor, amount, date
        model.addColumn("Vendor");
        model.addColumn("Amount");
        model.addColumn("Date");
        table.setModel(model);

        // create a scroll pane for the table
        JScrollPane scroll = new JScrollPane(table);
        scroll.setSize(500, 200);
        scroll.setLocation(50, 100);
        f.add(scroll);

        // connect to the database and get the payment history
        try {
            Conn c = new Conn();
            // use procedure get_all_payments_made_by_student(IN student_id varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_all_payments_made_by_student(?)}");
            cs.setString(1, User.getInstance().getId());
            ResultSet rs = cs.executeQuery();

            // display the payment history
            while (rs.next()) {
                model.addRow(new Object[] {rs.getString("v_name"), rs.getString("total_amount"), rs.getString("date_time")});
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 300);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                StudentHome home = new StudentHome();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        // display the frame
        f.setVisible(true);
    }
}
