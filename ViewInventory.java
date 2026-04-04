import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewInventory {

    private Stage stage;
    private User user;

    public ViewInventory(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        TableView<inventory> table = new TableView<>();

        TableColumn<inventory, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<inventory, Integer> stockCol = new TableColumn<>("Stock Level");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stockLevel"));

        table.getColumns().addAll(nameCol, stockCol);

        ObservableList<inventory> list = FXCollections.observableArrayList();

        Connection con = DBUtils.establishConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT itemName, stockLevel FROM inventory");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new inventory(rs.getString("itemName"), rs.getInt("stockLevel")));
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
        stage.setTitle("View Inventory");
        stage.show();
    }
}