package com.example.gkl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.time.LocalDate;

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
    LocalDate releaseDate;
    ProductGenre genre;
    String description;
    int length;
    String label;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Track> trackList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    Purchase purchase;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Comment> commentList = new ArrayList<>();

    public void addTrack(Track track) {
        trackList.add(track);
        track.setProduct(this);
    }

    public void removeTrack(Track track) {
        trackList.remove(track);
        track.setProduct(null);
    }

    public void removeTracks() {
        for (Track t : trackList) {
            t.setProduct(null);
        }
        trackList.clear();
    }

    public Product(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Product(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse) {
        this.price = price;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.description = description;
        this.length = length;
        this.label = label;
        this.warehouse = warehouse;
    }

    public Product(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, List<Track> trackList) {
        this.price = price;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.description = description;
        this.length = length;
        this.label = label;
        this.warehouse = warehouse;
        this.trackList = trackList;
    }


}
