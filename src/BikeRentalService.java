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

    public void displayRentalHistory(String customerId) {
        Customer customer = findCustomer(customerId);

        if (customer == null) {
            System.out.println("  [!] Customer not found.\n");
            return;
        }

        System.out.println("\n===== RENTAL HISTORY =====");
        System.out.println("Customer  : " + customer.getName());
        System.out.println("ID        : " + customer.getCustomerId());
        System.out.println("Contact   : " + customer.getContact());
        System.out.println("--------------------------");

        boolean found = false;
        int totalRentals = 0;
        double totalSpent = 0;

        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customerId)) {
                System.out.println("Rental ID : " + rental.getRentalId());
                System.out.println("Bike      : " + rental.getBike().getBrand()
                        + " (" + rental.getBike().getBikeId() + ")"
                        + " - " + rental.getBike().getType());
                System.out.println("Hours     : " + rental.getBookedHours());
                System.out.println("Cost      : PHP " + rental.getTotalCost());
                System.out.println("Status    : " + (rental.isReturned() ? "Returned" : "Active"));
                if (rental.getLateFee() > 0) {
                    System.out.println("Late Fee  : PHP " + rental.getLateFee());
                }
                System.out.println("--------------------------");
                totalRentals++;
                totalSpent += rental.getTotalCost();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rental records found for this customer.");
        } else {
            System.out.println("Total Rentals : " + totalRentals);
            System.out.println("Total Spent   : PHP " + totalSpent);
        }
        System.out.println("==========================\n");
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