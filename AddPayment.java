import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class AddPayment {

    private Stage stage;
    private User user;

    public AddPayment(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        Label orderIdLabel = new Label("Order ID:");
        TextField orderIdField = new TextField();

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label dateLabel = new Label("Payment Date (YYYY-MM-DD):");
        TextField dateField = new TextField();

        Label messageLabel = new Label("");

        Button saveBtn = new Button("Save Payment");
        saveBtn.setOnAction(e -> {
            String orderIdText = orderIdField.getText().trim();
            String amountText = amountField.getText().trim();
            String date = dateField.getText().trim();

            if (!orderIdText.matches("^[0-9]{1,5}$")) {
                messageLabel.setText("Invalid order ID.");
                return;
            }
            if (!amountText.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                messageLabel.setText("Invalid amount.");
                return;
            }
            if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                messageLabel.setText("Invalid date format.");
                return;
            }

            int orderId = Integer.parseInt(orderIdText);
            double amount = Double.parseDouble(amountText);

            Connection con = DBUtils.establishConnection();
            try {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO payments (orderId, amount, paymentDate) VALUES (?, ?, ?)");
                ps.setInt(1, orderId);
                ps.setDouble(2, amount);
                ps.setString(3, date);
                ps.executeUpdate();
                messageLabel.setText("Payment saved successfully.");
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
                orderIdLabel, orderIdField,
                amountLabel, amountField,
                dateLabel, dateField,
                messageLabel,
                saveBtn, backBtn);

        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 350));
        stage.setTitle("Add Payment");
        stage.show();
    }
}