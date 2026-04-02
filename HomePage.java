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
        Button orderBtn = new Button("Orders");
        Button menuBtn = new Button("Menu");
        Button inventoryBtn = new Button("Inventory");
        Button paymentBtn = new Button("Payments");

        VBox layout = new VBox(10,
                reservationBtn,
                orderBtn,
                menuBtn,
                inventoryBtn,
                paymentBtn
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Restaurant System");
        stage.show();
    }
}