package org.example.model;

public class Clothing extends Product {

    // Constructor
    public Clothing(String category, String prodName, int quantity, double unitCost, double margin) {
        super(category, prodName, quantity, unitCost, margin);
    }

    // Override getUnitPrice to calculate based on the margin (75% for Clothing)
    @Override
    public double getUnitPrice() {
        return unitCost * (1 + margin);  // margin here is 0.75 (75%)
    }

    // Optional method for additional subclass-specific functionality
    public String getProductType() {
        return "Clothing";
    }
}
