public class TestAttendee {
    
    public static void main(String[] args) {
        //create an attendee object
        Attendee attendee1 = new Attendee(
            101, 
            "Alice", 
            "alice123@gmail.com");
        
        //display original info
        attendee1.displayAttendeeDetails();

        //test setter methods
        attendee1.setName("Alice Smith");
        attendee1.setEmail("Smith_A123@gmail.com");

        //display updated info
        attendee1.displayAttendeeDetails();
    }
}
