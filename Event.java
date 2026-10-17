import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Event {

    // Private attributes (Encapsulation)
    private int eventId;
    private String title;
    private Venue venue;
    private double baseTicketPrice;
    private int maxCapacity;
    private int standardCapacity;
    private int vipCapacity;
    private double vipTicketPrice;
    // Event scheduling
    private LocalDateTime eventDateTime;
    private TicketInventory ticketInventory;

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

        if (ticketInventory != null) {
            return ticketInventory.getAvailableSeatCount();
        }

        // Before inventory creation, all allocated seats are available
        return maxCapacity;
    }


    // Associate this event with its ticket inventory
    public void setTicketInventory(TicketInventory inventory) {

        if (inventory == null || inventory.getEvent() != this) {
            throw new IllegalArgumentException(
                "Inventory must belong to this event."
            );
        }

        if (ticketInventory != null &&
            ticketInventory != inventory) {

            throw new IllegalStateException(
                "This event already has a ticket inventory."
            );
        }

        this.ticketInventory = inventory;
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



    // Configure or update ticket tier allocations
    public boolean configureTicketTiers(int standardSeats,
                                        int vipSeats,
                                        double vipPrice) {

        // Validate seat quantities
        if (standardSeats < 0 || vipSeats < 0) {
            System.out.println("Seat quantities cannot be negative.");
            return false;
        }

        // Check that total seats match event capacity
        if (standardSeats + vipSeats != maxCapacity) {
            System.out.println(
                "Total allocated seats must equal event capacity."
            );
            return false;
        }

        // Validate VIP ticket price
        if (vipPrice < 0) {
            System.out.println("VIP ticket price cannot be negative.");
            return false;
        }

        
        // Check whether seat quantities are being changed
        boolean allocationChanged =
            standardCapacity != standardSeats ||
            vipCapacity != vipSeats;

        // Prevent changing seats after reservation activity
        if (allocationChanged &&
            ticketInventory != null &&
            ticketInventory.hasBookingActivity()) {

            System.out.println(
                "Cannot change ticket allocation after " +
                "reservation activity has started."
            );

            return false;
        }

        // Store ticket allocations
        standardCapacity = standardSeats;
        vipCapacity = vipSeats;
        vipTicketPrice = vipPrice;

        // Refresh actual seat inventory when allocation changes
        if (allocationChanged && ticketInventory != null) {
            ticketInventory.refreshSeatsFromEvent();
        }

        System.out.println("Ticket allocation updated successfully!");
        return true;
    }

    // Getter methods for ticket tiers
    public int getStandardCapacity() {
        return standardCapacity;
    }

    public int getVipCapacity() {
        return vipCapacity;
    }

    public double getVipTicketPrice() {
        return vipTicketPrice;
    }
     
    // Update VIP ticket price
    public boolean setVipTicketPrice(double price) {

        if (!Double.isFinite(price) || price <= 0) {
            System.out.println(
                "VIP ticket price must be greater than zero."
            );
            return false;
        }

        this.vipTicketPrice = price;

        return true;
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
        return quantity > 0 && getAvailableSeats() >= quantity;
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
        System.out.println("Available Seats: " + getAvailableSeats());

        // Display ticket tier information
        System.out.println("\n===== TICKET ALLOCATION =====");
        System.out.println("Standard Seats: " + standardCapacity);
        System.out.println("VIP Seats: " + vipCapacity);

        System.out.printf("Standard Price: RM %.2f%n",
                          baseTicketPrice);

        if (vipCapacity > 0) {
            System.out.printf("VIP Price: RM %.2f%n",
                              vipTicketPrice);
        }

    }

    // Abstract method implemented by event subclasses
    public abstract String getEventType();

}