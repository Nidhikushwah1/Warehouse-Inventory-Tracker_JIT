package warehouseinventorytracker;

import java.io.File;
import java.util.*;

public class WarehouseManager {
    private final Map<String, Warehouse> warehouses = new HashMap<>();
    private final AlertService alertService;

    public WarehouseManager(AlertService alertService) {
        this.alertService = alertService;
        loadAllWarehouses();
    }

    private void loadAllWarehouses() {
        File base = new File("data");
        if (base.exists() && base.isDirectory()) {
            for (File f : base.listFiles(File::isDirectory)) {
                String name = f.getName();
                Warehouse w = new Warehouse(name, alertService);
                for (Product p : FileHandler.loadWarehouse(name)) w.addProduct(p);
                warehouses.put(name, w);
            }
        }
    }

    public void addWarehouse(String name) {
        if (!warehouses.containsKey(name)) {
            Warehouse w = new Warehouse(name, alertService);
            warehouses.put(name, w);
            FileHandler.saveWarehouse(w); // create empty folder + file
            System.out.println("✅ Warehouse added: " + name);
        } else System.out.println("❌ Warehouse exists!");
    }

    public Warehouse getWarehouse(String name) { return warehouses.get(name); }

    public void showAllWarehouses() {
        System.out.println("\nAll Warehouses:");
        if (warehouses.isEmpty()) System.out.println("(none)");
        else warehouses.keySet().forEach(System.out::println);
    }

    public Collection<Warehouse> getAllWarehouses() { return warehouses.values(); }
}
