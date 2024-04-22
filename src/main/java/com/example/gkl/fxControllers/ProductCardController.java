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

    private MainShopController mainShopController; // Add this line
    private Product product;

    public void initialize() {
        // Initialize controller
    }

    public void setProductData(Product product, MainShopController mainShopController) {
        this.product = product;
        this.mainShopController = mainShopController; // Add this line
        productNameLabel.setText(product.getTitle());
        productPriceLabel.setText("$" + product.getPrice());
        productImageView.setImage(new Image(product.getImageUrl()));
    }

    @FXML
    public void addToCart() {
        mainShopController.addToCart(product);
    }
}
