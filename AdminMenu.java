import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AdminMenu {

    private Admin admin;
    private Scanner scanner;

    // Constructor
    public AdminMenu(Admin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner;
    }

    // Read an integer safely
    private int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Read a decimal number safely
    private double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
    }


    // Read and validate event schedule
    private LocalDateTime readEventDateTime() {

        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter Event Time (HH:MM): ");
        String time = scanner.nextLine();

        try {
            LocalDateTime dateTime =
                LocalDateTime.parse(date + "T" + time);

            if (!dateTime.isAfter(LocalDateTime.now())) {
                System.out.println(
                    "Event must be scheduled in the future."
                );
                return null;
            }

            return dateTime;

        } catch (DateTimeParseException e) {
            System.out.println(
                "Invalid date or time. Use YYYY-MM-DD and HH:MM."
            );
            return null;
        }
    }


    // Display admin menu
    public void displayMenu() {

        int choice;

        do {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Event");
            System.out.println("2. View All Events");
            System.out.println("3. Search Event");
            System.out.println("4. Update Event");
            System.out.println("5. Add Venue");
            System.out.println("6. View Venues");
            System.out.println("7. Update Venue");
            System.out.println("8. Search Venue");
            System.out.println("9. View Upcoming Events");
            System.out.println("10. Return to Main Menu");

            choice = readInt("Enter your choice: ");

            
            switch (choice) {
                case 1:
                    addEvent();
                    break;

                case 2:
                    admin.viewEvents();
                    break;

                case 3:
                    searchEvent();
                    break;

                case 4:
                    updateEvent();
                    break;

                case 5:
                    addVenue();
                    break;

                case 6:
                    admin.viewVenues();
                    break;

                case 7:
                    updateVenue();
                    break;

                case 8:
                    searchVenue();
                    break;

                case 9:
                    admin.viewUpcomingEvents();
                    break;
                
                case 10:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 10);
    }

    // Add a new venue
    private void addVenue() {

        System.out.println("\n===== ADD VENUE =====");

        int venueId = readInt("Enter Venue ID: ");

        if (admin.searchVenue(venueId) != null) {
            System.out.println("Venue ID already exists.");
            return;
        }

        System.out.print("Enter Venue Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        int capacity = readInt("Enter Venue Capacity: ");

        if (name.trim().isEmpty() || location.trim().isEmpty()
                || capacity <= 0) {
            System.out.println("Invalid venue details.");
            return;
        }

        Venue venue = new Venue(venueId, name, location, capacity);
        admin.addVenue(venue);
    }


    // Update venue details
    private void updateVenue() {

        System.out.println("\n===== UPDATE VENUE =====");

        int venueId = readInt("Enter Venue ID: ");
        Venue venue = admin.searchVenue(venueId);

        if (venue == null) {
            System.out.println("Venue not found.");
            return;
        }

        System.out.println("\n1. Update Venue Name");
        System.out.println("2. Update Venue Location");
        System.out.println("3. Update Venue Capacity");

        int choice = readInt("Enter your choice: ");

        switch (choice) {

            case 1:
                System.out.print("Enter New Venue Name: ");
                String newName = scanner.nextLine();

                if (newName.trim().isEmpty()) {
                    System.out.println("Venue name cannot be empty.");
                } else {
                    admin.updateVenueName(venueId, newName);
                    System.out.println("Venue name updated!");
                }
                break;

            case 2:
                System.out.print("Enter New Venue Location: ");
                String newLocation = scanner.nextLine();

                if (newLocation.trim().isEmpty()) {
                    System.out.println("Venue location cannot be empty.");
                } else {
                    admin.updateVenueLocation(venueId, newLocation);
                    System.out.println("Venue location updated!");
                }
                break;

            case 3:
                int newCapacity = readInt("Enter New Capacity: ");
                admin.updateVenueCapacity(venueId, newCapacity);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }


    // Search venue by ID
    private void searchVenue() {

        System.out.println("\n===== SEARCH VENUE =====");

        int venueId = readInt("Enter Venue ID: ");
        Venue venue = admin.searchVenue(venueId);

        if (venue != null) {
            venue.displayVenueDetails();
        } else {
            System.out.println("Venue not found.");
        }
    }

    // Add a concert or conference
    private void addEvent() {

        System.out.println("\n===== ADD EVENT =====");
        System.out.println("1. Concert");
        System.out.println("2. Conference");

        int type = readInt("Select Event Type: ");

        if (type != 1 && type != 2) {
            System.out.println("Invalid event type.");
            return;
        }

        int eventId = readInt("Enter Event ID: ");

        if (admin.searchEvent(eventId) != null) {
            System.out.println("Event ID already exists.");
            return;
        }

        System.out.print("Enter Event Title: ");
        String title = scanner.nextLine();

        admin.viewVenues();

        int venueId = readInt("Enter Venue ID: ");
        Venue venue = admin.searchVenue(venueId);

        if (venue == null) {
            System.out.println("Venue not found. Please add a venue first.");
            return;
        }

        double price = readDouble("Enter Base Ticket Price (RM): ");
        int capacity = readInt("Enter Event Capacity: ");

        if (title.trim().isEmpty() || price < 0 ||
                capacity <= 0 || capacity > venue.getCapacity()) {
            System.out.println("Invalid event details.");
            return;
        }

        Event event;

        if (type == 1) {

            System.out.print("Enter Artist Name: ");
            String artist = scanner.nextLine();

            System.out.print("VIP Tickets Available? (yes/no): ");
            String vipAnswer = scanner.nextLine();

            if (artist.trim().isEmpty() ||
                    (!vipAnswer.equalsIgnoreCase("yes") &&
                     !vipAnswer.equalsIgnoreCase("no"))) {
                System.out.println("Invalid concert details.");
                return;
            }

            boolean vip = vipAnswer.equalsIgnoreCase("yes");

            event = new ConcertEvent(
                eventId, title, venue, price,
                capacity, artist, vip
            );

        } else {

            System.out.print("Enter Speaker Name: ");
            String speaker = scanner.nextLine();

            System.out.print("Enter Conference Topic: ");
            String topic = scanner.nextLine();

            if (speaker.trim().isEmpty() || topic.trim().isEmpty()) {
                System.out.println("Invalid conference details.");
                return;
            }

            event = new ConferenceEvent(
                eventId, title, venue, price,
                capacity, speaker, topic
            );
        }

        
        // Configure ticket allocation
        System.out.println("\n===== TICKET ALLOCATION =====");

        int standardSeats;
        int vipSeats = 0;
        double vipPrice = 0;

        boolean supportsVip = event instanceof ConcertEvent &&
            ((ConcertEvent) event).isVipAvailable();

        if (supportsVip) {
            standardSeats = readInt("Enter Standard Seats: ");
            vipSeats = readInt("Enter VIP Seats: ");
            vipPrice = readDouble("Enter VIP Price (RM): ");
        } else {
            standardSeats = capacity;
        }

        boolean allocationValid = event.configureTicketTiers(
            standardSeats, vipSeats, vipPrice
        );

        if (!allocationValid) {
            System.out.println("Event was not added.");
            return;
        }

        // Request event schedule
        LocalDateTime dateTime = readEventDateTime();

        if (dateTime == null) {
            System.out.println("Event was not added.");
            return;
        }

        // Assign event schedule
        event.setEventDateTime(dateTime);

        // Add event to the system
        admin.addEvent(event);
    }

    // Search for an event
    private void searchEvent() {

        System.out.println("\n===== SEARCH EVENT =====");

        int eventId = readInt("Enter Event ID: ");
        Event event = admin.searchEvent(eventId);

        if (event != null) {
            event.displayEventDetails();
            System.out.println("Event Type: " + event.getEventType());
        } else {
            System.out.println("Event not found.");
        }
    }

    
    
    // Update event information
    private void updateEvent() {

        System.out.println("\n===== UPDATE EVENT =====");

        int eventId = readInt("Enter Event ID: ");
        Event event = admin.searchEvent(eventId);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("\n1. Update Event Title");
        System.out.println("2. Update Standard Ticket Price");
        System.out.println("3. Update VIP Ticket Price");
        System.out.println("4. Update Event Schedule");
        System.out.println("5. Update Ticket Allocation");

        int choice = readInt("Enter your choice: ");

        switch (choice) {

            case 1: {
                System.out.print("Enter New Title: ");
                String title = scanner.nextLine();

                if (title.trim().isEmpty()) {
                    System.out.println("Title cannot be empty.");
                } else {
                    admin.updateEventTitle(eventId, title);
                    System.out.println("Event title updated!");
                }
                break;
            }

            case 2: {
                double price = readDouble(
                    "Enter New Standard Ticket Price (RM): "
                );

                if (!Double.isFinite(price) || price <= 0) {
                    System.out.println(
                        "Ticket price must be greater than zero."
                    );
                } else {
                    admin.updateEventPrice(eventId, price);
                    System.out.println(
                        "Standard ticket price updated!"
                    );
                }
                break;
            }

            case 3: {
                boolean supportsVip =
                    event instanceof ConcertEvent &&
                    ((ConcertEvent) event).isVipAvailable();

                if (!supportsVip) {
                    System.out.println(
                        "This event does not support VIP tickets."
                    );
                    break;
                }

                double vipPrice = readDouble(
                    "Enter New VIP Ticket Price (RM): "
                );

                if (event.setVipTicketPrice(vipPrice)) {
                    System.out.println(
                        "VIP ticket price updated successfully!"
                    );
                }
                break;
            }

            case 4: {
                LocalDateTime newDateTime = readEventDateTime();

                if (newDateTime != null) {
                    event.setEventDateTime(newDateTime);
                    System.out.println("Event schedule updated!");
                }
                break;
            }

            case 5: {
                updateTicketAllocation(event);
                break;
            }

            default:
                System.out.println("Invalid choice.");
        }
    }



    // Update ticket allocation for an existing event
    private void updateTicketAllocation(Event event) {

        System.out.println("\n===== UPDATE TICKET ALLOCATION =====");

        System.out.println("Event: " + event.getTitle());
        System.out.println("Maximum Capacity: " + event.getMaxCapacity());
        System.out.println("Current Standard Seats: " +
                           event.getStandardCapacity());
        System.out.println("Current VIP Seats: " +
                           event.getVipCapacity());

        // Check whether this concert supports VIP tickets
        boolean supportsVip = event instanceof ConcertEvent &&
            ((ConcertEvent) event).isVipAvailable();

        if (!supportsVip) {
            System.out.println(
                "This event only supports Standard tickets."
            );
            return;
        }

        int standardSeats = readInt("Enter New Standard Seats: ");
        int vipSeats = readInt("Enter New VIP Seats: ");

        // Keep the existing VIP price
        double vipPrice = event.getVipTicketPrice();

        if (vipSeats > 0 && vipPrice <= 0) {
            System.out.println(
                "Please set a valid VIP ticket price first using Update Event > Option 3."
            );
            return;
        }

        // Validate and update the ticket allocation
        boolean updated = event.configureTicketTiers(
            standardSeats, vipSeats, vipPrice
        );

        if (updated) {
            System.out.println("New ticket allocations:");
            System.out.println("Standard: " +
                               event.getStandardCapacity());
            System.out.println("VIP: " +
                               event.getVipCapacity());

            System.out.printf("VIP Price: RM %.2f%n",
                              event.getVipTicketPrice());
        } else {
            System.out.println("Ticket allocation was not updated.");
        }
    }

}