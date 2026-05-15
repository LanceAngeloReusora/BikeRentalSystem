public class Rental {

    private String rentalId;
    private Customer customer;
    private Bike bike;
    private int bookedHours;
    private int actualHours;
    private double totalCost;
    private double lateFee;
    private boolean isReturned;

    public Rental(String rentalId, Customer customer, Bike bike, int bookedHours) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.bike = bike;
        this.bookedHours = bookedHours;
        this.actualHours = bookedHours; // default, updated on return
        this.totalCost = bike.calculateRentalCost(bookedHours);
        this.lateFee = 0;
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

    public int getBookedHours() {
        return bookedHours;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getLateFee() {
        return lateFee;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void returnBike(int actualHours) {
        this.actualHours = actualHours;
        this.isReturned = true;
        bike.setAvailable(true);

        // Calculate late fee if actual hours exceed booked hours
        if (actualHours > bookedHours) {
            int extraHours = actualHours - bookedHours;
            this.lateFee = bike.getRatePerHour() * extraHours * 1.5; // 1.5x rate for overtime
            this.totalCost = bike.calculateRentalCost(bookedHours) + lateFee;
        }
    }

    public void displayRental() {
        System.out.println("Rental ID     : " + rentalId);
        customer.displayCustomer();
        bike.displayInfo();
        System.out.println("Booked Hours  : " + bookedHours);
        System.out.println("Actual Hours  : " + actualHours);
        System.out.println("Base Cost     : PHP " + bike.calculateRentalCost(bookedHours));
        if (lateFee > 0) {
            System.out.println("Extra Hours   : " + (actualHours - bookedHours));
            System.out.println("Late Fee      : PHP " + lateFee);
        }
        System.out.println("Total Cost    : PHP " + totalCost);
        System.out.println("Status        : " + (isReturned ? "Returned" : "Active"));
    }
}