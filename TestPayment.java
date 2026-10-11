
public class TestPayment {

    public static void main(String[] args) {

        // Step 1: Create attendee
        Attendee attendee = new Attendee(
            101, "Harry", "harry@gmail.com"
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

        // Step 4: Configure ticket tiers
        event.configureTicketTiers(4, 1, 500.00);

        // Step 5: Create seat inventory
        TicketInventory inventory = new TicketInventory(event);

        // Step 6: Select and lock seat
        Seat seat = inventory.findSeat("S001");

        boolean locked = inventory.lockSeat("S001");

        if (!locked) {
            System.out.println("Unable to lock seat.");
            return;
        }

        // Step 7: Create reservation
        TicketReservation reservation = new TicketReservation(
            1001, attendee, event, seat
        );

        // Step 8: Create payment
        Payment payment = new Payment(
            5001, reservation, 250.00, "Online Banking"
        );

        // Step 9: Display payment details
        System.out.println("\n===== PAYMENT DETAILS =====");
        System.out.println("Payment ID: " + payment.getPaymentId());
        System.out.println("Reservation ID: " +
            payment.getReservation().getReservationId());
        System.out.println("Attendee: " +
            payment.getReservation().getAttendee().getName());
        System.out.println("Amount: RM " + payment.getAmount());
        System.out.println("Payment Method: " + payment.getPaymentMethod());
        System.out.println("Payment Status: " + payment.getStatus());
        System.out.println("Reservation Status: " + reservation.getStatus());
        System.out.println("Seat Status: " + seat.getStatus());
    }
}

