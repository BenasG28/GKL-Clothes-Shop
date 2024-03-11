package com.example.gkl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private double amount;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart = new ArrayList<>();
    @OneToOne
    @JoinColumn(name="user_id", unique = true)
    private User user;


    public Cart(User user) {
        this.user = user;
    }

    public Cart(LocalDate dateCreated, User user) {
        this.dateCreated = dateCreated;
        this.user = user;
    }


    @Override
    public String toString() {
        return "Order: " +
                "ID: " + id +
                ", Creation date: " + dateCreated +
                ", Total amount: " + amount +
                ", Created By: " + user +
                '}';
    }
    public double getTotalPrice(){
        double sum = 0;
        List<Product> orderProducts = getItemsInCart();
        for(Product product: orderProducts){
            sum+= product.getPrice();
        }
        return sum;
    }

    public void setAmount() {
        this.amount = getTotalPrice();
    }

    public Cart(LocalDate dateCreated, User user, double amount, List<Product> itemsInCart) {
        this.dateCreated = dateCreated;
        this.user = user;
        this.amount = amount;
        this.itemsInCart = itemsInCart;

    }

    public Cart(LocalDate dateCreated, List<Product> itemsInCart) {
        this.dateCreated = dateCreated;
        this.itemsInCart = itemsInCart;
    }

    public Cart(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }


}

