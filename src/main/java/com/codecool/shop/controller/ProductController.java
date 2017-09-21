package com.codecool.shop.controller;

import com.codecool.shop.Cart.Cart;
import com.codecool.shop.Cart.CartInterface;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductController {
    public static ModelAndView renderProducts(Request req, Response res) {

        Map params = new HashMap<>();
        String productCategoryId = req.queryParams("cid");
        String supplierId = req.queryParams("sid");
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        params.put("allSuppliers", supplierDataStore.getAll());
        params.put("allCategory", productCategoryDataStore.getAll());
        if (productCategoryId != null) {
            params.put("category", productCategoryDataStore.find(Integer.parseInt(productCategoryId)));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(productCategoryId))));
        } else if (supplierId != null) {
            params.put("category", supplierDataStore.find(Integer.parseInt(supplierId)).getCategory());
            params.put("products", supplierDataStore.find(Integer.parseInt(supplierId)).getProducts());
        } else {
            params.put("suppliers", supplierDataStore.getAll());
            params.put("category", productCategoryDataStore.getAll());
            params.put("products", productDataStore.getAll());
        }
        CartInterface cart = Cart.getCart();
        params.put("productSumm", cart.summUp());
        params.put("cartContents", cart.getCartContents());
        System.out.println(cart.getCartContents());
        return new ModelAndView(params, "product/index");
    }

}
