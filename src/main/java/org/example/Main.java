package org.example;

import org.example.io.CSVProductReader;
import org.example.model.Product;
import org.example.model.Electronics;
import org.example.model.Clothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // In-memory product lists
        List<Product> electronicsList = new ArrayList<>();
        List<Product> clothingList = new ArrayList<>();

        boolean exit = false;

        while (!exit) {
            // Menu
            System.out.println("\n==== Inventory System ====");
            System.out.println("1. Import Electronics CSV");
            System.out.println("2. Import Clothing CSV");
            System.out.println("3. Display Electronics");
            System.out.println("4. Display Clothing");
            System.out.println("5. Compute Total Inventory Value");
            System.out.println("6. Display Packing Costs");
            System.out.println("7. Exit");

            System.out.print("Select option: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number 1-7.");
                continue;
            }

            switch (option) {
                case 1:
                    List<Product> importedElectronics = CSVProductReader.readElectronics("electronics/Electronics.csv");
                    electronicsList.clear();
                    electronicsList.addAll(importedElectronics);
                    System.out.println("Imported " + importedElectronics.size() + " electronics products.");
                    break;

                case 2:
                    List<Product> importedClothing = CSVProductReader.readClothing("clothing/Clothing.csv");
                    clothingList.clear();
                    clothingList.addAll(importedClothing);
                    System.out.println("Imported " + importedClothing.size() + " clothing products.");
                    break;

                case 3:
                    if (electronicsList.isEmpty()) {
                        System.out.println("No electronics products imported yet.");
                    } else {
                        System.out.println("\n--- Electronics Products ---");
                        displayProducts(electronicsList);
                    }
                    break;

                case 4:
                    if (clothingList.isEmpty()) {
                        System.out.println("No clothing products imported yet.");
                    } else {
                        System.out.println("\n--- Clothing Products ---");
                        displayProducts(clothingList);
                    }
                    break;

                case 5:
                    double totalValue = 0.0;
                    for (Product p : electronicsList) totalValue += p.getInventoryValue();
                    for (Product p : clothingList) totalValue += p.getInventoryValue();
                    System.out.printf("Total inventory value: %.2f%n", totalValue);
                    break;


                case 6:
                    // Display Packing Costs
                    double electronicsUnitWeight = 2.0; // default
                    double clothingUnitVolume = 0.03;   // default

                    if (!electronicsList.isEmpty()) {
                        System.out.print("Enter per-unit weight for Electronics (kg): ");
                        try {
                            electronicsUnitWeight = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, defaulting to 2.0 kg");
                        }
                        displayPackingCosts("Electronics", electronicsList, electronicsUnitWeight);
                    }

                    if (!clothingList.isEmpty()) {
                        System.out.print("Enter per-unit volume for Clothing (m³): ");
                        try {
                            clothingUnitVolume = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, defaulting to 0.03 m³");
                        }
                        displayPackingCosts("Clothing", clothingList, clothingUnitVolume);
                    }
                    break;

                case 7:
                    System.out.println("Exiting Inventory System. Goodbye!");
                    exit = true;
                    break;


                default:
                    System.out.println("Invalid option, please enter a number 1-7.");
            }
        }

        scanner.close();
    }

    public static void displayProducts(List<Product> products) {
        System.out.printf("%-15s %-25s %-10s %-15s %-15s %-15s\n",
                "Category", "Product Name", "Unit Cost", "Margin", "Quantity", "Unit Price");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Product p : products) {
            System.out.printf("%-15s %-25s %-10.2f %-15.2f %-15d %-15.2f\n",
                    p.getCategory(),
                    p.getProdName(),
                    p.getUnitCost(),
                    p.getMargin() * 100,
                    p.getQuantity(),
                    p.getUnitPrice());
        }
    }

    public static void displayPackingCosts(String category, List<Product> products, double unitMeasure) {
        System.out.println("\n--- " + category + " Packing Costs ---");
        double totalPackingUnits = 0.0;

        for (Product p : products) {
            double packingCost;
            if (p instanceof Electronics) {
                packingCost = ((Electronics) p).getShippingCost(unitMeasure);
            } else if (p instanceof Clothing) {
                packingCost = ((Clothing) p).getShippingCost(unitMeasure);
            } else {
                packingCost = 0.0;
            }

            System.out.printf("%-25s %-10d units: %.2f packing units\n",
                    p.getProdName(), p.getQuantity(), packingCost);

            totalPackingUnits += packingCost;
        }

        System.out.printf("Total %s packing units: %.2f\n", category, totalPackingUnits);
    }
}
