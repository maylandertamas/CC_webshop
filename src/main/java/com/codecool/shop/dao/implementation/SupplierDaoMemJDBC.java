package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.SupplierDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierDaoMemJDBC implements SupplierDao {
    private List<Supplier> DATA = new ArrayList<>();
    private static SupplierDaoMemJDBC instance = null;

    private SupplierDaoMemJDBC() {
    }

    public static SupplierDaoMemJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMemJDBC();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(DATA.size() + 1);
        DATA.add(supplier);
    }

    @Override
    public Supplier find(int id) {
        Supplier foundSupplier = null;
        try {
            Connection db = DatabaseConnection.getConnection();
            PreparedStatement statement = db.prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            statement.setInt(1, id);
            ExecuteQuery select = new ExecuteQuery(statement);
            select.process();
            HashMap<String, ArrayList<String>> result = select.getDatabaseData();
            String name = result.get(String.valueOf(id)).get(1);
            String description = result.get(String.valueOf(id)).get(2);
            foundSupplier = new Supplier(name, description);
            foundSupplier.setId(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundSupplier;
    }

    @Override
    public void remove(int id) {
        try {
            Connection dbConnection = DatabaseConnection.getConnection();
            PreparedStatement statement = dbConnection.prepareStatement("DELETE * FROM supplier WHERE id =?;");
            statement.setInt(1, id);
            ExecuteQuery delete = new ExecuteQuery(statement);
            delete.process();
            DATA.remove(id);
        } catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        DATA.clear();
        try {
            Connection db = DatabaseConnection.getConnection();
            PreparedStatement statement = db.prepareStatement("SELECT supplier.id, supplier.name, supplier.description," +
                                                    " products.productcatid, products.supplierid FROM supplier " +
                                                    "INNER JOIN products ON supplier.id = products.supplierid;");
            ExecuteQuery select = new ExecuteQuery(statement);
            select.process();
            for (ArrayList<String> array: select.getDatabaseData().values()){
                Supplier newSupplier = new Supplier(array.get(1), array.get(2));
                newSupplier.setId(Integer.valueOf(array.get(0)));
                DATA.add(newSupplier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DATA;
    }
}