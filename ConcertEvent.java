public class ConcertEvent extends Event {

    // Attributes specific to concert events
    private String artistName;
    private boolean vipAvailable;

    // Constructor
    public ConcertEvent(int eventId, String title, Venue venue,
                        double baseTicketPrice, int maxCapacity,
                        String artistName, boolean vipAvailable) {

        // Call the constructor of the parent Event class
        super(eventId, title, venue, baseTicketPrice, maxCapacity);

        this.artistName = artistName;
        this.vipAvailable = vipAvailable;
    }

    // Getter methods
    public String getArtistName() {
        return artistName;
    }

    public boolean isVipAvailable() {
        return vipAvailable;
    }

    // Setter methods
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setVipAvailable(boolean vipAvailable) {
        this.vipAvailable = vipAvailable;
    }

    // Method overriding (Polymorphism)
    @Override
    public void displayEventDetails() {

        // Display the common event details
        super.displayEventDetails();

        // Display concert-specific details
        System.out.println("Artist Name: " + artistName);
        System.out.println("VIP Tickets: " + (vipAvailable ? "Available" : "Not Available"));
    }

    // Implement the abstract method from Event
    @Override
    public String getEventType() {
        return "Concert";
    }
}
