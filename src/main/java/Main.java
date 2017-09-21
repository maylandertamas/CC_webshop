import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.Cart.Cart;
import com.codecool.shop.Cart.CartInterface;
import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
//import org.json.simple.JSONObject;

public class Main {

    public static void main(String[] args) {

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
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "Phone");
        productCategoryDataStore.add(phone);
        productCategoryDataStore.add(tablet);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        Product shittyPhone = new Product("ShittyPhone", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon);
        productDataStore.add(shittyPhone);

    }




}
