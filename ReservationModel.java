public class ReservationModel {

    private String customerName;
    private String date;
    private String tableNumber;

    public ReservationModel(String customerName, String date, String tableNumber) {
        this.customerName = customerName;
        this.date = date;
        this.tableNumber = tableNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
