public class Venue {

    // Private attributes (Encapsulation)
    private int venueId;
    private String venueName;
    private String location;
    private int capacity;

    // Constructor to initialise venue details
    public Venue(int venueId, String venueName, String location, int capacity) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }

    // Getter methods
    public int getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    
    // Update venue name
    public void setVenueName(String venueName) {
        if (venueName != null && !venueName.trim().isEmpty()) {
            this.venueName = venueName;
        } else {
            System.out.println("Venue name cannot be empty.");
        }
    }

    // Update venue location
    public void setLocation(String location) {
        if (location != null && !location.trim().isEmpty()) {
            this.location = location;
        } else {
            System.out.println("Venue location cannot be empty.");
        }
    }

    // Update venue capacity
    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            System.out.println("Venue capacity must be greater than 0.");
        }
    }

    // Display venue information
    public void displayVenueDetails() {
        System.out.println("\n===== VENUE DETAILS =====");
        System.out.println("Venue ID: " + venueId);
        System.out.println("Venue Name: " + venueName);
        System.out.println("Location: " + location);
        System.out.println("Capacity: " + capacity);
    }
}
