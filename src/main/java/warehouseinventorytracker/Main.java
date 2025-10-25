package warehouseinventorytracker;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        AlertService alertService = new StockAlertSystem();
        WarehouseManager manager = new WarehouseManager(alertService);

        // Load existing warehouses & products
        Map<String, List<Product>> loadedData = FileHandler.loadData();
        for (String wname : loadedData.keySet()) {
            manager.addWarehouse(wname);
            Warehouse w = manager.getWarehouse(wname);
            for (Product p : loadedData.get(wname)) {
                w.addProduct(p);
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1. Add Warehouse");
            System.out.println("2. Add Product");
            System.out.println("3. Receive Shipment");
            System.out.println("4. Fulfill Order");
            System.out.println("5. Show Inventory");
            System.out.println("6. Show Warehouses");
            System.out.println("7. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter warehouse name: ");
                    String wname = sc.next();
                    manager.addWarehouse(wname);
                    Warehouse w = manager.getWarehouse(wname);
                    FileHandler.saveWarehouse(w); // save empty warehouse
                }
                case 2 -> {
                    System.out.print("Enter warehouse name: ");
                    String wname = sc.next();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("Warehouse not found!"); break; }

                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Product Name: "); String pname = sc.next();
                    System.out.print("Quantity: "); int qty = sc.nextInt();
                    System.out.print("Threshold: "); int th = sc.nextInt();
                    w.addProduct(new Product(id, pname, qty, th));
                    FileHandler.saveWarehouse(w);
                }
                case 3 -> {
                    System.out.print("Enter warehouse name: ");
                    String wname = sc.next();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("Warehouse not found!"); break; }

                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Quantity to add: "); int qty = sc.nextInt();
                    new Thread(() -> {
                        w.receiveShipment(id, qty);
                        FileHandler.saveWarehouse(w);
                    }).start();
                }
                case 4 -> {
                    System.out.print("Enter warehouse name: ");
                    String wname = sc.next();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("Warehouse not found!"); break; }

                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Quantity to fulfill: "); int qty = sc.nextInt();
                    new Thread(() -> {
                        w.fulfillOrder(id, qty);
                        FileHandler.saveWarehouse(w);
                    }).start();
                }
                case 5 -> {
                    System.out.print("Enter warehouse name: ");
                    String wname = sc.next();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w != null) w.showAllProducts();
                    else System.out.println("Warehouse not found!");
                }
                case 6 -> manager.showAllWarehouses();
                case 7 -> {
                    for (String wname : manager.getDataMap().keySet())
                        FileHandler.saveWarehouse(manager.getWarehouse(wname));
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}
