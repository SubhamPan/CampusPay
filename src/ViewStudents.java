import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewStudents {

    private JLabel title;
    private JScrollPane scroll;
    private JButton add;
    private JButton edit;
    private String[] columns = {"ID", "BITS_account", "s_name", "contact"};
    private JTable table;
    //   table to display the list of students
    // this class will be used to view the list of students
    // display table of students with columns ID, BITS_account, s_name, Contact and buttons to add and edit students
    public void show() {
        // create a frame
        JFrame f = new JFrame("View Students");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 600);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(243, 238, 234));
        f.setResizable(false);

        // create a label for the title
        title = new JLabel("Students");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(200, 30);
        title.setLocation(375, 50);
        f.add(title);

//        String[][] data = new String[100][4];
        // data taken from sql query SELECT * FROM students
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("BITS_account");
        model.addColumn("s_name");
        model.addColumn("contact");
        table.setModel(model);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setSize(800, 250);
        table.setLocation(100, 100);
        f.add(table);

        try {
            Conn c = new Conn();
//            ResultSet rs = c.stmt.executeQuery("SELECT * FROM student");
//            calling sql procesdure
                CallableStatement cs = c.con.prepareCall("{call get_all_students()}");
                ResultSet rs = cs.executeQuery();
            int i = 0;
            while (rs.next()) {
//                data[i][0] = rs.getString("ID");
//                data[i][1] = rs.getString("BITS_account");
//                data[i][2] = rs.getString("s_name");
//                data[i][3] = rs.getString("contact");
                model.addRow(new Object[]{rs.getString("ID"), rs.getString("BITS_account"), rs.getString("s_name"), rs.getString("contact")});
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(800, 250);
        scroll.setLocation(50, 100);
        f.add(scroll);

        // create a button to add a student
        add = new JButton("Add Student");
//        add.setFont(new Font("Arial", Font.PLAIN, 15));
//        add.setSize(200, 20);
//        add.setLocation(200, 350);
        configureButton(add, 350, 375, 200, 20);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the student registration page
                StudentRegistration studentRegistration = new StudentRegistration();
                studentRegistration.show();
            }
        });
        f.add(add);

        // create a button to edit a student
        edit = new JButton("Edit Student");
//        edit.setFont(new Font("Arial", Font.PLAIN, 15));
//        edit.setSize(200, 20);
//        edit.setLocation(200, 400);
        configureButton(edit, 350, 425, 200, 20);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the edit student page
                EditStudent editStudent = new EditStudent();
                editStudent.show();
            }
        });
        f.add(edit);

//        create button to view payments of selected student from table
        JButton viewPayments = new JButton("View Payments");
//        viewPayments.setFont(new Font("Arial", Font.PLAIN, 15));
//        viewPayments.setSize(200, 20);
//        viewPayments.setLocation(200, 450);
        configureButton(viewPayments, 350, 475, 200, 20);
        viewPayments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String student_id = table.getModel().getValueAt(row, 0).toString();
                AdminViewStudentTransactions studentPaymentHistory = new AdminViewStudentTransactions();
                studentPaymentHistory.show(student_id);
                f.dispose();
            }
        });
        f.add(viewPayments);


// create a button to go back
        JButton back = new JButton("Back");
//        back.setFont(new Font("Arial", Font.PLAIN, 15));
//        back.setSize(100, 20);
//        back.setLocation(250, 500);
        configureButton(back, 400, 525, 100, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the admin home page
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

    private void configureButton(JButton button, int x, int y, int width, int height) {
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(176, 166, 149)); // B0A695
        button.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // B0A695
    }

    public static void main(String[] args) {
        ViewStudents viewStudents = new ViewStudents();
        viewStudents.show();
    }
}
