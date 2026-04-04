import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {

    private Stage stage;
    private User user;

    public HomePage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void initializeComponents() {

        Button reservationBtn = new Button("Reservations");
        reservationBtn.setOnAction(e -> {
            Reservation res = new Reservation(stage, user);
            res.initializeComponents();
        });
        Button viewResBtn = new Button("View Reservations");
        viewResBtn.setOnAction(e -> {
            ViewReservation view = new ViewReservation(stage , user);
            view.initializeComponents();
        });
        Button orderBtn = new Button("Orders");
        orderBtn.setOnAction(e -> {
            CreateOrder order = new CreateOrder(stage, user);
            order.initializeComponents();
        });
        Button viewOrderBtn = new Button("View Orders");
        viewOrderBtn.setOnAction(e -> {
            ViewOrders view = new ViewOrders(stage, user);
            view.initializeComponents();
        });

        Button staffBtn = new Button("Manage Staff");
        staffBtn.setOnAction(e -> {
            // coming in Phase 3
        });

        Button ManageMenuBtn = new Button("Manage Menu");
        ManageMenuBtn.setOnAction(e -> {
            AddMenu add = new AddMenu(stage, user);
            add.initializeComponents();
        });
        Button viewMenuBtn = new Button("View Menu");
        viewMenuBtn.setOnAction(e -> {
            ViewMenu view = new ViewMenu(stage, user);
            view.initializeComponents();
        });
        Button inventoryBtn = new Button("Inventory");
        Button paymentBtn = new Button("Payments");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            UserLogin login = new UserLogin(stage);
            login.initializeComponents();
        });

        VBox layout = new VBox(10,
                reservationBtn,
                viewResBtn,
                orderBtn,
                viewOrderBtn,
                ManageMenuBtn,
                viewMenuBtn,
                staffBtn,
                inventoryBtn,
                paymentBtn,
                logoutBtn
        );

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user) &&
                !AuthorizationService.isWaiter(user)) {
            reservationBtn.setVisible(false);
            viewResBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user)) {
            ManageMenuBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user) &&
                !AuthorizationService.isInventoryManager(user)) {
            inventoryBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user) &&
                !AuthorizationService.isCashier(user)) {
            paymentBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user)) {
            staffBtn.setVisible(false);
        }

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Restaurant System");
        stage.show();
    }
}