import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class SetBudget {
    // this class is used to set the budget for a student
    void show() {
        // create a new frame to store the budget form
        JFrame f = new JFrame("Set Budget");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(243, 238, 234)); // Set background color

        // create a label
        JLabel title = new JLabel("Set Budget");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(350, 130, 300, 30);
        f.add(title);

        // create a label for the amount
        JLabel amount = new JLabel("Amount");
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        amount.setBounds(300, 200, 100, 20);
        f.add(amount);

        // create a text field for the amount
        JTextField amountText = new JTextField();
        amountText.setFont(new Font("Arial", Font.PLAIN, 15));
        amountText.setBounds(400, 200, 190, 20);
        amountText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        f.add(amountText);

        // get the current budget
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_monthly_budget(?, ?)}");
            cs.setString(1, User.getInstance().getId());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeQuery();
            amountText.setText(cs.getString(2));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(f, "Error getting budget");
            e.printStackTrace();
        }

        // create a button to set the budget
        JButton set = new JButton("Set Budget");
        set.setFont(new Font("Arial", Font.PLAIN, 15));
        set.setBounds(350, 250, 190, 20);
        set.setBackground(new Color(176, 166, 149));
        set.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        f.add(set);

        // create an action listener for the set button
        set.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call set_monthly_budget(?, ?)}");
                    cs.setString(1, User.getInstance().getId());
                    cs.setString(2, amountText.getText());
                    cs.executeQuery();
                    JOptionPane.showMessageDialog(f, "Budget set successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Error setting budget");
                    ex.printStackTrace();
                }
            }
        });

        // create a button to go back
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setBounds(350, 300, 190, 20);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new StudentHome().show();
            }
        });
        f.add(back);

        f.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }
}
