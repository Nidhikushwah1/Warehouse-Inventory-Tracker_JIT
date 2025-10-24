
package warehouseinventorytracker;

import java.util.HashMap;

public class WarehouseManager {
    private HashMap<String, Warehouse> warehouses;

    public WarehouseManager() {
        this.warehouses = new HashMap<>();
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouses.put(warehouse.getName(), warehouse);
    }

    public Warehouse getWarehouse(String name) {
        return warehouses.get(name);
    }

    public void showAllWarehouses() {
        System.out.println("\n All Warehouses:");
        for (String name : warehouses.keySet()) {
            System.out.println("- " + name);
        }
    }
}
