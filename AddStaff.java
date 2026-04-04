import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class AddStaff {

    private Stage stage;
    private User user;

    public AddStaff(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.setItems(FXCollections.observableArrayList(
                "manager", "waiter", "cashier", "chef", "inventorymanager"
        ));

        Label messageLabel = new Label("");

        Button saveBtn = new Button("Add Staff");
        saveBtn.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleBox.getValue();

            if (!firstName.matches("^[a-zA-Z]{2,50}$")) {
                messageLabel.setText("Invalid first name.");
                return;
            }
            if (!lastName.matches("^[a-zA-Z]{2,50}$")) {
                messageLabel.setText("Invalid last name.");
                return;
            }
            if (!username.matches("^[a-zA-Z0-9]{3,20}$")) {
                messageLabel.setText("Invalid username.");
                return;
            }
            if (password.length() < 6) {
                messageLabel.setText("Password must be at least 6 characters.");
                return;
            }
            if (role == null) {
                messageLabel.setText("Please select a role.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

            Connection con = DBUtils.establishConnection();
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, passwordHash, role, firstName, lastName) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                ps.setString(3, role);
                ps.setString(4, firstName);
                ps.setString(5, lastName);
                ps.executeUpdate();
                messageLabel.setText("Staff added successfully.");
            } catch (Exception ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            } finally {
                DBUtils.closeConnection(con);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10,
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                roleLabel, roleBox,
                messageLabel,
                saveBtn, backBtn);

        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 500));
        stage.setTitle("Add Staff");
        stage.show();
    }
}