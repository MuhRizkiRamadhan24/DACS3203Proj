import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewMenu {

    private Stage stage;

    public ViewMenu(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        TableView<Menu> table = new TableView<>();

        TableColumn<Menu, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Menu, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, priceCol);

        ObservableList<Menu> list = FXCollections.observableArrayList(
                new Menu("Burger", 25),
                new Menu("Pizza", 40)
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
        stage.setTitle("View Menu");
        stage.show();
    }
}
