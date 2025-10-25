package warehouseinventorytracker;

public class WarehouseTask implements Runnable {
    private final Warehouse warehouse;
    private final int productId;
    private final int quantity;
    private final boolean isShipment;

    public WarehouseTask(Warehouse warehouse, int productId, int quantity, boolean isShipment) {
        this.warehouse = warehouse;
        this.productId = productId;
        this.quantity = quantity;
        this.isShipment = isShipment;
    }

    @Override
    public void run() {
        if (isShipment) warehouse.receiveShipment(productId, quantity);
        else warehouse.fulfillOrder(productId, quantity);
        FileHandler.saveWarehouse(warehouse); // save after operation
    }
}
