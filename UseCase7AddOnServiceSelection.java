import java.util.*;

/**
 * ==============================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ==============================================================
 * Use Case 7: Add-On Service Selection
 *
 * - Supports optional services for a reservation
 * - Uses Map<ReservationID, List<Service>>
 * - Does NOT affect booking or inventory logic
 * ==============================================================
 */

// Represents a service (WiFi, Breakfast, etc.)
class AddOnService {
    String serviceName;
    double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class to handle services
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        for (AddOnService service : getServices(reservationId)) {
            total += service.cost;
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Services for Reservation ID " + reservationId + ":");
        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation IDs (from Use Case 6)
        String res1 = "R101";
        String res2 = "R102";

        // Guest selects services
        manager.addService(res1, new AddOnService("Breakfast", 200));
        manager.addService(res1, new AddOnService("WiFi", 100));
        manager.addService(res1, new AddOnService("Airport Pickup", 500));

        manager.addService(res2, new AddOnService("Dinner", 300));

        // Display services
        System.out.println("\n--- Reservation 1 ---");
        manager.displayServices(res1);

        System.out.println("\n--- Reservation 2 ---");
        manager.displayServices(res2);
    }
}