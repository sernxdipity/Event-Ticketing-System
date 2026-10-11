public class TestTicketInventory {
    public static void main(String[] args) {
        //step1: create a venue
        Venue venue = new Venue(
            1,
            "National Hockey Stadium Bukit Jalil",
            "Kuala Lumpur",
            5000);

        //step2: create a concert event
        ConcertEvent concert = new ConcertEvent(
            101,
            "TWICE <THIS IS FOR> WORLD TOUR IN KUALA LUMPUR",
            venue,
            788.00,
            5,
            "TWICE",
            true);

        //step3: configure ticket tiers
        concert.configureTicketTiers(4, 1, 988.00);

        //step4: create ticket inventory
        TicketInventory inventory = new TicketInventory(concert);

        //step5: display generated seats
        inventory.displaySeats();

        //test searching seats
        Seat seat1 = inventory.findSeat("S003");

        if (seat1 != null) {
            System.out.println("\nSeat found: " + seat1.getSeatId());
            System.out.println("Status: " + seat1.getStatus());
        } else {
            System.out.println("\nSeat not found.");
        }

        //Test searching for a non existing seat
        Seat seat2 = inventory.findSeat("S100");

        if (seat2 == null) {
            System.out.println("S100 does not exist.");
        }

        //test locking an available seat
        System.out.println("\n=== SEAT LOCK TEST ===");

        boolean firstLock = inventory.lockSeat("S003");
        System.out.println("First lock successful: " + firstLock);

        // Try locking the same seat again
        boolean secondLock = inventory.lockSeat("S003");
        System.out.println("Second lock successful: " + secondLock);

        // Display updated inventory
        inventory.displaySeats();

        //test booking confirmation
        System.out.println("\n===== BOOKING TEST =====");

        boolean firstBooking = inventory.bookSeat("S003");
        System.out.println("First booking successful: " + firstBooking);

        //Attempt to book the same seat again
        boolean secondBooking = inventory.bookSeat("S003");
        System.out.println("Second booking succesful: " + secondBooking);

        // Attempt to book a seat that has not been locked
        boolean unlockedBooking = inventory.bookSeat("S004");
        System.out.println("Booking unlocked seat: " + unlockedBooking);

        //Display updated inventory
        inventory.displaySeats();

        //Test releasing a locked seat
        System.out.println("\n==== SEAT RELEASE TEST ====");

        //lock a vip seat
        inventory.lockSeat("V001");

        //release the vip seat
        boolean firstRelease = inventory.releaseSeat("V001");
        System.out.println("First release successful: " + firstRelease);

        //try releasing the same seat again
        boolean secondRelease = inventory.releaseSeat("V001");
        System.out.println("Second release successful: " + secondRelease);

        // Try releasing a confirmed booked seat
        boolean bookedRelease = inventory.releaseSeat("S003");
        System.out.println("Release booked seat: " + bookedRelease);

        // Display updated inventory
        inventory.displaySeats();

    }
}
