package com.example.gkl.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends User {
    private boolean discountCard;

    public Customer(String login, String password, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address, boolean discountCard) {
        super(login, password, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.discountCard = discountCard;
    }

    public Customer(String login, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address, boolean discountCard) {
        super(login, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.discountCard = discountCard;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", contactMail='" + contactMail + '\'' +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
