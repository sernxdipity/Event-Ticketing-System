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
            System.out.println("9. Return to Main Menu");

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
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 9);
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

        System.out.println("1. Update Event Title");
        System.out.println("2. Update Ticket Price");
        System.out.println("3. Update Event Schedule");

        int choice = readInt("Enter your choice: ");

        switch (choice) {

            case 1:
                System.out.print("Enter New Title: ");
                String title = scanner.nextLine();

                if (title.trim().isEmpty()) {
                    System.out.println("Title cannot be empty.");
                } else {
                    admin.updateEventTitle(eventId, title);
                    System.out.println("Event title updated!");
                }
                break;

            case 2:
                double price = readDouble("Enter New Price (RM): ");

                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                } else {
                    admin.updateEventPrice(eventId, price);
                    System.out.println("Ticket price updated!");
                }
                break;

            case 3:
                LocalDateTime newDateTime = readEventDateTime();

                if (newDateTime != null) {
                    event.setEventDateTime(newDateTime);
                    System.out.println("Event schedule updated!");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}