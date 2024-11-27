import api.HotelResource;
import model.Customer;
import model.EmailPattern;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelResource hotelResource = HotelResource.getInstance();

    private static void printMainMenu() {
        System.out.println("\nWelcome to the Hotel Reservation System!");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin (open the admin menu)");
        System.out.println("5. Exit");
        System.out.print("Please select an option: ");
    }

    private static String checkPastDate(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean validDate = false;
        String dateInput = "";
        while (!validDate) {
            try {
                System.out.print(msg);
                String inputDate = scanner.nextLine();
                LocalDate enteredDate = LocalDate.parse(inputDate, formatter);
                LocalDate today = LocalDate.now();
                LocalDate maxFutureDate = today.plusMonths(3);

                if (enteredDate.isBefore(today)) {
                    System.out.println("The entered date is in the past. Please enter a valid date.");
                } else if (enteredDate.isAfter(maxFutureDate)) {
                    System.out.println("The entered date is too far in the future. Please enter a date within 3 months.");
                }else {
                    dateInput = inputDate;
                    validDate = true;
                }


            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the format MM/dd/yyyy.");
            }
        }
        return dateInput;
    }
    private static String inputEmail(){
        boolean checkEmail = false;
        String email= null;
        while (!checkEmail){
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            if (EmailPattern.EMAIL_REGEX.isValid(email)) {
                checkEmail = true;
            } else {
                System.out.println("Email wrong type");
                checkEmail = false;
            }
            var emailExist = hotelResource.getCustomer(email);
            if(emailExist == null){
                checkEmail = false;
                System.out.println("Email not exist");
            }

        }
        return email;
    }

    private static void findAndReserveRoom() {
        try {

            String dateIn = checkPastDate("Enter Check-In Date (MM/dd/yyyy): ");
            Date checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateIn);
            boolean isOverlapDate = false;

            String dateOut = checkPastDate("Enter Check-Out Date (MM/dd/yyyy): ");
            Date checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateOut);


            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (availableRooms.isEmpty()) {
                System.out.println("No available rooms found for the selected dates.");
                return;
            }

            System.out.println("Available rooms:");
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }

            System.out.print("Enter Room Number to reserve: ");
            String roomNumber = scanner.nextLine();
            IRoom room = hotelResource.getRoom(roomNumber);

            if (room == null) {
                System.out.println("Room number is invalid.");
                return;
            }

            String email = inputEmail();

            hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
            System.out.println("Room successfully reserved!");

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Your Reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void createAnAccount() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void MainMenuRun() {
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAnAccount();
                    break;
                case "4":
                    AdminMenu.displayAdminMenu(); // Mở menu Admin
                    break;
                case "5":
                    exit = true;
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
