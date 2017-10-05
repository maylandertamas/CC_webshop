package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOMemJBDCTest {

    private static ProductDAOMemJBDC productJDBC = ProductDAOMemJBDC.getInstance();
    private static ProductCategory testProductCategory = new ProductCategory("testName", "testDep", "testDesc");
    private static Supplier testSupplier = new Supplier("testSup", "testDesc");
    private static Product testProduct = new Product(
            "testProduct",
            Float.valueOf("1.01"),
            "USD",
            "prodDesc",
            testProductCategory,
            testSupplier
            );
    List<Product> DATA = productJDBC.getAll();

    ProductDAOMemJBDCTest() throws IOException, SQLException {
    }

    @BeforeEach
    void setUp() {
        DATA.clear();
    }

    @Test
    void testToGetProperInstance() {
        String expected = "com.codecool.shop.dao.implementation.ProductDAOMemJBDC";
        Class productDAOJDBCClass = productJDBC.getClass();
        assertAll(
                "Should return a product instance",
                () -> assertEquals(expected, productDAOJDBCClass.getName())
        );
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        ProductDAOMemJBDC testProduct = ProductDAOMemJBDC.getInstance();
        assertEquals(productJDBC, testProduct);
    }

    @Test
    void testAddAddsToDATAList() throws IOException, SQLException {
        productJDBC.add(testProduct);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);
    }

    @Test
    void testFindFindsTheProduct() throws IOException, SQLException {
        productJDBC.add(testProduct);
        Product supplierFound = productJDBC.find(1);
        assertEquals(testProduct, supplierFound);
    }

    @Test
    void testRemoveRemovesProductFromDATA() throws IOException, SQLException {
        productJDBC.add(testProduct);
        productJDBC.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<Product> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }

    @Test
    void testGetByReturnsProductListBySupplier() throws IOException, SQLException {
        productJDBC.add(testProduct);
        List<Product> testGetBySupplier = productJDBC.getBy(testSupplier);
        List<Product> control = new ArrayList<>();
        control.add(testProduct);
        assertEquals(control, testGetBySupplier);
    }

    @Test
    void testGetByReturnsProductListByProductCategory() throws IOException, SQLException {
        productJDBC.add(testProduct);
        List<Product> testGetByProdCat = productJDBC.getBy(testProductCategory);
        List<Product> control = new ArrayList<>();
        control.add(testProduct);
        assertEquals(control, testGetByProdCat);
    }

}