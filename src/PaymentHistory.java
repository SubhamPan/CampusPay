import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class PaymentHistory {
    // this class is used to display the payment history of a student
    private Container c;
    private JLabel title;
    private JTextArea payments;
    private JScrollPane scroll;

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

        // create a scrollable area to store the payment history
        payments = new JTextArea(30, 30);
        payments.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(payments);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
                payments.append("Transaction ID: " + rs.getString("ID") + "\n");
                payments.append("Vendor ID: " + rs.getString("vendor_id") + "\n");
                payments.append("Amount: " + rs.getString("total_amount") + "\n");
                payments.append("Date and Time: " + rs.getString("date_time") + "\n\n");
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
                Home home = new Home();
                home.show();
                f.dispose();
            }
        });
        f.add(back);

        // display the frame
        f.setVisible(true);
    }
}
