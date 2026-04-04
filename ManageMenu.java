import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ManageMenu {

    private Stage stage;
    private User user;

    public ManageMenu(Stage stage, User user) {
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

        loadMenu(list);
        table.setItems(list);

        TextField nameField = new TextField();
        nameField.setPromptText("New name");
        TextField priceField = new TextField();
        priceField.setPromptText("New price");
        Label messageLabel = new Label("");

        Button editBtn = new Button("Edit Selected");
        editBtn.setOnAction(e -> {
            Menu selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                messageLabel.setText("Select an item first.");
                return;
            }
            String newName = nameField.getText().trim();
            String newPriceText = priceField.getText().trim();

            if (!newName.matches("^[a-zA-Z ]{2,50}$")) {
                messageLabel.setText("Invalid name.");
                return;
            }
            if (!newPriceText.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                messageLabel.setText("Invalid price.");
                return;
            }

            double newPrice = Double.parseDouble(newPriceText);

            Connection con = DBUtils.establishConnection();
            try {
                PreparedStatement ps = con.prepareStatement("UPDATE menu SET name = ?, price = ? WHERE name = ?");
                ps.setString(1, newName);
                ps.setDouble(2, newPrice);
                ps.setString(3, selected.getName());
                ps.executeUpdate();
                messageLabel.setText("Item updated.");
                list.clear();
                loadMenu(list);
            } catch (Exception ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            } finally {
                DBUtils.closeConnection(con);
            }
        });

        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setOnAction(e -> {
            Menu selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                messageLabel.setText("Select an item first.");
                return;
            }

            Connection con = DBUtils.establishConnection();
            try {
                PreparedStatement ps = con.prepareStatement("DELETE FROM menu WHERE name = ?");
                ps.setString(1, selected.getName());
                ps.executeUpdate();
                messageLabel.setText("Item deleted.");
                list.clear();
                loadMenu(list);
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

        HBox editRow = new HBox(10, nameField, priceField, editBtn);
        VBox layout = new VBox(10, table, editRow, deleteBtn, messageLabel, backBtn);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 500, 500));
        stage.setTitle("Manage Menu");
        stage.show();
    }

    private void loadMenu(ObservableList<Menu> list) {
        Connection con = DBUtils.establishConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT name, price FROM menu");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Menu(rs.getString("name"), rs.getDouble("price")));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(con);
        }
    }
}