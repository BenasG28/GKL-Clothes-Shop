//package com.example.gkl;
//
//import com.example.gkl.fxControllers.LoginController;
//import com.example.gkl.fxControllers.RegistrationController;
//import com.example.gkl.hibernateControllers.UserHib;
//import com.example.gkl.model.Customer;
//import com.example.gkl.model.User;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//
//import com.example.gkl.utils.JavaFxCustomUtils;
//
//import javafx.application.Platform;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class LoginTest {
//
////    // Initialize JavaFX Toolkit
////    static {
////        new JFXPanel();
////    }
//    private LoginController loginController;
//    private EntityManagerFactory entityManagerFactory;
//    @BeforeEach
//    public void setUp(){
//        // Inicializuojame JavaFX platformą
//        JFXPanel panel = new JFXPanel();
//        //Platform.runLater(() -> {
//
//   //     });
////        Platform.startup(() -> {
////        });
//        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
//
//
//    }
//    @AfterAll
//    public static void tearDown() {
//        Platform.exit();
//    }
//    @Test
//    public void testValidLogin() {
//        Platform.runLater(() -> {
//            // Sukuriame naują instanciją LoginController
//            loginController = new LoginController();
//            loginController.entityManagerFactory= entityManagerFactory;
//            // Kuriame naujus TextField ir PasswordField objektus
//            TextField loginField = new TextField("b");
//            PasswordField passwordField = new PasswordField();
//            passwordField.setText("b");
//            Scene scene = new Scene(new VBox(loginField, passwordField));
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//            loginController.loginField = loginField;
//            loginController.passwordField = passwordField;
//            // Sukuriame naują mygtuką prisijungimui
//            Button loginButton = new Button("Login");
//            // Priskiriame veiksmą prisijungimo mygtukui
//            loginButton.setOnAction(event -> loginController.validateAndConnect());
//            // Iškviečiame metodą, kuris patikrina prisijungimą
//            loginButton.fire();
//            System.out.println(stage.getTitle());
//            assertEquals("GKL", stage.getTitle());
//        });
//    }
//    @Test
//    public void testInvalidLogin() {
//        Platform.runLater(() -> {
//            // Sukuriame naują instanciją LoginController
//            loginController = new LoginController();
//            loginController.entityManagerFactory= entityManagerFactory;
//            // Kuriame naujus TextField ir PasswordField objektus
//            TextField loginField = new TextField("deeed");
//            PasswordField passwordField = new PasswordField();
//            passwordField.setText("bsd");
//            Scene scene = new Scene(new VBox(loginField, passwordField));
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//            loginController.loginField = loginField;
//            loginController.passwordField = passwordField;
//            // Sukuriame naują mygtuką prisijungimui
//            Button loginButton = new Button("Login");
//            // Priskiriame veiksmą prisijungimo mygtukui
//            loginButton.setOnAction(event -> loginController.validateAndConnect());
//            // Iškviečiame metodą, kuris patikrina prisijungimą
//            loginButton.fire();
//            System.out.println(stage.getTitle());
//            assertTrue(JavaFxCustomUtils.isAlertShown(), "Alert was shown:)");
//        });
//}
//}
