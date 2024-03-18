package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MeasuramentsController implements Initializable {
    @FXML
    public TextField chestmeasure;
    @FXML
    public TextField shouldermeasure;
    @FXML
    public TextField backmeasure;
    @FXML
    public TextField sleevemeasure;
    @FXML
    public TextField hipmeasure;
    @FXML
    public TextField outseammeasure;
    @FXML
    public TextField inseammeasure;
    @FXML
    public TextField waistmeasure;
    public Button saveButton;
    //public Button backButton;
    private EntityManagerFactory entityManagerFactory;
    private Customer currentCustomer;
    private UserHib userHib;

    public void setData(EntityManagerFactory entityManagerFactory, Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
        this.entityManagerFactory = entityManagerFactory;
    }
    private boolean checkIfFieldsEmpty(TextField chestmeasure, TextField shouldermeasure, TextField backmeasure, TextField sleevemeasure,
                                       TextField hipmeasure, TextField outseammeasure, TextField inseammeasure, TextField waistmeasure){
        return chestmeasure.getText().isEmpty() || shouldermeasure.getText().isEmpty()
                || backmeasure.getText().isEmpty() || sleevemeasure.getText().isEmpty()
                || hipmeasure.getText().isEmpty() || outseammeasure.getText().isEmpty()
                || inseammeasure.getText().isEmpty() || waistmeasure.getText().isEmpty();
    }

   /* public void goBack() throws IOException { Pati grįžimo funkcija
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) shouldermeasure.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }*/

    public void saveButtonFunc() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("main-shop.fxml"));
        Parent parent = fxmlLoader.load();
        MainShopController mainShopController = fxmlLoader.getController();
        mainShopController.setData(entityManagerFactory, currentCustomer);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) shouldermeasure.getScene().getWindow();
        stage.setTitle("GKL");
        stage.setScene(scene);
        stage.show();
    }

    public void editUserMeasurements() {
        userHib = new UserHib(entityManagerFactory);
        Customer existingCustomer = currentCustomer;

        existingCustomer.setCustomerWaistMeas(Double.parseDouble(waistmeasure.getText()));
        existingCustomer.setCustomerHipMeas(Double.parseDouble(hipmeasure.getText()));
        existingCustomer.setCustomerInseamMeas(Double.parseDouble(inseammeasure.getText()));
        existingCustomer.setCustomerLegLengthMeas(Double.parseDouble(outseammeasure.getText()));
        existingCustomer.setCustomerShoulderMeas(Double.parseDouble(shouldermeasure.getText()));
        existingCustomer.setCustomerChestMeas(Double.parseDouble(chestmeasure.getText()));
        existingCustomer.setCustomerBackMeas(Double.parseDouble(backmeasure.getText()));
        existingCustomer.setCustomerSleeveMeas(Double.parseDouble(sleevemeasure.getText()));

        userHib.updateCustomer(existingCustomer);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*backButton.setOnAction(event -> { Jeigu kada nors reikės back mygtuko funkcijos čia galite rasti gaspadoriai.
            try {
                goBack();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });*/
        saveButton.setOnAction(event -> {
            try {
                userHib = new UserHib(entityManagerFactory);
                boolean isEmpty = checkIfFieldsEmpty(chestmeasure, shouldermeasure,backmeasure,sleevemeasure,hipmeasure,outseammeasure,inseammeasure,waistmeasure);
                if(isEmpty){
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "User alert", "Missing fields", "Please fill all the fields.");
                }
                else{
                    editUserMeasurements();
                    saveButtonFunc();
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "User information", "Measurements", "The user measurements has been filled successfully.");
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
