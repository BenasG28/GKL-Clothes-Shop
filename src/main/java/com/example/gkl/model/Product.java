package com.example.gkl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double price;
    String title;
    String description;
    String imageUrl;
    private String color;
    private ProductType productType;
    private String productSex;
//    private Map<String, Integer> inventory;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    Purchase purchase;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Comment> commentList = new ArrayList<>();

    @Override
    public String toString() {
        return "Item{" +
                "ProductType=" + productType +
                ", title=" + title +
                ", price=" + price +
                '}';
    }

    public Product(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}
