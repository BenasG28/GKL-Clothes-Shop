package com.example.gkl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true)
    String login;
    String password;
    String contactMail;
    LocalDate birthDate;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    private Regions selectedRegion;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Purchase> purchaseList;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    Cart cart;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Comment> commentList;

    public User(String login, String password, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address) {
        this.login = login;
        this.password = password;
        this.contactMail = contactMail;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String login, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address) {
        this.login = login;
        this.contactMail = contactMail;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }



    public void setPasswordHashed(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public String toString() {
        return "User: " +
                "ID:" + id +
                ", Login: '" + login
                ;
    }

    //    public User(int id, String login, String password, String contactMail, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String address) {
//        this.id = id;
//        this.login = login;
//        this.password = password;
//        this.contactMail = contactMail;
//        this.birthDate = birthDate;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }



    //Dar bus
}
