public class Seat {

    //Private attributes 
    private String seatId;
    private String ticketType;
    private SeatStatus status;

    //constructor
    public Seat(String seatId, String ticketType) {
        this.seatId = seatId;
        this.ticketType = ticketType;
        this.status = SeatStatus.AVAILABLE;
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

    //temporarily lock an available seat
    public boolean lockSeat() {
        if (status == SeatStatus.AVAILABLE) {
            status = SeatStatus.LOCKED;
            return true;
        }
        return false;
    }

    //confirm booking after successful payment
    public boolean bookSeat(){
        if (status == SeatStatus.LOCKED) {
            status = SeatStatus.BOOKED;
            return true;
        }
        return false;
    }

    //release a locked seat after cancellation/payment fail
    public boolean releaseSeat(){
        if (status == SeatStatus.LOCKED) {
            status = SeatStatus.AVAILABLE;
            return true;
        }
        return false;
    }
}
