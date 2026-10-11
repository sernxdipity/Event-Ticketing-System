public class Payment {
    
    //private attributes
    private int paymentId;
    private TicketReservation reservation;
    private double amount;
    private String paymentMethod;
    private PaymentStatus status;

    //constructor
    public Payment(int paymentId, TicketReservation reservation, double amount, String paymentMethod){
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty.");
        }

        this.paymentId = paymentId;
        this.reservation = reservation;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
    }

    //getter
    public int getPaymentId() {
        return paymentId;
    }

    public TicketReservation getReservation() {
        return reservation;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    //proces payment
    public boolean processPayment(boolean simulateSuccess) {
        //prevent processing the same payment twice
        if (status != PaymentStatus.PENDING) {
            System.out.println("Payment has already been processed.");
            return false;
        }

        //check reservation status
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            System.out.println("Reservation is not pending.");
            return false;
        }

        //check if the seat is locked
        if (reservation.getSeat().getStatus() != SeatStatus.LOCKED) {
            System.out.println("Seat is not locked.");
            return false;
        }

        if(simulateSuccess) {
            status = PaymentStatus.SUCCESSFUL;
            System.out.println("Payment successful.");
            return true;
        } else {
            status = PaymentStatus.FAILED;
            System.out.println("Payment failed!");
            return false;
        }
    }
}
