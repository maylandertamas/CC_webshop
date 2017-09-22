package com.codecool.shop.model;

import java.util.ArrayList;


public class Supplier extends BaseModel {
    private ArrayList<Product> products;
    private ArrayList<ProductCategory> category;

    public Supplier(String name, String description) {
        super(name);
        this.products = new ArrayList<>();
        this.category = new ArrayList<>();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public ArrayList getCategory() {
        return this.category;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addCategory(ProductCategory category) {
        if (!this.category.contains(category)) {
            this.category.add(category);
        }
    }

    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}