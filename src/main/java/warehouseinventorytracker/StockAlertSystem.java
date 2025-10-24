
package warehouseinventorytracker;



    public class StockAlertSystem implements AlertService {
    @Override
    public void onLowStock(Product product, String warehouseName) {
        System.out.println(" Alert: Low stock for " + product.getName() + warehouseName + " Warehouse - only " + " in " + warehouseName + " warehouse - only " + product.getQuantity() + " left!");
    }
}

