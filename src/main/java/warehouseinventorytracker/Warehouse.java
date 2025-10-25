package warehouseinventorytracker;

import java.util.*;

public class Warehouse {
    private final String name;
    private final Map<Integer, Product> inventory = new HashMap<>();
    private final AlertService alertService;

    public Warehouse(String name, AlertService alertService) {
        this.name = name;
        this.alertService = alertService;
    }

    public String getName() { return name; }

    public synchronized void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println("❌ Product ID exists: " + product.getId());
        } else {
            inventory.put(product.getId(), product);
            System.out.println("✅ Product added: " + product.getName());
        }
    }

    public synchronized void receiveShipment(int id, int qty) {
        Product p = inventory.get(id);
        if (p != null) {
            p.increase(qty);
            System.out.println("📦 Shipment received: " + qty + " units of " + p.getName());
        } else System.out.println("❌ Product ID not found!");
    }

    public synchronized void fulfillOrder(int id, int qty) {
        Product p = inventory.get(id);
        if (p != null) {
            if (p.decrease(qty)) {
                System.out.println("🛒 Order fulfilled: " + qty + " units of " + p.getName());
                if (p.getQuantity() < p.getThreshold()) alertService.onLowStock(p, name);
            } else {
                System.out.println("❌ Insufficient stock for " + p.getName());
            }
        } else System.out.println("❌ Product ID not found!");
    }

    public synchronized List<Product> getProducts() {
        return new ArrayList<>(inventory.values());
    }

    public synchronized void showAllProducts() {
        System.out.println("\nInventory (" + name + "):");
        if (inventory.isEmpty()) System.out.println("(empty)");
        else inventory.values().forEach(p -> System.out.println("ID:" + p.getId() + " | " + p.getName() + " | Qty:" + p.getQuantity() + " | Th:" + p.getThreshold()));
    }
}
