package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    void addUserData(String name, String email, String phoneNumber,
                     String billingAddress, String shippingAddress, String payment);
    Map<String, String> getUserData();
    void setOrderedProducts(Product product);
    List<Product> getAllProducts();
}
