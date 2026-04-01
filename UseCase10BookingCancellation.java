import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * ==============================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * - Cancels confirmed bookings
 * - Uses Stack for rollback (LIFO)
 * - Restores inventory safely
 * ==============================================================
 */

// Reservation model
class Reservation {
    String reservationId;
    String customerName;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String customerName,
                       String roomType, String roomId) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return reservationId + " | " + customerName +
               " | " + roomType + " | RoomID: " + roomId;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Cancellation Service
class CancellationService {

    // Active reservations
    private Map<String, Reservation> activeBookings = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    private InventoryService inventoryService;

    public CancellationService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Add confirmed booking
    public void addBooking(Reservation reservation) {
        activeBookings.put(reservation.reservationId, reservation);
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        // Validate existence
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("❌ Cancellation Failed: Invalid Reservation ID");
            return;
        }

        Reservation reservation = activeBookings.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservation.roomId);

        // Restore inventory
        inventoryService.increment(reservation.roomType);

        // Remove booking
        activeBookings.remove(reservationId);

        System.out.println("✅ Booking Cancelled Successfully: " + reservationId);
        System.out.println("Released Room ID: " + reservation.roomId);
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack (LIFO): " + rollbackStack);
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        CancellationService cancellationService =
                new CancellationService(inventoryService);

        // Simulated confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single", "S101");
        Reservation r2 = new Reservation("R102", "Bob", "Double", "D201");

        cancellationService.addBooking(r1);
        cancellationService.addBooking(r2);

        // Cancel bookings
        cancellationService.cancelBooking("R102"); // valid
        cancellationService.cancelBooking("R999"); // invalid

        // Display state
        cancellationService.displayRollbackStack();
        inventoryService.displayInventory();
    }
}