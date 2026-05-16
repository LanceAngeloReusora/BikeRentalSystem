import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Safe integer input - keeps asking until a valid number is entered
    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("  [!] Please enter a positive number.\n");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number only.\n");
            }
        }
    }

    // Safe string input - keeps asking until non-empty input is entered
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("  [!] This field cannot be empty.\n");
            } else {
                return input;
            }
        }
    }

    // Safe menu choice - only accepts numbers within the valid range
    public static int getMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Choose option: ");
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("  [!] Please choose between " + min + " and " + max + ".\n");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number only.\n");
            }
        }
    }

    // Let user pick a payment method from the enum
    public static Payment.Method getPaymentMethod() {
        System.out.println("  Select Payment Method:");
        System.out.println("  1. Cash");
        System.out.println("  2. GCash");
        System.out.println("  3. Maya");
        System.out.println("  4. Credit/Debit Card");
        System.out.println("  5. Bank Transfer");

        int choice;
        while (true) {
            System.out.print("  Choose payment method (1-5): ");
            String input = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 5) {
                    System.out.println("  [!] Please choose between 1 and 5.\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number only.\n");
            }
        }

        switch (choice) {
            case 1: return Payment.Method.CASH;
            case 2: return Payment.Method.GCASH;
            case 3: return Payment.Method.MAYA;
            case 4: return Payment.Method.CREDIT_DEBIT;
            case 5: return Payment.Method.BANK_TRANSFER;
            default: return Payment.Method.CASH;
        }
    }

    public static void main(String[] args) {
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

            int choice = getMenuChoice(1, 6);

            switch (choice) {

                case 1:
                    String cid = getStringInput("Enter Customer ID: ");

                    if (service.findCustomer(cid) != null) {
                        System.out.println("  [!] Customer ID already exists. Please use a different ID.\n");
                        break;
                    }

                    String name = getStringInput("Enter Name: ");

                    String contact;
                    while (true) {
                        contact = getStringInput("Enter Contact Number: ");
                        if (contact.matches("\\d+")) {
                            break;
                        } else {
                            System.out.println("  [!] Contact must contain numbers only.\n");
                        }
                    }

                    service.registerCustomer(new Customer(cid, name, contact));
                    System.out.println("  [✓] Customer Registered Successfully!\n");
                    break;

                case 2:
                    service.displayAvailableBikes();
                    break;

                case 3:
                    String customerId = getStringInput("Enter Customer ID: ");
                    Customer customer = service.findCustomer(customerId);

                    if (customer == null) {
                        System.out.println("  [!] Customer not found. Please register first.\n");
                        break;
                    }

                    String bikeId = getStringInput("Enter Bike ID: ");
                    Bike bike = service.findBike(bikeId);

                    if (bike == null) {
                        System.out.println("  [!] Bike not available or does not exist.\n");
                        break;
                    }

                    int hours = getIntInput("Enter Hours to Rent: ");

                    String rentalId = "R" + customerId + "-" + bikeId;

                    if (service.findActiveRental(rentalId) != null) {
                        System.out.println("  [!] This customer already has an active rental for this bike.\n");
                        break;
                    }

                    Rental rental = new Rental(rentalId, customer, bike, hours);
                    service.addRental(rental);

                    Receipt.printReceipt(rental);

                    Payment.Method rentMethod = getPaymentMethod();
                    Payment payment = new Payment("P" + customerId, rental.getTotalCost(), rentMethod);
                    payment.processPayment();
                    break;

                case 4:
                    String rid = getStringInput("Enter Rental ID to return: ");
                    Rental activeRental = service.findActiveRental(rid);

                    if (activeRental == null) {
                        System.out.println("  [!] No active rental found with that ID.\n");
                        break;
                    }

                    System.out.println("  Booked Hours: " + activeRental.getBookedHours());

                    int actualHours;
                    while (true) {
                        actualHours = getIntInput("Enter Actual Hours Used: ");
                        if (actualHours < activeRental.getBookedHours()) {
                            System.out.println("  [!] Actual hours cannot be less than booked hours ("
                                    + activeRental.getBookedHours() + "). Please re-enter.\n");
                        } else {
                            break;
                        }
                    }

                    activeRental.returnBike(actualHours);

                    System.out.println("\n===== RETURN SUMMARY =====");
                    System.out.println("Rental ID     : " + activeRental.getRentalId());
                    System.out.println("Customer      : " + activeRental.getCustomer().getName());
                    System.out.println("Bike          : " + activeRental.getBike().getBrand()
                            + " (" + activeRental.getBike().getBikeId() + ")");
                    System.out.println("Booked Hours  : " + activeRental.getBookedHours());
                    System.out.println("Actual Hours  : " + actualHours);

                    if (activeRental.getLateFee() > 0) {
                        int extraHours = actualHours - activeRental.getBookedHours();
                        System.out.println("Extra Hours   : " + extraHours);
                        System.out.println("Late Fee      : PHP " + activeRental.getLateFee()
                                + " (1.5x rate for " + extraHours + " extra hour/s)");
                    } else {
                        System.out.println("Late Fee      : None");
                    }

                    System.out.println("Total Due     : PHP " + activeRental.getTotalCost());
                    System.out.println("Status        : Bike returned successfully!");
                    System.out.println("==========================\n");

                    if (activeRental.getLateFee() > 0) {
                        Payment.Method lateMethod = getPaymentMethod();
                        Payment latePayment = new Payment(
                                "LP-" + activeRental.getRentalId(),
                                activeRental.getLateFee(),
                                lateMethod
                        );
                        latePayment.processPayment();
                    }
                    break;

                case 5:
                    service.displayActiveRentals();
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    return;
            }
        }
    }
}