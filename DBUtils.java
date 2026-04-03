import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {

    public static Connection establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/rms";
            String user = "rms_user";
            String password = "StrongPassword123";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Close failed: " + e.getMessage());
        }
    }
}