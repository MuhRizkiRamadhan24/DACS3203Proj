import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class AddMenu {

    private Stage stage;
    private User user;

    public AddMenu(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }
    public void initializeComponents() {

        TextField nameField = new TextField();
        TextField priceField = new TextField();

        Button addButton = new Button("Add Item");
        Button backButton = new Button("Back");

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();

                if (!name.matches("^[a-zA-Z ]{2,50}$")) {
                    showAlert("Error", "Invalid item name.");
                    return;
                }
                if (!priceText.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    showAlert("Error", "Invalid price.");
                    return;
                }
                double price = Double.parseDouble(priceText);
                try {
                    Connection con = DBUtils.establishConnection();
                    PreparedStatement ps = con.prepareStatement("INSERT INTO menu (name, price) VALUES (?, ?)");
                    ps.setString(1, name);
                    ps.setDouble(2, price);
                    ps.executeUpdate();
                    DBUtils.closeConnection(con);
                    showAlert("Success", "Menu item added!");
                } catch (Exception ex) {
                    showAlert("Error", "Database error: " + ex.getMessage());
                }

                nameField.clear();
                priceField.clear();

            } catch (Exception ex) {
                showAlert("Error", "Enter valid price");
            }
        });

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10,
                new Label("Item Name"), nameField,
                new Label("Price"), priceField,
                addButton,
                backButton
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Add Menu Item");
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
