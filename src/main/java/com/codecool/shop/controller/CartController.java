package com.codecool.shop.controller;

import com.codecool.shop.Cart.Cart;
import com.codecool.shop.Cart.CartInterface;
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