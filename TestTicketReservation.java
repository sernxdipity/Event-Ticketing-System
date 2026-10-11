public class TestTicketReservation {
     public static void main(String[] args) {

        // Step 1: Create an attendee
        Attendee attendee = new Attendee(
            101,
            "Harry",
            "Harry@gmail.com"
        );

        // Step 2: Create a venue
        Venue venue = new Venue(
            1,
            "Axiata Arena",
            "Kuala Lumpur",
            100
        );

        // Step 3: Create a concert event
        ConcertEvent event = new ConcertEvent(
            201,
            "TWICE World Tour",
            venue,
            250.00,
            5,
            "TWICE",
            true
        );

        // Step 4: Configure ticket tiers
        event.configureTicketTiers(4, 1, 500.00);

        // Step 5: Generate the seat inventory
        TicketInventory inventory = new TicketInventory(event);

        // Step 6: Find and lock a seat
        Seat seat = inventory.findSeat("S001");
        boolean locked = inventory.lockSeat("S001");

        if (!locked) {
            System.out.println("Unable to reserve seat.");
            return;
        }

        // Step 7: Create the reservation
        TicketReservation reservation = new TicketReservation(
            1001,
            attendee,
            event,
            seat
        );

        // Step 8: Display reservation information
        System.out.println("\n===== RESERVATION DETAILS =====");
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Attendee: " + reservation.getAttendee().getName());
        System.out.println("Event: " + reservation.getEvent().getTitle());
        System.out.println("Seat: " + reservation.getSeat().getSeatId());
        System.out.println("Seat Status: " + reservation.getSeat().getStatus());
        System.out.println("Reservation Status: " + reservation.getStatus());

        //reservation confirmation testing
        System.out.println("\n==== RESERVATION CONFIRMATION TEST ====");

        //test 1: try confirming before booking the seat
        boolean firstAttempt = reservation.confirmReservation();
        System.out.println("Confirm locked seat: " + firstAttempt);

        //test 2: booking the seat
        boolean booked = inventory.bookSeat("S001");
        System.out.println("Seat booking successful: " + booked);

        //test 3: confirm after the seat is booked
        boolean secondAttempt = reservation.confirmReservation();
        System.out.println("Confirm booked seat: " + secondAttempt);

        // Test 4: Try confirming again
        boolean thirdAttempt = reservation.confirmReservation();
        System.out.println("Confirm same reservation again: " + thirdAttempt);

        // Display final statuses
        System.out.println("\n===== FINAL STATUS =====");
        System.out.println("Seat Status: " + seat.getStatus());
        System.out.println("Reservation Status: " + reservation.getStatus());
    }
}
