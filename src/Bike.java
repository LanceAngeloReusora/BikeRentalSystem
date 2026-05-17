public abstract class Bike {

    // Enum for bike condition - used when returning a bike
    public enum Condition {
        GOOD, DAMAGED
    }

    private String bikeId;
    private String brand;
    private double ratePerHour;
    private boolean isAvailable;
    private boolean underMaintenance;
    private Condition condition;

    public Bike(String bikeId, String brand, double ratePerHour) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.ratePerHour = ratePerHour;
        this.isAvailable = true;
        this.underMaintenance = false;
        this.condition = Condition.GOOD;
    }

    // ---------- Getters ----------

    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public Condition getCondition() {
        return condition;
    }

    // ---------- Setters ----------

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    // When flagged for maintenance, bike becomes unavailable automatically
    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
        if (underMaintenance) {
            this.isAvailable = false;
        }
    }

    // ---------- Abstract Methods ----------

    // Each subclass must provide its bike type name
    public abstract String getType();

    // Each subclass calculates rental cost differently
    public abstract double calculateRentalCost(int hours);

    // ---------- Display ----------

    public void displayInfo() {
        // Determine readable status
        String status;
        if (underMaintenance) {
            status = "Under Maintenance";
        } else if (isAvailable) {
            status = "Available";
        } else {
            status = "Rented";
        }

        System.out.println("ID: " + bikeId
                + " | Type: "      + getType()
                + " | Brand: "     + brand
                + " | Rate/hr: PHP " + ratePerHour
                + " | Status: "    + status
                + " | Condition: " + condition);
    }
}