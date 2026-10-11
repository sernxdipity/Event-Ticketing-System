
public class TestDigitalTicket {

    public static void main(String[] args) {

        // Step 1: Create attendee
        Attendee attendee = new Attendee(
            101, "Harry", "harry@gmail.com"
        );

        // Step 2: Create venue
        Venue venue = new Venue(
            1, "Axiata Arena", "Kuala Lumpur", 100
        );

        // Step 3: Create concert event
        ConcertEvent event = new ConcertEvent(
            201, "TWICE World Tour", venue,
            250.00, 5, "TWICE", true
        );

        event.configureTicketTiers(4, 1, 500.00);

        // Step 4: Create inventory
        TicketInventory inventory = new TicketInventory(event);

        // Step 5: Lock seat
        Seat seat = inventory.findSeat("S001");
        inventory.lockSeat("S001");

        // Step 6: Create reservation
        TicketReservation reservation = new TicketReservation(
            1001, attendee, event, seat
        );

        // Step 7: Create payment
        Payment payment = new Payment(
            5001, reservation, 250.00, "Online Banking"
        );

        // Step 8: Simulate successful payment
        boolean paid = payment.processPayment(true);

        // Step 9: Complete booking
        boolean booked = false;

        if (paid) {
            booked = reservation.completeBooking(inventory, payment);
        }

        // Step 10: Generate digital ticket
        if (booked) {
            DigitalTicket ticket = new DigitalTicket(
                7001, reservation, "TKT-7001"
            );

            ticket.displayTicket();
        }

        // Step 11: Test pending reservation rejection
        System.out.println("\n===== PENDING TICKET TEST =====");

        Seat pendingSeat = inventory.findSeat("S002");
        inventory.lockSeat("S002");

        TicketReservation pendingReservation = new TicketReservation(
            1002, attendee, event, pendingSeat
        );

        try {
            DigitalTicket invalidTicket = new DigitalTicket(
                7002, pendingReservation, "TKT-7002"
            );

            invalidTicket.displayTicket();

        } catch (IllegalArgumentException e) {
            System.out.println("Ticket rejected: " + e.getMessage());
        }
    }
}
