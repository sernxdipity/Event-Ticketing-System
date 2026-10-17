
public class TestSeatOwnership {

    public static void main(String[] args) {

        // Create a seat
        Seat seat = new Seat("S001", "STANDARD");

        // Test 1: Reservation 1001 locks the seat
        System.out.println("===== SEAT OWNERSHIP TEST =====");

        boolean firstLock = seat.lockSeat(1001);
        System.out.println("Reservation 1001 locks seat: " + firstLock);

        System.out.println("Seat Status: " + seat.getStatus());
        System.out.println("Lock Owner: " + seat.getLockedByReservationId());

        // Test 2: Reservation 1002 tries to book the seat
        boolean wrongBooking = seat.bookSeat(1002);
        System.out.println("\nReservation 1002 books seat: " + wrongBooking);

        // Test 3: Reservation 1002 tries to release the seat
        boolean wrongRelease = seat.releaseSeat(1002);
        System.out.println("Reservation 1002 releases seat: " + wrongRelease);

        // Test 4: Correct reservation books the seat
        boolean correctBooking = seat.bookSeat(1001);
        System.out.println("\nReservation 1001 books seat: " + correctBooking);

        // Final status
        System.out.println("\n===== FINAL STATUS =====");
        System.out.println("Seat Status: " + seat.getStatus());
        System.out.println("Reservation ID: " + seat.getLockedByReservationId());
    }
}
