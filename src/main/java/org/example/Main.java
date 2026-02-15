package org.example;

import org.example.io.CSVProductReader;
import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=PC2DB;"
                + "trustServerCertificate=true";

        String user = "Dave";
        String password = "Dave123";

        Scanner scanner = new Scanner(System.in);

        // In-memory product lists
        List<Product> electronicsList = new ArrayList<>();
        List<Product> clothingList = new ArrayList<>();

        boolean exit = false;

        while (!exit) {
            // Display menu
            System.out.println("\n==== Inventory System ====");
            System.out.println("1. Import Electronics CSV");
            System.out.println("2. Import Clothing CSV");
            System.out.println("3. Display Electronics");
            System.out.println("4. Display Clothing");
            System.out.println("5. Compute Total Inventory Value");
            System.out.println("6. Exit");
            System.out.print("Select option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number 1-6.");
                continue;
            }

            switch (option) {
                case 1:
                    // Import Electronics CSV
                    try {
                        List<Product> importedElectronics = CSVProductReader.readElectronics("electronics/Electronics.csv");
                        electronicsList.clear();
                        electronicsList.addAll(importedElectronics);
                        System.out.println("Imported " + importedElectronics.size() + " electronics products.");
                    } catch (Exception e) {
                        System.out.println("Error importing electronics CSV: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Import Clothing CSV
                    try {
                        List<Product> importedClothing = CSVProductReader.readClothing("clothing/Clothing.csv");
                        clothingList.clear();
                        clothingList.addAll(importedClothing);
                        System.out.println("Imported " + importedClothing.size() + " clothing products.");
                    } catch (Exception e) {
                        System.out.println("Error importing clothing CSV: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Display Electronics
                    if (electronicsList.isEmpty()) {
                        System.out.println("No electronics products imported yet.");
                    } else {
                        System.out.println("\n--- Electronics Products ---");
                        displayProducts(electronicsList); // Call the formatted method
                    }
                    break;

                case 4:
                    // Display Clothing
                    if (clothingList.isEmpty()) {
                        System.out.println("No clothing products imported yet.");
                    } else {
                        System.out.println("\n--- Clothing Products ---");
                        displayProducts(clothingList); // Call the formatted method
                    }
                    break;

                case 5:
                    // Compute Total Inventory Value
                    if (electronicsList.isEmpty() && clothingList.isEmpty()) {
                        System.out.println("No products imported yet.");
                    } else {
                        double totalValue = 0.0;

                        for (Product p : electronicsList) {
                            totalValue += p.getInventoryValue();
                        }
                        for (Product p : clothingList) {
                            totalValue += p.getInventoryValue();
                        }

                        System.out.printf("Total inventory value: %.2f%n", totalValue);
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting Inventory System. Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option, please enter a number 1-6.");
            }
        }

        scanner.close();
    }

    // Method to display products in a formatted table
    public static void displayProducts(List<Product> products) {
        // Print header with column names
        System.out.printf("%-15s %-25s %-10s %-15s %-15s %-15s\n", "Category", "Product Name", "Unit Cost", "Margin", "Quantity", "Unit Price");

        // Print separator line
        System.out.println("-----------------------------------------------------------------------------------------------");

        // Print each product in a formatted line
        for (Product p : products) {
            double unitPrice = p.getUnitPrice(); // Assuming this method exists
            System.out.printf("%-15s %-25s %-10.2f %-15.2f %-15d %-15.2f\n",
                    p.getCategory(),
                    p.getProdName(),  // Use getProdName() here
                    p.getUnitCost(),
                    p.getMargin() * 100,  // Margin is a decimal (0.75 -> 75%)
                    p.getQuantity(),
                    unitPrice);
        }
    }
}
