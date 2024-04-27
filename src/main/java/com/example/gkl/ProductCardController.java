package com.example.gkl;

import com.example.gkl.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    @FXML
    private ComboBox<String> sizeComboBox = new ComboBox<>();
    @FXML
    private Spinner<Integer> quantitySpinner = new Spinner<>();
    private MainShopController mainShopController;
    private Product product;

    public void initialize() {
        // Initialize controller
        sizeComboBox.getItems().addAll("S", "M", "L", "XL");

        // Initialize quantity Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);
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
        // Retrieve selected size and quantity
        String selectedSize = sizeComboBox.getValue();
        int selectedQuantity = quantitySpinner.getValue();

        // Pass the selected product, size, and quantity to the main shop controller for adding to the cart
        mainShopController.addToCart(product, selectedSize, selectedQuantity);
    }
}
