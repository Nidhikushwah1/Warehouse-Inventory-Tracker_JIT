
package warehouseinventorytracker;


public class Main {
    public static void main(String[] args) {
        
        AlertService alertService = new StockAlertSystem();

        WarehouseManager manager = new WarehouseManager();
        
        Warehouse delhi = new Warehouse("Delhi", alertService);
        Warehouse mumbai = new Warehouse("Mumbai", alertService);
         
        manager.addWarehouse(delhi);
        manager.addWarehouse(mumbai);

        
        

        
        Product laptop = new Product(1, "Laptop", 0, 5);
        Product phone = new Product(2, "Phone", 3, 2);
        delhi.addProduct(laptop);
        mumbai.addProduct(phone);

       
        delhi.receiveShipment(1, 10);
        delhi.fulfillOrder(1, 6); 

       
        mumbai.receiveShipment(2, 5);
        mumbai.fulfillOrder(2, 7); 

        
        delhi.showAllProducts();
        mumbai.showAllProducts();

        
     
       
        manager.showAllWarehouses();
    }
}

