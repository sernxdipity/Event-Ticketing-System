
public class TestReservationManager {

    public static void main(String[] args) {

        ReservationManager manager = new ReservationManager();

        int id1 = manager.generateReservationId();
        int id2 = manager.generateReservationId();
        int id3 = manager.generateReservationId();

        System.out.println("===== RESERVATION ID TEST =====");

        System.out.println("First Reservation ID: " + id1);
        System.out.println("Second Reservation ID: " + id2);
        System.out.println("Third Reservation ID: " + id3);

        // Check whether all generated IDs are different
        boolean unique = id1 != id2 &&
                         id1 != id3 &&
                         id2 != id3;

        System.out.println("All IDs are unique: " + unique);
    }
}

