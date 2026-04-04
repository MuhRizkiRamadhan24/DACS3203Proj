import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewMenu {

    private Stage stage;
    private User user;

    public ViewMenu(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        TableView<Menu> table = new TableView<>();

        TableColumn<Menu, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Menu, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, priceCol);

        ObservableList<Menu> list = FXCollections.observableArrayList();

        try {
            Connection con = DBUtils.establishConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name, price FROM menu");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                list.add(new Menu(name, price));
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
        stage.setTitle("View Menu");
        stage.show();
    }
}
