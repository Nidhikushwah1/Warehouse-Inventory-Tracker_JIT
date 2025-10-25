
package warehouseinventorytracker;




import java.util.*;

public class Main {
    public static void main(String[] args) {

        AlertService alertService = new StockAlertSystem();
        WarehouseManager manager = new WarehouseManager(alertService);

        
        Map<String, List<Product>> loadedData = FileHandler.loadData();
        for (String name : loadedData.keySet()) {
            Warehouse w = new Warehouse(name, alertService);
            for (Product p : loadedData.get(name)) {
                w.addProduct(p);
            }
            manager.addWarehouse(name);
        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n========= WAREHOUSE INVENTORY TRACKER =========");
            System.out.println("1. Add Warehouse");
            System.out.println("2. Add Product");
            System.out.println("3. Receive Shipment");
            System.out.println("4. Fulfill Order");
            System.out.println("5. Show Products");
            System.out.println("6. Show All Warehouses");
            System.out.println("7. Save Data");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Warehouse name: ");
                    String wname = sc.next();
                    manager.addWarehouse(wname);
                    break;

                case 2:
                    System.out.print("Enter Warehouse name: ");
                    wname = sc.next();
                    Warehouse w = manager.getWarehouse(wname);
                    if (w == null) {
                        System.out.println(" Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter Product ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Product Name: ");
                    String pname = sc.next();
                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    System.out.print("Enter Threshold: ");
                    int th = sc.nextInt();
                    w.addProduct(new Product(id, pname, qty, th));
                    break;

                case 3:
                    System.out.print("Enter Warehouse name: ");
                    wname = sc.next();
                    w = manager.getWarehouse(wname);
                    if (w == null) {
                        System.out.println(" Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter Product ID: ");
                    id = sc.nextInt();
                    System.out.print("Enter Quantity to add: ");
                    qty = sc.nextInt();
                    Thread shipmentThread = new Thread(() -> w.receiveShipment(id, qty));
                    shipmentThread.start();
                    break;

                case 4:
                    System.out.print("Enter Warehouse name: ");
                    wname = sc.next();
                    w = manager.getWarehouse(wname);
                    if (w == null) {
                        System.out.println(" Warehouse not found!");
                        break;
                    }
                    System.out.print("Enter Product ID: ");
                    id = sc.nextInt();
                    System.out.print("Enter Quantity to fulfill: ");
                    qty = sc.nextInt();
                    Thread orderThread = new Thread(() -> w.fulfillOrder(id, qty));
                    orderThread.start();
                    break;

                case 5:
                    System.out.print("Enter Warehouse name: ");
                    wname = sc.next();
                    w = manager.getWarehouse(wname);
                    if (w != null) w.showAllProducts();
                    else System.out.println("Warehouse not found!");
                    break;

                case 6:
                    manager.showAllWarehouses();
                    break;

                case 7:
                    FileHandler.saveData(manager.getDataMap());
                    break;

                case 8:
                    FileHandler.saveData(manager.getDataMap());
                    System.out.println(" Exiting system...");
                    running = false;
                    break;

                default:
                    System.out.println(" Invalid choice!");
            }
        }

        sc.close();
    }
}
