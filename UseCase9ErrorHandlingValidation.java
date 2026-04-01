import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * ==============================================================
 * Use Case 9: Error Handling & Validation
 *
 * - Validates input before processing
 * - Uses custom exceptions
 * - Prevents invalid system state
 * - Ensures graceful failure
 * ==============================================================
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking Request
class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// Validator Class
class InvalidBookingValidator {

    private Set<String> validRoomTypes = new HashSet<>(Arrays.asList("Single", "Double", "Suite"));

    public void validate(BookingRequest request, Map<String, Integer> inventory)
            throws InvalidBookingException {

        // Validate customer name
        if (request.customerName == null || request.customerName.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }

        // Validate room type
        if (!validRoomTypes.contains(request.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + request.roomType);
        }

        // Validate inventory availability
        if (!inventory.containsKey(request.roomType) || inventory.get(request.roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + request.roomType);
        }
    }
}

// Booking Service with validation
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();
    private InvalidBookingValidator validator = new InvalidBookingValidator();

    public BookingService() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public void processBooking(BookingRequest request) {

        try {
            // Fail-fast validation
            validator.validate(request, inventory);

            // If validation passes → proceed
            inventory.put(request.roomType, inventory.get(request.roomType) - 1);

            System.out.println("✅ Booking Confirmed for " + request.customerName +
                    " (" + request.roomType + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("❌ Booking Failed: " + e.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        // Test cases
        BookingRequest b1 = new BookingRequest("Alice", "Single");   // valid
        BookingRequest b2 = new BookingRequest("", "Double");        // invalid name
        BookingRequest b3 = new BookingRequest("Bob", "Luxury");     // invalid type
        BookingRequest b4 = new BookingRequest("Charlie", "Suite");  // no inventory

        service.processBooking(b1);
        service.processBooking(b2);
        service.processBooking(b3);
        service.processBooking(b4);

        service.displayInventory();
    }
}