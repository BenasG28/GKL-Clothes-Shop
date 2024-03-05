package com.example.demo1.fxControllers;

import com.example.demo1.StartGui;
import com.example.demo1.hibernateControllers.GenericHib;
import com.example.demo1.hibernateControllers.UserHib;
import com.example.demo1.model.Customer;
import com.example.demo1.model.Manager;
import com.example.demo1.model.User;
import com.example.demo1.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
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
    public TextField uniqueIdField;
    @FXML
    public TextField medCertField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public DatePicker employDatePicker;
    @FXML
    public RadioButton customerRadio;
    @FXML
    public RadioButton managerRadio;
    @FXML
    public Button createUserButton;
    @FXML
    public Button backButton;
    @FXML
    public ToggleGroup userType;
    @FXML
    public CheckBox isAdminCheck;
    @FXML
    public TextField contactMailField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public CheckBox wantDiscountCard;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private GenericHib genericHib;
    private String locationOfFile;
    private User user;
    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields, String location, User user) {
        this.user = user;
        this.locationOfFile = location;
        this.entityManagerFactory = entityManagerFactory;
        hideFields(showManagerFields);
    }
    private boolean checkIfFieldsEmpty(TextField loginField, TextField passwordField, TextField contactMailField, DatePicker birthDatePicker, TextField nameField, TextField lastNameField, TextField phoneNumberField, TextField addressField, RadioButton customerRadio, RadioButton managerRadio){
        return loginField.getText().isEmpty() || passwordField.getText().isEmpty() || contactMailField.getText().isEmpty() || birthDatePicker.getValue() == null || nameField.getText().isEmpty() || lastNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || addressField.getText().isEmpty() || (customerRadio.isDisabled() && managerRadio.isDisabled());
    }
    private void hideFields(boolean showManagerFields) {
        if(!showManagerFields){
            uniqueIdField.setVisible(false);
            medCertField.setVisible(false);
            isAdminCheck.setVisible(false);
            employDatePicker.setVisible(false);
            managerRadio.setVisible(false);
        }
    }
    private boolean isPasswordMatch(PasswordField passwordField, PasswordField repeatPasswordField){
        return passwordField.getText().equals(repeatPasswordField.getText());
    }
    public void createUser(){
        genericHib = new GenericHib(entityManagerFactory);
        userHib = new UserHib(entityManagerFactory);
        boolean isEmpty = checkIfFieldsEmpty(loginField, passwordField,contactMailField,birthDatePicker,nameField,lastNameField,phoneNumberField,addressField,customerRadio,managerRadio);
        if(isEmpty){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration Error", "Missing fields", "Please fill all the fields and check all the checks.");
        }
        else if(userHib.checkIfLoginExists(loginField.getText())){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration error", "Login already in use", "Please choose another login");
        }
        else if(!isPasswordMatch(passwordField, repeatPasswordField)){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Registration error", "Passwords do not match", "Please match passwords");
        }
        else if(customerRadio.isSelected() ){
            try {
                Customer customer = new Customer(loginField.getText(), contactMailField.getText(), birthDatePicker.getValue(), nameField.getText(), lastNameField.getText(), phoneNumberField.getText(), addressField.getText(), wantDiscountCard.isSelected());
                customer.setPasswordHashed(passwordField.getText());
                genericHib.create(customer);
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration Success", "Registration worked", "Nice job!");
                goBack(locationOfFile, user);
            }catch(Exception e){
                e.printStackTrace();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING,"Registration Error", "Error during registration", "Got an error trying to register");

            }
        }
        else if(managerRadio.isSelected() ){
            try {
                Manager manager = new Manager(loginField.getText(), contactMailField.getText(), birthDatePicker.getValue(), nameField.getText(), lastNameField.getText(), phoneNumberField.getText(), addressField.getText(), isAdminCheck.isSelected(), uniqueIdField.getText(), medCertField.getText(), employDatePicker.getValue());
                manager.setPasswordHashed(passwordField.getText());
                genericHib.create(manager);
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration Success", "Registration worked", "Nice job!");
                goBack(locationOfFile,user);
            }catch(Exception e){
                e.printStackTrace();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING,"Registration Error", "Error during registration", "Got an error trying to register");

            }
        }


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> {
            try {
                goBack(String.valueOf(locationOfFile), user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void goBack(String filename, User user) throws IOException {
          FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource(filename));
          Parent parent = fxmlLoader.load();
          Scene scene = new Scene(parent);
          Stage stage = (Stage) customerRadio.getScene().getWindow();
          if(user != null){
              MainShopController mainShopController = fxmlLoader.getController();
              mainShopController.setData(entityManagerFactory, user);
          }
          stage.setScene(scene);
          stage.show();
    }

}
