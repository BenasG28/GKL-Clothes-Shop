package com.example.gkl.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends User {
    private Double customerWaistMeas;
    private Double customerHipMeas;
    private Double customerInseamMeas;
    private Double customerLegLengthMeas;
    private Double customerShoulderMeas;
    private Double customerChestMeas;
    private Double customerBackMeas;
    private Double customerSleeveMeas;
    private boolean discountCard;

    @Builder
    public Customer(String login, String password, String contactMail, LocalDate birthDate,
                    String firstName, String lastName, String phoneNumber, String address,
                    boolean discountCard, Double customerWaistMeas, Double customerHipMeas, Double customerInseamMeas, Double customerLegLengthMeas,
                    Double customerShoulderMeas, Double customerChestMeas,
                    Double customerBackMeas, Double customerSleeveMeas) {
        super(login, password, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.discountCard = discountCard;
        this.customerWaistMeas = customerWaistMeas;
        this.customerHipMeas = customerHipMeas;
        this.customerInseamMeas = customerInseamMeas;
        this.customerLegLengthMeas = customerLegLengthMeas;
        this.customerShoulderMeas = customerShoulderMeas;
        this.customerChestMeas = customerChestMeas;
        this.customerBackMeas = customerBackMeas;
        this.customerSleeveMeas = customerSleeveMeas;
    }

   /* public Customer(String login, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address, boolean discountCard) {
        super(login, contactMail, birthDate, firstName, lastName, phoneNumber, address);
        this.discountCard = discountCard;
    }
*/

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
