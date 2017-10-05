package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemJDBCTest {

    ProductCategoryDaoMemJDBC prodCatJDBCtest = ProductCategoryDaoMemJDBC.getInstance();
    ProductCategory prodCatTest = new ProductCategory("Hats", "dep", "null");
    List<ProductCategory> DATA = prodCatJDBCtest.getAll();

    ProductCategoryDaoMemJDBCTest() throws SQLException {
    }


    @BeforeEach
    void clearDATA() {
        DATA.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() throws SQLException {
        ProductCategoryDaoMemJDBC testSupplier = ProductCategoryDaoMemJDBC.getInstance();
        assertEquals(prodCatJDBCtest, testSupplier);
    }

    @Test
    void testAddAddsToDATAList() {
        prodCatJDBCtest.add(prodCatTest);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);

    }

    @Test
    void testFindFindsTheProdCat() {
        prodCatJDBCtest.add(prodCatTest);
        List<ProductCategory> testList = new ArrayList<>();
        ProductCategory prodCatFound = prodCatJDBCtest.find(1);
        testList.add(prodCatFound);
        assertEquals(prodCatTest.toString(), prodCatFound.toString());
    }

    @Test
    void testRemoveRemovesProdCatFromDATA() {
        prodCatJDBCtest.add(prodCatTest);
        prodCatJDBCtest.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<ProductCategory> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }
}