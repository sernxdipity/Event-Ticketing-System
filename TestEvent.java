public class TestEvent extends Event {

    public TestEvent(int eventId, String title, Venue venue,
                     double price, int capacity) {

        super(eventId, title, venue, price, capacity);
    }

    @Override
    public String getEventType() {
        return "Test Event";
    }
}
