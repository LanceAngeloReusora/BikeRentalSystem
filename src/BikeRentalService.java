import java.util.ArrayList;

public class BikeRentalService {

    private ArrayList<Bike> bikes = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    // ---------- Add / Register ----------

    // Returns false and warns if bike ID already exists
    public boolean addBike(Bike bike) {
        if (findBikeById(bike.getBikeId()) != null) {
            System.out.println("  [!] Bike ID \"" + bike.getBikeId() + "\" already exists. Skipping.");
            return false;
        }
        bikes.add(bike);
        return true;
    }

    // Returns false and warns if customer ID already exists
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

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // ---------- Display ----------

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
            System.out.println("  No bikes available at the moment.");
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
            System.out.println("  No active rentals.");
        }
        System.out.println();
    }

    public void displayActiveReservations() {
        System.out.println("\n===== ACTIVE RESERVATIONS =====");
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (reservation.isActive()) {
                reservation.displayReservation();
                found = true;
            }
        }
        if (!found) {
            System.out.println("  No active reservations.");
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
        System.out.println("Customer : " + customer.getName());
        System.out.println("ID       : " + customer.getCustomerId());
        System.out.println("Contact  : " + customer.getContact());
        System.out.println("--------------------------");

        int totalRentals = 0;
        double totalSpent = 0;
        boolean found = false;

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
                if (rental.getDamagePenalty() > 0) {
                    System.out.println("Damage Fee: PHP " + rental.getDamagePenalty());
                }
                System.out.println("--------------------------");
                totalRentals++;
                totalSpent += rental.getTotalCost();
                found = true;
            }
        }

        if (!found) {
            System.out.println("  No rental records found for this customer.");
        } else {
            System.out.println("Total Rentals : " + totalRentals);
            System.out.println("Total Spent   : PHP " + totalSpent);
        }

        System.out.println("==========================\n");
    }

    // ---------- Search ----------

    // Finds a customer by ID
    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // Finds any bike by ID regardless of availability (used for duplicate checks)
    public Bike findBikeById(String id) {
        for (Bike b : bikes) {
            if (b.getBikeId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    // Finds an available bike by ID (used when renting or reserving)
    public Bike findBike(String id) {
        for (Bike b : bikes) {
            if (b.getBikeId().equals(id) && b.isAvailable()) {
                return b;
            }
        }
        return null;
    }

    // Finds an active (not yet returned) rental by rental ID
    public Rental findActiveRental(String rentalId) {
        for (Rental r : rentals) {
            if (r.getRentalId().equals(rentalId) && !r.isReturned()) {
                return r;
            }
        }
        return null;
    }

    // Finds an active reservation by reservation ID
    public Reservation findActiveReservation(String reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equals(reservationId) && r.isActive()) {
                return r;
            }
        }
        return null;
    }
}