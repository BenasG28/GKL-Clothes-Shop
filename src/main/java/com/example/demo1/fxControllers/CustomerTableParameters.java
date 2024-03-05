package com.example.demo1.fxControllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class CustomerTableParameters extends TableParameters{
    private SimpleBooleanProperty discountCard = new SimpleBooleanProperty();

    public CustomerTableParameters() {}

    public CustomerTableParameters(SimpleIntegerProperty id, SimpleStringProperty login, SimpleStringProperty password, SimpleStringProperty contactMail, SimpleStringProperty birthDate, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty phoneNumber, SimpleStringProperty address, SimpleBooleanProperty discountCard) {
        super(id, login, password, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.discountCard = discountCard;
    }

    public CustomerTableParameters(SimpleBooleanProperty discountCard) {
        this.discountCard = discountCard;
    }

    public boolean isDiscountCard() {
        return discountCard.get();
    }

    public SimpleBooleanProperty discountCardProperty() {
        return discountCard;
    }

    public void setDiscountCard(boolean discountCard) {
        this.discountCard.set(discountCard);
    }
}
