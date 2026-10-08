import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Event {

    // Private attributes (Encapsulation)
    private int eventId;
    private String title;
    private Venue venue;
    private double baseTicketPrice;
    private int maxCapacity;
    private int availableSeats;
    // Event scheduling
    private LocalDateTime eventDateTime;

    // Constructor to initialise event details
    public Event(int eventId, String title, Venue venue,
                 double baseTicketPrice, int maxCapacity) {

        if (venue == null || title == null || title.isEmpty()
                || baseTicketPrice < 0 || maxCapacity <= 0
                || maxCapacity > venue.getCapacity()) {
            throw new IllegalArgumentException("Invalid event details.");
        }

        this.eventId = eventId;
        this.title = title;
        this.venue = venue;
        this.baseTicketPrice = baseTicketPrice;
        this.maxCapacity = maxCapacity;
        this.availableSeats = maxCapacity;
    }

    // Getter methods
    public int getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public Venue getVenue() {
        return venue;
    }

    public double getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Setter methods
    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        } else {
            System.out.println("Event title cannot be empty.");
        }
    }

    public void setBaseTicketPrice(double price) {
        if (price >= 0) {
            this.baseTicketPrice = price;
        } else {
            System.out.println("Ticket price cannot be negative.");
        }
    }


    // Get event date and time
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    // Update event schedule
    public void setEventDateTime(LocalDateTime dateTime) {
        if (dateTime == null || !dateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "Event date and time must be in the future."
            );
        }

        this.eventDateTime = dateTime;
    }

    // Check if enough seats are available
    public boolean hasAvailableSeats(int quantity) {
        return quantity > 0 && availableSeats >= quantity;
    }

    // Reserve seats and update availability
    public boolean reserveSeats(int quantity) {
        if (hasAvailableSeats(quantity)) {
            availableSeats -= quantity;
            return true;
        }

        return false;
    }

    // Release seats after a cancellation
    public void releaseSeats(int quantity) {
        if (quantity > 0 && availableSeats + quantity <= maxCapacity) {
            availableSeats += quantity;
        } else {
            System.out.println("Invalid number of seats to release.");
        }
    }

    
    // Display event information
    public void displayEventDetails() {
        System.out.println("\n===== EVENT DETAILS =====");
        System.out.println("Event ID: " + eventId);
        System.out.println("Title: " + title);
        System.out.println("Venue: " + venue.getVenueName());

        // Display event schedule
        if (eventDateTime != null) {
            DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            System.out.println("Date & Time: " +
                eventDateTime.format(formatter));
        } else {
            System.out.println("Date & Time: Not Scheduled");
        }

        System.out.printf("Base Ticket Price: RM %.2f%n",
                          baseTicketPrice);

        System.out.println("Maximum Capacity: " + maxCapacity);
        System.out.println("Available Seats: " + availableSeats);
    }


    // Abstract method implemented by event subclasses
    public abstract String getEventType();
}