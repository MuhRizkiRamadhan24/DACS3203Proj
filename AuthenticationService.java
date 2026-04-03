import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationService {

    public static User authenticate(String username, String password) {
        Connection con = DBUtils.establishConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        User loggedInUser = null;

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                if (BCrypt.checkpw(password, storedHash)) {
                    loggedInUser = new User(
                            rs.getString("username"),
                            storedHash,
                            rs.getString("role"),
                            rs.getString("firstName"),
                            rs.getString("lastName")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Auth error: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(con);
        }
        return loggedInUser;
    }
}