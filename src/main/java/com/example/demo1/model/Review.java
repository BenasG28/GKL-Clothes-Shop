package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Comment {
    private double rating;

    public Review(String commentTitle, String commentBody, LocalDate dateCreated, User user, double rating, Product product) {
        super(commentTitle, commentBody, dateCreated, user);
        this.rating = rating;
        this.product = product;
    }

    @Override
    public String toString() {
        return "("+ String.format("%.2f",rating) +")" + getCommentBody();
    }
}