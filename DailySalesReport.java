import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class DailySalesReport {

    private Stage stage;
    private User user;

    public DailySalesReport(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        Label titleLabel = new Label("Daily Sales Report");
        Label totalLabel = new Label("Loading...");

        Connection con = DBUtils.establishConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT SUM(amount) FROM payments WHERE paymentDate = CURDATE()");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble(1);
                totalLabel.setText("Total Sales Today: " + total);
            }
        } catch (Exception e) {
            totalLabel.setText("Error loading report.");
            System.out.println("Error: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(con);
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10, titleLabel, totalLabel, backBtn);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 200));
        stage.setTitle("Daily Sales Report");
        stage.show();
    }
}