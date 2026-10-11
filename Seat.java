public class Seat {

    //Private attributes 
    private String seatId;
    private String ticketType;
    private SeatStatus status;
    private int lockedByReservationId;

    //constructor
    public Seat(String seatId, String ticketType) {
        this.seatId = seatId;
        this.ticketType = ticketType;
        this.status = SeatStatus.AVAILABLE;
        this.lockedByReservationId = -1;
    }
    
    //getter
    public String getSeatId() {
        return seatId;
    }
    public String getTicketType() {
        return ticketType;
    }
    public SeatStatus getStatus() {
        return status;
    }
    public int getLockedByReservationId() {
    return lockedByReservationId;
    }

    //temporarily lock an available seat
    public boolean lockSeat(int reservationId) {
        if (reservationId <= 0) {
            return false;
        }

        if (status == SeatStatus.AVAILABLE) {
            status = SeatStatus.LOCKED;
            lockedByReservationId = reservationId;
            return true;
        }

        return false;
    }

    //confirm booking after successful payment
    public boolean bookSeat(int reservationId) {

        if (status == SeatStatus.LOCKED &&
            lockedByReservationId == reservationId) {

            status = SeatStatus.BOOKED;
            return true;
        }

        return false;
    }


    //release a locked seat after cancellation/payment fail
    public boolean releaseSeat(int reservationId){
        if (status == SeatStatus.LOCKED &&
            lockedByReservationId == reservationId) {

            status = SeatStatus.AVAILABLE;
            lockedByReservationId = -1;
            return true;
        }
        return false;
    }
}
