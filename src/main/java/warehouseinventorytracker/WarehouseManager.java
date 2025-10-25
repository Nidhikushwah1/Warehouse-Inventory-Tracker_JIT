package warehouseinventorytracker;

import java.util.*;

public class WarehouseManager {
    private Map<String, Warehouse> warehouses = new HashMap<>();
    private AlertService alertService;

    public WarehouseManager(AlertService alertService) {
        this.alertService = alertService;
    }

    public void addWarehouse(String name) {
        if (!warehouses.containsKey(name)) {
            warehouses.put(name, new Warehouse(name, alertService));
            System.out.println("Warehouse added: " + name);
        } else {
            System.out.println("Warehouse already exists!");
        }
    }

    public Warehouse getWarehouse(String name) {
        return warehouses.get(name);
    }

    public void showAllWarehouses() {
        System.out.println("\nAll Warehouses:");
        warehouses.keySet().forEach(name -> System.out.println("➡️ " + name));
    }

    public Map<String, List<Product>> getDataMap() {
        Map<String, List<Product>> map = new HashMap<>();
        for (String name : warehouses.keySet()) {
            map.put(name, warehouses.get(name).getProducts());
        }
        return map;
    }
}
