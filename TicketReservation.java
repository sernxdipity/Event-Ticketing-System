import java.time.LocalDateTime;

public class TicketReservation {
    
    //PRIVATE attributes
    private int reservationId;
    private Attendee attendee;
    private Event event;
    private Seat seat;
    private ReservationStatus status;
    // Record when the reservation was created
    private LocalDateTime bookingTimestamp;
    // Composition: reservation owns its digital ticket
    private DigitalTicket digitalTicket;

    //conversion
    public TicketReservation(int reservationId, Attendee attendee, Event event, Seat seat) {
    if (attendee == null || event == null || seat == null) {
        throw new IllegalArgumentException("Reservation details cannot be null.");
    }

    if (reservationId <= 0) {
        throw new IllegalArgumentException(
            "Reservation ID must be greater than zero."
        );
    }

    this.reservationId = reservationId;
    this.attendee = attendee;
    this.event = event;
    this.seat = seat;
    this.status = ReservationStatus.PENDING;
    // No ticket exists until the reservation is confirmed
    this.digitalTicket = null;
    // Store the current date and time
    this.bookingTimestamp = LocalDateTime.now();
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
    // Retrieve reservation timestamp
    public LocalDateTime getBookingTimestamp() {
        return bookingTimestamp;
    }
    // Retrieve the digital ticket
    public DigitalTicket getDigitalTicket() {
        return digitalTicket;
    }

    
    // Confirm reservation and generate digital ticket
    public boolean confirmReservation() {

        if (status != ReservationStatus.PENDING) {
            System.out.println("Reservation cannot be confirmed.");
            return false;
        }

        if (seat.getStatus() != SeatStatus.BOOKED) {
            System.out.println(
                "Seat must be booked before confirmation."
            );
            return false;
        }

        // Confirm the reservation
        status = ReservationStatus.CONFIRMED;

        // Composition: create the ticket inside the reservation
        int ticketId = reservationId;
        String ticketCode = "TKT-" + ticketId;

        digitalTicket = new DigitalTicket(
            ticketId,
            this,
            ticketCode
        );

        System.out.println("Reservation confirmed successfully!");
        System.out.println("Digital ticket generated successfully!");

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
            return false;
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

        if (!event.hasAvailableSeats(1)) {
            System.out.println("No available event capacity.");
            return false;
        }

        if (!inventory.bookSeat(seat.getSeatId(), reservationId)) {
            System.out.println("Seat booking failed.");
            return false;
        }

        if (!event.reserveSeats(1)) {
            System.out.println("Unable to update event availablity.");
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

        if (!seat.releaseSeat(reservationId)) {
            System.out.println("Unable to release seat.");
            return false;
        }

        status = ReservationStatus.CANCELLED;
        System.out.println("Reservation cancelled successfully!");
        return true;
    }
}



