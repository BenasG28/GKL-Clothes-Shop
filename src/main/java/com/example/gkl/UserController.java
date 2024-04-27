package com.example.gkl;

import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.Manager;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {
    public TableView<CustomerTableParameters> customerTable;
    public TableColumn<CustomerTableParameters, Integer> customerIdCol;
    public TableColumn<CustomerTableParameters, String> customerLoginCol;
    public TableColumn<CustomerTableParameters, String> customerPasswordCol;
    public TableColumn<CustomerTableParameters, String> customerContactMailCol;
    public TableColumn<CustomerTableParameters, String> customerBirthDateCol;
    public TableColumn<CustomerTableParameters, String> customerFirstNameCol;
    public TableColumn<CustomerTableParameters, String> customerLastNameCol;
    public TableView<ManagerTableParameters> managerTable;
    public TableColumn<ManagerTableParameters, Integer> managerIdCol;
    public TableColumn<ManagerTableParameters, String> managerLoginCol;
    public TableColumn<ManagerTableParameters, String> managerPasswordCol;
    public TableColumn<ManagerTableParameters, String> managerContactMailCol;
    public TableColumn<ManagerTableParameters, String> managerBirthDateCol;
    public TableColumn<ManagerTableParameters, String> managerFirstNameCol;
    public TableColumn<ManagerTableParameters, String> managerLastNameCol;
    private final ObservableList<CustomerTableParameters> customerData = FXCollections.observableArrayList();
    private final ObservableList<ManagerTableParameters> managerData = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private User currentUser;

    //----------------------User functionality-------------------------------//
    public void setData(EntityManagerFactory entityManagerFactory, User currentUser){
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);
    }
    public void initialize(){
        customerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (checkAdmin(currentUser)) {
                    getSelectedUser();
                } else {
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
                }
            }
        });
        managerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                getSelectedUser();
            }
        });
    }
    public void loadCustomerTable() {
        customerTable.getItems().clear();
        customerIdCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, Integer>("id"));
        customerLoginCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("login"));
        customerPasswordCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("password"));
        customerFirstNameCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("firstName"));
        customerLastNameCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("lastName"));
        customerContactMailCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("contactMail"));
        customerBirthDateCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("birthDate"));
        for (Customer customer : genericHib.getAllRecords(Customer.class)) {
            CustomerTableParameters customerTableParameters = new CustomerTableParameters();
            customerTableParameters.setId(customer.getId());
            customerTableParameters.setLogin(customer.getLogin());
            customerTableParameters.setPassword(customer.getPassword());
            customerTableParameters.setContactMail(customer.getContactMail());
            customerTableParameters.setBirthDate(String.valueOf(customer.getBirthDate()));
            customerTableParameters.setFirstName(customer.getFirstName());
            customerTableParameters.setLastName(customer.getLastName());
            customerData.add(customerTableParameters);
            customerTable.setItems(customerData);
        }
    }

    public void loadManagerTable() {
        managerTable.getItems().clear();
        managerIdCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, Integer>("id"));
        managerLoginCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("login"));
        managerPasswordCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("password"));
        managerFirstNameCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("firstName"));
        managerLastNameCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("lastName"));
        managerContactMailCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("contactMail"));
        managerBirthDateCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("birthDate"));
        for (Manager manager : genericHib.getAllRecords(Manager.class)) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();
            managerTableParameters.setId(manager.getId());
            managerTableParameters.setLogin(manager.getLogin());
            managerTableParameters.setPassword(manager.getPassword());
            managerTableParameters.setContactMail(manager.getContactMail());
            managerTableParameters.setBirthDate(String.valueOf(manager.getBirthDate()));
            managerTableParameters.setFirstName(manager.getFirstName());
            managerTableParameters.setLastName(manager.getLastName());
            managerData.add(managerTableParameters);
            managerTable.setItems(managerData);
        }
    }
    public void getSelectedUser() {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            var selectedItem = customerTable.getSelectionModel().getSelectedItem();
            editUser(selectedItem);
            customerTable.getSelectionModel().clearSelection();
        }
        if (managerTable.getSelectionModel().getSelectedItem() != null) {
            var selectedItem = managerTable.getSelectionModel().getSelectedItem();
            Manager manager = (Manager) currentUser;
            if (selectedItem.getId() == manager.getId() || manager.isAdmin()) {
                editUser(selectedItem);
                managerTable.getSelectionModel().clearSelection();
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
            }
        }
    }

    public boolean checkAdmin(User currentUser) {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            return manager.isAdmin();
        }
        return false;
    }

    @FXML
    private void createUser() throws IOException {
        if (checkAdmin(currentUser)) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
            Parent parent = fxmlLoader.load();
            RegistrationController registrationController = fxmlLoader.getController();
            registrationController.setData(entityManagerFactory, currentUser);
            Stage stage = (Stage) customerTable.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Create User");
            stage.setScene(scene);
            stage.show();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
        }
    }

    public void deleteUser() {
        if (checkAdmin(currentUser)) {
            if (customerTable.getSelectionModel().getSelectedItem() != null) {
                var selectedItem = customerTable.getSelectionModel().getSelectedItem();
                int selectedId = selectedItem.getId();
                genericHib.delete(User.class, selectedId);
                loadCustomerTable();
            }
            if (managerTable.getSelectionModel().getSelectedItem() != null) {
                var selectedItem = managerTable.getSelectionModel().getSelectedItem();
                int selectedId = selectedItem.getId();
                genericHib.delete(User.class, selectedId);
                loadManagerTable();
            }
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
        }

    }

    private void editUser(TableParameters selectedUser) {
        int selectedId = selectedUser.getId();
        User user = genericHib.getEntityById(User.class, selectedId);
        var userType = user.getClass();
        Stage editCustomerStage = new Stage();
        VBox root = new VBox();
        TextField loginField = new TextField(user.getLogin());
        TextField passwordField = new TextField(user.getPassword());
        TextField firstNameField = new TextField(user.getFirstName());
        TextField lastNameField = new TextField(user.getLastName());
        TextField contactMailField = new TextField(user.getContactMail());
        TextField phoneNumberField = new TextField(user.getPhoneNumber());
        DatePicker birthDatePicker = new DatePicker(user.getBirthDate());
        CheckBox discountCardCheck = new CheckBox("Discount card");
        discountCardCheck.setDisable(true);
        TextField addressField = new TextField(user.getAddress());
        CheckBox adminCheck = new CheckBox("Admin");
        adminCheck.setDisable(true);
        TextField employeeIdField = new TextField();
        employeeIdField.setDisable(true);
        TextField medCertificateField = new TextField();
        medCertificateField.setDisable(true);
        DatePicker employmentDatePicker = new DatePicker();
        employmentDatePicker.setDisable(true);
        if (userType.equals(Customer.class)) {
            Customer customer = (Customer) user;
            discountCardCheck.setDisable(false);
            discountCardCheck.setSelected(customer.isDiscountCard());
        } else if (userType.equals(Manager.class)) {
            Manager manager = (Manager) user;
            employeeIdField.setDisable(false);
            employeeIdField.setText(manager.getEmployeeId());
            medCertificateField.setDisable(false);
            medCertificateField.setText(manager.getMedCertificate());
            employmentDatePicker.setDisable(false);
            employmentDatePicker.setValue(manager.getEmploymentDate());
            if (checkAdmin(currentUser)) {
                adminCheck.setDisable(false);
                adminCheck.setSelected(manager.isAdmin());
            }
        }
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label loginLabel = new Label("Login:");
        Label passwordLabel = new Label("Password:");
        Label contactMailLabel = new Label("Contact mail:");
        Label phoneNumberLabel = new Label("Phone number:");
        Label birthDateLabel = new Label("Birth Date:");
        Label addressLabel = new Label("Address:");
        Label employeeIdLabel = new Label("Employee ID:");
        Label medCertLabel = new Label("Medical Certificate:");
        Label employeeDateLabel = new Label("Employee Date Of Employ:");
        Button updateUserButton = new Button("Update");
        updateUserButton.setOnAction(event -> {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setLogin(loginField.getText());
            user.setPasswordHashed(passwordField.getText());
            user.setContactMail(contactMailField.getText());
            user.setPhoneNumber(phoneNumberField.getText());
            user.setBirthDate(birthDatePicker.getValue());
            user.setAddress(addressField.getText());
            if (user instanceof Customer customer) {
                customer.setDiscountCard(discountCardCheck.isSelected());
            } else if (user instanceof Manager manager) {
                manager.setEmployeeId(employeeIdField.getText());
                manager.setAdmin(adminCheck.isSelected());
                manager.setMedCertificate(medCertificateField.getText());
                manager.setEmploymentDate(employmentDatePicker.getValue());
            }
            try {
                genericHib.update(user);
                if (user instanceof Customer) {
                    loadCustomerTable();
                } else if (user instanceof Manager) {
                    loadManagerTable();
                }
                editCustomerStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, firstNameLabel, firstNameField, lastNameLabel, lastNameField, contactMailLabel, contactMailField, phoneNumberLabel, phoneNumberField, birthDateLabel, birthDatePicker, addressLabel, addressField, discountCardCheck, employeeIdLabel, employeeIdField, medCertLabel, medCertificateField, employeeDateLabel, employmentDatePicker, adminCheck, updateUserButton);
        Scene scene = new Scene(root, 500, 650);
        editCustomerStage.setTitle("Edit User");
        editCustomerStage.setScene(scene);
        editCustomerStage.show();
    }

}
