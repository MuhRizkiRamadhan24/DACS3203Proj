import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class ViewStaff {

    private Stage stage;
    private User user;

    public ViewStaff(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        TableView<User> table = new TableView<>();

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        table.getColumns().addAll(usernameCol, firstNameCol, lastNameCol, roleCol);

        ObservableList<User> list = FXCollections.observableArrayList();

        Connection con = DBUtils.establishConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT username, firstName, lastName, role FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getString("username"),
                        "",
                        rs.getString("role"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
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
        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("View Staff");
        stage.show();
    }
}