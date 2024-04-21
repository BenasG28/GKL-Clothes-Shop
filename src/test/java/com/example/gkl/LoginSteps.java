package com.example.gkl;

import com.example.gkl.fxControllers.LoginController;
import com.example.gkl.utils.JavaFxCustomUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    private LoginController loginController;
    private Scene scene;
    private Stage stage;

    @Given("the user is on the login screen")
    public void userIsOnLoginScreen() {
        // Initialize JavaFX components
        JFXPanel panel = new JFXPanel();
        loginController = new LoginController();

        // Create login UI components
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        loginField.setId("loginField");
        passwordField.setId("passwordField");

        scene = new Scene(new VBox(loginField, passwordField));
        stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @When("the user enters valid credentials and clicks login")
    public void userEntersValidCredentialsAndClicksLogin() {
        // Simulate user action: enter valid credentials and click login
        TextField loginField = (TextField) scene.lookup("#loginField");
        PasswordField passwordField = (PasswordField) scene.lookup("#passwordField");
        loginField.setText("b");
        passwordField.setText("b");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> loginController.validateAndConnect());
        loginButton.fire();

    }

    @When("the user enters invalid credentials and clicks login")
    public void userEntersInvalidCredentialsAndClicksLogin() {
        // Simulate user action: enter invalid credentials and click login
        TextField loginField = (TextField) scene.lookup("#loginField");
        PasswordField passwordField = (PasswordField) scene.lookup("#passwordField");
        loginField.setText("deeed");
        passwordField.setText("bsd");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> loginController.validateAndConnect());
        loginButton.fire();

    }

    @Then("the main application screen should be displayed")
    public void mainApplicationScreenIsDisplayed() {
        // Verify that the main application screen is displayed
        // For example:
         assertEquals("GKL", stage.getTitle());
        // Additional assertions or GUI verifications can be done here

    }

    @Then("an alert should be shown")
    public void alertIsShown() {
        // Verify that an alert is shown
        // For example:
         assertTrue(JavaFxCustomUtils.isAlertShown());
        // Additional assertions or GUI verifications can be done here

    }
}

