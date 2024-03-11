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

    //    public static Customer fromCustomerTable(CustomerTableParameters parameters){
//        Customer customer = new Customer();
//        customer.setFirstName(parameters.getFirstName());
//        customer.setLastName(parameters.getLastName());
//        customer.setLogin(parameters.getLogin());
//        customer.setPassword(parameters.getPassword());
//        customer.setContactMail(parameters.getContactMail());
//        customer.setBirthDate(parameters.getBirthDate());
//        customer.setAddress(parameters.getAddress());
//        customer.setPhoneNumber(parameters.getPhoneNumber());
//        customer.setDiscountCard(parameters.isDiscountCard());
//        return customer;
//    }

//    public void setPasswordHashed(String password) {
//        super.setPassword(password);
//    }
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
