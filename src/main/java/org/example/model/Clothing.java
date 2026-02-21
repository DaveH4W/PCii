package org.example.model;

public class Clothing extends Product {

    public Clothing(String category, String prodName, int quantity, double unitCost, double margin) {
        super(category, prodName, quantity, unitCost, margin);
    }

    @Override
    public double getUnitPrice() {
        return unitCost * (1 + margin); // margin applies
    }

    public String getProductType() {
        return "Clothing";
    }

    @Override
    public double getShippingCost(double volume) {
        final double costPerUnitVolume = 1.0; // placeholder packing factor
        return volume * costPerUnitVolume * quantity; // scale by quantity
    }
}
