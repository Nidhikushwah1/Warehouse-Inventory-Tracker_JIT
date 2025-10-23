
package warehouseinventorytracker;



    public class StockAlertSystem implements AlertService {
    @Override
    public void onLowStock(Product product) {
        System.out.println("⚠️ Alert: Low stock for " + product.getName() + " – only " + product.getQuantity() + " left!");
    }
}

