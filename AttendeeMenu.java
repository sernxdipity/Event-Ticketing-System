import java.util.Scanner;
import java.util.ArrayList;

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
                    System.out.println("View My Reservations - Coming Soon");
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
    public void viewAvailableEvents() {

        System.out.println("\n===== AVAILABLE EVENTS =====");

        if (events.isEmpty()) {
            System.out.println("No events are currently available.");
            return;
        }

        for (Event event : events) {
            System.out.println("---------------------------");
            System.out.println("Event ID: " + event.getEventId());
            System.out.println("Title: " + event.getTitle());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Venue: " + event.getVenue().getVenueName());
            System.out.printf("Standard Price: RM %.2f%n",
                    event.getBaseTicketPrice());
            System.out.println("Available Seats: " + event.getAvailableSeats());
        }
    }

    //case2
    public void viewAvailableSeats() {

        System.out.println("\n===== VIEW AVAILABLE SEATS =====");

        if (events.isEmpty()) {
            System.out.println("No events are currently available.");
            return;
        }

        // Show events first
        viewAvailableEvents();

        System.out.print("\nEnter Event ID: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid Event ID.");
            scanner.next();
            return;
        }

        int eventId = scanner.nextInt();

        // Find the inventory belonging to the selected event
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

        // Check whether events are available
        if (events.isEmpty()) {
            System.out.println("No events available for booking.");
            return;
        }

        // Display available events
        viewAvailableEvents();

        // Ask attendee to select an event
        System.out.print("\nEnter Event ID: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            return;
        }

        int eventId = scanner.nextInt();

        // Find the selected event
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

        
        // Find the ticket inventory for the selected event
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

        // Display seats
        selectedInventory.displaySeats();

        // Ask attendee to select a seat
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

        // Generate a reservation ID
        int reservationId = reservationManager.generateReservationId();

        // Lock the selected seat
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

        System.out.print("Enter your name: ");
        scanner.nextLine(); // Clear the remaining newline
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Validate attendee details
        if (name.trim().isEmpty() || email.trim().isEmpty()) {
            System.out.println("Name and email cannot be empty.");
            selectedInventory.releaseSeat(seatId, reservationId);
            return;
        }

        // Create attendee
        Attendee attendee = new Attendee(
            reservationId,
            name,
            email
        );

        // Create reservation
        TicketReservation reservation = new TicketReservation(
            reservationId,
            attendee,
            selectedEvent,
            selectedSeat
        );

        // Store reservation
        reservations.add(reservation);

        System.out.println("\n===== RESERVATION CREATED =====");
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Attendee: " + reservation.getAttendee().getName());
        System.out.println("Event: " + reservation.getEvent().getTitle());
        System.out.println("Seat: " + reservation.getSeat().getSeatId());
        System.out.println("Status: " + reservation.getStatus());


    }



}
