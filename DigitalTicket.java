import java.time.format.DateTimeFormatter;

public class DigitalTicket {

    // Private attributes (Encapsulation)
    private int ticketId;
    private TicketReservation reservation;
    private String ticketCode;

    // Constructor
    public DigitalTicket(int ticketId, TicketReservation reservation,
                         String ticketCode) {

        if (ticketId <= 0) {
            throw new IllegalArgumentException(
                "Ticket ID must be greater than zero."
            );
        }

        if (reservation == null) {
            throw new IllegalArgumentException(
                "Reservation cannot be null."
            );
        }

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                "Only confirmed reservations can receive tickets."
            );
        }

        if (ticketCode == null || ticketCode.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Ticket code cannot be empty."
            );
        }

        this.ticketId = ticketId;
        this.reservation = reservation;
        this.ticketCode = ticketCode;
    }

    // Getter methods
    public int getTicketId() {
        return ticketId;
    }

    public TicketReservation getReservation() {
        return reservation;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    // Display digital ticket
    public void displayTicket() {

        Event event = reservation.getEvent();

        System.out.println("\n==================================");
        System.out.println("          DIGITAL TICKET          ");
        System.out.println("==================================");

        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Ticket Code: " + ticketCode);

        System.out.println("----------------------------------");

        // Event information
        System.out.println("Event: " + event.getTitle());
        System.out.println("Event Type: " + event.getEventType());

        System.out.println("Venue: " +
            event.getVenue().getVenueName());

        System.out.println("Location: " +
            event.getVenue().getLocation());

        // Display event date and time
        if (event.getEventDateTime() != null) {

            DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

            DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm");

            System.out.println("Event Date: " +
                event.getEventDateTime().format(dateFormatter));

            System.out.println("Event Time: " +
                event.getEventDateTime().format(timeFormatter));

        } else {
            System.out.println("Event Date: Not Scheduled");
            System.out.println("Event Time: Not Scheduled");
        }

        System.out.println("----------------------------------");

        // Attendee information
        System.out.println("Attendee: " +
            reservation.getAttendee().getName());

        System.out.println("Seat: " +
            reservation.getSeat().getSeatId());

        System.out.println("Ticket Type: " +
            reservation.getSeat().getTicketType());

        // Display ticket pricing
        System.out.printf("Original Price: RM %.2f%n",
            reservation.getOriginalPrice());

        if (reservation.getDiscountAmount() > 0) {

            System.out.printf("Early-Bird Discount: -RM %.2f%n",
                reservation.getDiscountAmount());
        }

        System.out.printf("Amount Paid: RM %.2f%n",
            reservation.getFinalAmount());


        System.out.println("----------------------------------");

        // Reservation information
        System.out.println("Reservation ID: " +
            reservation.getReservationId());

        System.out.println("Reservation Status: " +
            reservation.getStatus());

        System.out.println("==================================");
        System.out.println("       ENJOY YOUR EVENT!          ");
        System.out.println("==================================");
    }
}