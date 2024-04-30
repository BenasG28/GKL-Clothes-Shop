package com.example.gkl.Cucumber;

import com.example.gkl.fxControllers.MainShopController;
import com.example.gkl.fxControllers.OrderController;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Order_History {

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private OrderController orderController;
    private CountDownLatch latch;

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
        UserHib userHib = new UserHib(entityManagerFactory);
        currentUser = userHib.getUserByCredentials("testuotojas2", "Testuotojas2@");
    }

    @When("the user navigates to the order history tab")
    public void theUserNavigatesToTheOrderHistoryTab() throws IOException, InterruptedException {
        new JFXPanel();

        latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/orderTab.fxml"));
                Parent orderRoot = loader.load();
                orderController = loader.getController();
                orderController.setData(entityManagerFactory, currentUser);
                MainShopController mainShopController = new MainShopController();
                Tab ordersTab = mainShopController.ordersTab;
                ordersTab.setContent(orderRoot);
                orderController.getAllOrders();
                orderController.setupStatusComboBox();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Then("the user should see a list of their past orders")
    public void theUserShouldSeeAListOfTheirPastOrders() {
        int numberOfOrders = orderController.orderListManager.getItems().size();
        assertTrue(numberOfOrders > 0);
    }
}
