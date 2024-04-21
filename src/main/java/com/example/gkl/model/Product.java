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
    private String sizeEn;
    private String sizeUk;
    private String sizeUs;
    private String sizeSML;
    private String productCode;
    private String color;
    private String productType;
    private String productSex;
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


    public Product(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
