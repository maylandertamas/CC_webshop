package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(int id) throws SQLException, IOException;
    void remove(int id) throws IOException, SQLException;

    List<ProductCategory> getAll() throws SQLException, IOException;

}
