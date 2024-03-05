package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cassette extends Product{
    private String type;

    public Cassette( double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, String type) {
        super( price, title, releaseDate, genre, description, length, label, warehouse);
        this.type = type;
    }

    public Cassette(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, List<Track> trackList, String type) {
        super(price, title, releaseDate, genre, description, length, label, warehouse, trackList);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cassette{" +
                "type='" + type + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", label='" + label + '\'' +
                ", warehouse=" + warehouse +
                ", trackList=" + trackList +
                '}';
    }
}
