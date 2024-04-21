package com.example.gkl;
import com.example.gkl.fxControllers.PasswordChangedCallback;
import com.example.gkl.model.Manager;
import com.example.gkl.utils.JavaFxCustomUtils;
import com.example.gkl.fxControllers.ChangePasswordController;
import com.example.gkl.fxControllers.RegistrationController;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javafx.stage.Stage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChangePasswordTest {
    ChangePasswordController changePasswordController;
    @Mock
    private User mockUser;
    @Mock
    private EntityManagerFactory mockEntityManagerFactory;
    @Mock
    private Stage mockPasswordStage;
    @Mock
    private GenericHib mockGenericHib;
    @Mock
    private JavaFxCustomUtils javaFxCustomUtils;
//    @Mock
//    private PasswordField currentPasswordField;
//
//    @Mock
//    private PasswordField newPasswordField;
//
//    @Mock
//    private PasswordField confNewPasswordField;


    @Mock
    private UserHib userHib;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Platform.startup(() -> {
        });
//        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
//        changePasswordController.setData(null, user, passwordStage);
//        changePasswordController.currentPasswordTextfield = currentPasswordField;
//        changePasswordController.newPasswordTextfield = newPasswordField;
//        changePasswordController.confNewPasswordTextfield = confNewPasswordField;
    }
    @AfterAll
    public static void tearDown() {
        Platform.exit();
//        if (entityManagerFactory != null) {
//            entityManagerFactory.close();
//        }
    }
    @Test
    public void testSaveNewPassword() {
        System.out.println("Starting testSaveNewPassword...");
        Platform.runLater(() -> {

        // Set up the controller
        ChangePasswordController controller = new ChangePasswordController();
        controller.setData(mockEntityManagerFactory, mockUser, mockPasswordStage);
        controller.currentPasswordTextfield = new PasswordField();
        controller.currentPasswordTextfield.setText("b");
        controller.newPasswordTextfield = new PasswordField();
        controller.newPasswordTextfield.setText("newPassword");
        controller.confNewPasswordTextfield = new PasswordField();
        controller.confNewPasswordTextfield.setText("newPassword");

        // Set up expectations
        when(mockUser.getPassword()).thenReturn("$2a$10$fEzaUrN66AQfAEz8kOUY7OeORBhaeNUEgpM7TyLJgh8SHWJOZUoJ."); // hashed "currentPassword"
        doNothing().when(mockGenericHib).update(mockUser); // Mock the update method to do nothing
        // Call the method under test
        controller.saveNewPassword();

        // Verify that the password is updated
        verify(mockUser).setPasswordHashed("newPassword");
        // Verify that genericHib.update is called
        verify(mockGenericHib).update(mockUser);
        // Verify that the password stage is closed
        verify(mockPasswordStage).close();
        });
//        Platform.runLater(() -> {
//            // Arrange
//            changePasswordController.currentPasswordTextfield.setText("currentPassword");
//            // Act
//            changePasswordController.saveNewPassword();
//
//            // Assert
//            verify(user).setPasswordHashed("newPassword");
//            verify(genericHib).update(user); // Verify interaction with GenericHib
//            verify(passwordStage).close();
//            verifyNoInteractions(Alert.class); // Ensure no alert is shown
//        });
    }
    @Test
    public void testIsCurrentPasswordMatch() throws NoSuchFieldException, IllegalAccessError {
        System.out.println("Starting testIsCurrentPasswordMatch()...");
        Platform.runLater(() -> {
            // Create an instance of ChangePasswordController
            ChangePasswordController changePasswordController = new ChangePasswordController();
            Field currentPassworField = null;
            try {
                currentPassworField = ChangePasswordController.class.getDeclaredField("currentPasswordTextfield");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            currentPassworField.setAccessible(true);
            try {
                currentPassworField.set(changePasswordController, createMockPasswordField("b"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Customer currentUser = new Customer();
            currentUser.setPassword("$2a$10$fnFO7QNPotyQjWRzIcZhK.xL0iGJNr1yl4/OEQmo86RuzejYz4ciO");
            changePasswordController.setData(null, currentUser, null);

            boolean result = changePasswordController.isCurrentPasswordValid();
            System.out.println("Result of isCurrentPasswordValid(): " + result);
            // Test the method
            assertTrue(result);
            System.out.println("testIsCurrentPasswordMatch() completed successfully.");

        });
    }
    @Test
    public void testIsNewPasswordMatch() throws NoSuchFieldException, IllegalAccessError {
        System.out.println("Starting testIsNewPasswordMatch()...");
        Platform.runLater(() -> {
            // Create an instance of ChangePasswordController
            ChangePasswordController changePasswordController = new ChangePasswordController();
            Field newPasswordField = null;
            try {
                newPasswordField = ChangePasswordController.class.getDeclaredField("newPasswordTextfield");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            newPasswordField.setAccessible(true);
            try {
                newPasswordField.set(changePasswordController, createMockPasswordField("newPassword"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Field confNewPasswordField = null;
            try {
                confNewPasswordField = ChangePasswordController.class.getDeclaredField("confNewPasswordTextfield");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            confNewPasswordField.setAccessible(true);
            try {
                confNewPasswordField.set(changePasswordController, createMockPasswordField("newPassword"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            boolean result = changePasswordController.areNewPasswordMatch();
            System.out.println("Result of areNewPasswordMatch(): " + result);
            // Test the method
            assertTrue(result);
            System.out.println("testIsNewPasswordMatch() completed successfully.");

        });
    }
    @Test
    public void testIsNewPasswordNotMatch() throws NoSuchFieldException, IllegalAccessError {
        System.out.println("Starting testIsNewPasswordNotMatch()...");
        Platform.runLater(() -> {
            // Create an instance of ChangePasswordController
            ChangePasswordController changePasswordController = new ChangePasswordController();
            Field newPasswordField = null;
            try {
                newPasswordField = ChangePasswordController.class.getDeclaredField("newPasswordTextfield");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            newPasswordField.setAccessible(true);
            try {
                newPasswordField.set(changePasswordController, createMockPasswordField("newPasswordas"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Field confNewPasswordField = null;
            try {
                confNewPasswordField = ChangePasswordController.class.getDeclaredField("confNewPasswordTextfield");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            confNewPasswordField.setAccessible(true);
            try {
                confNewPasswordField.set(changePasswordController, createMockPasswordField("newPassword"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            boolean result = changePasswordController.areNewPasswordMatch();
            System.out.println("Result of areNewPasswordNotMatch(): " + result);
            // Test the method
            assertFalse(result);
            System.out.println("testIsNewPasswordNotMatch() completed successfully.");

        });
    }
    private PasswordField createMockPasswordField(String text) {
        PasswordField passwordField = new PasswordField();
        passwordField.setText(text);
        return passwordField;
    }

}
