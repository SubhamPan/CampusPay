import java.sql.*;

public class Conn {
    // this class is used to connect to the database
    Connection con;
    Statement stmt;

    public Conn() {
        try {
            String user = "root";
            // get password from environment variable MYSQL_ROOT_PASSWORD
            String password = System.getenv("MYSQL_ROOT_PASSWORD");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/campuspay", user, password);
            stmt = con.createStatement();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
