package org.example.model;

public class Electronics extends Product {

    public Electronics(String category, String prodName, int quantity, double unitCost, double margin) {
        super(category, prodName, quantity, unitCost, margin);
    }

    @Override
    public double getUnitPrice() {
        return unitCost * (1 + margin); // margin applies
    }

    public String getProductType() {
        return "Electronics";
    }

    @Override
    public double getShippingCost(double weight) {
        final double costPerUnitWeight = 1.0; // placeholder packing factor
        return weight * costPerUnitWeight * quantity; // scale by quantity
    }
}
