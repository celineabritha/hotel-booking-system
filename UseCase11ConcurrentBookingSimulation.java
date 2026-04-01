import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * ==============================================================
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * - Multiple threads simulate concurrent users
 * - Shared queue + shared inventory
 * - Synchronized critical sections prevent race conditions
 * ==============================================================
 */

// Booking Request
class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// Shared Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    // Critical section (synchronized)
    public synchronized boolean allocateRoom(String roomType) {

        if (inventory.getOrDefault(roomType, 0) > 0) {
            int current = inventory.get(roomType);

            // Simulate delay (to expose race condition if unsynchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(roomType, current - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Thread class
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(String name, BookingQueue queue, InventoryService inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            BookingRequest request;

            // synchronized retrieval
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            boolean success = inventory.allocateRoom(request.roomType);

            if (success) {
                System.out.println(getName() + " ✅ Booked for "
                        + request.customerName + " (" + request.roomType + ")");
            } else {
                System.out.println(getName() + " ❌ Failed for "
                        + request.customerName + " (" + request.roomType + ")");
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple users
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));   // conflict
        queue.addRequest(new BookingRequest("Charlie", "Double"));
        queue.addRequest(new BookingRequest("David", "Double")); // conflict

        // Create threads
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        // Final state
        inventory.displayInventory();
    }
}