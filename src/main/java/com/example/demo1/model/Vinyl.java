package com.example.demo1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vinyl extends Product {
    private String rpm;
    public Vinyl(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, List<Track> trackList, String rpm) {
        super(price, title, releaseDate, genre, description, length, label, warehouse, trackList);
        this.rpm = rpm;
    }

    public Vinyl( double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, String rpm) {
        super( price, title, releaseDate, genre, description, length, label, warehouse);
        this.rpm = rpm;
    }

    @Override
    public String toString() {
        return "Vinyl{" +
                "rpm='" + rpm + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", label='" + label + '\'' +
                ", warehouse=" + warehouse +
                ", tracks = " + trackList +
                '}';
    }
}
