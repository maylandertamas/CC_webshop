package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemJDBCTest {

    private static SupplierDaoMemJDBC supplierJDBC = SupplierDaoMemJDBC.getInstance();
    private static Supplier supplierTest = new Supplier("testName", "testDescription");
    List<Supplier> DATA = supplierJDBC.getAll();

    @BeforeEach
    void clearDATA() {
        DATA.clear();
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        SupplierDaoMem testSupplier = SupplierDaoMem.getInstance();
        assertEquals(supplierJDBC, testSupplier);
    }

    @Test
    void testAddAddsToDATAList() {
        supplierJDBC.add(supplierTest);
        int DATALength = DATA.size();
        int controlLength = 1;
        assertEquals(controlLength, DATALength);

    }

    @Test
    void testFindFindsTheSupplier() {
        supplierJDBC.add(supplierTest);
        List<Supplier> testList = new ArrayList<>();
        Supplier supplierFound = supplierJDBC.find(1);
        testList.add(supplierFound);
        assertEquals(supplierTest, supplierFound);
    }

    @Test
    void testRemoveRemovesSupplierFromDATA() {
        supplierJDBC.add(supplierTest);
        supplierJDBC.remove(1);
        int DATALength = DATA.size();
        assertEquals(0, DATALength);
    }

    @Test
    void testGetAllReturnsArrayList() {
        List<Supplier> testList = new ArrayList<>();
        assertEquals(DATA, testList);
    }

}

