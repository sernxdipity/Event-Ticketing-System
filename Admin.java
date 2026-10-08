import java.util.ArrayList;

public class Admin {

    // Private attributes (Encapsulation)
    private int adminId;
    private String adminName;

    // Store events and venues in memory
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;

    // Constructor
    public Admin(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;

        events = new ArrayList<Event>();
        venues = new ArrayList<Venue>();
    }

    // Getter methods
    public int getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    // Add a new event
    public void addEvent(Event event) {

        if (event == null) {
            System.out.println("Invalid event.");
            return;
        }

        if (searchEvent(event.getEventId()) != null) {
            System.out.println("Event ID already exists.");
            return;
        }

        events.add(event);
        System.out.println("Event added successfully!");
    }

    // Display all events
    public void viewEvents() {

        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n===== ALL EVENTS =====");

        for (Event event : events) {
            event.displayEventDetails();
            System.out.println("Event Type: " + event.getEventType());
            System.out.println("-----------------------------");
        }
    }

    // Search for an event by ID
    public Event searchEvent(int eventId) {

        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event;
            }
        }

        return null;
    }

    // Update event title
    public void updateEventTitle(int eventId, String newTitle) {

        Event event = searchEvent(eventId);

        if (event != null) {
            event.setTitle(newTitle);
        } else {
            System.out.println("Event not found.");
        }
    }

    // Update event ticket price
    public void updateEventPrice(int eventId, double newPrice) {

        Event event = searchEvent(eventId);

        if (event != null) {
            event.setBaseTicketPrice(newPrice);
        } else {
            System.out.println("Event not found.");
        }
    }

    // Add a venue
    public void addVenue(Venue venue) {

        if (venue == null) {
            System.out.println("Invalid venue.");
            return;
        }

        if (searchVenue(venue.getVenueId()) != null) {
            System.out.println("Venue ID already exists.");
            return;
        }

        venues.add(venue);
        System.out.println("Venue added successfully!");
    }

    // Display all venues
    public void viewVenues() {

        if (venues.isEmpty()) {
            System.out.println("No venues available.");
            return;
        }

        System.out.println("\n===== ALL VENUES =====");

        for (Venue venue : venues) {
            venue.displayVenueDetails();
            System.out.println("-----------------------------");
        }
    }

    // Search for a venue by ID
    public Venue searchVenue(int venueId) {

        for (Venue venue : venues) {
            if (venue.getVenueId() == venueId) {
                return venue;
            }
        }

        return null;
    }

    // Update venue name
    public void updateVenueName(int venueId, String newName) {

        Venue venue = searchVenue(venueId);

        if (venue != null) {
            venue.setVenueName(newName);
        } else {
            System.out.println("Venue not found.");
        }
    }

    // Update venue location
    public void updateVenueLocation(int venueId, String newLocation) {

        Venue venue = searchVenue(venueId);

        if (venue != null) {
            venue.setLocation(newLocation);
        } else {
            System.out.println("Venue not found.");
        }
    }
}