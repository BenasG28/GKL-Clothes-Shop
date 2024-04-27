package com.example.gkl;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.CommentHib;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.*;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CommentController {
    public ListView<Comment> commentList;
    public TextField commentTitleField;
    public TextArea commentBodyField;
    public Button addCommentButton;
    public Button updateCommentButton;
    public Button removeCommentButton;
    public ListView<Product> productListForComments;
    public DatePicker commentDateField;
    public TextField commentProductTitleField;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private CommentHib commentHib;
    private User currentUser;
    public void setData(EntityManagerFactory entityManagerFactory, User currentUser){
        this.entityManagerFactory = entityManagerFactory;
        genericHib = new GenericHib(entityManagerFactory);
        commentHib = new CommentHib(entityManagerFactory);
        this.currentUser = currentUser;
    }
    public void selectProduct() {
        Product selectedProduct = productListForComments.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            commentProductTitleField.setText(selectedProduct.getTitle());
            List<Comment> commentsForProduct = commentHib.getAllCommentsForProduct(selectedProduct);
            commentList.getItems().clear();
            commentList.getItems().addAll(commentsForProduct);
        } else {
            commentProductTitleField.clear();
            commentList.getItems().clear(); // Clear commentList when no product is selected
        }
    }


    public void addComment() {
        Comment comment = new Comment(commentTitleField.getText(), commentBodyField.getText(), commentDateField.getValue(), currentUser);
        Product selectedProduct = productListForComments.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            comment.setProduct(selectedProduct);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Missing Product", "No product selected", "Please select a product");
        }
        genericHib.create(comment);
        clearCommentFields();
        loadCommentList();
    }

    public void updateComment() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        Comment comment = genericHib.getEntityById(Comment.class, selectedComment.getId());
        comment.setCommentTitle(commentTitleField.getText());
        comment.setCommentBody(commentBodyField.getText());
        comment.setDateCreated(commentDateField.getValue());
        genericHib.update(comment);
        loadCommentList();
    }

    public void removeComment() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        commentHib.deleteComment(selectedComment.getId());
        loadCommentList();
    }

    private void clearCommentFields() {
        commentTitleField.setText(null);
        commentBodyField.setText(null);
        commentDateField.setValue(null);
        commentProductTitleField.setText(null);
    }

    public void loadCommentData() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        commentTitleField.setText(selectedComment.getCommentTitle());
        commentBodyField.setText(selectedComment.getCommentBody());
        commentDateField.setDisable(false);
        commentDateField.setValue(selectedComment.getDateCreated());
        commentProductTitleField.setText(selectedComment.getProduct().getTitle());
    }

    public void loadCommentList() {
        commentList.getItems().clear();
        if (currentUser.getClass() == Customer.class) {
            commentList.getItems().addAll(commentHib.getAllCommentsByUser(currentUser));
        } else {
            commentList.getItems().addAll(genericHib.getAllRecords(Comment.class));

        }
        productListForComments.getItems().clear();
        productListForComments.getItems().addAll(genericHib.getAllRecords(Product.class));
        commentDateField.setDisable(true);
        commentDateField.setValue(LocalDate.now());
    }
}

