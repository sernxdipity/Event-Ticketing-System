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
            
            // Baby blue music-themed main menu
            CliTheme.banner();

            CliTheme.section("Main Menu");

            CliTheme.option(1, "♪ Admin Login");
            CliTheme.option(2, "♫ Attendee");
            CliTheme.option(3, "✧ Exit");

            CliTheme.prompt("Enter your choice: ");

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
    // Administrator authentication
    private static boolean adminLogin(Scanner scanner) {

        CliTheme.section("Admin Login");

        for (int attempt = 1; attempt <= 3; attempt++) {

            CliTheme.prompt("Enter Admin Password: ");
            String password = scanner.nextLine();

            if (password.equals(ADMIN_PASSWORD)) {

                CliTheme.success(
                    "Welcome back, Administrator! ♪"
                );

                return true;

            } else {

                CliTheme.error("Incorrect password!");

                System.out.println(
                    "  Attempts remaining: " + (3 - attempt)
                );
            }
        }

        CliTheme.error(
            "Maximum attempts reached. Access denied."
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