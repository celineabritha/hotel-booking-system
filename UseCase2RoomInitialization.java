/**
 * Use Case 2 - Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance and polymorphism
 * using simple room types with static availability.
 *
 * @author Celine
 * @version 2.1
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double size;
    protected double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : $" + price);
    }
}

/* Single Room */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

/* Double Room */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

/* Suite Room */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 300);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("           Version 2.1           ");
        System.out.println("=================================");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 10;
        int doubleAvailability = 7;
        int suiteAvailability = 3;

        System.out.println("\n--- Room Information ---\n");

        single.displayRoomDetails();
        System.out.println("Available Rooms : " + singleAvailability);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleAvailability);
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteAvailability);
    }
}