import java.sql.Connection;

public class DBUtils {

    public static Connection establishConnection() {
        System.out.println("nothing yet");
        return null;
    }

    public static void closeConnection(Connection con) {
        System.out.println("DB closed");
    }
}