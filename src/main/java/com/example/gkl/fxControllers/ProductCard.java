package com.example.gkl.fxControllers;

import com.example.gkl.model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import lombok.Getter;

import java.io.IOException;

public class ProductCard {
    @Getter
    private Node node;
    private ProductCardController controller;

    public ProductCard() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/productCard.fxml"));
        try {
            node = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProductData(Product product, MainShopController mainShopController) {
        controller.setProductData(product, mainShopController);
    }
}
