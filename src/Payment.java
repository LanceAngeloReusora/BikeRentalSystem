public class Payment {

    private String paymentId;
    private double amount;
    private String method;

    public Payment(String paymentId, double amount, String method) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
    }

    public void processPayment() {
        System.out.println("===== PAYMENT SUCCESSFUL =====");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Amount Paid: " + amount);
        System.out.println("Method: " + method);
    }
}