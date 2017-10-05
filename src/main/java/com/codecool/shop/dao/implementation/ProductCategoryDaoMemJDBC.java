package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.databaseConnection.DatabaseConnection;
import com.codecool.shop.databaseConnection.ExecuteQuery;
import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMemJDBC implements ProductCategoryDao {

    private List<ProductCategory> DATA = new ArrayList<>();
    private static ProductCategoryDaoMemJDBC instance = null;
    private Connection dbConnection = DatabaseConnection.getConnection();

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMemJDBC() throws SQLException {
    }

    public static ProductCategoryDaoMemJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductCategoryDaoMemJDBC();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) throws SQLException, IOException {
        PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM prod_cat WHERE id=?;");
        statement.setInt(1,id);
        ExecuteQuery select = new ExecuteQuery(statement);
        select.process();
        String name = select.getDatabaseData().get(id).get(1);
        String  department = select.getDatabaseData().get(id).get(2);
        String description = select.getDatabaseData().get(id).get(3);
        ProductCategory foundProduct = new ProductCategory(name, department, description);
        foundProduct.setId(id);
        return foundProduct;
    }

    @Override
    public void remove(int id) throws SQLException, IOException {
        PreparedStatement statement = dbConnection.prepareStatement("DELETE * FROM prod_cat WHERE id =?;");
        statement.setInt(1,id);
        ExecuteQuery delete = new ExecuteQuery(statement);
        delete.process();
        DATA.remove(id);

    }

    @Override
    public List<ProductCategory> getAll() throws SQLException, IOException {
        DATA.clear();
        PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM prod_cat;");
        ExecuteQuery select = new ExecuteQuery(statement);
        select.process();
        for (ArrayList<String> array : select.getDatabaseData().values()) {
            ProductCategory newProductCategory = new ProductCategory(array.get(1),array.get(2), array.get(3));
            newProductCategory.setId(Integer.valueOf(array.get(0)));
            DATA.add(newProductCategory);

        }
        return DATA;
    }
}
