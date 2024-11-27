import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void displayAdminMenu() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            printAdminMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void printAdminMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customers:");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("Rooms:");
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter price per night: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
        int roomTypeChoice = Integer.parseInt(scanner.nextLine());
        RoomType roomType = (roomTypeChoice == 1) ? RoomType.SINGLE : RoomType.DOUBLE;

        Room room = new Room(roomNumber, price, roomType);
        ArrayList<IRoom> rooms = new ArrayList<>();
        rooms.add(room);

        adminResource.addRoom(rooms);
        System.out.println("Room added successfully!");
    }

}
