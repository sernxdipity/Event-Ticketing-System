public class ReservationManager {
    private int nextReservationId;

    //Constructor
    public ReservationManager() {
        nextReservationId = 1001;
    }

    //generate the next reservation id
    public int generateReservationId() {
        return nextReservationId++;
    }
}
