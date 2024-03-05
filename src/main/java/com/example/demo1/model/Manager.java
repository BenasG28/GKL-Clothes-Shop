package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {
    boolean isAdmin;
    @Column(unique = true)
    String employeeId;
    String medCertificate;
    LocalDate employmentDate;
//    @ManyToMany
//    private List<Warehouse> worksAtWarehouse;

    public Manager(String login, String password, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address, boolean isAdmin, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(login, password, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.isAdmin = isAdmin;
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public Manager(String login, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address, boolean isAdmin, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(login, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.isAdmin = isAdmin;
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "isAdmin=" + isAdmin +
                ", employeeId='" + employeeId + '\'' +
                ", medCertificate='" + medCertificate + '\'' +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
