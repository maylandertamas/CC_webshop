import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.Cart.Cart;
import com.codecool.shop.Cart.CartInterface;
import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.databaseConnection.SelectQuery;
import com.codecool.shop.model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
//import org.json.simple.JSONObject;

public class Main {

    public static void main(String[] args) {

        try {
            SelectQuery select = new SelectQuery("users", null, "*");
            select.process();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();

        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        // Equivalent with above
        get("/index", (Request req, Response res) -> {
           return new ThymeleafTemplateEngine().render( ProductController.renderProducts(req, res));
        });

        get("/index/add", (Request req, Response res) -> {
            ProductDao productDataStore = ProductDaoMem.getInstance();
            CartInterface cart = Cart.getCart();
            cart.addToCart(productDataStore.find(Integer.valueOf(req.queryParams("id"))));
            return cart.generateCartSize();
        });

        get("/refresh-cart", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( CartController.renderProducts(req, res));
        });

        get("/index/substract-product", (Request req, Response res) -> {
            CartInterface cart = Cart.getCart();
            Product productQuantitySubstract = cart.find(Integer.valueOf(req.queryParams("substractid")));
            if (productQuantitySubstract.getQuantityInCart() == 1) {
                cart.removeFromCart(productQuantitySubstract.getId());
            } else {
                productQuantitySubstract.subtractQuantityInCart();
            }
            return cart.generateCartSize();
        });

        get("/index/add-product", (Request req, Response res) -> {
            CartInterface cart = Cart.getCart();
            Product productQuantityAdd = cart.find(Integer.valueOf(req.queryParams("addid")));
            productQuantityAdd.addQuantityInCart();
            return cart.generateCartSize();
        });

        get("/index/remove-product", (Request req, Response res) -> {
            CartInterface cart = Cart.getCart();
            Product productToRemove = cart.find(Integer.valueOf(req.queryParams("removeid")));
            productToRemove.setDefaultQuantity();
            cart.removeFromCart(productToRemove.getId());
            return cart.generateCartSize();
        });

        get("/index/checkout", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(new HashMap<>(), "product/checkout"));
        });

        post("/index/checkout/submit", (Request req, Response res) -> {
            OrderDao newOrder = new OrderDaoMem();

            String name = req.queryParams("name");
            String email = req.queryParams("email");
            String phoneNumber = req.queryParams("phoneNumber");
            String billingAddress = req.queryParams("billingAddress");
            String shippingAddress = req.queryParams("shippingAddress");
            String payment = req.queryParams("payment");
            newOrder.addUserData(name, email, phoneNumber, billingAddress, shippingAddress, payment);

            return "Checkout processed";
        });


        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");

        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory hats = new ProductCategory("Hats", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory canes = new ProductCategory("Canes", "Hardware", "Phone");
        ProductCategory rings = new ProductCategory("Rings", "Hardware", "Phone");
        ProductCategory neklace = new ProductCategory("Neklace", "Hardware", "Phone");
        productCategoryDataStore.add(canes);
        productCategoryDataStore.add(hats);
        productCategoryDataStore.add(rings);
        productCategoryDataStore.add(neklace);

        //setting up products and printing it
        productDataStore.add(new Product("Bid Daddy Dollar Pimp Hat", 449.9f, "USD", "Fantastic price. Large content ecosystem. Helpful technical support.", hats, amazon));
        productDataStore.add(new Product("Gay Pride Pimp Hat", 479, "USD", "You know who to F with.", hats, lenovo));
        productDataStore.add(new Product("Elegant - For every occasion - Pimp Hat", 89, "USD", "Just what you need.", hats, amazon));
        productDataStore.add(new Product("F. the Police Pimp Cane", 119, "USD", "Who they callin a pimp?", canes, amazon));
        productDataStore.add(new Product("Standard Pimp Cane", 299, "USD", "Simple as F. A must have.", canes, amazon));
        productDataStore.add(new Product("Go out with style Pimp Cane", 789, "USD", "For you Steampunk Pimps out there.", canes, amazon));
        productDataStore.add(new Product("You in tha Wrong Hood Dog Pimp Ring", 19, "USD", "This daddy be Gangsta.", rings, amazon));
        productDataStore.add(new Product("King of the Streets Pimp Ring", 579, "USD", "Royalty at first glance.", rings, amazon));
        productDataStore.add(new Product("The B**ch Slapper Pimp Ring", 149, "USD", "Order and Discipline.", rings, amazon));
        productDataStore.add(new Product("99 Problems, but a b**ch ain't one Pimp Necklace", 99, "USD", "Now with free MK1911 with armor piercing bullets.", neklace, amazon));
        productDataStore.add(new Product("No Competition Pimp Necklace", 999, "USD", "Don't you talk back to me boi!", neklace, amazon));
        productDataStore.add(new Product("Be Kind to B**ches Pimp Necklace", 369, "USD", "Daddy be warm and fuzzy.", neklace, amazon));

    }




}
