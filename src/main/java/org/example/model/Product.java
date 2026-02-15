package org.example.model;

public abstract class Product {
    protected String category;
    protected String prodName;  // Renamed from 'name' to 'prodName'
    protected int quantity;
    protected double unitCost;  // Cost to the inventory owner
    protected double margin;    // Margin for pricing

    // Constructor
    public Product(String category, String prodName, int quantity, double unitCost, double margin) {
        this.category = category;
        this.prodName = prodName;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.margin = margin;
    }

    // Getters
    public String getCategory() { return category; }
    public String getProdName() { return prodName; }  // Renamed from 'getName()' to 'getProdName()'
    public int getQuantity() { return quantity; }
    public double getUnitCost() { return unitCost; }
    public double getMargin() { return margin; }

    // Compute inventory value (total cost)
    public double getInventoryValue() {
        return unitCost * quantity;
    }

    // Abstract method to calculate unit price based on margin
    public abstract double getUnitPrice();

    @Override
    public String toString() {
        return category + " | " + prodName + " | " + quantity + " | " + unitCost + " | " + margin;
    }
}
