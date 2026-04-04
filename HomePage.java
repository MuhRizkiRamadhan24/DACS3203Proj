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
            AddStaff staff = new AddStaff(stage, user);
            staff.initializeComponents();
        });

        Button viewStaffBtn = new Button("View Staff");
        viewStaffBtn.setOnAction(e -> {
            ViewStaff view = new ViewStaff(stage, user);
            view.initializeComponents();
        });

        Button ManageMenuBtn = new Button("Manage Menu");
        ManageMenuBtn.setOnAction(e -> {
            ManageMenu manage = new ManageMenu(stage, user);
            manage.initializeComponents();
        });
        Button viewMenuBtn = new Button("View Menu");
        viewMenuBtn.setOnAction(e -> {
            ViewMenu view = new ViewMenu(stage, user);
            view.initializeComponents();
        });
        Button inventoryBtn = new Button("Inventory");
        inventoryBtn.setOnAction(e -> {
            AddInventory add = new AddInventory(stage, user);
            add.initializeComponents();
        });
        Button viewInventoryBtn = new Button("View Inventory");
        viewInventoryBtn.setOnAction(e -> {
            ViewInventory view = new ViewInventory(stage, user);
            view.initializeComponents();
        });

        Button paymentBtn = new Button("Payments");
        paymentBtn.setOnAction(e -> {
            AddPayment add = new AddPayment(stage, user);
            add.initializeComponents();
        });
        Button viewPaymentBtn = new Button("View Payments");
        viewPaymentBtn.setOnAction(e -> {
            ViewPayments view = new ViewPayments(stage, user);
            view.initializeComponents();
        });

        Button reportBtn = new Button("Daily Sales Report");
        reportBtn.setOnAction(e -> {
            DailySalesReport report = new DailySalesReport(stage, user);
            report.initializeComponents();
        });

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
                viewStaffBtn,
                inventoryBtn,
                viewInventoryBtn,
                paymentBtn,
                viewPaymentBtn,
                reportBtn,
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
            viewInventoryBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user) &&
                !AuthorizationService.isCashier(user)) {
            paymentBtn.setVisible(false);
            viewPaymentBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user)) {
            staffBtn.setVisible(false);
            viewStaffBtn.setVisible(false);
        }

        if (!AuthorizationService.isAdmin(user) &&
                !AuthorizationService.isManager(user)) {
            reportBtn.setVisible(false);
        }

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 500));
        stage.setTitle("Restaurant System");
        stage.show();
    }
}