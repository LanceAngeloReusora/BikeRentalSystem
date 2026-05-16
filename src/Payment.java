public class Payment {

    public enum Method {
        CASH, GCASH, MAYA, CREDIT_DEBIT, BANK_TRANSFER
    }

    private String paymentId;
    private double amount;
    private Method method;

    public Payment(String paymentId, double amount, Method method) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
    }

    public void processPayment() {
        System.out.println("===== PAYMENT SUCCESSFUL =====");
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Amount Paid: PHP " + amount);
        System.out.println("Method     : " + method);
        System.out.println("==============================\n");
    }
}