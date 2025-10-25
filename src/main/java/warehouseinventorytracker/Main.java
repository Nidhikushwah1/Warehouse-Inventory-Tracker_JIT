package warehouseinventorytracker;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AlertService alert = new StockAlertSystem();
        WarehouseManager manager = new WarehouseManager(alert);

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add Warehouse\n2. Add Product\n3. Receive Shipment\n4. Fulfill Order\n5. Show Inventory\n6. Show Warehouses\n7. Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> {
                    System.out.print("Warehouse name: ");
                    String wname = sc.nextLine();
                    manager.addWarehouse(wname);
                }
                case 2 -> {
                    System.out.print("Warehouse name: ");
                    String wname = sc.nextLine();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("❌ Not found"); break; }
                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Product Name: "); sc.nextLine(); String pname = sc.nextLine();
                    System.out.print("Quantity: "); int qty = sc.nextInt();
                    System.out.print("Threshold: "); int th = sc.nextInt();
                    w.addProduct(new Product(id, pname, qty, th));
                    FileHandler.saveWarehouse(w);
                }
                case 3 -> {
                    System.out.print("Warehouse name: "); String wname = sc.nextLine();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("❌ Not found"); break; }
                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Quantity to add: "); int qty = sc.nextInt();
                    new Thread(new WarehouseTask(w, id, qty, true)).start();
                }
                case 4 -> {
                    System.out.print("Warehouse name: "); String wname = sc.nextLine();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) { System.out.println("❌ Not found"); break; }
                    System.out.print("Product ID: "); int id = sc.nextInt();
                    System.out.print("Quantity to fulfill: "); int qty = sc.nextInt();
                    new Thread(new WarehouseTask(w, id, qty, false)).start();
                }
                case 5 -> {
                    System.out.print("Warehouse name: "); String wname = sc.nextLine();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w != null) w.showAllProducts();
                    else System.out.println("❌ Not found");
                }
                case 6 -> manager.showAllWarehouses();
                case 7 -> { System.out.println("Exiting..."); running = false; }
                default -> System.out.println("❌ Invalid choice");
            }
        }

        sc.close();
    }
}
