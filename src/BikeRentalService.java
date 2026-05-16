import java.util.ArrayList;

public class BikeRentalService {

    private ArrayList<Bike> bikes = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals = new ArrayList<>();

    // Returns true if bike was added, false if ID already exists
    public boolean addBike(Bike bike) {
        if (findBikeById(bike.getBikeId()) != null) {
            System.out.println("  [!] Bike ID \"" + bike.getBikeId() + "\" already exists. Skipping.");
            return false;
        }
        bikes.add(bike);
        return true;
    }

    // Returns true if customer was added, false if ID already exists
    public boolean registerCustomer(Customer customer) {
        if (findCustomer(customer.getCustomerId()) != null) {
            System.out.println("  [!] Customer ID \"" + customer.getCustomerId() + "\" already exists.");
            return false;
        }
        customers.add(customer);
        return true;
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

    // Finds a bike by ID regardless of availability (for duplicate checking)
    public Bike findBikeById(String id) {
        for (Bike b : bikes) {
            if (b.getBikeId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    // Finds an available bike by ID (used when renting)
    public Bike findBike(String id) {
        for (Bike b : bikes) {
            if (b.getBikeId().equals(id) && b.isAvailable()) {
                return b;
            }
        }
        return null;
    }
}