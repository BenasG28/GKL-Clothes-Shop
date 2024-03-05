package com.example.demo1.fxControllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StatsTableParameters {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleDoubleProperty salesAmount = new SimpleDoubleProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getSalesAmount() {
        return salesAmount.get();
    }

    public SimpleDoubleProperty salesAmountProperty() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount.set(salesAmount);
    }
}
