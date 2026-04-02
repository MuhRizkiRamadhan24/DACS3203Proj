import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewOrders {

    private Stage stage;

    public ViewOrders(Stage stage) {
        this.stage = stage;
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

        // data(not actual)
        ObservableList<Order> list = FXCollections.observableArrayList(
                new Order("101", "Pending", 45.5),
                new Order("102", "Completed", 88.0)
        );

        table.setItems(list);

        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage);
            home.initializeComponents();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("View Orders");
        stage.show();
    }
}