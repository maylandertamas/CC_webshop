package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        productCategoryDataStore.getAll().size();
        for (int i = 1; i < productCategoryDataStore.getAll().size()+1; i++) {
            params.put(productCategoryDataStore.find(i).getName() + "category", productCategoryDataStore.find(i));
            params.put(productCategoryDataStore.find(i).getName() + "products", productDataStore.getBy(productCategoryDataStore.find(i)));
        }
        return new ModelAndView(params, "product/index");
    }

}
