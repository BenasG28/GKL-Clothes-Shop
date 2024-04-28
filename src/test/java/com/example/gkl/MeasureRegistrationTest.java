package com.example.gkl;

import com.example.gkl.fxControllers.LoginController;
import com.example.gkl.fxControllers.RegistrationController;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MeasureRegistrationTest extends ApplicationTest {
    private RegistrationController registrationController;
    private static EntityManagerFactory entityManagerFactory;
    private static UserHib userHib;
    private static Parent root;
    private static GenericHib genericHib;
    private LoginController loginController;
    private User user;
    public void setData(User user) {
        this.user = user;
    }
    @BeforeAll
    public static void setUpDatabaseAndTestUser(){
        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
        userHib = new UserHib(entityManagerFactory);
        genericHib = new GenericHib(entityManagerFactory);
    }
    @AfterAll
    public static void cleanUp() {
        // Ensure userHib and entityManagerFactory are initialized
        if (userHib == null || entityManagerFactory == null) {
            System.out.println("UserHib or EntityManagerFactory is not initialized. Cleanup skipped.");
            return;
        }

        User testUser = userHib.getUserByCredentials("testUser1", "Password123!");
        if (testUser != null) {
            try {
                // Ensure genericHib is initialized before using it
                if (genericHib == null) {
                    System.out.println("GenericHib is not initialized. Cannot delete test user.");
                    return;
                }

                int userId = testUser.getId();
                genericHib.delete(User.class, userId);
                System.out.println("Test user deleted successfully.");
            } catch (Exception e) {
                System.err.println("Failed to delete test user: " + e.getMessage());
                // Handle the exception as needed
            }
        } else {
            System.out.println("Test user not found.");
        }

        try {
            entityManagerFactory.close();
            System.out.println("Entity manager factory closed.");
        } catch (Exception e) {
            System.err.println("Failed to close entity manager factory: " + e.getMessage());
            // Handle the exception as needed
        }
    }

    @BeforeEach
    public void setUp() {
        registrationController = new RegistrationController();
        loginController = new LoginController();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        try {
            root = fxmlLoader.load();
            registrationController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        registrationController.userHib = userHib;
        registrationController.entityManagerFactory = entityManagerFactory;
    }
    @Test
    void testCreateUser() {
        Platform.runLater(() -> {
        registrationController.loginField.setText("testUser1");
        registrationController.passwordField.setText("Password123!");
        registrationController.repeatPasswordField.setText("Password123!");
        registrationController.phoneNumberField.setText("065839201");
        registrationController.nameField.setText("Dominykas");
        registrationController.lastNameField.setText("Slusnys");
        registrationController.addressField.setText("Slusnynu milionierius g.11");
        registrationController.contactMailField.setText("Mail@gmail.com");
        registrationController.createUserButton.fire();

        assertTrue(JavaFxCustomUtils.isAlertShown());
        });
        sleep(5000);
    }
@Test
public void testEmptyFieldsHandling() {
    Platform.runLater(() -> {
        registrationController.createUserButton.fire();
        assertTrue(JavaFxCustomUtils.isAlertShown());
    });
    sleep(5000);
}

    @Test
    public void testPasswordNotMatching() {
        Platform.runLater(() -> {
            registrationController.loginField.setText("testUser1");
            registrationController.passwordField.setText("Password123!");
            registrationController.repeatPasswordField.setText("Password123!!");
            registrationController.phoneNumberField.setText("065839201");
            registrationController.nameField.setText("Dominykas");
            registrationController.lastNameField.setText("Slusnys");
            registrationController.addressField.setText("Slusnynu milionierius g.11");
            registrationController.contactMailField.setText("Mail@gmail.com");
            registrationController.createUserButton.fire();

            assertTrue(JavaFxCustomUtils.isAlertShown());
        });
        sleep(5000);
    }


    @Test
    public void testPasswordStrength() {
        Platform.runLater(() -> {
            registrationController.loginField.setText("testUser1");
            registrationController.passwordField.setText("slaptazodis");
            registrationController.repeatPasswordField.setText("slaptazodis");
            registrationController.phoneNumberField.setText("065839201");
            registrationController.nameField.setText("Dominykas");
            registrationController.lastNameField.setText("Slusnys");
            registrationController.addressField.setText("Slusnynu milionierius g.11");
            registrationController.contactMailField.setText("Mail@gmail.com");

            registrationController.createUserButton.fire();

        assertTrue(JavaFxCustomUtils.isAlertShown());
    });
        sleep(5000);
    }

    @Test
    public void testLoginExist() {
        Platform.runLater(() -> {
            registrationController.loginField.setText("testUser1");
            registrationController.passwordField.setText("Password123!");
            registrationController.repeatPasswordField.setText("Password123!");
            registrationController.phoneNumberField.setText("065839201");
            registrationController.nameField.setText("Dominykas");
            registrationController.lastNameField.setText("Slusnys");
            registrationController.addressField.setText("Slusnynu milionierius g.11");
            registrationController.contactMailField.setText("Mail@gmail.com");
            registrationController.createUser();
            registrationController.createUserButton.fire();

            assertTrue(JavaFxCustomUtils.isAlertShown());
        });
        sleep(5000);
    }

}
