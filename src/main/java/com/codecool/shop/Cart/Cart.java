package com.codecool.shop.Cart;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart implements CartInterface {
    private static Cart instance = null;
    List<Product> cartContents = new ArrayList<>();
    private Cart(){}

    public static Cart getCart(){
        if (instance == null){
            instance = new Cart();
        }
        return instance;
    }

    public List getCartContents(){
        return cartContents;
    }

    public void removeFromCart(int id){
        cartContents.remove(find(id));

    }

    public void addToCart(Product product){
        if (cartContents.contains(product)) {
            product.addQuantityInCart();
        }
        else {
            cartContents.add(product);
            product.addQuantityInCart();
        }
    }

    public Product find(int id){
        for (Product item: cartContents){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public float summUp() {
        float totalProductPrice = 0;
        for (Product item: cartContents){
            totalProductPrice += (item.getDefaultPrice()*item.getQuantityInCart());
        }
        return totalProductPrice;
    }

    public int generateCartSize() {
        int crtSize = 0;
        for (Product prod : cartContents) {
            crtSize += prod.getQuantityInCart();
        }
        return crtSize;
    }


}
