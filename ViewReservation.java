import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewReservation {

    private Stage stage;
    private User user;

    public ViewReservation(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
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

        ObservableList<Reservation> list = FXCollections.observableArrayList();

        try {
            Connection con = DBUtils.establishConnection();
            PreparedStatement ps = con.prepareStatement("SELECT customerName, date, tableNumber FROM reservations");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("customerName");
                String date = rs.getString("date");
                String tableNum = rs.getString("tableNumber");
                list.add(new Reservation(name, date, tableNum));
            }

            DBUtils.closeConnection(con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        table.setItems(list);

        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {
            HomePage home = new HomePage(stage,user);
            home.initializeComponents();
        });

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("View Reservations");
        stage.show();
    }
}
