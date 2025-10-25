package warehouseinventorytracker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private String name;
    private Map<Integer, Product> inventory = new ConcurrentHashMap<>();
    private AlertService alertService;

    public Warehouse(String name, AlertService alertService) {
        this.name = name;
        this.alertService = alertService;
    }

    public String getName() { return name; }

    public synchronized void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println("Product with ID " + product.getId() + " already exists!");
        } else {
            inventory.put(product.getId(), product);
            System.out.println("Product added to " + name + ": " + product.getName());
        }
    }

    public synchronized void receiveShipment(int id, int quantity) {
        Product p = inventory.get(id);
        if (p != null) {
            p.setQuantity(p.getQuantity() + quantity);
            System.out.println("Shipment received: " + quantity + " units of " + p.getName());
        } else {
            System.out.println("Product ID not found!");
        }
    }

    public synchronized void fulfillOrder(int id, int quantity) {
        Product p = inventory.get(id);
        if (p != null) {
            if (p.getQuantity() >= quantity) {
                p.setQuantity(p.getQuantity() - quantity);
                System.out.println("Order fulfilled: " + quantity + " units of " + p.getName());
                if (p.getQuantity() < p.getThreshold()) {
                    alertService.onLowStock(p, name);
                }
            } else {
                System.out.println("Insufficient stock for " + p.getName());
            }
        } else {
            System.out.println("Product ID not found!");
        }
    }

    public List<Product> getProducts() {
        return new ArrayList<>(inventory.values());
    }

    public void showAllProducts() {
        System.out.println("\nInventory (" + name + "):");
        inventory.values().forEach(System.out::println);
    }
}
