package com.codecool.shop.controller;

import com.codecool.shop.Cart.Cart;
import com.codecool.shop.Cart.CartInterface;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductController {
    private static ProductDao productDataStore;
    private static ProductCategoryDao productCategoryDataStore;
    private static SupplierDao supplierDataStore;
    public static ModelAndView renderProducts(Request req, Response res) throws IOException, SQLException {

        Map params = new HashMap<>();

        String productCategoryId = req.queryParams("cid");
        String supplierId = req.queryParams("sid");
        productDataStore = ProductDAOMemJBDC.getInstance();
        productCategoryDataStore = ProductCategoryDaoMemJDBC.getInstance();
        supplierDataStore = SupplierDaoMemJDBC.getInstance();

        params.put("allCategory", productCategoryDataStore.getAll());
        params.put("allSuppliers", supplierDataStore.getAll());
        if (productCategoryId != null) {
            params.put("category", productCategoryDataStore.find(Integer.valueOf(productCategoryId)));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(Integer.valueOf(productCategoryId))));
        } else if (supplierId != null) {
            params.put("category", supplierDataStore.find(Integer.valueOf(supplierId)).getCategory());
            params.put("products", supplierDataStore.find(Integer.valueOf(supplierId)).getProducts());
        } else {
            params.put("suppliers", supplierDataStore.getAll());
            params.put("category", productCategoryDataStore.getAll());
            params.put("products", productDataStore.getAll());

        }
        CartInterface cart = Cart.getCart();
        params.put("productSumm", cart.summUp());
        params.put("cartContents", cart.getCartContents());
        params.put("cartSize", cart.generateCartSize());
        return new ModelAndView(params, "product/index");
    }

    public static ProductCategoryDao getProductCategoryDataStore() {
        return productCategoryDataStore;
    }

    public static ProductDao getProductDataStore() {
        return productDataStore;
    }
}
