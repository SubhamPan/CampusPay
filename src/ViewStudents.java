import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

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
        f.setSize(600, 600);
        f.setLayout(null);

        // create a label for the title
        title = new JLabel("Students");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(100, 20);
        title.setLocation(200, 50);
        f.add(title);

        String[][] data = new String[100][4];
        // data taken from sql query SELECT * FROM students

        try {
            Conn c = new Conn();
//            ResultSet rs = c.stmt.executeQuery("SELECT * FROM student");
//            calling sql procesdure
                CallableStatement cs = c.con.prepareCall("{call get_all_students()}");
                ResultSet rs = cs.executeQuery();
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("ID");
                data[i][1] = rs.getString("BITS_account");
                data[i][2] = rs.getString("s_name");
                data[i][3] = rs.getString("contact");
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

        // create a button to add a student
        add = new JButton("Add Student");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setSize(200, 20);
        add.setLocation(200, 350);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the student registration page
                StudentRegistration studentRegistration = new StudentRegistration();
                studentRegistration.show();
                f.dispose();
            }
        });
        f.add(add);

        // create a button to edit a student
        edit = new JButton("Edit Student");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(200, 20);
        edit.setLocation(200, 400);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to the edit student page
                EditStudent editStudent = new EditStudent();
                editStudent.show();
            }
        });
        f.add(edit);

// create a button to go back
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 450);
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
    }
}
