public class Payment {

    private int orderId;
    private double amount;
    private String paymentDate;

    public Payment(int orderId, double amount, String paymentDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
}