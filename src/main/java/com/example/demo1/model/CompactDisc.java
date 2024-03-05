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
public class CompactDisc extends Product{
    private String audioFormat;



    public void setAudioFormat(String audioFormat) {
        this.audioFormat = audioFormat;
    }

    public CompactDisc(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, String audioFormat) {
        super(price, title, releaseDate, genre, description, length, label, warehouse);
        this.audioFormat = audioFormat;
    }

    public CompactDisc(double price, String title, LocalDate releaseDate, ProductGenre genre, String description, int length, String label, Warehouse warehouse, List<Track> trackList, String audioFormat) {
        super(price, title, releaseDate, genre, description, length, label, warehouse, trackList);
        this.audioFormat = audioFormat;
    }

    @Override
    public String toString() {
        return "CompactDisc{" +
                "audioFormat='" + audioFormat + '\'' +
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
