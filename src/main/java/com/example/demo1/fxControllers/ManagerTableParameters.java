package com.example.demo1.fxControllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Tab;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ManagerTableParameters extends TableParameters {
    SimpleBooleanProperty isAdmin = new SimpleBooleanProperty();
    SimpleStringProperty employeeId = new SimpleStringProperty();
    SimpleStringProperty medCertificate = new SimpleStringProperty();
    SimpleStringProperty employmentDate = new SimpleStringProperty();

    public ManagerTableParameters() {}

    public ManagerTableParameters(SimpleIntegerProperty id, SimpleStringProperty login, SimpleStringProperty password, SimpleStringProperty contactMail, SimpleStringProperty birthDate, SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty phoneNumber, SimpleStringProperty address, SimpleBooleanProperty isAdmin, SimpleStringProperty employeeId, SimpleStringProperty medCertificate, SimpleStringProperty employmentDate) {
        super(id, login, password, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.isAdmin = isAdmin;
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public ManagerTableParameters(SimpleBooleanProperty isAdmin, SimpleStringProperty employeeId, SimpleStringProperty medCertificate, SimpleStringProperty employmentDate) {
        this.isAdmin = isAdmin;
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public boolean isIsAdmin() {
        return isAdmin.get();
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public SimpleStringProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId.set(employeeId);
    }

    public String getMedCertificate() {
        return medCertificate.get();
    }

    public SimpleStringProperty medCertificateProperty() {
        return medCertificate;
    }

    public void setMedCertificate(String medCertificate) {
        this.medCertificate.set(medCertificate);
    }

    public String getEmploymentDate() {
        return employmentDate.get();
    }

    public SimpleStringProperty employmentDateProperty() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate.set(employmentDate);
    }
}
