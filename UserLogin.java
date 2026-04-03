import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserLogin {

    private Stage stage;

    public UserLogin(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Enter username and password");
                return;
            }

            User user = AuthenticationService.authenticate(username, password);

            if (user != null) {
                HomePage home = new HomePage(stage, user);
                home.initializeComponents();
            } else {
                showAlert("Error", "Invalid login");
            }
        });

        VBox layout = new VBox(10,
                new Label("Username"), usernameField,
                new Label("Password"), passwordField,
                loginButton
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Login");
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}