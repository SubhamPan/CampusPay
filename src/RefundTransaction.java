import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class RefundTransaction {
    // this class is used to refund a transaction
    void show(String transaction_id) {
        // create a 900x600 frame to store the refund transaction form
        JFrame f = new JFrame("Refund Transaction");
        f.setBounds(350, 90, 900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a container to store the components
        Container container = f.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        // create a label for the title
        JLabel title = new JLabel("Refund Transaction: " + transaction_id);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(300, 150, 300, 30);
        container.add(title);

        // confirmatory message
        JLabel message = new JLabel("Are you sure you want to refund this transaction?");
        message.setFont(new Font("Arial", Font.PLAIN, 15));
        message.setBounds(300, 200, 500, 20);
        container.add(message);

        // create a button to confirm the refund
        JButton refund = new JButton("Confirm");
        refund.setFont(new Font("Arial", Font.PLAIN, 15));
        refund.setBounds(400, 250, 100, 30);
        refund.setBackground(new Color(176,166,149));
        refund.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
        container.add(refund);

        // action listener for the refund button
        refund.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call delete_transaction(?)}");
                    cs.setString(1, transaction_id);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "Transaction refunded successfully");
                    f.dispose();
                } catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(f, "An error occurred while refunding the transaction");
                }
            }
        });

        // create a button to cancel the refund
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 15));
        cancel.setBounds(400, 300, 100, 30);
        cancel.setBackground(new Color(176,166,149));
        cancel.setBorder(BorderFactory.createLineBorder(new Color(176,166,149), 2));
        container.add(cancel);

        // action listener for the cancel button
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        f.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);

    }

//    public static void main(String[] args) {
//        RefundTransaction refundTransaction = new RefundTransaction();
//        refundTransaction.show("1");
//    }
}
