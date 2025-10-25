
package warehouseinventorytracker;

  public class Product {
    private int id;
    private String name;
    private int quantity;
    private int threshold;

    public Product(int id, String name, int quantity, int threshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Product ID: " + id +
               ", Name: " + name +
               ", Quantity: " + quantity +
               ", Threshold: " + threshold;
    }
}
