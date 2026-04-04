import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewOrders {

    private Stage stage;
    private User user;

    public ViewOrders(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }
    public void initializeComponents() {

        TableView<Order> table = new TableView<>();

        TableColumn<Order, String> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        table.getColumns().addAll(idCol, statusCol, totalCol);

        ObservableList<Order> list = FXCollections.observableArrayList();

        try {
            Connection con = DBUtils.establishConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id, status, totalAmount FROM orders");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String status = rs.getString("status");
                double total = rs.getDouble("totalAmount");
                list.add(new Order(id, status, total));
            }

            DBUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        table.setItems(list);

        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage, user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("View Orders");
        stage.show();
    }
}