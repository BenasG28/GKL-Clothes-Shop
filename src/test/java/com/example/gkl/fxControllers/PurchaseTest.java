package com.example.gkl.fxControllers;

import com.example.gkl.fxControllers.MainShopController;
import com.example.gkl.hibernateControllers.CartHib;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Product;
import com.example.gkl.model.ProductType;
import com.example.gkl.model.UpperBodyClothing;
import com.example.gkl.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class PurchaseTest {

    private static EntityManagerFactory entityManagerFactory;
    private MainShopController controller;
    Product product;
    @BeforeAll
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
    }

    @AfterAll
    public static void tearDownAfterClass() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    public void setUp() {
        UserHib userHib = new UserHib(entityManagerFactory);
        User currentUser = userHib.getUserByCredentials("Testuotojas2", "Testuotojas2@");
        controller = new MainShopController();
        controller.setData(entityManagerFactory, currentUser);
        controller.currentOrderList = new ListView<>();
        controller.totalAmountLabel = new Label();
        controller.productContainer = new AnchorPane(); // Initialize productContainer
        controller.displayProductCards();
    }

    @Test
    public void testPurchaseAndCheckOrderTab() {
        int initialSize = controller.currentOrderList.getItems().size();
        controller.addToCart(createNewUpperBodyProduct(), "Size", 1);
        controller.placeOrder();
        assertTrue(controller.currentOrderList.getItems().size() > initialSize, "Order list should contain the new purchase");
    }

    private UpperBodyClothing createNewUpperBodyProduct() {
        UpperBodyClothing upperBodyProduct = new UpperBodyClothing();
        upperBodyProduct.setTitle("New Upper Body Product");
        upperBodyProduct.setPrice(10.0);
        upperBodyProduct.setDescription("Description of the new upper body product");
        upperBodyProduct.setImageUrl("https://cdn-images.farfetch-contents.com/20/66/39/98/20663998_50647152_600.jpg");
        upperBodyProduct.setColor("Red");
        upperBodyProduct.setProductType(ProductType.UPPERBODY);

        return upperBodyProduct;
    }


}
