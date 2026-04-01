import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * ==============================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * - Processes booking requests in FIFO order
 * - Assigns unique room IDs
 * - Prevents double booking using Set
 * - Updates inventory immediately
 * ==============================================================
 */

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryService() {
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void allocateRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

class BookingService {

    private Queue<BookingRequest> requestQueue = new LinkedList<>();

    // Map roomType -> Set of allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> usedRoomIds = new HashSet<>();

    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request); // FIFO
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            System.out.println("\nProcessing booking for: " + request.customerName);

            // Check availability
            if (!inventoryService.isAvailable(roomType)) {
                System.out.println("❌ No rooms available for type: " + roomType);
                continue;
            }

            // Generate unique room ID
            String roomId = generateUniqueRoomId(roomType);

            // Store in allocatedRooms map
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);

            // Update inventory (atomic step)
            inventoryService.allocateRoom(roomType);

            // Confirm booking
            System.out.println("✅ Booking Confirmed!");
            System.out.println("Room Type: " + roomType);
            System.out.println("Allocated Room ID: " + roomId);
        }
    }

    private String generateUniqueRoomId(String roomType) {
        String roomId;

        do {
            roomId = roomType.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }

    public void displayAllocations() {
        System.out.println("\nFinal Room Allocations:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        // Add booking requests (FIFO)
        bookingService.addRequest(new BookingRequest("Alice", "Single"));
        bookingService.addRequest(new BookingRequest("Bob", "Double"));
        bookingService.addRequest(new BookingRequest("Charlie", "Single"));
        bookingService.addRequest(new BookingRequest("David", "Single")); // may fail if full

        // Process bookings
        bookingService.processBookings();

        // Show final state
        bookingService.displayAllocations();
        inventoryService.displayInventory();
    }
}



