import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BikeRentalService service = new BikeRentalService();

        // Example bikes
        service.addBike(new MountainBike("M1", "Trek"));
        service.addBike(new BMXBike("B1", "Haro"));
        service.addBike(new RoadBike("R1", "Giant"));
        service.addBike(new ElectricBike("E1", "Xiaomi"));
        service.addBike(new JapaneseBike("J1", "Bridgestone"));

        while (true) {
            System.out.println("===== BIKE RENTAL SYSTEM =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View Available Bikes");
            System.out.println("3. Rent Bike");
            System.out.println("4. Return Bike");
            System.out.println("5. View Active Rentals");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Customer ID: ");
                    String cid = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Contact: ");
                    String contact = sc.nextLine();

                    service.registerCustomer(new Customer(cid, name, contact));
                    System.out.println("Customer Registered!\n");
                    break;

                case 2:
                    service.displayAvailableBikes();
                    break;

                case 3:
                    System.out.print("Enter Customer ID: ");
                    String customerId = sc.nextLine();
                    Customer customer = service.findCustomer(customerId);

                    if (customer == null) {
                        System.out.println("Customer not found!\n");
                        break;
                    }

                    System.out.print("Enter Bike ID: ");
                    String bikeId = sc.nextLine();
                    Bike bike = service.findBike(bikeId);

                    if (bike == null) {
                        System.out.println("Bike not available!\n");
                        break;
                    }

                    System.out.print("Enter Hours: ");
                    int hours = sc.nextInt();
                    sc.nextLine();

                    String rentalId = "R" + customerId + "-" + bikeId;
                    Rental rental = new Rental(rentalId, customer, bike, hours);
                    service.addRental(rental);

                    Receipt.printReceipt(rental);

                    Payment payment = new Payment("P" + customerId, rental.getTotalCost(), "Cash");
                    payment.processPayment();
                    break;

                case 4:
                    System.out.print("Enter Rental ID to return: ");
                    String rid = sc.nextLine();

                    Rental activeRental = service.findActiveRental(rid);

                    if (activeRental == null) {
                        System.out.println("No active rental found with that ID.\n");
                        break;
                    }

                    activeRental.returnBike();
                    System.out.println("\n===== BIKE RETURNED =====");
                    System.out.println("Rental ID : " + activeRental.getRentalId());
                    System.out.println("Bike      : " + activeRental.getBike().getBrand()
                            + " (" + activeRental.getBike().getBikeId() + ")");
                    System.out.println("Customer  : " + activeRental.getCustomer().getName());
                    System.out.println("Status    : Returned successfully. Bike is now available.");
                    System.out.println("=========================\n");
                    break;

                case 5:
                    service.displayActiveRentals();
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    return;

                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }
}