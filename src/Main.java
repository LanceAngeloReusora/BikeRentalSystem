import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

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
                System.out.println("  [!] Invalid input. Numbers only please.\n");
            }
        }
    }

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
                System.out.println("  [!] Invalid input. Numbers only please.\n");
            }
        }
    }

    public static Payment.Method getPaymentMethod() {

        System.out.println("  Select Payment Method:");
        System.out.println("    1. Cash");
        System.out.println("    2. GCash");
        System.out.println("    3. Maya");
        System.out.println("    4. Credit / Debit Card");
        System.out.println("    5. Bank Transfer");

        while (true) {

            System.out.print("  Choose (1-5): ");
            String input = sc.nextLine().trim();

            switch (input) {

                case "1":
                    return Payment.Method.CASH;

                case "2":
                    return Payment.Method.GCASH;

                case "3":
                    return Payment.Method.MAYA;

                case "4":
                    return Payment.Method.CREDIT_DEBIT;

                case "5":
                    return Payment.Method.BANK_TRANSFER;

                default:
                    System.out.println("  [!] Please enter a number from 1 to 5.\n");
            }
        }
    }

    public static Bike.Condition getBikeCondition() {

        System.out.println("  Bike Condition:");
        System.out.println("    1. Good");
        System.out.println("    2. Damaged");

        while (true) {

            System.out.print("  Select condition (1-2): ");
            String input = sc.nextLine().trim();

            switch (input) {

                case "1":
                    return Bike.Condition.GOOD;

                case "2":
                    return Bike.Condition.DAMAGED;

                default:
                    System.out.println("  [!] Please enter 1 or 2 only.\n");
            }
        }
    }

    public static void main(String[] args) {

        BikeRentalService service = new BikeRentalService();

        service.addBike(new MountainBike("M1", "Trek"));
        service.addBike(new BMXBike("B1", "Haro"));
        service.addBike(new RoadBike("R1", "Giant"));
        service.addBike(new ElectricBike("E1", "Xiaomi"));
        service.addBike(new JapaneseBike("J1", "Bridgestone"));

        while (true) {

            System.out.println("===== BIKE RENTAL SYSTEM =====");
            System.out.println("1.  Register Customer");
            System.out.println("2.  View Available Bikes");
            System.out.println("3.  Rent Bike");
            System.out.println("4.  Return Bike");
            System.out.println("5.  View Active Rentals");
            System.out.println("6.  View Rental History");
            System.out.println("7.  Make Reservation");
            System.out.println("8.  Cancel Reservation");
            System.out.println("9.  Confirm Reservation");
            System.out.println("10. View Active Reservations");
            System.out.println("11. Exit");

            int choice = getMenuChoice(1, 11);

            switch (choice) {

                case 1:

                    String cid = getStringInput("Enter Customer ID: ");

                    if (service.findCustomer(cid) != null) {
                        System.out.println("  [!] Customer ID already exists. Use a different ID.\n");
                        break;
                    }

                    String name = getStringInput("Enter Name: ");

                    String contact;

                    while (true) {

                        contact = getStringInput("Enter Contact Number: ");

                        if (contact.matches("\\d+")) {
                            break;
                        }

                        System.out.println("  [!] Contact must contain numbers only.\n");
                    }

                    double discount = 0;

                    System.out.print("Enter Promo Code (Press Enter to skip): ");
                    String promo = sc.nextLine().trim();

                    if (promo.equalsIgnoreCase("WELCOME")) {
                        discount = 0.05;
                        System.out.println("  [✓] Promo code applied! 5% discount granted.");
                    } else if (!promo.isEmpty()) {
                        System.out.println("  [!] Invalid promo code. No discount applied.");
                    }

                    service.registerCustomer(
                            new Customer(cid, name, contact, discount)
                    );

                    System.out.println("  [✓] Customer registered successfully!\n");

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

                    Payment rentPayment = new Payment(
                            "P-" + rentalId,
                            rental.getTotalCost(),
                            rentMethod
                    );

                    rentPayment.processPayment();

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

                            System.out.println(
                                    "  [!] Actual hours cannot be less than booked hours ("
                                            + activeRental.getBookedHours() + ").\n"
                            );

                        } else {
                            break;
                        }
                    }

                    Bike.Condition condition = getBikeCondition();

                    activeRental.returnBike(actualHours, condition);

                    System.out.println("\n===== RETURN SUMMARY =====");

                    System.out.println("Rental ID     : " + activeRental.getRentalId());

                    System.out.println("Customer      : "
                            + activeRental.getCustomer().getName());

                    System.out.println("Bike          : "
                            + activeRental.getBike().getBrand()
                            + " (" + activeRental.getBike().getBikeId() + ")");

                    System.out.println("Booked Hours  : "
                            + activeRental.getBookedHours());

                    System.out.println("Actual Hours  : " + actualHours);

                    System.out.println("Bike Condition: " + condition);

                    System.out.println("Base Cost     : PHP "
                            + activeRental.getBaseCost());

                    if (activeRental.getLateFee() > 0) {

                        int extraHours =
                                actualHours - activeRental.getBookedHours();

                        System.out.println("Late Fee      : PHP "
                                + activeRental.getLateFee()
                                + " (" + extraHours
                                + " extra hour/s at 1.5x rate)");
                    }

                    if (activeRental.getDamagePenalty() > 0) {

                        System.out.println("Damage Penalty: PHP "
                                + activeRental.getDamagePenalty());

                        System.out.println(
                                "  [!] Bike flagged for maintenance."
                        );
                    }

                    System.out.println("Total Due     : PHP "
                            + activeRental.getTotalCost());

                    System.out.println("==========================\n");

                    double extraCharges =
                            activeRental.getLateFee()
                                    + activeRental.getDamagePenalty();

                    if (extraCharges > 0) {

                        System.out.println(
                                "  Additional charges: PHP "
                                        + extraCharges
                        );

                        Payment.Method extraMethod =
                                getPaymentMethod();

                        Payment extraPayment = new Payment(
                                "EP-" + activeRental.getRentalId(),
                                extraCharges,
                                extraMethod
                        );

                        extraPayment.processPayment();

                    } else {

                        System.out.println(
                                "  [✓] Bike returned successfully. No extra charges.\n"
                        );
                    }

                    break;

                case 5:
                    service.displayActiveRentals();
                    break;

                case 6:

                    String historyId =
                            getStringInput("Enter Customer ID: ");

                    service.displayRentalHistory(historyId);

                    break;

                case 7:

                    String resCustId =
                            getStringInput("Enter Customer ID: ");

                    Customer resCust =
                            service.findCustomer(resCustId);

                    if (resCust == null) {

                        System.out.println(
                                "  [!] Customer not found. Please register first.\n"
                        );

                        break;
                    }

                    String resBikeId =
                            getStringInput("Enter Bike ID: ");

                    Bike resBike =
                            service.findBike(resBikeId);

                    if (resBike == null) {

                        System.out.println(
                                "  [!] Bike not available or does not exist.\n"
                        );

                        break;
                    }

                    int resHours =
                            getIntInput("Enter Reserved Hours: ");

                    String reservationId =
                            "RES" + resCustId + "-" + resBikeId;

                    Reservation reservation = new Reservation(
                            reservationId,
                            resCust,
                            resBike,
                            LocalDateTime.now(),
                            resHours
                    );

                    service.addReservation(reservation);

                    reservation.displayReservation();

                    System.out.println(
                            "  [✓] Reservation made successfully!\n"
                    );

                    break;

                case 8:

                    String cancelId =
                            getStringInput("Enter Reservation ID to cancel: ");

                    Reservation toCancel =
                            service.findActiveReservation(cancelId);

                    if (toCancel == null) {

                        System.out.println(
                                "  [!] No active reservation found with that ID.\n"
                        );

                        break;
                    }

                    toCancel.cancelReservation();

                    System.out.println(
                            "  [✓] Reservation " + cancelId + " has been cancelled.\n"
                    );

                    break;

                case 9:

                    String confirmId =
                            getStringInput("Enter Reservation ID to confirm: ");

                    Reservation toConfirm =
                            service.findActiveReservation(confirmId);

                    if (toConfirm == null) {

                        System.out.println(
                                "  [!] No active reservation found with that ID.\n"
                        );

                        break;
                    }

                    Rental confirmedRental =
                            toConfirm.confirmReservation();

                    if (confirmedRental != null) {

                        service.addRental(confirmedRental);

                        Receipt.printReceipt(confirmedRental);

                        Payment.Method confirmMethod =
                                getPaymentMethod();

                        Payment confirmPayment = new Payment(
                                "P-" + confirmedRental.getRentalId(),
                                confirmedRental.getTotalCost(),
                                confirmMethod
                        );

                        confirmPayment.processPayment();
                    }

                    break;

                case 10:
                    service.displayActiveReservations();
                    break;

                case 11:
                    System.out.println(
                            "Thank you for using the Bike Rental System!"
                    );
                    return;
            }
        }
    }
}