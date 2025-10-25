
package warehouseinventorytracker;



   

public class StockAlertSystem implements AlertService {

    @Override
    public synchronized void onLowStock(Product product, String warehouseName) {
        System.out.println(" ALERT: Low stock for " + product.getName() + 
                           " in " + warehouseName + " — only " + product.getQuantity() + " left!");
    }
}


