package com.codecool.shop.Cart;

import com.codecool.shop.model.Product;

import java.util.List;

public interface CartInterface {
    List<Product> getCartContents();
    void removeFromCart(int id);
    void addToCart(Product product);
    Product find(int id);
    //public float summUp;

}
