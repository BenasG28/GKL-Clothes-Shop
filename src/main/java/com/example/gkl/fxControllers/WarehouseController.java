package com.example.gkl.fxControllers;

import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.Warehouse;
import jakarta.persistence.EntityManagerFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class WarehouseController {

    public ListView warehouseList;
    public Button addWarehouseButton;
    public Button updateWarehouseButton;
    public Button removeWarehouseButton;
    public TextField addressWarehouseField;
    public TextField titleWarehouseField;
    private GenericHib genericHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        genericHib = new GenericHib(entityManagerFactory);
    }
    public void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(genericHib.getAllRecords(Warehouse.class));
    }

    public void addNewWarehouse() {
        genericHib.create(new Warehouse(titleWarehouseField.getText(), addressWarehouseField.getText()));
        clearWarehouseFields();
        loadWarehouseList();
    }

    public void clearWarehouseFields() {
        titleWarehouseField.setText(null);
        addressWarehouseField.setText(null);
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = (Warehouse) warehouseList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        warehouse.setTitle(titleWarehouseField.getText());
        warehouse.setAddress(addressWarehouseField.getText());
        genericHib.update(warehouse);
        loadWarehouseList();
    }

    public void removeWarehouse() {
        Warehouse selectedWarehouse = (Warehouse) warehouseList.getSelectionModel().getSelectedItem();
        genericHib.delete(Warehouse.class, selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = (Warehouse) warehouseList.getSelectionModel().getSelectedItem();
        titleWarehouseField.setText(selectedWarehouse.getTitle());
        addressWarehouseField.setText(selectedWarehouse.getAddress());
    }


}
