import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewPayments {

    private Stage stage;
    private User user;

    public ViewPayments(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        TableView<Payment> table = new TableView<>();

        TableColumn<Payment, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Payment, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Payment, String> dateCol = new TableColumn<>("Payment Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        table.getColumns().addAll(orderIdCol, amountCol, dateCol);

        ObservableList<Payment> list = FXCollections.observableArrayList();

        Connection con = DBUtils.establishConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT orderId, amount, paymentDate FROM payments");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Payment(
                        rs.getInt("orderId"),
                        rs.getDouble("amount"),
                        rs.getString("paymentDate")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(con);
        }

        table.setItems(list);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10, table, backBtn);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("View Payments");
        stage.show();
    }
}