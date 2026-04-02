import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {

    private Stage stage;

    public HomePage(Stage stage) {
        this.stage = stage;
    }

    public void initializeComponents() {

        Button reservationBtn = new Button("Reservations");
        reservationBtn.setOnAction(e -> {
            Reservation res = new Reservation(stage);
            res.initializeComponents();
        });
        Button viewResBtn = new Button("View Reservations");
        viewResBtn.setOnAction(e -> {
            ViewReservation view = new ViewReservation(stage);
            view.initializeComponents();
        });
        Button orderBtn = new Button("Orders");
        orderBtn.setOnAction(e -> {
            CreateOrder order = new CreateOrder(stage);
            order.initializeComponents();
        });
        Button viewOrderBtn = new Button("View Orders");
        viewOrderBtn.setOnAction(e -> {
            ViewOrders view = new ViewOrders(stage);
            view.initializeComponents();
        });
        Button menuBtn = new Button("Menu");
        Button addMenuBtn = new Button("Add Menu Item");
        addMenuBtn.setOnAction(e -> {
            AddMenu add = new AddMenu(stage);
            add.initializeComponents();
        });
        Button viewMenuBtn = new Button("View Menu");
        viewMenuBtn.setOnAction(e -> {
            ViewMenu view = new ViewMenu(stage);
            view.initializeComponents();
        });
        Button inventoryBtn = new Button("Inventory");
        Button paymentBtn = new Button("Payments");

        VBox layout = new VBox(10,
                reservationBtn,
                viewResBtn,
                orderBtn,
                viewOrderBtn,
                menuBtn,
                addMenuBtn,
                viewMenuBtn,
                inventoryBtn,
                paymentBtn
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Restaurant System");
        stage.show();
    }
}