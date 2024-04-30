package com.example.gkl;

import com.example.gkl.fxControllers.RegistrationController;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class GoBackRegistrationTest extends ApplicationTest {

    private RegistrationController registrationController;
    private static EntityManagerFactory entityManagerFactory;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, null);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testGoBackWithNullUser() throws IOException {
        Platform.runLater(() -> {
            try {
                registrationController.goBack(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            verifyThat("#loginField", isVisible());
        });
        sleep(5000);
    }

    @Test
    public void testGoBackFireButton() throws IOException {
        Platform.runLater(() -> {
           registrationController.backButton.fire();
            verifyThat("#loginField", isVisible());
        });
        sleep(5000);
    }
}
