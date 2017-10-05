package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void add(Product product) throws SQLException, IOException;
    Product find(int id);
    void remove(int id);

    List<Product> getAll() throws SQLException, IOException;
    List<Product> getBy(Supplier supplier);
    List<Product> getBy(ProductCategory productCategory);

}
