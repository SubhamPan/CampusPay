import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentHome {
    public void show() {
        JFrame frame = new JFrame("Home");
        frame.setBounds(300, 90, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Container container = frame.getContentPane();
        container.setBackground(new Color(243, 238, 234));

        JLabel title = new JLabel("Home Page");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBounds(375, 30, 300, 30);
        container.add(title);

        JLabel totalLabel = new JLabel("Total Amount Spent: ");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        totalLabel.setBounds(325, 100, 200, 20);
        container.add(totalLabel);

        JLabel totalAmount = new JLabel();
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 20));
        totalAmount.setBounds(515, 100, 150, 20);
        container.add(totalAmount);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_total_amount_spent_by_student(?, ?)}");
            cs.setString(1, User.getInstance().getId());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            int total_amount_spent = cs.getInt(2);
//            int total_amount_spent = 1000;
            totalAmount.setText(Integer.toString(total_amount_spent));
        } catch (Exception e) {
            System.out.println(e);
        }

        // make a label for most bought item
        JLabel mostBoughtLabel = new JLabel("Most Bought Item: ");
        mostBoughtLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mostBoughtLabel.setBounds(325, 130, 200, 20);
        container.add(mostBoughtLabel);

        // make a label for most bought item
        JLabel mostBought = new JLabel();
        mostBought.setFont(new Font("Arial", Font.PLAIN, 20));
        mostBought.setBounds(515, 130, 150, 20);
        container.add(mostBought);

        try {
            Conn c = new Conn();
            // use procedure get_most_bought_item_by_student(IN student_id varchar(50), OUT ID int, OUT item_name varchar(50), OUT v_name varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_most_bought_item_by_student(?, ?, ?, ?)}");
            cs.setString(1, User.getInstance().getId());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            int id = cs.getInt(2);
            String item_name = cs.getString(3);
            String v_name = cs.getString(4);
            mostBought.setText(id + " - " + item_name + " by " + v_name);
        } catch (Exception e) {
            System.out.println(e);
        }

        JButton vendorsButton = new JButton("Vendors");
        vendorsButton.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorsButton.setBounds(400, 180, 100, 30);
        vendorsButton.setBackground(new Color(176, 166, 149));
        vendorsButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        vendorsButton.addActionListener(e -> {
            VendorsMenu vendorsMenu = new VendorsMenu();
            vendorsMenu.show();
            frame.dispose();
        });
        container.add(vendorsButton);

        JButton paymentButton = new JButton("Payment");
        paymentButton.setFont(new Font("Arial", Font.PLAIN, 15));
        paymentButton.setBounds(400, 230, 100, 30);
        paymentButton.setBackground(new Color(176, 166, 149));
        paymentButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        paymentButton.addActionListener(e -> {
            Payment payment = new Payment();
            payment.show();
            frame.dispose();
        });
        container.add(paymentButton);

        JButton paymentHistoryButton = new JButton("Payment History");
        paymentHistoryButton.setFont(new Font("Arial", Font.PLAIN, 15));
        paymentHistoryButton.setBounds(375, 280, 150, 30);
        paymentHistoryButton.setBackground(new Color(176, 166, 149));
        paymentHistoryButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        paymentHistoryButton.addActionListener(e -> {
            StudentPaymentHistory paymentHistory = new StudentPaymentHistory();
            paymentHistory.show();
            frame.dispose();
        });
        container.add(paymentHistoryButton);

        JButton updateDetailsButton = new JButton("Update Details");
        updateDetailsButton.setFont(new Font("Arial", Font.PLAIN, 15));
        updateDetailsButton.setBounds(375, 330, 150, 30);
        updateDetailsButton.setBackground(new Color(176, 166, 149));
        updateDetailsButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        updateDetailsButton.addActionListener(e -> {
            EditStudent editStudent = new EditStudent();
            editStudent.show();
            frame.dispose();
        });
        container.add(updateDetailsButton);

        JButton ordersButton = new JButton("Show Orders");
        ordersButton.setFont(new Font("Arial", Font.PLAIN, 15));
        ordersButton.setBounds(375, 380, 150, 30);
        ordersButton.setBackground(new Color(176, 166, 149));
        ordersButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        ordersButton.addActionListener(e -> {
            ShowOrders showOrders = new ShowOrders();
            showOrders.show();
            frame.dispose();
        });
        container.add(ordersButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 15));
        logoutButton.setBounds(400, 480, 100, 30);
        logoutButton.setBackground(new Color(176, 166, 149));
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        logoutButton.addActionListener(e -> {
            User.getInstance().clear();
            LoginSystem login = new LoginSystem();
            login.show();
            frame.dispose();
        });
        container.add(logoutButton);

        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getSize().width) / 2;
        int y = (dim.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
    }
//    public static void main(String[] args) {
//        StudentHome studentHome = new StudentHome();
//        studentHome.show();
//    }
}
