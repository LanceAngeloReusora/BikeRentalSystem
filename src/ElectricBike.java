public class ElectricBike extends Bike {

    public ElectricBike(String id, String brand) {
        super(id, brand, 100);
    }

    @Override
    public String getType() {
        return "Electric Bike";
    }

    @Override
    public double calculateRentalCost(int hours) {
        return (getRatePerHour() * hours) + 20;
    }
}