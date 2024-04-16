import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EditStudent {
    private JLabel title;
    private JLabel ID;
    private JLabel name;
    private JTextField nameText;
    private JLabel account;
    private JTextField accountText;
    private JLabel contact;
    private JTextField contactText;
    private JLabel password;
    private JPasswordField passwordText;
    private JButton edit;
    private JButton back;

    public void show() {
        JFrame f = new JFrame("Edit Student");
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234));

        title = new JLabel("Edit Student");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setBounds(375, 30, 300, 30);
        container.add(title);

        ID = new JLabel("Student ID");
        ID.setFont(new Font("Arial", Font.PLAIN, 20));
        ID.setBounds(250, 100, 100, 20);
        container.add(ID);

        JComboBox<String> studentList = new JComboBox<String>();
        if(User.getInstance().getRole() == 2) {
            // user is admin, get all students
            try {
                Conn c = new Conn();
                CallableStatement cs = c.con.prepareCall("{call get_all_students()}");
                ResultSet rs = cs.executeQuery();
                while (rs.next()) {
                    // ID - sname
                    studentList.addItem(rs.getString("ID") + " - " + rs.getString("s_name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // user is student, make the drop down list uneditable and show only the student's details
            studentList.setEnabled(false);
            studentList.addItem(User.getInstance().getId());
        }

        studentList.setFont(new Font("Arial", Font.PLAIN, 15));
        studentList.setBounds(450, 100, 190, 20);
        studentList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(studentList);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setBounds(250, 150, 100, 20);
        container.add(name);

        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setBounds(450, 150, 190, 20);
        nameText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(nameText);

        account = new JLabel("Account Number");
        account.setFont(new Font("Arial", Font.PLAIN, 20));
        account.setBounds(250, 200, 200, 20);
        container.add(account);

        accountText = new JTextField();
        accountText.setFont(new Font("Arial", Font.PLAIN, 15));
        accountText.setBounds(450, 200, 190, 20);
        accountText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(accountText);

        contact = new JLabel("Contact");
        contact.setFont(new Font("Arial", Font.PLAIN, 20));
        contact.setBounds(250, 250, 100, 20);
        container.add(contact);

        contactText = new JTextField();
        contactText.setFont(new Font("Arial", Font.PLAIN, 15));
        contactText.setBounds(450, 250, 190, 20);
        contactText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(contactText);

        password = new JLabel("Password");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(250, 300, 100, 20);
        container.add(password);

        passwordText = new JPasswordField();
        passwordText.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordText.setBounds(450, 300, 190, 20);
        passwordText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2));
        container.add(passwordText);

        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_student_details(?)}");
            if(User.getInstance().getRole() != 2) {
                cs.setString(1, User.getInstance().getId());
            } else {
                String[] parts = studentList.getSelectedItem().toString().split(" - ");
                cs.setString(1, parts[0]);
            }
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                nameText.setText(rs.getString("s_name"));
                accountText.setText(rs.getString("account_no"));
                contactText.setText(rs.getString("contact"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        edit = new JButton("Edit");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setBounds(425, 350, 100, 20);
        edit.setBackground(new Color(176, 166, 149));
        edit.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the values from the form
                String s_name = nameText.getText();
                String s_account = accountText.getText();
                String s_contact = contactText.getText();
                String s_password = passwordText.getText();

                // use procedure update_student_details(IN ID varchar(50), IN v_name varchar(50), IN account_no varchar(50), IN contact char(10), IN password varchar(256))
                try {
                    Conn c = new Conn();
                    CallableStatement cs = c.con.prepareCall("{call update_student_details(?, ?, ?, ?, ?)}");
                    if(User.getInstance().getRole() != 2) {
                        cs.setString(1, User.getInstance().getId());
                    } else {
                        String[] parts = studentList.getSelectedItem().toString().split(" - ");
                        cs.setString(1, parts[0]);
                    }
                    cs.setString(3, s_name);
                    cs.setString(2, s_account);
                    cs.setString(4, s_contact);
                    s_password = Hash.hash(s_password);
                    cs.setString(5, s_password);
                    cs.execute();
                    JOptionPane.showMessageDialog(f, "student details updated successfully");
                    f.dispose();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        container.add(edit);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setBounds(425, 400, 100, 20);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(User.getInstance().getRole() == 0){
                    StudentHome home = new StudentHome();
                    home.show();
                }
                f.dispose();
            }
        });

        container.add(back);

        f.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }
    public static void main(String[] args) {
        EditStudent editStudent = new EditStudent();
        editStudent.show();
    }
}
