import java.util.ArrayList;

public class BikeRentalService {

    private ArrayList<Bike> bikes = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals = new ArrayList<>();

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public void displayAvailableBikes() {
        System.out.println("\n===== AVAILABLE BIKES =====");
        boolean found = false;
        for (Bike bike : bikes) {
            if (bike.isAvailable()) {
                bike.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No bikes available at the moment.");
        }
        System.out.println();
    }

    public void displayActiveRentals() {
        System.out.println("\n===== ACTIVE RENTALS =====");
        boolean found = false;
        for (Rental rental : rentals) {
            if (!rental.isReturned()) {
                rental.displayRental();
                System.out.println("--------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No active rentals.");
        }
        System.out.println();
    }

    public Rental findActiveRental(String rentalId) {
        for (Rental rental : rentals) {
            if (rental.getRentalId().equals(rentalId) && !rental.isReturned()) {
                return rental;
            }
        }
        return null;
    }

    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Bike findBike(String id) {
        for (Bike b : bikes) {
            if (b.getBikeId().equals(id) && b.isAvailable()) {
                return b;
            }
        }
        return null;
    }
}