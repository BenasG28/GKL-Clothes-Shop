package com.example.gkl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    private LocalDate dateCreated;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
   @ManyToOne
   @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;

    @Override
    public String toString() {
        return "Comment{" +
                "commentTitle='" + commentTitle + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public Comment(String commentTitle, String commentBody, LocalDate dateCreated, Comment parentComment, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = dateCreated;
        this.parentComment = parentComment;
        this.user = user;
    }

    public Comment(String commentTitle, String commentBody, LocalDate dateCreated, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = dateCreated;
        this.user = user;
    }
}
