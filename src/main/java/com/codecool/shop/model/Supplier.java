package com.codecool.shop.model;

import com.codecool.shop.controller.ProductController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    public void setProductsCategory(ArrayList<ProductCategory> productsCategory) {
        this.category = productsCategory;
    }
    public ArrayList getProducts() throws SQLException, IOException {
        List<Product> allProducts =  ProductController.getProductDataStore().getAll();
        ArrayList<Product> result = new ArrayList<>(allProducts.stream()
                .filter(p -> p.getSupplier().getId() == this.getId())
                .collect(Collectors.toList()));
        return result;
    }

    public ArrayList getCategory() throws IOException, SQLException {

        ArrayList<Product> allProducts = this.getProducts();
        Set<Integer> ProductCategoryIds = new HashSet<>();
        ArrayList<Integer> IdsWithoutDuplications = new ArrayList<>();
        ArrayList<ProductCategory> result = new ArrayList<>();
        allProducts.forEach(pc -> ProductCategoryIds.add(pc.getProductCategory().getId()));
        ProductCategoryIds.addAll(ProductCategoryIds);
        IdsWithoutDuplications.addAll(ProductCategoryIds);
        ProductCategoryIds.clear();
        for (ProductCategory prodcat : ProductController.getProductCategoryDataStore().getAll()) {
            if (IdsWithoutDuplications.contains(prodcat.getId())) {
                result.add(prodcat);
            }
        }

        return result;
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