import java.util.ArrayList;

public class TicketInventory {
    //private attributes
    private Event event;
    private ArrayList<Seat> seats;

    //constructor
    public TicketInventory(Event event) {

        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        this.event = event;
        this.seats = new ArrayList<>();

        generateSeats();
    }

    //getter
    public Event getEvent() {
        return event;
    }


    //display all seats and their current status
    public void displaySeats() {
        System.out.println("\n==== SEAT INVENTORY ====");
        System.out.println("Event: " + event.getTitle());

        for (Seat seat : seats) {
            System.out.println(
                "Seat ID: " + seat.getSeatId() + 
                " | Type: " + seat.getTicketType() + 
                " | Status: " + seat.getStatus()
            );
        }
    }
    
    //search for a seat by its id
    public Seat findSeat(String seatId) {

        for (Seat seat : seats) {
            if (seat.getSeatId().equalsIgnoreCase(seatId)) {
                return seat;
            }
        }
        return null;
    }

    //lock a specific seat during checkout
    public boolean lockSeat(String seatId) {
        Seat seat = findSeat(seatId);

        if (seat == null) {
            System.out.println("Seat does not exist.");
            return false;
        }

        if (seat.lockSeat()){
            System.out.println("Seat " + seatId + " locked successfully.");
            return true;
        }

        System.out.println("Seat " + seatId + " is not available.");
        return false;
    }

    //confirm a locked seat after successful payment
    public boolean bookSeat(String seatId) {

        Seat seat = findSeat(seatId);

        if(seat == null) {
            System.out.println("Seat does not exist.");
            return false;
        }

        if(seat.bookSeat()) {
            System.out.println("Seat " + seatId + " booked successfully.");
            return true;
        }

        System.out.println("Seat " + seatId + " cannot be booked.");
        return false;
    }

    
    // Release a locked seat
    public boolean releaseSeat(String seatId) {

        Seat seat = findSeat(seatId);

        if (seat == null) {
            System.out.println("Seat does not exist.");
            return false;
        }

        if (seat.releaseSeat()) {
            System.out.println("Seat " + seatId + " released successfully.");
            return true;
        }

        System.out.println("Seat " + seatId + " cannot be released.");
        return false;
    }


    //generate standard and vip seats
    private void generateSeats() {
        //generate standard seats
        for (int i = 1; i <= event.getStandardCapacity(); i++) {
            String seatId = String.format("S%03d", i);
            seats.add(new Seat(seatId, "STANDARD"));
        }

    //generate vip seats
        for (int i = 1; i <= event.getVipCapacity(); i++) {
            String seatId = String.format("V%03d", i);
            seats.add(new Seat(seatId, "VIP"));
        }
    }

}
