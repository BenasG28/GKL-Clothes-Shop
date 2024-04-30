package com.example.gkl.steps;

import com.example.gkl.StartGui;
import com.example.gkl.fxControllers.RegistrationController;
import com.example.gkl.hibernateControllers.UserHib;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class RegistrationSteps {
    private RegistrationController registrationController;
    private static EntityManagerFactory entityManagerFactory;
    private static UserHib userHib;
    private Stage stage;

        @Before
        public void setUp() {
            Platform.startup(() -> {});
            registrationController = new RegistrationController();
            registrationController.loginField = new TextField();
            registrationController.passwordField = new PasswordField();
            String password;
        }

    @Given("the user opens the registration page")
    public void openRegistrationPage() {

        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
                Parent parent = fxmlLoader.load();
                stage = new Stage();
                Scene scene = new Scene(parent);
                registrationController = fxmlLoader.getController();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @When("the user enters their username as {string}")
    public void theUserEntersTheirUsernameAs(String username) {
        Platform.runLater(() -> {
            registrationController.loginField.setText(username);
        });
        assertTrue(registrationController.loginField.isVisible());
        if (username.isEmpty()) {
            fail("Username field cannot be empty");
        }
    }
    @And("the user enters their email address as {string}")
    public void theUserEntersTheirEmailAddressAs(String email) {
        Platform.runLater(() -> {
            registrationController.contactMailField.setText(email);
        });
        if (email.isEmpty()) {
            fail("Email field cannot be empty");
        }
    }

    @And("enters their password as {string}")
    public void entersTheirPasswordAs(String password){
        Platform.runLater(() -> {
            registrationController.passwordField.setText(password);
        });
        if (password.isEmpty()) {
            fail("Password field cannot be empty");
        }
    }
    @And("repeats the password as {string}")
    public void repeatsThePasswordAs(String repeatedPassword) {

        Platform.runLater(() -> {
            registrationController.repeatPasswordField.setText(repeatedPassword);
        });
        if (repeatedPassword.isEmpty()) {
            fail("Repeat password field cannot be empty");
        }
//         else if (repeatedPassword.equals()) {
//            fail("Passwords do not match");
//        }
    }
    @And("the user enters their home address as {string}")
    public void theUserEntersTheirHomeAddressAs(String address) {
        Platform.runLater(() -> {
            registrationController.addressField.setText(address);
        });
        if (address.isEmpty()) {
            fail("Address field cannot be empty");
        }
    }
    @And("the user enters their phone number as {string}")
    public void theUserEntersTheirPhoneNumberAs(String number) {
        Platform.runLater(() -> {
            registrationController.phoneNumberField.setText(number);
        });
        if (number.isEmpty()) {
            fail("Number field cannot be empty");
        }
    }
    @And("the user enters their name as {string}")
    public void theUserEntersTheirNameAs(String name) {
        Platform.runLater(() -> {
            registrationController.nameField.setText(name);
        });
        if (name.isEmpty()) {
            fail("Name field cannot be empty");
        }
    }

    @And("the user enters their surname as {string}")
    public void theUserEntersTheirSurnameAs(String surname) {
        Platform.runLater(() -> {
            registrationController.lastNameField.setText(surname);
        });
        if (surname.isEmpty()) {
            fail("Surname field cannot be empty");
        }
    }
    @And("clicks the register button")
    public void clicksTheRegisterButton(){
        Platform.runLater(() -> {
        registrationController.createUserButton.fire();
        });
    }
}
