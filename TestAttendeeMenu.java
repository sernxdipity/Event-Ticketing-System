
import java.util.ArrayList;

public class TestAttendeeMenu {

    public static void main(String[] args) {

        // Create a venue
        Venue venue = new Venue(
            1,
            "Axiata Arena",
            "Bukit Jalil",
            100
        );

        // Create a concert event
        ConcertEvent concert = new ConcertEvent(
            101,
            "TWICE World Tour",
            venue,
            250.00,
            100,
            "TWICE",
            true
        );

        // Configure standard and VIP seats
        concert.configureTicketTiers(80, 20, 450.00);

        // Create inventory for the TWICE concert
        TicketInventory inventory = new TicketInventory(concert);

        // Create a shared event list
        ArrayList<Event> events = new ArrayList<>();
        events.add(concert);

        // Store the shared inventory
        ArrayList<TicketInventory> inventories = new ArrayList<>();
        inventories.add(inventory);

        // Pass events and inventories to the attendee menu
        AttendeeMenu menu = new AttendeeMenu(events, inventories);

        // Display attendee menu
        menu.displayMenu();
    }
}

