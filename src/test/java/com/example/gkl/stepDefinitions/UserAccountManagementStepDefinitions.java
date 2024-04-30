package com.example.gkl.stepDefinitions;

import com.example.gkl.StartGui;
import com.example.gkl.fxControllers.MainShopController;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.User;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.example.gkl.fxControllers.LoginController;
import com.example.gkl.fxControllers.AccountController;
import io.cucumber.java.it.Ma;
import io.cucumber.junit.Cucumber;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserAccountManagementStepDefinitions {
    private static final Logger LOGGER = Logger.getLogger(UserAccountManagementStepDefinitions.class.getName());
    private LoginController loginController;
    private MainShopController mainShopController;
    private AccountController accountController = new AccountController();
    private UserHib mockUserHib;
    private Parent root;
    private EntityManagerFactory entityManagerFactory;
    private User currentUser;


    @Before
    public void setUp(){
        Platform.startup(() -> {});
        entityManagerFactory = mock(EntityManagerFactory.class);
        currentUser = new Customer();
        currentUser.setFirstName("BABABOYAY");
//        mainShopController = new MainShopController();
        accountController = new AccountController();
        accountController.setData(entityManagerFactory, currentUser);
        accountController.nameTextfield = new TextField();
        accountController.emailTextfield = new TextField();
        accountController.surnameTextField = new TextField();
        accountController.loginTextfield = new TextField();
        accountController.emailTextfield = new TextField();
        accountController.passwordField = new PasswordField();
        accountController.chestTextfield = new TextField();
        accountController.shoulderTextfield = new TextField();
        accountController.backTextfield = new TextField();
        accountController.sleeveTextfield = new TextField();
        accountController.hipTextfield = new TextField();
        accountController.legLengthTextfield = new TextField();
        accountController.inseamTextfield = new TextField();
        accountController.waistTextfield = new TextField();

        accountController.nameText = new Text();
        accountController.emailText = new Text();
        accountController.surnameText = new Text();
        accountController.loginText = new Text();
        accountController.emailText = new Text();
        accountController.passwordText = new Text();
        accountController.chestText = new Text();
        accountController.shoulderText = new Text();
        accountController.backText = new Text();
        accountController.sleeveText = new Text();
        accountController.hipText = new Text();
        accountController.legLengthText = new Text();
        accountController.inseamText = new Text();
        accountController.waistText = new Text();
    }


    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.startup(latch::countDown);
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
            loginController = new LoginController();
            mockUserHib = mock(UserHib.class);
            loginController.userHib = mockUserHib;
            when(mockUserHib.getUserByCredentials("test", "test")).thenReturn(new Customer());
    }

    @When("the user navigates to the account settings page")
    public void userNavigatesToAccountSettingsPage() {
//            mainShopController.loadAccountTab();
    }

    @Then("the system should display clear instructions on how to edit account information with text")
    public void systemDisplaysInstructions() {
        Platform.runLater(() -> {
            assertTrue(accountController.nameText.isVisible());
            assertTrue(accountController.surnameText.isVisible());
            assertTrue(accountController.loginText.isVisible());
            assertTrue(accountController.emailText.isVisible());
            assertTrue(accountController.passwordText.isVisible());
            assertTrue(accountController.chestText.isVisible());
            assertTrue(accountController.shoulderText.isVisible());
            assertTrue(accountController.backText.isVisible());
            assertTrue(accountController.sleeveText.isVisible());
            assertTrue(accountController.hipText.isVisible());
            assertTrue(accountController.legLengthText.isVisible());
            assertTrue(accountController.inseamText.isVisible());
            assertTrue(accountController.waistText.isVisible());
        });
    }

    @And("the user should see input fields")
    public void theUserShouldSeeInputFields() {
        Platform.runLater(() -> {
        assertTrue(accountController.nameTextfield.isVisible());
        assertTrue(accountController.surnameTextField.isVisible());
        assertTrue(accountController.loginTextfield.isVisible());
        assertTrue(accountController.emailTextfield.isVisible());
        assertTrue(accountController.passwordField.isVisible());
        assertTrue(accountController.chestTextfield.isVisible());
        assertTrue(accountController.shoulderTextfield.isVisible());
        assertTrue(accountController.backTextfield.isVisible());
        assertTrue(accountController.sleeveTextfield.isVisible());
        assertTrue(accountController.hipTextfield.isVisible());
        assertTrue(accountController.legLengthTextfield.isVisible());
        assertTrue(accountController.inseamTextfield.isVisible());
        assertTrue(accountController.waistTextfield.isVisible());
        });
    }

    @When("the user edits their account information")
    public void userEditsAccountInformation() {
        Platform.runLater(() -> {
            accountController.nameTextfield.setText("New Name");
            accountController.emailTextfield.setText("newemail@example.com");
        });

    }

    @And("confirms the changes")
    public void userConfirmsChanges() {
        Platform.runLater(() -> {
            accountController.handleSaveInfo();
            assertEquals("New Names", accountController.currentUser.getFirstName());
        });
    }

    @Then("the system should save the changes and update the user's account")
    public void systemSavesChangesAndUpdateAccount() {
        // Assuming the AccountController instance is already initialized
        // Add assertions to verify that changes are saved and account is updated
        // Example assertion: Assert.assertTrue(accountController.areChangesSavedAndUpdate());
    }
}
