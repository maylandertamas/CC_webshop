package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {

    private static ProductCategoryDaoMem prodCatDao = ProductCategoryDaoMem.getInstance();
    private static ProductCategory prodCatTest = new ProductCategory("testName", "testDepartment", "testDescription");
    List<ProductCategory> DATA = prodCatDao.getAll();

    @BeforeEach
    void clearDATA() {
        DATA.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        ProductCategoryDaoMem testProdCat = ProductCategoryDaoMem.getInstance();
        assertEquals(prodCatDao, testProdCat);
    }

    @Test
    void testAddAddsToDATAList() {
        prodCatDao.add(prodCatTest);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);

    }

    @Test
    void testFindFindsTheSupplier() {
        prodCatDao.add(prodCatTest);
        ProductCategory supplierFound = prodCatDao.find(1);
        assertEquals(prodCatTest, supplierFound);
    }

    @Test
    void testRemoveRemovesSupplierFromDATA() {
        prodCatDao.add(prodCatTest);
        prodCatDao.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<ProductCategory> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }

}