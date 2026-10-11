public class DigitalTicket {
    
    //private attribute
    private int ticketId;
    private TicketReservation reservation;
    private String ticketCode;

    //constructor
    public DigitalTicket(int ticketId, TicketReservation reservation, String ticketCode) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only confirmed reservations can receive tickets.");
        }

        if (ticketCode == null || ticketCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket code cannot be empty.");
        }

        this.ticketId = ticketId;
        this.reservation = reservation;
        this.ticketCode = ticketCode;
    }

    //getter
    public int getTicketId() {
        return ticketId;
    }

    public TicketReservation getReservation() {
        return reservation;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    //display ticket
    public void displayTicket() {

        System.out.println("\n==================================");
        System.out.println("          DIGITAL TICKET          ");
        System.out.println("==================================");

        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Ticket Code: " + ticketCode);

        System.out.println("----------------------------------");

        System.out.println("Event: " +
            reservation.getEvent().getTitle());

        System.out.println("Attendee: " +
            reservation.getAttendee().getName());

        System.out.println("Seat: " +
            reservation.getSeat().getSeatId());

        System.out.println("Ticket Type: " +
            reservation.getSeat().getTicketType());

        System.out.println("Reservation ID: " +
            reservation.getReservationId());

        System.out.println("Reservation Status: " +
            reservation.getStatus());

        System.out.println("==================================");
    }


}
