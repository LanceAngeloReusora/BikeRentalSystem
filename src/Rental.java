public class Rental {

    // Fixed penalty fee for returning a damaged bike
    private static final double DAMAGE_PENALTY = 500.00;

    private String rentalId;
    private Customer customer;
    private Bike bike;
    private int bookedHours;
    private int actualHours;
    private double baseCost;
    private double lateFee;
    private double damagePenalty;
    private double totalCost;
    private boolean isReturned;

    public Rental(String rentalId, Customer customer, Bike bike, int bookedHours) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.bike = bike;
        this.bookedHours = bookedHours;
        this.actualHours = bookedHours;
        this.baseCost = bike.calculateRentalCost(bookedHours);
        this.lateFee = 0;
        this.damagePenalty = 0;
        this.totalCost = baseCost;
        this.isReturned = false;

        // Mark bike as unavailable when rented
        bike.setAvailable(false);
    }

    // ---------- Getters ----------

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

    public double getBaseCost() {
        return baseCost;
    }

    public double getLateFee() {
        return lateFee;
    }

    public double getDamagePenalty() {
        return damagePenalty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isReturned() {
        return isReturned;
    }

    // ---------- Return Logic ----------

    public void returnBike(int actualHours, Bike.Condition condition) {
        this.actualHours = actualHours;
        this.isReturned = true;

        // Update the bike's condition
        bike.setCondition(condition);

        // Charge 1.5x rate for every extra hour beyond the booked hours
        if (actualHours > bookedHours) {
            int extraHours = actualHours - bookedHours;
            this.lateFee = bike.getRatePerHour() * extraHours * 1.5;
        }

        // Add damage penalty and flag bike for maintenance if damaged
        if (condition == Bike.Condition.DAMAGED) {
            this.damagePenalty = DAMAGE_PENALTY;
            bike.setUnderMaintenance(true);  // bike becomes unavailable until fixed
        } else {
            bike.setAvailable(true);  // bike goes back to available if in good condition
        }

        // Final total = base cost + late fee + damage penalty
        this.totalCost = baseCost + lateFee + damagePenalty;
    }

    // ---------- Display ----------

    public void displayRental() {
        System.out.println("Rental ID     : " + rentalId);
        customer.displayCustomer();
        bike.displayInfo();
        System.out.println("Booked Hours  : " + bookedHours);
        System.out.println("Actual Hours  : " + actualHours);
        System.out.println("Base Cost     : PHP " + baseCost);

        if (lateFee > 0) {
            System.out.println("Late Fee      : PHP " + lateFee);
        }
        if (damagePenalty > 0) {
            System.out.println("Damage Penalty: PHP " + damagePenalty);
        }

        System.out.println("Total Cost    : PHP " + totalCost);
        System.out.println("Status        : " + (isReturned ? "Returned" : "Active"));
    }
}