package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {
    private static ProductCategory testProductCategory = new ProductCategory("testName", "testDep", "testDesc");
    private static Supplier testSupplier = new Supplier("testSup", "testDesc");
    private static ProductDaoMem productDao = ProductDaoMem.getInstance();
    private static Product productTest = new Product("testName", 5, "USD","testDescription", testProductCategory, testSupplier);
    List<Product> DATA = productDao.getAll();

    @BeforeEach
    void clearDATA(){
        DATA.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        ProductDaoMem testProduct = ProductDaoMem.getInstance();
        assertEquals(productDao, testProduct);
    }

    @Test
    void testAddAddsToDATAList() {
        productDao.add(productTest);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);
    }

    @Test
    void testFindFindsTheProduct() {
        productDao.add(productTest);
        Product supplierFound = productDao.find(1);
        assertEquals(productTest, supplierFound);
    }

    @Test
    void testRemoveRemovesProductFromDATA() {
        productDao.add(productTest);
        productDao.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<Product> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }

    @Test
    void testGetByReturnsProductListBySupplier() {
        productDao.add(productTest);
        List<Product> testGetBySupplier = productDao.getBy(testSupplier);
        List<Product> control = new ArrayList<>();
        control.add(productTest);
        assertEquals(control, testGetBySupplier);
    }

    @Test
    void testGetByReturnsProductListByProductCategory() {
        productDao.add(productTest);
        List<Product> testGetByProdCat = productDao.getBy(testProductCategory);
        List<Product> control = new ArrayList<>();
        control.add(productTest);
        assertEquals(control, testGetByProdCat);
    }

}