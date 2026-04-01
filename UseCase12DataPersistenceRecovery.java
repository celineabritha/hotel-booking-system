import java.io.*;
import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * ==============================================================
 * Use Case 12: Data Persistence & System Recovery
 *
 * - Saves system state to file (serialization)
 * - Loads state on restart (deserialization)
 * - Handles missing/corrupt files safely
 * ==============================================================
 */

// Reservation must be Serializable
class Reservation implements Serializable {
    String reservationId;
    String customerName;
    String roomType;

    public Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + customerName + " | " + roomType;
    }
}

// System State (combined data)
class SystemState implements Serializable {
    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("✅ System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("❌ Error loading data. Starting safe state.");
        }

        // Safe fallback
        return new SystemState(new ArrayList<>(), new HashMap<>());
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // LOAD existing state
        SystemState state = persistence.load();

        List<Reservation> history = state.bookingHistory;
        Map<String, Integer> inventory = state.inventory;

        // Initialize if empty (first run)
        if (inventory.isEmpty()) {
            inventory.put("Single", 2);
            inventory.put("Double", 2);
        }

        // Simulate booking
        Reservation r1 = new Reservation("R201", "Alice", "Single");
        history.add(r1);
        inventory.put("Single", inventory.get("Single") - 1);

        System.out.println("\n--- Current State ---");
        System.out.println("Bookings: " + history);
        System.out.println("Inventory: " + inventory);

        // SAVE state before shutdown
        persistence.save(new SystemState(history, inventory));
    }
}