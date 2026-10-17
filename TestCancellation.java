public class TestCancellation {
    public static void main(String[] args) {
         // Step 1: Create attendee
        Attendee attendee = new Attendee(
            102, "Harry", "harry@gmail.com"
        );

        // Step 2: Create venue
        Venue venue = new Venue(
            1, "Axiata Arena", "Kuala Lumpur", 100
        );

        // Step 3: Create event
        ConcertEvent event = new ConcertEvent(
            201, "TWICE World Tour", venue,
            250.00, 5, "TWICE", true
        );

        event.configureTicketTiers(4, 1, 500.00);

        //Step 4: create inventory
        TicketInventory inventory = new TicketInventory(event);

        //Step 5: lock seat
        Seat seat = inventory.findSeat("S002");
        inventory.lockSeat("S002", 1002);

        //Step 6: Create reservation
        TicketReservation reservation = new TicketReservation(
            1002, attendee, event, seat
        );
        
        //Step 7: Cancel reservation
        System.out.println("\n==== CANCELATION TEST ====");

        boolean firstCancel = reservation.cancelReservation();
        System.out.println("First cancellation: " + firstCancel);

        // Step 8: Try cancelling again
        boolean secondCancel = reservation.cancelReservation();
        System.out.println("Second cancellation: " + secondCancel);

        //Step 9: Display final statuses
        System.out.println("\n==== FINAL STATUS ====");
        System.out.println("Seat Status: " + seat.getStatus());
        System.out.println("Reservation Status: " + reservation.getStatus());
        

    }
}
