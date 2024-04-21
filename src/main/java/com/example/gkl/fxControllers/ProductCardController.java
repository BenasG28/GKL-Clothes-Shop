package com.example.gkl.fxControllers;

import com.example.gkl.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class ProductCardController {
    public AnchorPane productCardRoot;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private ImageView productImageView;

    public void initialize() {
        // Initialize controller
    }

    public void setProductData(Product product) {
        // Update UI elements with product information
        productNameLabel.setText(product.getTitle());
        productPriceLabel.setText("$" + product.getPrice());
        productImageView.setImage(new Image(product.getImageUrl()));
    }
}
