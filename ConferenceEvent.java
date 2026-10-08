public class ConferenceEvent extends Event {

    // Attributes specific to conference events
    private String speakerName;
    private String conferenceTopic;

    // Constructor
    public ConferenceEvent(int eventId, String title, Venue venue,
                           double baseTicketPrice, int maxCapacity,
                           String speakerName, String conferenceTopic) {

        // Call the parent Event constructor
        super(eventId, title, venue, baseTicketPrice, maxCapacity);

        this.speakerName = speakerName;
        this.conferenceTopic = conferenceTopic;
    }

    // Getter methods
    public String getSpeakerName() {
        return speakerName;
    }

    public String getConferenceTopic() {
        return conferenceTopic;
    }

    // Setter methods
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public void setConferenceTopic(String conferenceTopic) {
        this.conferenceTopic = conferenceTopic;
    }

    // Method overriding (Polymorphism)
    @Override
    public void displayEventDetails() {

        // Display common event information
        super.displayEventDetails();

        // Display conference-specific information
        System.out.println("Speaker Name: " + speakerName);
        System.out.println("Conference Topic: " + conferenceTopic);
    }

    // Implement the abstract method from Event
    @Override
    public String getEventType() {
        return "Conference";
    }
}