package com.example.gkl.fxControllers;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeasureRegistrationTest extends ApplicationTest {
    @Test
    public void testCheckIfFieldsEmpty_AllEmpty() {
        MeasuramentsController controller = new MeasuramentsController();
        TextField chestMeasure = new TextField();
        TextField shoulderMeasure = new TextField();
        TextField backMeasure = new TextField();
        TextField sleeveMeasure = new TextField();
        TextField hipMeasure = new TextField();
        TextField outseamMeasure = new TextField();
        TextField inseamMeasure = new TextField();
        TextField waistMeasure = new TextField();
        boolean result = controller.checkIfFieldsEmpty(chestMeasure, shoulderMeasure, backMeasure, sleeveMeasure,
                hipMeasure, outseamMeasure, inseamMeasure, waistMeasure);
        assertTrue(result, "All fields are empty, so the method should return true.");
    }

    @Test
    public void testCheckIfFieldsEmpty_NotEmpty() {
        MeasuramentsController controller = new MeasuramentsController();
        TextField chestMeasure = new TextField("20");
        TextField shoulderMeasure = new TextField("18");
        TextField backMeasure = new TextField("25");
        TextField sleeveMeasure = new TextField("30");
        TextField hipMeasure = new TextField("35");
        TextField outseamMeasure = new TextField("40");
        TextField inseamMeasure = new TextField("32");
        TextField waistMeasure = new TextField("30");
        boolean result = controller.checkIfFieldsEmpty(chestMeasure, shoulderMeasure, backMeasure, sleeveMeasure,
                hipMeasure, outseamMeasure, inseamMeasure, waistMeasure);
        assertFalse(result, "All fields are not empty, so the method should return false.");
    }
    @Test
    public void testIsPasswordMatch() {
        Platform.runLater(() -> {
            RegistrationController controller = new RegistrationController();
            PasswordField passwordField = new PasswordField();
            passwordField.setText("Autogidas12345!");
            PasswordField repeatPasswordField = new PasswordField();
            repeatPasswordField.setText("Autogidas12345!");
            assertTrue(controller.isPasswordMatch(passwordField, repeatPasswordField),
                    "Passwords match");
            repeatPasswordField.setText("Autogidas12345@");
            assertFalse(controller.isPasswordMatch(passwordField, repeatPasswordField),
                    "Passwords do not match");
        });
    }
    @Test
    public void testCheckIfFieldsEmpty(){
        Platform.runLater(() -> {
            RegistrationController controller = new RegistrationController();
            TextField mockEmptyField = new TextField("");
            TextField mockNonEmptyField = new TextField("Test");
            assertTrue(controller.checkIfFieldsEmpty(
                    mockEmptyField, mockEmptyField, mockEmptyField,
                    mockEmptyField, mockEmptyField, mockEmptyField, mockEmptyField
            ), "All fields are empty");
            assertFalse(controller.checkIfFieldsEmpty(
                    mockNonEmptyField, mockNonEmptyField, mockNonEmptyField,
                    mockNonEmptyField, mockNonEmptyField, mockNonEmptyField, mockNonEmptyField
            ), "At least one field is not empty");
        });
    }
}
