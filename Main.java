import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create the system administrator
        Admin admin = new Admin(1, "System Administrator");

        // Create the admin menu
        AdminMenu adminMenu = new AdminMenu(admin, scanner);

        int choice;

        do {
            System.out.println("\n===================================");
            System.out.println(" EVENT TICKETING SYSTEM");
            System.out.println("===================================");

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin");
            System.out.println("2. Attendee (Coming Soon)");
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
                    adminMenu.displayMenu();
                    break;

                case 2:
                    System.out.println(
                        "Attendee system is under development."
                    );
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
}