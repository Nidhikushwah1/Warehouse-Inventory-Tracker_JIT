package warehouseinventorytracker;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandler {

    private static final String BASE_DIR = "data";

    private static String getFilePath(String warehouseName) {
        return BASE_DIR + File.separator + warehouseName + File.separator + "inventory.txt";
    }

    private static void ensureDir(String warehouseName) throws IOException {
        Path dir = Paths.get(BASE_DIR, warehouseName);
        if (!Files.exists(dir)) Files.createDirectories(dir);
    }

    public static synchronized void saveWarehouse(Warehouse warehouse) {
        try {
            ensureDir(warehouse.getName());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(warehouse.getName())))) {
                for (Product p : warehouse.getProducts()) writer.write(p.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving warehouse: " + e.getMessage());
        }
    }

    public static synchronized List<Product> loadWarehouse(String warehouseName) {
        List<Product> products = new ArrayList<>();
        File file = new File(getFilePath(warehouseName));
        if (!file.exists()) return products;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 4) {
                    int id = Integer.parseInt(arr[0]);
                    String name = arr[1];
                    int qty = Integer.parseInt(arr[2]);
                    int th = Integer.parseInt(arr[3]);
                    products.add(new Product(id, name, qty, th));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading warehouse: " + e.getMessage());
        }
        return products;
    }
}
