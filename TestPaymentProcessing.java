
public class TestPaymentProcessing {

    public static void main(String[] args) {

        // Create attendee
        Attendee attendee = new Attendee(
            101, "Harry", "harry@gmail.com"
        );

        // Create venue
        Venue venue = new Venue(
            1, "Axiata Arena", "Kuala Lumpur", 100
        );

        // Create event
        ConcertEvent event = new ConcertEvent(
            201, "TWICE World Tour", venue,
            250.00, 5, "TWICE", true
        );

        event.configureTicketTiers(4, 1, 500.00);

        // Create inventory
        TicketInventory inventory = new TicketInventory(event);

        // TEST 1: Successful payment
        System.out.println("\n===== SUCCESSFUL PAYMENT TEST =====");

        Seat seat1 = inventory.findSeat("S001");
        inventory.lockSeat("S001");

        TicketReservation reservation1 = new TicketReservation(
            1001, attendee, event, seat1
        );

        Payment payment1 = new Payment(
            5001, reservation1, 250.00, "Online Banking"
        );

        boolean result1 = payment1.processPayment(true);

        System.out.println("Payment result: " + result1);
        System.out.println("Payment status: " + payment1.getStatus());

        System.out.println("\n==== BOOKING COMPLETION TEST ====");

        boolean bookingCompleted = false;

        if (result1 && payment1.getStatus() == PaymentStatus.SUCCESSFUL) {
            bookingCompleted = reservation1.completeBooking(inventory, payment1);
        }

        System.out.println("Booking completed: " + bookingCompleted);
        System.out.println("Seat status: " + seat1.getStatus());
        System.out.println("Reservation status: " + reservation1.getStatus());

        // TEST 2: Duplicate payment
        System.out.println("\n===== DUPLICATE PAYMENT TEST =====");

        boolean result2 = payment1.processPayment(true);

        System.out.println("Second payment result: " + result2);
        System.out.println("Payment status: " + payment1.getStatus());

        // TEST 3: Failed payment on a different seat
        System.out.println("\n===== FAILED PAYMENT TEST =====");

        Seat seat2 = inventory.findSeat("S002");
        inventory.lockSeat("S002");

        TicketReservation reservation2 = new TicketReservation(
            1002, attendee, event, seat2
        );

        Payment payment2 = new Payment(
            5002, reservation2, 250.00, "Credit Card"
        );

        boolean result3 = payment2.processPayment(false);

        System.out.println("Payment result: " + result3);
        System.out.println("Payment status: " + payment2.getStatus());

        //test booking rejection after failed payment
        System.out.println("\n===== FAILED PAYMENT BOOKING TEST =====");

        boolean failedBooking = reservation2.completeBooking(inventory, payment2);

        System.out.println("Booking completed: " + failedBooking);
        System.out.println("Seat status: " + seat2.getStatus());
        System.out.println("Reservation status: " + reservation2.getStatus());
        
        //release the seat after failed payment
        System.out.println("\n===== FAILED PAYMENT CLEANUP TEST =====");

        if (payment2.getStatus() == PaymentStatus.FAILED) {
            boolean cancelled = reservation2.cancelReservation();
            System.out.println("Reservation cancelled: " + cancelled);
        }

        System.out.println("Seat status after cleanup: " + seat2.getStatus());
        System.out.println("Reservation status after cleanup: " + reservation2.getStatus());

        // Display final seat and reservation statuses
        System.out.println("\n===== FINAL STATUS =====");

        System.out.println("S001: " + seat1.getStatus());
        System.out.println("Reservation 1001: " + reservation1.getStatus());

        System.out.println("S002: " + seat2.getStatus());
        System.out.println("Reservation 1002: " + reservation2.getStatus());
    }
}

