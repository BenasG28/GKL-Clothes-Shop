package com.example.gkl;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.PurchaseHib;
import com.example.gkl.model.*;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import java.io.IOException;
import java.time.LocalDate;

public class OrderController {
    public ListView<Purchase> orderListManager;
    public Button removeOrderButton;
    public ListView<Product> orderProductsListManager;
    public TextField orderClientField;
    public TextField orderAmountField;
    public DatePicker orderDatePicker;
    public Button leaveRatingButton;
    public DatePicker filterDateStart;
    public DatePicker filterDateEnd;
    public Button dateFilterButton;
    public ComboBox<PurchaseStatus> statusComboBox;
    public Button statusFilterButton;
    public TextField customerIdField;
    public Button customerFilterButton;
    public Button filterButton;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private User currentUser;
    private PurchaseHib purchaseHib;
    public void setData(EntityManagerFactory entityManagerFactory, User currentUser){
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);
        purchaseHib = new PurchaseHib(entityManagerFactory);
    }
    public void setupStatusComboBox(){
        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll(PurchaseStatus.values());
    }
    public void loadOrderData() {
        Purchase purchase = orderListManager.getSelectionModel().getSelectedItem();
        orderProductsListManager.getItems().clear();
        orderProductsListManager.getItems().addAll(purchase.getItemsInPurchase());
        User orderUser = purchase.getUser();
        orderClientField.setText(orderUser.getLogin());
        orderAmountField.setText(String.valueOf(purchase.getPurchaseAmount()));
        orderDatePicker.setValue(purchase.getDateCreated());
    }

    public void getAllOrders() {
        orderListManager.getItems().clear();
        if (currentUser.getClass() == Customer.class) {
            orderListManager.getItems().addAll(purchaseHib.getAllPurchaseByUser(currentUser));
        } else {
            orderListManager.getItems().addAll(genericHib.getAllRecords(Purchase.class));
        }
    }
    public void clearPurchaseFields(){
        orderProductsListManager.getItems().clear();
        orderClientField.clear();
        orderAmountField.clear();
        orderDatePicker.setValue(null);
    }

    public void deleteOrder() {
        Purchase selectedOrder = orderListManager.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                purchaseHib.deletePurchase(selectedOrder.getId());
                clearPurchaseFields();
                getAllOrders();
            } catch (Exception e) {
                e.printStackTrace();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Deletion error", "Could not delete", e.getMessage());
            }
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "No Order", "You have not chosen an order", "Choose an order");
        }
    }


    public void filterByDate() {
        orderListManager.getItems().clear();
        LocalDate startDate = filterDateStart.getValue();
        LocalDate endDate = filterDateEnd.getValue();
        orderListManager.getItems().addAll(purchaseHib.filterByDateAndUser(startDate,endDate,currentUser));
    }
    public void filterByStatus(){
        orderListManager.getItems().clear();
        PurchaseStatus status = statusComboBox.getValue();
        orderListManager.getItems().addAll(purchaseHib.filterByStatus(status));
    }

    public void filterByCustomer() {
        orderListManager.getItems().clear();
        int customerId = Integer.parseInt(customerIdField.getText());
        orderListManager.getItems().addAll(purchaseHib.filterByCustomer(customerId));
    }
}
