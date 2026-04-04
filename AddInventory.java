import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddInventory {

    private Stage stage;
    private User user;

    public AddInventory(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        Label itemNameLabel = new Label("Item Name:");
        TextField itemNameField = new TextField();

        Label stockLevelLabel = new Label("Stock Level:");
        TextField stockLevelField = new TextField();

        Label messageLabel = new Label("");

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            String itemName = itemNameField.getText().trim();
            String stockText = stockLevelField.getText().trim();

            if (!itemName.matches("^[a-zA-Z ]{2,50}$")) {
                messageLabel.setText("Invalid item name.");
                return;
            }

            if (!stockText.matches("^[0-9]{1,5}$")) {
                messageLabel.setText("Stock level must be a number.");
                return;
            }

            int stockLevel = Integer.parseInt(stockText);

            Connection con = DBUtils.establishConnection();
            try {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO inventory (itemName, stockLevel) VALUES (?, ?)");
                ps.setString(1, itemName);
                ps.setInt(2, stockLevel);
                ps.executeUpdate();
                messageLabel.setText("Item saved successfully.");
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
                itemNameLabel, itemNameField,
                stockLevelLabel, stockLevelField,
                messageLabel,
                saveBtn, backBtn);

        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Add Inventory Item");
        stage.show();
    }
}