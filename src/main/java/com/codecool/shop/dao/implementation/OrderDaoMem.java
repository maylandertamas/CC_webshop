package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoMem implements OrderDao{

     private HashMap<String, String> userData;
     private List<Product> orderedProduct;

    @Override
    public void addUserData(String name, String email, String phoneNumber,
                            String billingAddress, String shippingAddress, String payment) {
        System.out.println(name);
        System.out.println(email);
        System.out.println(phoneNumber);
        System.out.println(billingAddress);
        System.out.println(shippingAddress);
        System.out.println(payment);
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phonenumber", phoneNumber);
        userData.put("billingaddress", billingAddress);
        userData.put("shippingaddress", shippingAddress);
        userData.put("payment", payment);
    }

    @Override
    public Map<String, String> getUserData() {
        return userData;
    }

    @Override
    public void setOrderedProducts(Product product) {
        orderedProduct.add(product);

    }

    @Override
    public List<Product> getAllProducts() {
        return orderedProduct;
    }
}
