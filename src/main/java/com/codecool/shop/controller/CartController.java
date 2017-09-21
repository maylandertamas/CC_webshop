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
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CartController {
    public static ModelAndView renderProducts(Request req, Response res) {

        Map params = new HashMap<>();

        CartInterface cart = Cart.getCart();
        params.put("productSumm", cart.summUp());
        params.put("cartContents", cart.getCartContents());
        params.put("cartSize", cart.generateCartSize());
        return new ModelAndView(params, "product/cart");
    }

}