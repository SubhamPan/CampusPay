import java.sql.*;

public class Conn {
    // this class is used to connect to the database
    Connection con;
    Statement stmt;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/campuspay", "root", "Lmao_ded@456");
            stmt = con.createStatement();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
