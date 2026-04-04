import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class CreateOrder {

    private Stage stage;
    private User user;

    public CreateOrder(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        TextField itemField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();

        Button addButton = new Button("Add Item");
        Button saveButton = new Button("Save Order");
        Button backButton = new Button("Back");

        Label totalLabel = new Label("Total: 0");

        final double[] total = {0};
        final String[] lastItem = {""};
        final int[] lastQty = {0};
        final double[] lastPrice = {0};

        addButton.setOnAction(e -> {
            try {
                String item = itemField.getText().trim();
                String qtyText = quantityField.getText().trim();
                String priceText = priceField.getText().trim();

                if (!item.matches("^[a-zA-Z ]{2,50}$")) {
                    showAlert("Error", "Invalid item name.");
                    return;
                }
                if (!qtyText.matches("^[0-9]{1,4}$")) {
                    showAlert("Error", "Invalid quantity.");
                    return;
                }
                if (!priceText.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    showAlert("Error", "Invalid price.");
                    return;
                }
                int qty = Integer.parseInt(qtyText);
                double price = Double.parseDouble(priceText);
                total[0] += qty * price;
                totalLabel.setText("Total: " + total[0]);

                lastItem[0] = itemField.getText();
                lastQty[0] = qty;
                lastPrice[0] = price;

                itemField.clear();
                quantityField.clear();
                priceField.clear();

            } catch (Exception ex) {
                showAlert("Error", "Enter valid numbers");
            }
        });

        saveButton.setOnAction(e -> {
            try {
                Connection con = DBUtils.establishConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO orders (status, totalAmount) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, "Pending");
                ps.setDouble(2, total[0]);
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int orderId = keys.getInt(1);

                PreparedStatement ps2 = con.prepareStatement("INSERT INTO order_items (orderId, itemName, quantity, price) VALUES (?, ?, ?, ?)");
                ps2.setInt(1, orderId);
                ps2.setString(2, lastItem[0]);
                ps2.setInt(3, lastQty[0]);
                ps2.setDouble(4, lastPrice[0]);
                ps2.executeUpdate();

                DBUtils.closeConnection(con);
                showAlert("Success", "Order created!");
            } catch (Exception ex) {
                showAlert("Error", "Database error: " + ex.getMessage());
            }
        });

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10,
                new Label("Item Name"), itemField,
                new Label("Quantity"), quantityField,
                new Label("Price"), priceField,
                addButton,
                totalLabel,
                saveButton,
                backButton
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Create Order");
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}