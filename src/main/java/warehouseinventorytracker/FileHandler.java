package warehouseinventorytracker;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static void saveWarehouse(Warehouse w) {
        try {
            String folder = "data" + File.separator + w.getName();
            new File(folder).mkdirs();  // create folder
            File file = new File(folder + File.separator + "inventory.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Product p : w.getProducts()) {
                    writer.write(p.getId() + "," + p.getName() + "," + p.getQuantity() + "," + p.getThreshold());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving warehouse: " + e.getMessage());
        }
    }

    public static Map<String, List<Product>> loadData() {
        Map<String, List<Product>> data = new HashMap<>();
        File base = new File("data");
        if (!base.exists()) return data;

        for (File warehouseDir : base.listFiles(File::isDirectory)) {
            String warehouseName = warehouseDir.getName();
            File file = new File(warehouseDir, "inventory.txt");
            if (!file.exists()) continue;
            List<Product> products = new ArrayList<>();
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
                System.out.println("Error loading warehouse: " + warehouseName);
            }
            data.put(warehouseName, products);
        }
        return data;
    }
}
