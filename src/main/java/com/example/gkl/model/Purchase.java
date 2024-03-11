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
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private double purchaseAmount;
    private PurchaseStatus status;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInPurchase = new ArrayList<>();

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", purchaseAmount=" + purchaseAmount +
                ", user=" + user +
                '}';
    }

    public Purchase(LocalDate dateCreated, double purchaseAmount, User user, List<Product> itemsInPurchase) {
        this.dateCreated = dateCreated;
        this.purchaseAmount = purchaseAmount;
        this.user = user;
        this.itemsInPurchase = itemsInPurchase;
    }

}
