import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class AttendeeMenu {
    private Scanner scanner;
    private ArrayList<Event> events;
    private ArrayList<TicketInventory> inventories;
    private ArrayList<TicketReservation> reservations;
    private ReservationManager reservationManager;

    //constructor
    public AttendeeMenu(ArrayList<Event> events, ArrayList<TicketInventory> inventories) {
        this.scanner = new Scanner(System.in);
        this.events = events;
        this.inventories = inventories;

        this.reservations = new ArrayList<>();
        this.reservationManager = new ReservationManager();
    }

    // Format event date and time
    private String formatEventSchedule(Event event) {

        if (event.getEventDateTime() == null) {
            return "Not Scheduled";
        }

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return event.getEventDateTime().format(formatter);
    }

    //display attendee menu
    public void displayMenu() {

        int choice;

        do {
            System.out.println("\n===== ATTENDEE MENU =====");
            System.out.println("1. View Available Events");
            System.out.println("2. View Available Seats");
            System.out.println("3. Make Reservation");
            System.out.println("4. View My Reservations");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                choice = 0;
            }

            switch (choice) {
                case 1:
                    viewAvailableEvents();
                    break;
                case 2:
                    viewAvailableSeats();
                    break;

                case 3:
                    makeReservation();
                    break;

                case 4:
                    viewMyReservations();
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }

    //case1
    
    // Display available events with schedules and ticket prices
    public void viewAvailableEvents() {

        System.out.println("\n===== AVAILABLE EVENTS =====");

        if (events.isEmpty()) {
            System.out.println("No events are currently available.");
            return;
        }

        for (Event event : events) {

            System.out.println("\n---------------------------");
            System.out.println("Event ID: " + event.getEventId());
            System.out.println("Title: " + event.getTitle());
            System.out.println("Type: " + event.getEventType());

            System.out.println("Venue: " +
                event.getVenue().getVenueName());

            // Display event date and time
            System.out.println("Date & Time: " +
                formatEventSchedule(event));

            // Display ticket prices
            System.out.println("\n===== TICKET PRICES =====");

            System.out.printf("Standard: RM %.2f%n",
                event.getBaseTicketPrice());

            if (event.getVipCapacity() > 0) {
                System.out.printf("VIP: RM %.2f%n",
                    event.getVipTicketPrice());
            }

            System.out.println("Available Seats: " +
                event.getAvailableSeats());
        }
    }


    //case2
    public void viewAvailableSeats() {

        System.out.println("\n===== VIEW AVAILABLE SEATS =====");

        if (events.isEmpty()) {
            System.out.println("No events are currently available.");
            return;
        }

        //Show events first
        viewAvailableEvents();

        System.out.print("\nEnter Event ID: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid Event ID.");
            scanner.next();
            return;
        }

        int eventId = scanner.nextInt();

        //Find the inventory belonging to the selected event
        for (TicketInventory inventory : inventories) {

            if (inventory.getEvent().getEventId() == eventId) {

                System.out.println("\nEvent: " +
                    inventory.getEvent().getTitle());

                inventory.displaySeats();
                return;
            }
        }

        System.out.println("Event not found or no inventory available.");
    }

    //case3
    public void makeReservation() {

        System.out.println("\n===== MAKE RESERVATION =====");

        //Check whether events are available
        if (events.isEmpty()) {
            System.out.println("No events available for booking.");
            return;
        }

        //Display available events
        viewAvailableEvents();

        //Ask attendee to select an event
        System.out.print("\nEnter Event ID: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            return;
        }

        int eventId = scanner.nextInt();

        //Find the selected event
        Event selectedEvent = null;

        for (Event event : events) {
            if (event.getEventId() == eventId) {
                selectedEvent = event;
                break;
            }
        }

        // Validate selected event
        if (selectedEvent == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("\nEvent selected successfully!");
        System.out.println("Event: " + selectedEvent.getTitle());
        System.out.println("Event Type: " + selectedEvent.getEventType());

        System.out.println("Venue: " +
            selectedEvent.getVenue().getVenueName());

        System.out.println("Date & Time: " +
            formatEventSchedule(selectedEvent));

        System.out.printf("Standard Ticket: RM %.2f%n",
            selectedEvent.getBaseTicketPrice());

        if (selectedEvent.getVipCapacity() > 0) {
            System.out.printf("VIP Ticket: RM %.2f%n",
                selectedEvent.getVipTicketPrice());
        }

        
        //Find the ticket inventory for the selected event
        TicketInventory selectedInventory = null;

        for (TicketInventory inventory : inventories) {
            if (inventory.getEvent() == selectedEvent) {
                selectedInventory = inventory;
                break;
            }
        }

        if (selectedInventory == null) {
            System.out.println("No ticket inventory found for this event.");
            return;
        }

        //Display seats
        selectedInventory.displaySeats();

        //Ask attendee to select a seat
        System.out.print("\nEnter Seat ID (e.g. S001 or V001): ");
        String seatId = scanner.next().toUpperCase();

        Seat selectedSeat = selectedInventory.findSeat(seatId);

        if (selectedSeat == null) {
            System.out.println("Seat not found.");
            return;
        }

        if (selectedSeat.getStatus() != SeatStatus.AVAILABLE) {
            System.out.println("Seat is not available.");
            return;
        }

        //Generate a reservation ID
        int reservationId = reservationManager.generateReservationId();

        //Lock the selected seat
        if (!selectedInventory.lockSeat(seatId, reservationId)) {
            System.out.println("Unable to lock seat.");
            return;
        }

        System.out.println("\nSeat locked successfully!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Selected Seat: " + selectedSeat.getSeatId());
        System.out.println("Ticket Type: " + selectedSeat.getTicketType());
        System.out.println("Seat Status: " + selectedSeat.getStatus());

        
    // Ask for attendee details
    System.out.println("\n===== ATTENDEE DETAILS =====");

    // Clear the newline remaining after seat selection
    scanner.nextLine();

    // Validate attendee name
    String name;

    while (true) {
        System.out.print("Enter your name: ");
        name = scanner.nextLine().trim();

        if (name.matches("[\\p{L}]+(?:[ .'-][\\p{L}]+)*")) {
            break;
        }

        System.out.println(
            "Invalid name. Please enter a name using letters."
        );
    }

    // Validate attendee email
    String email;

    while (true) {
        System.out.print("Enter your email: ");
        email = scanner.nextLine().trim();

        if (email.matches(
                "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            break;
        }

        System.out.println(
            "Invalid email. Example: example@gmail.com"
        );
    }


        //Create attendee
        Attendee attendee = new Attendee(
            reservationId,
            name,
            email
        );

        //Create reservation
        TicketReservation reservation = new TicketReservation(
            reservationId,
            attendee,
            selectedEvent,
            selectedSeat
        );

        //Store reservation
        reservations.add(reservation);

        System.out.println("\n===== RESERVATION CREATED =====");
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Attendee: " + reservation.getAttendee().getName());
        System.out.println("Event: " + reservation.getEvent().getTitle());
        System.out.println("Seat: " + reservation.getSeat().getSeatId());
        System.out.println("Status: " + reservation.getStatus());

        //Payment process
        System.out.println("\n==== PAYMENT ====");

        
        // Retrieve pricing from the reservation
        double amount = reservation.getFinalAmount();

        System.out.println("\n===== PAYMENT SUMMARY =====");

        System.out.printf("Original Ticket Price: RM %.2f%n",
            reservation.getOriginalPrice());

        if (reservation.getDiscountAmount() > 0) {

            System.out.println("Early-Bird Discount: 10%");

            System.out.printf("Discount Amount: -RM %.2f%n",
                reservation.getDiscountAmount());

        } else {
            System.out.println(
                "Early-Bird Discount: Not Applicable"
            );
        }

        System.out.println("----------------------------------");

        System.out.printf("Total Amount: RM %.2f%n", amount);


        
    // Display available payment methods
    System.out.println("\nSelect Payment Method:");
    System.out.println("1. Credit/Debit Card");
    System.out.println("2. Online Banking");

    String paymentMethod;

    // Validate payment method selection
    while (true) {

        System.out.print("Enter your choice (1-2): ");
        String paymentChoice = scanner.nextLine().trim();

        if (paymentChoice.equals("1")) {
            paymentMethod = "Credit/Debit Card";
            break;

        } else if (paymentChoice.equals("2")) {
            paymentMethod = "Online Banking";
            break;

        } else {
            System.out.println(
                "Invalid payment method. Please select 1 or 2."
            );
        }
    }


        //create payment
        Payment payment = new Payment(reservationId, reservation, amount, paymentMethod);

        
        // Simulate payment result
        String paymentChoice;

        while (true) {
            System.out.print("Simulate successful payment? (Y/N): ");
            paymentChoice = scanner.nextLine().trim();

            if (paymentChoice.equalsIgnoreCase("Y") ||
                paymentChoice.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println(
                "Invalid input. Please enter Y or N."
            );
        }

        boolean paymentSuccess =
            paymentChoice.equalsIgnoreCase("Y");

        if (!payment.processPayment(paymentSuccess)) {
            System.out.println("Payment unsuccessful.");
            reservation.cancelReservation();
            return;
        }

        
        // Complete booking after successful payment
        if (!reservation.completeBooking(selectedInventory, payment)) {

            System.out.println(
                "\nBooking could not be completed."
            );

            System.out.println(
                "Initiating automatic payment reversal..."
            );

            // Restore seat and reverse simulated payment
            boolean reversed = reservation.rollbackFailedBooking(
                selectedInventory, payment
            );

            if (reversed) {
                System.out.println(
                    "Payment reversal completed successfully."
                );

                System.out.println(
                    "Your reservation has been cancelled."
                );
            } else {
                System.out.println(
                    "Automatic recovery failed. Manual review required."
                );
            }

            return;
        }


        System.out.println("\n==== BOOKING SUCCESSFULL ====");
        System.out.println("Payment successful via " + paymentMethod + "!");
        System.out.printf("Amount Paid: RM %.2f%n", amount);
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Reservation Status: " + reservation.getStatus());
        System.out.println("Seat Status: " + selectedSeat.getStatus());

        
        // Retrieve digital ticket owned by the reservation
        System.out.println("\n===== YOUR DIGITAL TICKET =====");

        DigitalTicket digitalTicket =
            reservation.getDigitalTicket();

        if (digitalTicket != null) {
            digitalTicket.displayTicket();
        } else {
            System.out.println(
                "Digital ticket is not available."
            );
        }


    }

    //case4
    public void viewMyReservations() {
        System.out.println("\n==== MY RESERVATIONS ====");

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("Enter your email: ");
        String email = scanner.next().trim();

        boolean found = false;

        for (TicketReservation reservation : reservations) {
            if (reservation.getAttendee().getEmail().equalsIgnoreCase(email)) {

                found = true;
                
                System.out.println("\n--------------------------");
                System.out.println("Reservation ID: " + reservation.getReservationId());
                System.out.println("Event: " + reservation.getEvent().getTitle());
                System.out.println("Date & Time: " +
                    formatEventSchedule(reservation.getEvent()));
                System.out.println("Seat: " + reservation.getSeat().getSeatId());
                System.out.println("Ticket Type: " + reservation.getSeat().getTicketType());
                System.out.println("Status: " + reservation.getStatus());
            }
        }
        if (!found) {
            System.out.println("No reservations found for this email.");
        }
    }


}
