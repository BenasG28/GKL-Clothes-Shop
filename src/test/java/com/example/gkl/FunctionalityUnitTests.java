package com.example.gkl;

import com.example.gkl.fxControllers.MeasuramentsController;
import com.example.gkl.fxControllers.RegistrationController;
import com.example.gkl.model.Regions;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FunctionalityUnitTests {

    private MeasuramentsController measuramentsController;
    private RegistrationController registrationController;
    @BeforeEach
    public void setUp() {
        new JFXPanel();
        Platform.runLater(() -> {
            measuramentsController = new MeasuramentsController();
            registrationController = new RegistrationController();
        });
    }

    @Test
    public void testAllFieldsEmpty_FALSE() {
        TextField chestmeasure = new TextField("10");
        TextField shouldermeasure = new TextField("10");
        TextField backmeasure = new TextField("10");
        TextField sleevemeasure = new TextField("10");
        TextField hipmeasure = new TextField("10");
        TextField outseammeasure = new TextField("10");
        TextField inseammeasure = new TextField("10");
        TextField waistmeasure = new TextField("10");

        assertFalse(measuramentsController.checkIfFieldsEmpty(chestmeasure, shouldermeasure, backmeasure, sleevemeasure,
                hipmeasure, outseammeasure, inseammeasure, waistmeasure));
    }
    @Test
    public void testAllFieldsEmpty_TRUE() {
        TextField chestmeasure = new TextField("");
        TextField shouldermeasure = new TextField("");
        TextField backmeasure = new TextField("");
        TextField sleevemeasure = new TextField("");
        TextField hipmeasure = new TextField("");
        TextField outseammeasure = new TextField("");
        TextField inseammeasure = new TextField("");
        TextField waistmeasure = new TextField("");

        assertTrue(measuramentsController.checkIfFieldsEmpty(chestmeasure, shouldermeasure, backmeasure, sleevemeasure,
                hipmeasure, outseammeasure, inseammeasure, waistmeasure));
    }

    @Test
    public void testCheckIfFieldsEmpty_AllFieldsEmpty() {
        TextField loginField = new TextField();
        TextField passwordField = new TextField();
        TextField contactMailField = new TextField();
        TextField nameField = new TextField();
        TextField lastNameField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField addressField = new TextField();

        boolean result = registrationController.checkIfFieldsEmpty(loginField, passwordField, contactMailField, nameField, lastNameField, phoneNumberField, addressField);

        assertTrue(result, "All fields are empty, so expected result should be true");
    }

    @Test
    public void testIsPasswordMatch_PasswordsMatch() {
        PasswordField passwordField = new PasswordField();
        passwordField.setText("password123");
        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setText("password123");

        boolean result = registrationController.isPasswordMatch(passwordField, repeatPasswordField);

        assertTrue(result, "Passwords match, so expected result should be true");
    }
    @Test
    public void testIsPasswordMatch_PasswordsNOTMatch() {
        PasswordField passwordField = new PasswordField();
        passwordField.setText("password123");
        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setText("password1234");

        boolean result = registrationController.isPasswordMatch(passwordField, repeatPasswordField);

        assertFalse(result, "Passwords do not match, so expected result should be false");
    }

    @Test
    public void testConvertSizeToStandardUnit_EU() {
        double size = measuramentsController.convertSizeToStandardUnit(10.0, Regions.EU);
        assertEquals(10.0, size);
    }
    @Test
    public void testConvertSizeToStandardUnit_US() {
        double size = measuramentsController.convertSizeToStandardUnit(10.0, Regions.US);
        assertEquals(25.4, size);
    }
    @Test
    public void testConvertSizeToStandardUnit_UK() {
        double size = measuramentsController.convertSizeToStandardUnit(10.0, Regions.UK);
        assertEquals(25.4, size);
    }


}
