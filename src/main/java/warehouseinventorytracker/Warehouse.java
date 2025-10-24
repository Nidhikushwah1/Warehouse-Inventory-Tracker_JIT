
package warehouseinventorytracker;


import java.util.HashMap;

public class Warehouse {
    private String name;
    private HashMap<Integer, Product> inventory;
    private transient AlertService alertService;
 

    public Warehouse( String name ,AlertService alertService) {
        this.name= name;
        this.inventory = new HashMap<>();
        this.alertService = alertService;
    }

    public String getName(){
        return name;
    }
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getId())) {
            System.out.println(" Product already exists in " + name + " warehouse!");
        } else {
            inventory.put(product.getId(), product);
            System.out.println(" Product added: " + name + " warehouse: " + product.getName()) ;
    }
        System.out.println("=====================================================");
    }

    
    public void receiveShipment(int productId, int quantity) {
        Product p = inventory.get(productId);
        if (p != null) {
            p.setQuantity(p.getQuantity() + quantity);
              System.out.println("Shipment received (" + name + "): +" + quantity + " → Total = " + p.getQuantity());
             
        } else {
            System.out.println("Invalid product ID in " + name + " warehouse!");
        }
        System.out.println("================================================");
    }
 
    
    public void fulfillOrder(int productId, int quantity) {
        Product p = inventory.get(productId);
        if (p == null) {
             System.out.println(" Invalid product ID in " + name + "warehouse!");
            return;
        }

        if (p.getQuantity() < quantity) {
            System.out.println(" Not enough stock to fulfill the order for " + p.getName());
            return;
        }

        p.setQuantity(p.getQuantity() - quantity);
        System.out.println("Order fulfilled(" + name + "): -"+ quantity + " removed → Remaining = " + p.getQuantity());

        
        if (p.getQuantity() < p.getReorderThreshold()) {
            alertService.onLowStock(p,name);
        }
        System.out.println("============================================================");
    }

   
    public void showAllProducts() {
        System.out.println("\n Current Inventory" + name + " warehouse:");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }
}  
   


