package com.example.demo1.fxControllers;

import com.example.demo1.hibernateControllers.GenericHib;
import com.example.demo1.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class CommentFormController {

    @FXML
    public TextField commentTitleField;
    @FXML
    public TextArea commentBodyField;
    @FXML
    public Slider ratingField;
    public DatePicker commentDateCreationPicker;

    public void initialize() {
        commentDateCreationPicker.setValue(LocalDate.now());
        commentDateCreationPicker.setDisable(true);
    }

    private int productId = 0;
    private int commentId = 0;
    private GenericHib genericHib;
    private User user;
    private CommentType commentType;

    public void setData(GenericHib genericHib, int productId, int commentId, User user, CommentType commentType) {
        this.genericHib = genericHib;
        this.productId = productId;
        this.commentId = commentId;
        this.user = user;
        this.commentType = commentType;
    }

    public void saveData() {

        if (commentType == CommentType.COMMENT) {
            Product product = genericHib.getEntityById(Product.class, productId);
            Review review = new Review(commentTitleField.getText(), commentBodyField.getText(), commentDateCreationPicker.getValue(), user, ratingField.getValue(), product);
            product.getCommentList().add(review);
            genericHib.update(product);
        } else if (commentType == CommentType.REPLY) {
            Comment parentComment = genericHib.getEntityById(Comment.class, commentId);
            Comment reply = new Comment(commentTitleField.getText(), commentBodyField.getText(), commentDateCreationPicker.getValue(), parentComment, user);
            parentComment.getReplies().add(reply);
            genericHib.update(parentComment);
        }

    }
}
