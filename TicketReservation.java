public class TicketReservation {
    
    //PRIVATE attributes
    private int reservationId;
    private Attendee attendee;
    private Event event;
    private Seat seat;
    private ReservationStatus status;

    //conversion
    public TicketReservation(int reservationId, Attendee attendee, Event event, Seat seat) {
    if (attendee == null || event == null || seat == null) {
        throw new IllegalArgumentException("Reservation details cannot be null.");
    }

    this.reservationId = reservationId;
    this.attendee = attendee;
    this.event = event;
    this.seat = seat;
    this.status = ReservationStatus.PENDING;
    }

    //getter
    public int getReservationId() {
        return reservationId;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public Event getEvent() {
        return event;
    }

    public Seat getSeat() {
        return seat;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    //confirm reservation
    public boolean confirmReservation() {
        if (status != ReservationStatus.PENDING) {
            System.out.println("Reservation cannot be confirmed.");
            return false;
        }

        if (seat.getStatus() != SeatStatus.BOOKED) {
            System.out.println("Seat must be booked before confirmation.");
            return false;
        }

        status = ReservationStatus.CONFIRMED;
        System.out.println("Reservation confirmed successfully!");
        return true;
    }

    //complete booking
    public boolean completeBooking(TicketInventory inventory, Payment payment) {

        if (inventory == null) {
            System.out.println("Ticket inventory is required.");
            return false;
        }

        if (payment == null) {
            System.out.println("Payment is required.");
            return false;
        }

        if (payment.getReservation() != this) {
            System.out.println("Payment does not belong to this reservation.");
            return false;
        }

        if (payment.getStatus() != PaymentStatus.SUCCESSFUL) {
            System.out.println("Payment must be successful before booking.");
            return false;
        }

        if (status != ReservationStatus.PENDING) {
            System.out.println("Reservation is not pending.");
        }

        if (inventory.getEvent() != event) {
            System.out.println("Inventory does not belong to this event.");
            return false;
        }

        if (inventory.findSeat(seat.getSeatId()) != seat) {
            System.out.println("Seat does not belong to this inventory.");
            return false;
        }

        if (seat.getStatus() != SeatStatus.LOCKED) {
            System.out.println("Seat is not locked.");
            return false;
        }

        if (!inventory.bookSeat(seat.getSeatId())) {
            System.out.println("Seat booking failed.");
            return false;
        }

        return confirmReservation();
    }

    //cancel reservation
    public boolean cancelReservation() {

        if (status != ReservationStatus.PENDING) {
            System.out.println("Only pending reservations can be cancelled.");
            return false;
        }

        if (seat.getStatus() != SeatStatus.LOCKED) {
            System.out.println("Seat is not locked. Cancellation failed.");
            return false;
        }

        if (!seat.releaseSeat()) {
            System.out.println("Unable to release seat.");
            return false;
        }

        status = ReservationStatus.CANCELLED;
        System.out.println("Reservation cancelled successfully!");
        return true;
    }
}



