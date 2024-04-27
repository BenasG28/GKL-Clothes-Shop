package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.*;
import com.example.gkl.utils.CustomerMeasurementProcessor;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public StringProperty firstName = new SimpleStringProperty();
    public Text nameText;
    public Text surnameText;
    public TextField surnameTextField;
    public StringProperty lastName = new SimpleStringProperty();
    public Text loginText;
    public TextField loginTextfield;
    public StringProperty login = new SimpleStringProperty();
    public Text emailText;
    public TextField emailTextfield;
    public StringProperty email = new SimpleStringProperty();
    public Text passwordText;
    public TextField waistTextfield;
    public DoubleProperty waistMeas = new SimpleDoubleProperty();
    public Text waistText;
    public Text hipText;
    public TextField hipTextfield;
    public DoubleProperty hipMeas = new SimpleDoubleProperty();
    public Text inseamText;
    public TextField inseamTextfield;
    public DoubleProperty inseamMeas = new SimpleDoubleProperty();
    public Text legLengthText;
    public TextField legLengthTextfield;
    public DoubleProperty legLengthMeas = new SimpleDoubleProperty();
    public Text shoulderText;
    public TextField shoulderTextfield;
    public DoubleProperty shoulderMeas = new SimpleDoubleProperty();
    public Text chestText;
    public TextField chestTextfield;
    public DoubleProperty chestMeas = new SimpleDoubleProperty();
    public Text upperBodyText;
    public Text backText;
    public TextField backTextfield;
    public DoubleProperty backMeas = new SimpleDoubleProperty();
    public Text sleeveText;
    public TextField sleeveTextfield;
    public DoubleProperty sleeveMeas = new SimpleDoubleProperty();
    public Button sizeHelpButton;
    public Button editInfoButton;
    public PasswordField passwordField;
    public ComboBox regionSelectionAccountCombobox;
    public StringProperty regionSelection = new SimpleStringProperty();
    public TextField upperSizeTextfield;
    public TextField lowerSizeTextfield;
    private StringProperty password = new SimpleStringProperty();
    public Button saveInfoButton;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private User currentUser;
    private Map<Property<?>, Object> originalValues = new HashMap<>();
    private Map<Property<?>, Object> modifiedValues = new HashMap<>();

    @FXML
        public void initialize(){
            bindProperties();
            addListeners();
            regionSelectionAccountCombobox.getItems().addAll(Regions.values());
        // Add listener to update regionSelection when ComboBox selection changes
            regionSelectionAccountCombobox.valueProperty().addListener((obs, oldValue, newValue) -> {
                regionSelection.set(newValue.toString());
            });
    }
    private void bindProperties(){
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
        addListenerToProperty(regionSelection);
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
        originalValues.put(regionSelection, regionSelection.getValue());
    }

    private void addListenerToProperty(Property<?> property) {
        if (property instanceof StringProperty && property.equals(regionSelection)) {
            // Add listener to regionSelection separately
            StringProperty stringProperty = (StringProperty) property;
            stringProperty.addListener((obs, oldValue, newValue) -> {
                modifiedValues.put(stringProperty, newValue);  // Update current value

                Platform.runLater(() -> {
                    boolean isModified = !Objects.equals(originalValues.get(stringProperty), newValue);
                    saveInfoButton.setDisable(!isModified);
                });
            });
        } else {
            // Add listener to other properties as before
            modifiedValues.put(property, property.getValue());  // Initial value
            property.addListener((obs, oldValue, newValue) -> {
                modifiedValues.put(property, newValue);  // Update current value

                Platform.runLater(() -> {
                    boolean isModified = !Objects.equals(originalValues.get(property), newValue);
                    saveInfoButton.setDisable(!isModified);
                });
            });
        }
    }



    public void setData(EntityManagerFactory entityManagerFactory, User currentUser){
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);

    }
    public void loadUserInfo(){
        if(currentUser.getClass() == Customer.class){
            if(currentUser.getSelectedRegion() == Regions.UK || currentUser.getSelectedRegion() == Regions.US){
                backText.setText("Back(inch)");
                chestText.setText("Chest(inch)");
                shoulderText.setText("Shoulder(inch)");
                sleeveText.setText("Sleeve(inch)");
                hipText.setText("Hip(inch)");
                legLengthText.setText("Leg Length(inch)");
                inseamText.setText("Inseam(inch)");
                waistText.setText("Waist(inch)");
            }
            else{
                backText.setText("Back(cm)");
                chestText.setText("Chest(cm)");
                shoulderText.setText("Shoulder(cm)");
                sleeveText.setText("Sleeve(cm)");
                hipText.setText("Hip(cm)");
                legLengthText.setText("Leg Length(cm)");
                inseamText.setText("Inseam(cm)");
                waistText.setText("Waist(cm)");
            }
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
        regionSelectionAccountCombobox.setDisable(true);
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
            regionSelectionAccountCombobox.setDisable(false);
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
    private boolean checkIfPersonalFieldsAreEmpty(){
        if(nameTextfield.getText().isEmpty() ||
                surnameTextField.getText().isEmpty() ||
                loginTextfield.getText().isEmpty() ||
                emailTextfield.getText().isEmpty()){
            return true;
        }

        return false;
    }
    private boolean checkIfMeasurementsFieldsAreEmpty(){
        if(backTextfield.getText().isEmpty() ||
                chestTextfield.getText().isEmpty() ||
                shoulderTextfield.getText().isEmpty() ||
                sleeveTextfield.getText().isEmpty() ||
                inseamTextfield.getText().isEmpty() ||
                hipTextfield.getText().isEmpty() ||
                legLengthTextfield.getText().isEmpty() ||
                waistTextfield.getText().isEmpty()){
            return true;
        }
        return false;
    }
    private void updateUniversalSizes(){
        Customer currentCustomer = (Customer) currentUser;
        CustomerMeasurementProcessor customerMeasurementProcessor = new CustomerMeasurementProcessor(currentCustomer);
        currentCustomer.setUpperBodyUniversalSize(customerMeasurementProcessor.generateUpperBodyUniversalSize());
        currentCustomer.setLowerBodyUniversalSize(customerMeasurementProcessor.generateLowerBodyUniversalSize());
        genericHib.update(currentCustomer);
    }
    @FXML
    private void handleSaveInfo() {
        if(!checkIfPersonalFieldsAreEmpty()){
            boolean measurementsFieldsFlag = false;
            if(currentUser instanceof Customer customer){
                measurementsFieldsFlag = checkIfMeasurementsFieldsAreEmpty();
            }
            if(!measurementsFieldsFlag){
                currentUser.setFirstName(nameTextfield.getText());
                currentUser.setLastName(surnameTextField.getText());
                currentUser.setLogin(loginTextfield.getText());
                currentUser.setContactMail(emailTextfield.getText());
                currentUser.setSelectedRegion((Regions) regionSelectionAccountCombobox.getValue());
                if(currentUser instanceof Customer customer){
                    if(currentUser.getSelectedRegion() == Regions.UK || currentUser.getSelectedRegion() == Regions.US){
                        customer.setCustomerBackMeas(convertToCm(Double.valueOf(backTextfield.getText())));
                        customer.setCustomerChestMeas(convertToCm(Double.valueOf(chestTextfield.getText())));
                        customer.setCustomerShoulderMeas(convertToCm(Double.valueOf(shoulderTextfield.getText())));
                        customer.setCustomerSleeveMeas(convertToCm(Double.valueOf(sleeveTextfield.getText())));
                        customer.setCustomerInseamMeas(convertToCm(Double.valueOf(inseamTextfield.getText())));
                        customer.setCustomerHipMeas(convertToCm(Double.valueOf(hipTextfield.getText())));
                        customer.setCustomerLegLengthMeas(convertToCm(Double.valueOf(legLengthTextfield.getText())));
                        customer.setCustomerWaistMeas(convertToCm(Double.valueOf(waistTextfield.getText())));
                    }
                    else{
                        customer.setCustomerBackMeas(Double.valueOf(backTextfield.getText()));
                        customer.setCustomerChestMeas(Double.valueOf(chestTextfield.getText()));
                        customer.setCustomerShoulderMeas(Double.valueOf(shoulderTextfield.getText()));
                        customer.setCustomerSleeveMeas(Double.valueOf(sleeveTextfield.getText()));
                        customer.setCustomerInseamMeas(Double.valueOf(inseamTextfield.getText()));
                        customer.setCustomerHipMeas(Double.valueOf(hipTextfield.getText()));
                        customer.setCustomerLegLengthMeas(Double.valueOf(legLengthTextfield.getText()));
                        customer.setCustomerWaistMeas(Double.valueOf(waistTextfield.getText()));
                    }
                }
                try{
                    genericHib.update(currentUser);
                    updateUniversalSizes();
                    loadUserInfo();
                    makeFieldsViewOnly();


                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update Error", "Missing Fields", "Please fill out all the measurement information fields.");

            }
        }else{
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update Error", "Missing Fields", "Please fill out all the personal information fields.");
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

    private Double convertToCm(double meas){
        return meas*2.54;
    }
    private Double convertToInch(double meas){
        return meas/2.54;
    }

    private void loadCustomerInfo() {
        Customer currentCustomer = (Customer) currentUser;
        nameTextfield.setText(currentCustomer.getFirstName());
        surnameTextField.setText(currentCustomer.getLastName());
        loginTextfield.setText(currentCustomer.getLogin());
        emailTextfield.setText(currentCustomer.getContactMail());
        passwordField.setText(currentCustomer.getPassword());
        regionSelectionAccountCombobox.setValue(currentCustomer.getSelectedRegion());
        upperSizeTextfield.setText(currentCustomer.getUpperBodyUniversalSize());
        lowerSizeTextfield.setText(currentCustomer.getLowerBodyUniversalSize());
        if(currentCustomer.getSelectedRegion() == Regions.UK || currentCustomer.getSelectedRegion() == Regions.US){
            chestTextfield.setText(convertToInch(currentCustomer.getCustomerChestMeas()).toString());
            shoulderTextfield.setText(convertToInch(currentCustomer.getCustomerShoulderMeas()).toString());
            backTextfield.setText(convertToInch(currentCustomer.getCustomerBackMeas()).toString());
            sleeveTextfield.setText(convertToInch(currentCustomer.getCustomerSleeveMeas()).toString());
            hipTextfield.setText(convertToInch(currentCustomer.getCustomerHipMeas()).toString());
            legLengthTextfield.setText(convertToInch(currentCustomer.getCustomerLegLengthMeas()).toString());
            inseamTextfield.setText(convertToInch(currentCustomer.getCustomerInseamMeas()).toString());
            waistTextfield.setText(convertToInch(currentCustomer.getCustomerWaistMeas()).toString());
        }
        else{
            chestTextfield.setText(currentCustomer.getCustomerChestMeas().toString());
            shoulderTextfield.setText(currentCustomer.getCustomerShoulderMeas().toString());
            backTextfield.setText(currentCustomer.getCustomerBackMeas().toString());
            sleeveTextfield.setText(currentCustomer.getCustomerSleeveMeas().toString());
            hipTextfield.setText(currentCustomer.getCustomerHipMeas().toString());
            legLengthTextfield.setText(currentCustomer.getCustomerLegLengthMeas().toString());
            inseamTextfield.setText(currentCustomer.getCustomerInseamMeas().toString());
            waistTextfield.setText(currentCustomer.getCustomerWaistMeas().toString());
        }

    }

    @Override
    public void onPasswordChanged() {
        loadUserInfo();
    }
}
