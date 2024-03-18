package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.CommentType;
import com.example.gkl.model.Customer;
import com.example.gkl.model.Manager;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountController implements PasswordChangedCallback{

    public TextField nameTextfield;
    public Button changePasswordButton;
    private StringProperty firstName = new SimpleStringProperty();
    public Text nameText;
    public Text surnameText;
    public TextField surnameTextField;
    private StringProperty lastName = new SimpleStringProperty();
    public Text loginText;
    public TextField loginTextfield;
    private StringProperty login = new SimpleStringProperty();
    public Text emailText;
    public TextField emailTextfield;
    private StringProperty email = new SimpleStringProperty();
    public Text passwordText;
    public TextField waistTextfield;
    private DoubleProperty waistMeas = new SimpleDoubleProperty();
    public Text waistText;
    public Text hipText;
    public TextField hipTextfield;
    private DoubleProperty hipMeas = new SimpleDoubleProperty();
    public Text inseamText;
    public TextField inseamTextfield;
    private DoubleProperty inseamMeas = new SimpleDoubleProperty();
    public Text legLengthText;
    public TextField legLengthTextfield;
    private DoubleProperty legLengthMeas = new SimpleDoubleProperty();
    public Text shoulderText;
    public TextField shoulderTextfield;
    private DoubleProperty shoulderMeas = new SimpleDoubleProperty();
    public Text chestText;
    public TextField chestTextfield;
    private DoubleProperty chestMeas = new SimpleDoubleProperty();
    public Text upperBodyText;
    public Text backText;
    public TextField backTextfield;
    private DoubleProperty backMeas = new SimpleDoubleProperty();
    public Text sleeveText;
    public TextField sleeveTextfield;
    private DoubleProperty sleeveMeas = new SimpleDoubleProperty();
    public Button sizeHelpButton;
    public Button editInfoButton;
    public PasswordField passwordField;
    private StringProperty password = new SimpleStringProperty();
    public Button saveInfoButton;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private User currentUser;
    private Map<Property<?>, Object> originalValues = new HashMap<>();
    private Map<Property<?>, Object> modifiedValues = new HashMap<>();

    @FXML
    public void initialize(){
        nameTextfield.textProperty().bindBidirectional(firstName);
        surnameTextField.textProperty().bindBidirectional(lastName);
        loginTextfield.textProperty().bindBidirectional(login);
        emailTextfield.textProperty().bindBidirectional(email);
        waistTextfield.textProperty().bindBidirectional(waistMeas, new NumberStringConverter());
        hipTextfield.textProperty().bindBidirectional(hipMeas, new NumberStringConverter());
        inseamTextfield.textProperty().bindBidirectional(inseamMeas, new NumberStringConverter());
        legLengthTextfield.textProperty().bindBidirectional(legLengthMeas, new NumberStringConverter());
        shoulderTextfield.textProperty().bindBidirectional(shoulderMeas, new NumberStringConverter());
        chestTextfield.textProperty().bindBidirectional(chestMeas, new NumberStringConverter());
        backTextfield.textProperty().bindBidirectional(backMeas, new NumberStringConverter());
        sleeveTextfield.textProperty().bindBidirectional(sleeveMeas, new NumberStringConverter());
        addListeners();
    }

    private void addListeners() {
        addListenerToProperty(firstName);
        addListenerToProperty(lastName);
        addListenerToProperty(login);
        addListenerToProperty(email);
        addListenerToProperty(waistMeas);
        addListenerToProperty(hipMeas);
        addListenerToProperty(inseamMeas);
        addListenerToProperty(legLengthMeas);
        addListenerToProperty(shoulderMeas);
        addListenerToProperty(chestMeas);
        addListenerToProperty(backMeas);
        addListenerToProperty(sleeveMeas);
        saveOriginalValues();
    }

    private void saveOriginalValues() {
        originalValues.clear();
        originalValues.put(firstName, firstName.getValue());
        originalValues.put(lastName, lastName.getValue());
        originalValues.put(login, login.getValue());
        originalValues.put(email, email.getValue());
        originalValues.put(waistMeas, waistMeas.getValue());
        originalValues.put(hipMeas, hipMeas.getValue());
        originalValues.put(inseamMeas, inseamMeas.getValue());
        originalValues.put(legLengthMeas, legLengthMeas.getValue());
        originalValues.put(shoulderMeas, shoulderMeas.getValue());
        originalValues.put(chestMeas, chestMeas.getValue());
        originalValues.put(backMeas, backMeas.getValue());
        originalValues.put(sleeveMeas, sleeveMeas.getValue());
    }

    private void addListenerToProperty(Property<?> property) {
        modifiedValues.put(property, property.getValue());  // Initial value

        property.addListener((obs, oldValue, newValue) -> {
            modifiedValues.put(property, newValue);  // Update current value

            Platform.runLater(() -> {
                boolean isModified = !Objects.equals(originalValues.get(property), newValue);
                saveInfoButton.setDisable(!isModified);
            });
        });
//        property.addListener((obs, oldValue, newValue) ->{
////                    if(!Objects.equals(String.valueOf(newValue), String.valueOf(originalValues.get(property)))){
////                        saveInfoButton.setDisable(false);
////                    }
////                    else{
////                        saveInfoButton.setDisable(true);
////                    }
//
////            boolean anyValueChanged = originalValues.entrySet().stream()
////                    .anyMatch(entry -> !Objects.equals(entry.getValue(), entry.getKey().getValue()));
////
////            saveInfoButton.setDisable(!anyValueChanged);
//
//            Property<?> currentProperty = (Property<?>) obs;  // Get a reference to the current property
//            Platform.runLater(() -> {  // Schedule comparison for later execution
//                boolean valueChanged = !Objects.equals(originalValues.get(currentProperty), newValue);
//                saveInfoButton.setDisable(!valueChanged);
//            });
//                });

//        property.addListener((obs, oldValue, newValue) -> {
//            Property<?> currentProperty = (Property<?>) obs;
//            System.out.println("Changed property: " + currentProperty.getName()); // Print property name
//            System.out.println("Old value: " + oldValue);
//            System.out.println("New value: " + newValue);
//
//            Platform.runLater(() -> {
//                boolean valueChanged = !Objects.equals(originalValues.get(currentProperty), newValue);
//                System.out.println("Value changed: " + valueChanged);
//                saveInfoButton.setDisable(!valueChanged);
//            });
//        });
    }


    public void setData(EntityManagerFactory entityManagerFactory, User currentUser){
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);

    }
    public void loadUserInfo(){
        if(currentUser.getClass() == Customer.class){
            loadCustomerInfo();
        }
        else if(currentUser.getClass() == Manager.class){
            loadManagerInfo();
        }
    }
    @FXML
    private void handleChangePassword(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("passwordEditWindow.fxml"));
            Parent passwordRoot = fxmlLoader.load();
            ChangePasswordController changePasswordController = fxmlLoader.getController();
            changePasswordController.setCallback(this);
            Stage passwordStage = new Stage();
            passwordStage.initModality(Modality.APPLICATION_MODAL);
            passwordStage.setTitle("Change Password");
            Scene scene = new Scene(passwordRoot);
            passwordStage.setScene(scene);
            changePasswordController.setData(entityManagerFactory, currentUser, passwordStage);
            passwordStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void makeFieldsViewOnly() {
        nameTextfield.setDisable(true);
        surnameTextField.setDisable(true);
        loginTextfield.setDisable(true);
        emailTextfield.setDisable(true);
        editInfoButton.setVisible(true);
        saveInfoButton.setVisible(false);
        saveInfoButton.setDisable(false);
        if (currentUser.getClass() == Customer.class) {
            chestTextfield.setDisable(true);
            shoulderTextfield.setDisable(true);
            backTextfield.setDisable(true);
            sleeveTextfield.setDisable(true);
            hipTextfield.setDisable(true);
            legLengthTextfield.setDisable(true);
            inseamTextfield.setDisable(true);
            waistTextfield.setDisable(true);
        }
    }
    private void makeFieldsEditable(){
            nameTextfield.setDisable(false);
            surnameTextField.setDisable(false);
            loginTextfield.setDisable(false);
            emailTextfield.setDisable(false);
            editInfoButton.setVisible(false);
            saveInfoButton.setVisible(true);
            saveInfoButton.setDisable(true);
        if(currentUser.getClass() == Customer.class){
            chestTextfield.setDisable(false);
            shoulderTextfield.setDisable(false);
            backTextfield.setDisable(false);
            sleeveTextfield.setDisable(false);
            hipTextfield.setDisable(false);
            legLengthTextfield.setDisable(false);
            inseamTextfield.setDisable(false);
            waistTextfield.setDisable(false);
            editInfoButton.setVisible(false);
            saveInfoButton.setVisible(true);
            saveInfoButton.setDisable(true);
        }
    }

    @FXML
    private void handleEditInfo(){
        makeFieldsEditable();
    }
    @FXML
    private void handleSaveInfo() {
        currentUser.setFirstName(nameTextfield.getText());
        currentUser.setLastName(surnameTextField.getText());
        currentUser.setLogin(loginTextfield.getText());
        currentUser.setContactMail(emailTextfield.getText());
        if(currentUser instanceof Customer customer){
            customer.setCustomerBackMeas(Double.valueOf(backTextfield.getText()));
            System.out.println("DEBUG TRY: " +Double.valueOf(backTextfield.getText()));

            customer.setCustomerChestMeas(Double.valueOf(chestTextfield.getText()));

            customer.setCustomerShoulderMeas(Double.valueOf(shoulderTextfield.getText()));
            customer.setCustomerSleeveMeas(Double.valueOf(sleeveTextfield.getText()));
            customer.setCustomerInseamMeas(Double.valueOf(inseamTextfield.getText()));
            customer.setCustomerHipMeas(Double.valueOf(hipTextfield.getText()));
            customer.setCustomerLegLengthMeas(Double.valueOf(legLengthTextfield.getText()));
            customer.setCustomerWaistMeas(Double.valueOf(waistTextfield.getText()));
        }
        try{
            genericHib.update(currentUser);
            loadUserInfo();
            makeFieldsViewOnly();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadManagerInfo() {
        Manager currentManager = (Manager) currentUser;
        nameTextfield.setText(currentManager.getFirstName());
        surnameTextField.setText(currentManager.getLastName());
        loginTextfield.setText(currentManager.getLogin());
        emailTextfield.setText(currentManager.getContactMail());
        passwordField.setText(currentManager.getPassword());
    }

    private void loadCustomerInfo() {
        Customer currentCustomer = (Customer) currentUser;
        nameTextfield.setText(currentCustomer.getFirstName());
        surnameTextField.setText(currentCustomer.getLastName());
        loginTextfield.setText(currentCustomer.getLogin());
        emailTextfield.setText(currentCustomer.getContactMail());
        passwordField.setText(currentCustomer.getPassword());
        chestTextfield.setText(currentCustomer.getCustomerChestMeas().toString());
        shoulderTextfield.setText(currentCustomer.getCustomerShoulderMeas().toString());
        backTextfield.setText(currentCustomer.getCustomerBackMeas().toString());
        sleeveTextfield.setText(currentCustomer.getCustomerSleeveMeas().toString());
        hipTextfield.setText(currentCustomer.getCustomerHipMeas().toString());
        legLengthTextfield.setText(currentCustomer.getCustomerLegLengthMeas().toString());
        inseamTextfield.setText(currentCustomer.getCustomerInseamMeas().toString());
        waistTextfield.setText(currentCustomer.getCustomerWaistMeas().toString());
    }

    @Override
    public void onPasswordChanged() {
        loadUserInfo();
    }
}
