
package warehouseinventorytracker;




import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String FILE_NAME = "inventory_data.txt";

    
    public static synchronized void saveData(Map<String, List<Product>> warehouses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String warehouseName : warehouses.keySet()) {
                for (Product p : warehouses.get(warehouseName)) {
                    writer.write(warehouseName + "," + p.getId() + "," + p.getName() + "," + p.getQuantity() + "," + p.getThreshold());
                    writer.newLine();
                }
            }
            System.out.println("Inventory data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println(" Error saving data: " + e.getMessage());
        }
    }

   
    public static synchronized Map<String, List<Product>> loadData() {
        Map<String, List<Product>> data = new HashMap<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return data;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 5) {
                    String warehouseName = arr[0];
                    int id = Integer.parseInt(arr[1]);
                    String name = arr[2];
                    int quantity = Integer.parseInt(arr[3]);
                    int threshold = Integer.parseInt(arr[4]);

                    Product product = new Product(id, name, quantity, threshold);
                    data.putIfAbsent(warehouseName, new ArrayList<>());
                    data.get(warehouseName).add(product);
                }
            }
            System.out.println(" Inventory data loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println(" Error loading data: " + e.getMessage());
        }
        return data;
    }
}

