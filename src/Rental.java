public class Rental {

    private String rentalId;
    private Customer customer;
    private Bike bike;
    private int hours;
    private double totalCost;
    private boolean isReturned;

    public Rental(String rentalId, Customer customer, Bike bike, int hours) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.bike = bike;
        this.hours = hours;
        this.totalCost = bike.calculateRentalCost(hours);
        this.isReturned = false;
        bike.setAvailable(false);
    }

    public String getRentalId() {
        return rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bike getBike() {
        return bike;
    }

    public int getHours() {
        return hours;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void returnBike() {
        this.isReturned = true;
        bike.setAvailable(true);
    }

    public void displayRental() {
        System.out.println("===== RENTAL DETAILS =====");
        System.out.println("Rental ID: " + rentalId);
        customer.displayCustomer();
        bike.displayInfo();
        System.out.println("Hours Rented: " + hours);
        System.out.println("Total Cost: PHP " + totalCost);
        System.out.println("Status: " + (isReturned ? "Returned" : "Active"));
    }
}