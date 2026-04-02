import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateOrder {

    private Stage stage;

    public CreateOrder(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        TextField orderIdField = new TextField();
        TextField itemField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();

        Button addButton = new Button("Add Item");
        Button saveButton = new Button("Save Order");
        Button backButton = new Button("Back");

        Label totalLabel = new Label("Total: 0");

        final double[] total = {0};

        addButton.setOnAction(e -> {
            try {
                int qty = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                total[0] += qty * price;
                totalLabel.setText("Total: " + total[0]);

                itemField.clear();
                quantityField.clear();
                priceField.clear();

            } catch (Exception ex) {
                showAlert("Error", "Enter valid numbers");
            }
        });

        saveButton.setOnAction(e -> {
            if (orderIdField.getText().isEmpty()) {
                showAlert("Error", "Enter order ID");
                return;
            }

            showAlert("Success", "Order created (mock)");
        });

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage);
            home.initializeComponents();
        });

        VBox layout = new VBox(10,
                new Label("Order ID"), orderIdField,
                new Label("Item Name"), itemField,
                new Label("Quantity"), quantityField,
                new Label("Price"), priceField,
                addButton,
                totalLabel,
                saveButton,
                backButton
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 450));
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
