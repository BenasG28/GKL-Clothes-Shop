package com.example.gkl.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
