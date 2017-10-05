package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.databaseConnection.DatabaseConnection;
import com.codecool.shop.databaseConnection.ExecuteQuery;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDAOMemJBDC implements ProductDao{


    private List<Product> DATA = new ArrayList<>();
    private static ProductDAOMemJBDC instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDAOMemJBDC() {
    }

    public static ProductDAOMemJBDC getInstance() {
        if (instance == null) {
            instance = new ProductDAOMemJBDC();
        }
        return instance;
    }

    @Override
    public void add(Product product) throws SQLException, IOException {
        product.setId(DATA.size() + 1);
        DATA.add(product);
    }

    @Override
    public Product find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<Product> getAll() throws SQLException, IOException {
        DATA.clear();
        Connection db = DatabaseConnection.getConnection();
        PreparedStatement statement = db.prepareStatement("SELECT * FROM products;");
        ExecuteQuery select = new ExecuteQuery(statement);
        select.process();
        for (int i = 1; i <= select.getDatabaseData().size(); i++) {
            ArrayList<String> data = select.getDatabaseData().get(Integer.toString(i));

            String supplierId = data.get(2);
            PreparedStatement supplierStatement = db.prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            supplierStatement.setInt(1,Integer.valueOf(supplierId));
            ExecuteQuery getSupplier = new ExecuteQuery(supplierStatement);
            getSupplier.process();
            ArrayList<String> supplierData = getSupplier.getDatabaseData().get(supplierId);
            Supplier foundSupplier = new Supplier(supplierData.get(1), supplierData.get(2));
            foundSupplier.setId(Integer.valueOf(supplierId));

            String productCategoryId = data.get(1);
            PreparedStatement productCategoryStatement = db.prepareStatement("SELECT * FROM prod_cat WHERE id = ?;");
            productCategoryStatement.setInt(1,Integer.valueOf(productCategoryId));
            ExecuteQuery getProductCategory = new ExecuteQuery(productCategoryStatement);
            getProductCategory.process();
            ArrayList<String> productCategoryData = getProductCategory.getDatabaseData().get(productCategoryId);
            ProductCategory foundProductCategory = new ProductCategory(productCategoryData.get(1),
                                                    productCategoryData.get(2), productCategoryData.get(3));
            foundProductCategory.setId(Integer.valueOf(productCategoryId));

            Product newProduct = new
                    Product(
                    data.get(3),
                    Float.valueOf(data.get(4)),
                    data.get(5),
                    data.get(6),
                    foundProductCategory,
                    foundSupplier);
            newProduct.setId(Integer.valueOf(data.get(0)));
            DATA.add(newProduct);
        }
        return DATA;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return DATA.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return DATA.stream().filter(t -> !t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

}
