package org.example.model;

public class Electronics extends Product {

    // Constructor
    public Electronics(String category, String prodName, int quantity, double unitCost, double margin) {
        super(category, prodName, quantity, unitCost, margin);
    }

    // Override getUnitPrice to calculate based on the margin (50% for Electronics)
    @Override
    public double getUnitPrice() {
        return unitCost * (1 + margin);  // margin here is 0.50 (50%)
    }

    // Optional method for additional subclass-specific functionality
    public String getProductType() {
        return "Electronics";
    }
}
