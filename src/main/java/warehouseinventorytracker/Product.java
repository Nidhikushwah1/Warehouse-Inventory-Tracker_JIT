package warehouseinventorytracker;

public class Product {
    private final int id;
    private final String name;
    private int quantity;
    private final int threshold;

    public Product(int id, String name, int quantity, int threshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public synchronized int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    public synchronized void increase(int amount) {
        if (amount > 0) quantity += amount;
    }

    public synchronized boolean decrease(int amount) {
        if (amount <= 0) return false;
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + quantity + "," + threshold;
    }
}
