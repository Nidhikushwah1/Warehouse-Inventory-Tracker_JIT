
package warehouseinventorytracker;


import java.util.HashMap;

public class Warehouse {
    private HashMap<Integer, Product> inventory;
    private AlertService alertService;

    public Warehouse(AlertService alertService) {
        this.inventory = new HashMap<>();
        this.alertService = alertService;
    }

    
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println("❌ Product already exists!");
        } else {
            inventory.put(product.getId(), product);
            System.out.println("✅ Product added: " + product.getName() + " (Threshold: " + product.getReorderThreshold() + ")");
        }
    }

    
    public void receiveShipment(int productId, int quantity) {
        Product p = inventory.get(productId);
        if (p != null) {
            p.setQuantity(p.getQuantity() + quantity);
            System.out.println("📦 Shipment received: " + quantity + " added → Total = " + p.getQuantity());
        } else {
            System.out.println("❌ Invalid product ID!");
        }
    }

    
    public void fulfillOrder(int productId, int quantity) {
        Product p = inventory.get(productId);
        if (p == null) {
            System.out.println("❌ Invalid product ID!");
            return;
        }

        if (p.getQuantity() < quantity) {
            System.out.println("⚠️ Not enough stock to fulfill the order for " + p.getName());
            return;
        }

        p.setQuantity(p.getQuantity() - quantity);
        System.out.println("🛒 Order fulfilled: " + quantity + " removed → Remaining = " + p.getQuantity());

        
        if (p.getQuantity() < p.getReorderThreshold()) {
            alertService.onLowStock(p);
        }
    }

   
    public void showAllProducts() {
        System.out.println("\n📋 Current Inventory:");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }
}

