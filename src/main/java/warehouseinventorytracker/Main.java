
package warehouseinventorytracker;


public class Main {
    public static void main(String[] args) {
        
        AlertService alertService = new StockAlertSystem();

        
        Warehouse warehouse = new Warehouse(alertService);

        
        Product laptop = new Product(1, "Laptop", 0, 5);
        warehouse.addProduct(laptop);

       
        warehouse.receiveShipment(1, 10); 

        
        warehouse.fulfillOrder(1, 6); 

     
        warehouse.showAllProducts();
    }
}

