import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewReservation {

    private Stage stage;

    public ViewReservation(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, String> nameCol = new TableColumn<>("Customer");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Reservation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Reservation, String> tableCol = new TableColumn<>("Table");
        tableCol.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));

        table.getColumns().addAll(nameCol, dateCol, tableCol);

        // no DB yet, Omar fix this
        ObservableList<Reservation> list = FXCollections.observableArrayList(
                new Reservation("Ali", "2026-04-10", "5"),
                new Reservation("Sara", "2026-04-11", "2")
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
        stage.setTitle("View Reservations");
        stage.show();
    }
}
