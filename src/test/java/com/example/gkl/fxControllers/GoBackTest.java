package com.example.gkl.fxControllers;

import com.example.gkl.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoBackTest extends ApplicationTest {
    EntityManagerFactory entityManagerFactory;
    private User user;
private RegistrationController registrationController;
    @Test
    public void testGoBack() {
        Platform.runLater(() -> {
            registrationController = new RegistrationController();
            registrationController.entityManagerFactory= entityManagerFactory;
            TextField passwordField = new TextField("b");
            Scene scene = new Scene(new VBox(passwordField));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            registrationController.loginField = passwordField;
            Button backButton = new Button("Back");
            backButton.setOnAction(event -> {
                try {
                    registrationController.goBack(user);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            backButton.fire();
            System.out.println(stage.getTitle());
            assertEquals("GKL", stage.getTitle());
        });
    }
}
