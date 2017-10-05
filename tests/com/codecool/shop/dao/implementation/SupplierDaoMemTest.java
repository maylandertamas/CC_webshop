package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    private static SupplierDaoMem supplierDao = SupplierDaoMem.getInstance();
    private static Supplier supplierTest = new Supplier("testName", "testDescription");
    List<Supplier> DATA = supplierDao.getAll();

    @BeforeEach
    void clearDATA() {
        DATA.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        SupplierDaoMem testSupplier = SupplierDaoMem.getInstance();
        assertEquals(supplierDao, testSupplier);
    }

    @Test
    void testAddAddsToDATAList() {
        supplierDao.add(supplierTest);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);

    }

    @Test
    void testFindFindsTheSupplier() {
        supplierDao.add(supplierTest);
        Supplier supplierFound = supplierDao.find(1);
        assertEquals(supplierTest, supplierFound);
    }

    @Test
    void testRemoveRemovesSupplierFromDATA() {
        supplierDao.add(supplierTest);
        supplierDao.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<Supplier> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }

}