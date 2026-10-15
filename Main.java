import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    // Admin password
    private static final String ADMIN_PASSWORD = "Admin@123";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create the system administrator
        Admin admin = new Admin(1, "System Administrator");

        // Shared ticket inventories for all events
        ArrayList<TicketInventory> inventories = new ArrayList<>();

        // Create the admin menu
        AdminMenu adminMenu = new AdminMenu(admin, scanner);

        // Create attendee menu only one
        AttendeeMenu attendeeMenu = new AttendeeMenu(admin.getEvents(), inventories);

        int choice;

        do {
            System.out.println("\n===================================");
            System.out.println(" EVENT TICKETING SYSTEM");
            System.out.println("===================================");

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin");
            System.out.println("2. Attendee");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = 0;
                continue;
            }

            switch (choice) {
                case 1:

                    // Check admin password before opening admin menu
                    if (adminLogin(scanner)) {

                        adminMenu.displayMenu();

                         // Create inventories for newly added events
                        syncTicketInventories(admin.getEvents(), inventories);

                    } else {
                        System.out.println("Returning to Main Menu...");
                    }

                    break;

                case 2:
                    // Ensure all events have their ticket inventories
                    syncTicketInventories(admin.getEvents(), inventories);

                    // Open the existing attendee menu
                    attendeeMenu.displayMenu();
                    break;

                case 3:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);

        scanner.close();
    }

    
    // Admin login authentication
    private static boolean adminLogin(Scanner scanner) {

        System.out.println("\n===== ADMIN LOGIN =====");

        for (int attempt = 1; attempt <= 3; attempt++) {

            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();

            if (password.equals(ADMIN_PASSWORD)) {

                System.out.println(
                    "Login successful! Welcome, Administrator."
                );

                return true;

            } else {

                System.out.println("Incorrect password!");

                System.out.println(
                    "Attempts remaining: " + (3 - attempt)
                );
            }
        }

        System.out.println(
            "Maximum login attempts reached. Access denied."
        );

        return false;
    }

    private static void syncTicketInventories(ArrayList<Event> events, ArrayList<TicketInventory> inventories) {

        for (Event event : events) {

            boolean inventoryExists = false;

            //check whether inventory alr exists
            for (TicketInventory inventory : inventories) {
                if (inventory.getEvent() == event) {
                    inventoryExists = true;
                    break;
                }
            }

            //create inventory only for new events
            if (!inventoryExists) {

                TicketInventory newInventory = new TicketInventory(event);

                // Connect the event to its inventory
                event.setTicketInventory(newInventory);

                inventories.add(newInventory);

                System.out.println("Ticket inventory created for: " + event.getTitle());
            }
        }
    }
    
}