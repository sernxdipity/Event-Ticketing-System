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
    // Early-bird discount settings
    private static final int EARLY_BIRD_DAYS = 14;
    private static final double EARLY_BIRD_RATE = 0.10;

    // Reservation price details
    private final double originalPrice;
    private final double discountAmount;
    private final double finalAmount;


    // Constructor
    public TicketReservation(int reservationId, Attendee attendee,
                             Event event, Seat seat) {

        if (attendee == null || event == null || seat == null) {
            throw new IllegalArgumentException(
                "Reservation details cannot be null."
            );
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

        // No ticket exists until reservation is confirmed
        this.digitalTicket = null;

        // Store the current date and time
        this.bookingTimestamp = LocalDateTime.now();

        // Determine original ticket price
        if (seat.getTicketType().equalsIgnoreCase("VIP")) {
            originalPrice = event.getVipTicketPrice();
        } else {
            originalPrice = event.getBaseTicketPrice();
        }

        // Check early-bird eligibility
        boolean isEarlyBird = false;

        if (event.getEventDateTime() != null) {
            LocalDateTime deadline =
                event.getEventDateTime().minusDays(EARLY_BIRD_DAYS);

            isEarlyBird = !bookingTimestamp.isAfter(deadline);
        }

        // Calculate discount
        if (isEarlyBird) {
            discountAmount =
                Math.round(originalPrice * EARLY_BIRD_RATE * 100.0)
                / 100.0;
        } else {
            discountAmount = 0.0;
        }

        // Calculate final amount
        finalAmount =
            Math.round((originalPrice - discountAmount) * 100.0)
            / 100.0;
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
    // Get original ticket price
    public double getOriginalPrice() {
        return originalPrice;
    }

    // Get early-bird discount amount
    public double getDiscountAmount() {
        return discountAmount;
    }

    // Get final ticket price after discount
    public double getFinalAmount() {
        return finalAmount;
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

        
        // Confirm ownership of the locked seat
        if (seat.getLockedByReservationId() != reservationId) {
            System.out.println(
                "Seat is locked by another reservation."
            );
            return false;
        }

        // Confirm the individual seat booking
        if (!inventory.bookSeat(seat.getSeatId(), reservationId)) {
            System.out.println("Seat booking failed.");
            return false;
        }

        // Confirm reservation and generate digital ticket
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


    // Roll back a booking after successful payment
    // when reservation completion fails
    public boolean rollbackFailedBooking(
            TicketInventory inventory, Payment payment) {

        System.out.println("\n===== BOOKING ROLLBACK =====");

        // Only pending reservations can be rolled back
        if (status != ReservationStatus.PENDING) {
            System.out.println(
                "Only pending reservations can be rolled back."
            );
            return false;
        }

        // Validate inventory and payment ownership
        if (inventory == null ||
            inventory.getEvent() != event ||
            inventory.findSeat(seat.getSeatId()) != seat ||
            payment == null ||
            payment.getReservation() != this) {

            System.out.println("Invalid rollback details.");
            return false;
        }

        // Payment must have succeeded
        if (payment.getStatus() != PaymentStatus.SUCCESSFUL) {
            System.out.println(
                "No successful payment to reverse."
            );
            return false;
        }

        boolean seatRestored = false;

        // Release a temporarily locked seat
        if (seat.getStatus() == SeatStatus.LOCKED) {

            seatRestored = inventory.releaseSeat(
                seat.getSeatId(), reservationId
            );

        // Undo a seat booked before confirmation failed
        } else if (seat.getStatus() == SeatStatus.BOOKED) {

            seatRestored = inventory.rollbackBookedSeat(
                seat.getSeatId(), reservationId
            );

        // Seat may already have been released
        } else if (seat.getStatus() == SeatStatus.AVAILABLE) {

            seatRestored = true;
        }

        // Do not reverse if seat restoration failed
        if (!seatRestored) {
            System.out.println(
                "Seat restoration failed. Manual review required."
            );
            return false;
        }

        // Reverse the successful simulated payment
        if (!payment.reversePayment()) {
            System.out.println(
                "Payment reversal failed. Manual review required."
            );
            return false;
        }

        // Cancel the incomplete reservation
        status = ReservationStatus.CANCELLED;

        System.out.println(
            "Reservation cancelled after booking failure."
        );

        return true;
    }

}



