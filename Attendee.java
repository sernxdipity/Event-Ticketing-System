public class Attendee{

    //Private attributes
    private int attendeeId;
    private String name;
    private String email;

    //Constructor
    public Attendee(int attendeeId, String name, String email) {
        this.attendeeId = attendeeId;
        this.name = name;
        this.email = email;

    }

    //getter methods
    public int getAttendeeId() {
        return attendeeId;
     }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    //setter methods
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else{
            System.out.println("Name cannot be empty.");
        }
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        } else{
            System.out.println("Email cannot be empty.");
        }
    }

    //display attendee info
    public void displayAttendeeDetails() {
        System.out.println("\n==== ATTENDEE DETAILS ====");
        System.out.println("Attendee ID: " + attendeeId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}