import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Parking Lot ID: ");
        String parkingLotId = scanner.nextLine();

        System.out.print("Enter number of floors: ");
        int numberOfFloors = scanner.nextInt();

        System.out.print("Enter number of slots per floor: ");
        int slotsPerFloor = scanner.nextInt();
        scanner.nextLine(); // consume newline

        ParkingLot parkingLot = new ParkingLot(parkingLotId, numberOfFloors, slotsPerFloor);
        System.out.println("Parking Lot initialized!");

        while (true) {
            System.out.println("\nEnter command (park/unpark/available/exit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            switch (command.toLowerCase()) {
                case "park":
                    System.out.print("Enter vehicle type (Car/Bike/Truck): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter registration number: ");
                    String reg = scanner.nextLine();
                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();

                    Vehicle vehicle = new Vehicle(type, reg, color);
                    String ticket = parkingLot.parkVehicle(vehicle);
                    if (ticket != null) {
                        System.out.println("Parked successfully. Ticket ID: " + ticket);
                    } else {
                        System.out.println("No available slot for vehicle.");
                    }
                    break;

                case "unpark":
                    System.out.print("Enter ticket ID to unpark: ");
                    String ticketId = scanner.nextLine();
                    boolean success = parkingLot.unparkVehicle(ticketId);
                    if (success) {
                        System.out.println("Vehicle unparked successfully.");
                    } else {
                        System.out.println("Invalid or empty slot.");
                    }
                    break;

                case "available":
                    System.out.print("Enter vehicle type (Car/Bike/Truck): ");
                    String vehicleType = scanner.nextLine();
                    int count = parkingLot.getAvailableSlotsCount(vehicleType);
                    System.out.println("Available slots for " + vehicleType + ": " + count);
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }

        scanner.close();
    }
}
