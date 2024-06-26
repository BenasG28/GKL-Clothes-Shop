package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.UserHib;
import com.example.gkl.model.Customer;
import com.example.gkl.model.Regions;
import com.example.gkl.model.User;
import com.example.gkl.utils.CustomerMeasurementProcessor;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
    public Button backButton;
    public ComboBox regionSelect;
    public Text shoulderText;
    public Text chestText;
    public Text backText;
    public Text sleeveText;
    public Text hipText;
    public Text outseamText;
    public Text inseamText;
    public Text waistText;
    private EntityManagerFactory entityManagerFactory;
    private Customer currentCustomer;
    private UserHib userHib;
    private CustomerMeasurementProcessor measurementProcessor;


    public void setData(EntityManagerFactory entityManagerFactory, Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
        this.entityManagerFactory = entityManagerFactory;
        this.measurementProcessor = new CustomerMeasurementProcessor(currentCustomer);
    }
    public boolean checkIfFieldsEmpty(TextField chestmeasure, TextField shouldermeasure, TextField backmeasure, TextField sleevemeasure,
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

    private double convertSizeToStandardUnit(double size, Regions selectedRegion) {
        return switch (selectedRegion) {
            case US, UK ->
                    size * 2.54;
            case EU ->
                    size;
            default -> throw new IllegalArgumentException("Unknown region: " + selectedRegion);
        };
    }

    public void editUserMeasurements() {

        double waistMeasCm = convertSizeToStandardUnit(Double.parseDouble(waistmeasure.getText()), currentCustomer.getSelectedRegion());
        double hipMeasCm = convertSizeToStandardUnit(Double.parseDouble(hipmeasure.getText()), currentCustomer.getSelectedRegion());
        double inseamMeasCm = convertSizeToStandardUnit(Double.parseDouble(inseammeasure.getText()), currentCustomer.getSelectedRegion());
        double legLengthMeasCm = convertSizeToStandardUnit(Double.parseDouble(outseammeasure.getText()), currentCustomer.getSelectedRegion());
        double shoulderMeasCm = convertSizeToStandardUnit(Double.parseDouble(shouldermeasure.getText()), currentCustomer.getSelectedRegion());
        double chestMeasCm = convertSizeToStandardUnit(Double.parseDouble(chestmeasure.getText()), currentCustomer.getSelectedRegion());
        double backMeasCm = convertSizeToStandardUnit(Double.parseDouble(backmeasure.getText()), currentCustomer.getSelectedRegion());
        double sleeveMeasCm = convertSizeToStandardUnit(Double.parseDouble(sleevemeasure.getText()), currentCustomer.getSelectedRegion());

        currentCustomer.setCustomerWaistMeas(waistMeasCm);
        currentCustomer.setCustomerHipMeas(hipMeasCm);
        currentCustomer.setCustomerInseamMeas(inseamMeasCm);
        currentCustomer.setCustomerLegLengthMeas(legLengthMeasCm);
        currentCustomer.setCustomerShoulderMeas(shoulderMeasCm);
        currentCustomer.setCustomerChestMeas(chestMeasCm);
        currentCustomer.setCustomerBackMeas(backMeasCm);
        currentCustomer.setCustomerSleeveMeas(sleeveMeasCm);

        // Generate upper body universal size
        String upperBodySize = measurementProcessor.generateUpperBodyUniversalSize();
        System.out.println("STAI ATS: " + upperBodySize);
        currentCustomer.setUpperBodyUniversalSize(upperBodySize);

        // Generate lower body universal size
        String lowerBodySize = measurementProcessor.generateLowerBodyUniversalSize();
        currentCustomer.setLowerBodyUniversalSize(lowerBodySize);
    }

    public void initialize(URL location, ResourceBundle resources) {

        regionSelect.getItems().addAll(Regions.values());
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
       
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
                    userHib.updateCustomer(currentCustomer);
                    saveButtonFunc();
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "User information", "Measurements", "The user measurements has been filled successfully.");
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUserSelectedRegion(ComboBox regionSelect) {
        Regions selectedRegion = (Regions) regionSelect.getSelectionModel().getSelectedItem();

        if (selectedRegion != null) {
            currentCustomer.setSelectedRegion(selectedRegion);
        }
    }



    public void enableRegionSelect() {
        if (regionSelect.getSelectionModel().getSelectedItem() == Regions.US || regionSelect.getSelectionModel().getSelectedItem() == Regions.UK)
        {
            shoulderText.setText("Shoulder (inch)");
            chestText.setText("Chest (inch)");
            sleeveText.setText("Sleeve (inch)");
            backText.setText("Back (inch)");
            hipText.setText("Hip (inch)");
            outseamText.setText("Outseam (inch)");
            inseamText.setText("Inseam (inch)");
            waistText.setText("Waist (inch)");
        }
        else
        {
            shoulderText.setText("Shoulder (cm)");
            chestText.setText("Chest (cm)");
            sleeveText.setText("Sleeve (cm)");
            backText.setText("Back (cm)");
            hipText.setText("Hip (cm)");
            outseamText.setText("Outseam (cm)");
            inseamText.setText("Inseam (cm)");
            waistText.setText("Waist (cm)");
        }
        setUserSelectedRegion(regionSelect);
    }
}
