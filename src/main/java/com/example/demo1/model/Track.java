package com.example.demo1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int lengthBySeconds;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    public Track(String name, int lengthBySeconds) {
        this.name = name;
        this.lengthBySeconds = lengthBySeconds;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", lengthBySeconds=" + lengthBySeconds +
                '}';
    }
}
