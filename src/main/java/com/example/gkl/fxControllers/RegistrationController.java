package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class RegistrationController implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField addressField;
    @FXML
    public Button createUserButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField contactMailField;
    @FXML
    public TextField phoneNumberField;
    public Button measurementsButton;
    EntityManagerFactory entityManagerFactory;
    public UserHib userHib;
    private User user;
    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.user = user;
        this.entityManagerFactory = entityManagerFactory;
    }
    public boolean checkIfFieldsEmpty(TextField loginField, TextField passwordField, TextField contactMailField, TextField nameField, TextField lastNameField, TextField phoneNumberField, TextField addressField){
        return loginField.getText().isEmpty() || passwordField.getText().isEmpty() || contactMailField.getText().isEmpty() || nameField.getText().isEmpty() || lastNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || addressField.getText().isEmpty();
    }
    public boolean isPasswordMatch(PasswordField passwordField, PasswordField repeatPasswordField){
        return passwordField.getText().equals(repeatPasswordField.getText());
    }
   public void createUser(){

        userHib = new UserHib(entityManagerFactory);
       if (userHib == null) {
           throw new IllegalStateException("UserHib is not initialized. Call setData() first.");
       }
        User user = Customer.builder()
                .login(loginField.getText())
                .password(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()))
                .phoneNumber(phoneNumberField.getText())
                .firstName(nameField.getText())
                .lastName(lastNameField.getText())
                .address(addressField.getText())
                .contactMail(contactMailField.getText())
                .build();
        userHib.createUser(user);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> {
            try {
                goBack(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        createUserButton.setOnAction(event -> {
            try {
                userHib = new UserHib(entityManagerFactory);
                String password = passwordField.getText();
                boolean isEmpty = checkIfFieldsEmpty(loginField, passwordField,contactMailField,nameField,lastNameField,phoneNumberField,addressField);
                if(isEmpty){
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration Error", "Missing fields", "Please fill all the fields and check all the checks.");
                }
                else if(userHib.checkIfLoginExists(loginField.getText())){
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration error", "Login already in use", "Please choose another login");
                }
                else if(!isPasswordMatch(passwordField, repeatPasswordField)){
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration error", "Passwords do not match", "Please match passwords");
                }
                else if (!password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[!@#$%^&*()_+?~`<>,.±§'/|:;].*")) {
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Password Error", "Password needs to be stronger" ,"Password must contain at least one number, one capital letter, and one special symbol like '@'.");
                }
                else{
                    createUser();
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "User information", "Registration", "The user has been created successfully");
                    goBack(user);
                }

            } catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }

    public void goBack(User user) throws IOException {
          FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("login.fxml"));
          Parent parent = fxmlLoader.load();
          Scene scene = new Scene(parent);
          Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("GKL");
          if(user != null){
              MainShopController mainShopController = fxmlLoader.getController();
              mainShopController.setData(entityManagerFactory, user);
          }
          stage.setScene(scene);
          stage.show();
    }

 /*   public void setMeasurementsButton() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("measurements.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        MeasuramentsController measuramentsController = fxmlLoader.getController();
        measuramentsController.setData(entityManagerFactory, null);
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }*/

}
