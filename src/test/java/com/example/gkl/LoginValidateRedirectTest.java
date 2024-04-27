package com.example.gkl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.gkl.fxControllers.LoginController;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.ProductHib;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Manager;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginValidateRedirectTest {

    private LoginController loginController;
    private static EntityManagerFactory entityManagerFactory;
    private static UserHib userHib;
    private static GenericHib genericHib;
    private static ProductHib productHib;
    private static Parent root;

    @BeforeAll
    public static void setUpDatabaseAndTestUser(){
        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
        userHib = new UserHib(entityManagerFactory);
        genericHib = new GenericHib(entityManagerFactory);
        Manager manager = new Manager();
        manager.setPasswordHashed("testas");
        manager.setAdmin(true);
        manager.setLogin("testMan");
        userHib.createUser(manager);
        Platform.startup(() -> {});
    }
    @AfterAll
    public static void cleanUp(){
        int userId = userHib.getUserByCredentials("testMan", "testas").getId();
        genericHib.delete(User.class, userId);
        entityManagerFactory.close();
    }

    @BeforeEach
    public void setUp() {

            loginController = new LoginController();
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("login.fxml"));
            try {
                root = fxmlLoader.load();
                loginController = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loginController.userHib = userHib;
            loginController.entityManagerFactory = entityManagerFactory;


    }

    @Test
    public void testValidateAndConnect_ValidCredentials_Manager() {
        Platform.runLater(() -> {
            // Trigger the method under test
            loginController.loginField.setText("testMan");
            loginController.passwordField.setText("testas");
            loginController.validateAndConnect();
           // Parent root = loginController.loginField.getScene().getRoot();
            assertEquals("tabPane", root.getId());
        });
    }

    @Test
    public void testValidateAndConnect_InvalidCredentials() {
        Platform.runLater(() -> {
            // Set invalid credentials
            loginController.loginField.setText("invalidUser");
            loginController.passwordField.setText("invalidPassword");

            // Trigger the method under test
            loginController.validateAndConnect();
            // Verify that an alert is generated indicating invalid credentials
            assertTrue(JavaFxCustomUtils.isAlertShown());
        });
    }

}
