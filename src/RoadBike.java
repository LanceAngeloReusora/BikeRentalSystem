public class RoadBike extends Bike {

    public RoadBike(String id, String brand) {
        super(id, brand, 60);
    }

    @Override
    public String getType() {
        return "Road Bike";
    }

    @Override
    public double calculateRentalCost(int hours) {
        return getRatePerHour() * hours;
    }
}