package org.example.io;

import org.example.model.Clothing;
import org.example.model.Electronics;
import org.example.model.Product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVProductReader {

    /**
     * Reads Electronics products from a CSV file in the resources folder.
     *
     * @param csvFile path relative to resources, e.g., "electronics/Electronics.csv"
     * @return List of Electronics products
     */
    public static List<Product> readElectronics(String csvFile) {
        return readProducts(csvFile, "Electronics");
    }

    /**
     * Reads Clothing products from a CSV file in the resources folder.
     *
     * @param csvFile path relative to resources, e.g., "clothing/Clothing.csv"
     * @return List of Clothing products
     */
    public static List<Product> readClothing(String csvFile) {
        return readProducts(csvFile, "Clothing");
    }

    /**
     * Generic method to read products from CSV, validate, and produce Product subclasses.
     *
     * @param csvFile path relative to resources
     * @param type    "Electronics" or "Clothing"
     * @return List of valid products
     */
    private static List<Product> readProducts(String csvFile, String type) {
        List<Product> products = new ArrayList<>();

        try {
            InputStream is = CSVProductReader.class.getClassLoader().getResourceAsStream(csvFile);
            if (is == null) {
                throw new RuntimeException("Resource not found: " + csvFile);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 5) {
                    System.out.println("Skipping incomplete record (not enough columns): " + line);
                    continue;
                }

                // Trim and extract fields
                String category = fields[0].trim();
                String prodName = fields[1].trim();
                String qtyStr = fields[2].trim();
                String unitCostStr = fields[3].trim();
                String marginStr = fields[4].trim();

                // Validate mandatory fields
                if (category.isEmpty() || prodName.isEmpty()) {
                    System.out.println("Skipping incomplete record (missing mandatory fields): " + line);
                    continue;
                }

                // Parse numeric fields
                int qty = 0;
                double unitCost = 0.0;
                double margin = 0.0;
                try {
                    qty = Integer.parseInt(qtyStr);
                    unitCost = Double.parseDouble(unitCostStr);
                    margin = Double.parseDouble(marginStr) / 100;  // Convert percentage to decimal
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid number record: " + line);
                    continue;
                }

                // Create product object
                Product product;
                if (type.equals("Electronics")) {
                    product = new Electronics(category, prodName, qty, unitCost, margin);
                } else { // Clothing
                    product = new Clothing(category, prodName, qty, unitCost, margin);
                }

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
