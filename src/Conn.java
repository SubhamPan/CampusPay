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
//            String password = "Ritvik@2005";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/campuspay", user, password);
            stmt = con.createStatement();
        }
        catch (SQLException e) {
            System.out.println("Error connecting to the database:");
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found:");
            System.out.println(e.getMessage());
        }
    }
}