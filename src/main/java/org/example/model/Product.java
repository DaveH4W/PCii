package org.example.model;

public abstract class Product {
    protected String category;
    protected String prodName;
    protected int quantity;
    protected double unitCost;
    protected double margin;

    public Product(String category, String prodName, int quantity, double unitCost, double margin) {
        this.category = category;
        this.prodName = prodName;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.margin = margin;
    }

    // Getters
    public String getCategory() { return category; }
    public String getProdName() { return prodName; }
    public int getQuantity() { return quantity; }
    public double getUnitCost() { return unitCost; }
    public double getMargin() { return margin; }

    // Compute inventory value
    public double getInventoryValue() {
        return unitCost * quantity;
    }

    // Abstract method for unit price
    public abstract double getUnitPrice();

    // Abstract method for shipping/packing cost
    public abstract double getShippingCost(double measure);

    @Override
    public String toString() {
        return category + " | " + prodName + " | " + quantity + " | " + unitCost + " | " + margin;
    }
}
