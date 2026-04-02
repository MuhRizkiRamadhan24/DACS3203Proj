import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Reservation {

    private Stage stage;

    public Reservation(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        TextField nameField = new TextField();
        TextField dateField = new TextField();
        TextField tableField = new TextField();

        Button saveButton = new Button("Create Reservation");
        Button backButton = new Button("Back");

        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String table = tableField.getText();

            if (name.isEmpty() || date.isEmpty() || table.isEmpty()) {
                showAlert("Error", "Fill all fields");
                return;
            }

            showAlert("Success", "Reservation created (mock)");
        });

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage);
            home.initializeComponents();
        });

        VBox layout = new VBox(10,
                new Label("Customer Name"), nameField,
                new Label("Date (YYYY-MM-DD)"), dateField,
                new Label("Table Number"), tableField,
                saveButton,
                backButton
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 350));
        stage.setTitle("Create Reservation");
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
