import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class Reservation {

    private Stage stage;
    private User user;
    private String customerName;
    private String date;
    private String tableNumber;

    public Reservation(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }
    public Reservation(String customerName, String date, String tableNumber) {
        this.customerName = customerName;
        this.date = date;
        this.tableNumber = tableNumber;
    }

    public String getCustomerName() { return customerName; }
    public String getDate() { return date; }
    public String getTableNumber() { return tableNumber; }

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
            try {
                Connection con = DBUtils.establishConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO reservations (customerName, date, tableNumber) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, date);
                ps.setInt(3, Integer.parseInt(table));
                ps.executeUpdate();
                DBUtils.closeConnection(con);
                showAlert("Success", "Reservation created!");;
            } catch (Exception ex) {
                showAlert("Error", "Database error: " + ex.getMessage());
            }

        });

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
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
