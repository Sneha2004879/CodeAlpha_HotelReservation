import java.util.*;
import java.io.*;

class Room {
    int number;
    String type; // Standard, Deluxe, Suite
    boolean isBooked;

    Room(int number, String type) {
        this.number = number;
        this.type = type;
        this.isBooked = false;
    }
}

class Booking {
    String guestName;
    int roomNumber;
    String roomType;
    String paymentStatus;

    Booking(String guestName, int roomNumber, String roomType) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.paymentStatus = "Paid";
    }

    public String toString() {
        return "Guest: " + guestName + ", Room: " + roomNumber + " (" + roomType + "), Payment: " + paymentStatus;
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    Hotel() {
        // Predefine rooms
        for (int i = 101; i <= 105; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 201; i <= 203; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 301; i <= 302; i++) rooms.add(new Room(i, "Suite"));
    }

    void showAvailableRooms() {
        System.out.println("🛏 Available Rooms:");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.number + " (" + r.type + ")");
            }
        }
    }

    Room bookRoom(String type) {
        for (Room r : rooms) {
            if (!r.isBooked && r.type.equalsIgnoreCase(type)) {
                r.isBooked = true;
                return r;
            }
        }
        return null;
    }

    void cancelBooking(String guestName) {
        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.guestName.equalsIgnoreCase(guestName)) {
                for (Room r : rooms) {
                    if (r.number == b.roomNumber) {
                        r.isBooked = false;
                        break;
                    }
                }
                it.remove();
                System.out.println("✅ Booking for " + guestName + " cancelled.");
                return;
            }
        }
        System.out.println("❌ No booking found for " + guestName);
    }

    void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("📭 No current bookings.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    void makeBooking(String guestName, String type) {
        Room r = bookRoom(type);
        if (r != null) {
            Booking b = new Booking(guestName, r.number, r.type);
            bookings.add(b);
            System.out.println("✅ Room " + r.number + " booked for " + guestName + ". Payment status: " + b.paymentStatus);
        } else {
            System.out.println("❌ No " + type + " rooms available.");
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        int choice;
        do {
            System.out.println("\n🏨 HOTEL RESERVATION SYSTEM");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Make a Booking");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Room Type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();
                    hotel.makeBooking(name, type);
                    break;
                case 3:
                    System.out.print("Enter your name to cancel booking: ");
                    String cancelName = sc.nextLine();
                    hotel.cancelBooking(cancelName);
                    break;
                case 4:
                    hotel.showBookings();
                    break;
                case 5:
                    System.out.println("👋 Thank you for using Hotel Reservation System.");
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }

        } while (choice != 5);
    }
}
