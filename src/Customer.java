public class Customer {

    private String customerId;
    private String name;
    private String contact;
    private double discountRate;

    public Customer(String customerId, String name, String contact, double discountRate) {
        this.customerId = customerId;
        this.name = name;
        this.contact = contact;
        this.discountRate = discountRate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void displayCustomer() {
        System.out.println("Customer ID: " + customerId +
                " | Name: " + name +
                " | Contact: " + contact +
                " | Discount: " + (discountRate * 100) + "%");
    }
}