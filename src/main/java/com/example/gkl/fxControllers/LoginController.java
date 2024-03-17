package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.Manager;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

public class LoginController implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    public Button loginButton;
    public Button registerButton = new Button();
    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private boolean isManager;


    public void registerNewUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, null);
        stage.setTitle("MusicShop");
        stage.setScene(scene);
        stage.show();
    }

    public void validateAndConnect() {
        try{
            userHib = new UserHib(entityManagerFactory);
            User user = userHib.getUserByCredentials(loginField.getText(),passwordField.getText());

            if (user != null) {
                if (user instanceof Manager) {
                    Manager manager = (Manager) user;
                    FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("main-shop.fxml"));
                    Parent parent = fxmlLoader.load();
                    MainShopController mainShopController = fxmlLoader.getController();
                    mainShopController.setData(entityManagerFactory, manager);
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) loginField.getScene().getWindow();
                    stage.setTitle("Music shop");
                    stage.setScene(scene);
                    stage.show();
                } else if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    if (customer.getCustomerBackMeas() == null &&
                            customer.getCustomerHipMeas() == null &&
                            customer.getCustomerChestMeas() == null &&
                            customer.getCustomerInseamMeas() == null &&
                            customer.getCustomerLegLengthMeas() == null &&
                            customer.getCustomerSleeveMeas() == null &&
                            customer.getCustomerWaistMeas() == null &&
                            customer.getCustomerShoulderMeas() == null) {
                        JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "User information", "Measurements", "You haven't provided your measurement information, please do it now.");
                        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("measurements.fxml"));
                        Parent parent = fxmlLoader.load();
                        Stage stage = (Stage) loginField.getScene().getWindow();
                        Scene scene = new Scene(parent);
                        MeasuramentsController measuramentsController = fxmlLoader.getController();
                        measuramentsController.setData(entityManagerFactory, customer);
                        stage.setTitle("Shop");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("main-shop.fxml"));
                        Parent parent = fxmlLoader.load();
                        MainShopController mainShopController = fxmlLoader.getController();
                        mainShopController.setData(entityManagerFactory, customer);
                        Scene scene = new Scene(parent);
                        Stage stage = (Stage) loginField.getScene().getWindow();
                        stage.setTitle("Music shop");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
    }
}
