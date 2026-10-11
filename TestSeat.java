public class TestSeat {
    
    public static void main(String[] args) {
        //create a standard seat
        Seat seat1 = new Seat("S001", "STANDARD");

        //test 1: check initial status
        System.out.println("Seat ID: " + seat1.getSeatId());
        System.out.println("Ticket Type: " + seat1.getTicketType());
        System.out.println("Initial Status: " + seat1.getStatus());

        //test 2: lock the seat
        System.out.println("\nLock Seat: " + seat1.lockSeat());
        System.out.println("Status: " + seat1.getStatus());

        //test 3: lock the seat again
        System.out.println("\nLock Again: " + seat1.lockSeat());

        //test 4: confirm booking
        System.out.println("\nBooking Seat: " + seat1.bookSeat());
        System.out.println("Status: " + seat1.getStatus());

        //test 5: try booking the seat again
        System.out.println("\nBook Again: " + seat1.bookSeat());

        //test 6: try releasing a booked seat
        System.out.println("\nRelease Booked Seat: " + seat1.releaseSeat());

        //test 7: release a locked seat
        Seat seat2 = new Seat("V001" , "VIP");
        seat2.lockSeat();
        System.out.println("\nRelease Locked Seat: " + seat2.releaseSeat());
        System.out.println("Status" + seat2.getStatus());
    }
}
