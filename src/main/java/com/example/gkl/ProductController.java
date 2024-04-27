package com.example.gkl;

import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.Product;
import com.example.gkl.model.ProductType;
import com.example.gkl.model.Warehouse;
import com.example.gkl.utils.JavaFxCustomUtils;
import com.example.gkl.model.InventoryItem;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    public ListView<Product> productListManager;
    public TextField productTitleField;
    public TextField productDescriptionField;
    public TextField productLabelField;
    public ComboBox<Warehouse> warehouseComboBox;
    public ComboBox<ProductType> productType;
    public DatePicker productReleaseDateField;
    public Button addNewProductButton;
    public Button updateProductButton;
    public Button deleteProductButton;
    public TextField productPriceField;
    public Button clearButton;
    public TextField productImageUrlField;
    public TextField productColorField;
    public Spinner<Integer> quantitySpinner;
    public Spinner<Integer> quantityMSpinner;
    public Spinner<Integer> quantityLSpinner;
    public Spinner<Integer> quantityXLSpinner;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        genericHib = new GenericHib(entityManagerFactory);

    }


    public void setupWarehouseComboBox(){
        warehouseComboBox.getItems().clear();
        warehouseComboBox.getItems().addAll(genericHib.getAllRecords(Warehouse.class));
    }


   /* public void changeProductField() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.VINYL) {
            productUniqueAttributeField.setPromptText("RPM");
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.COMPACT_DISC) {
            productUniqueAttributeField.setPromptText("Audio Format");
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.CASSETTE) {
            productUniqueAttributeField.setPromptText("Type");
        }
    }*/

    public void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(genericHib.getAllRecords(Product.class));
    }



   public void addNewProduct() {
       Warehouse selectedWarehouse = warehouseComboBox.getValue(); // Use getValue() instead of getSelectionModel().getSelectedItem()

       // You can add more conditions based on your product types
       if (selectedWarehouse != null) {
           Product newProduct = new Product();
           newProduct.setTitle(productTitleField.getText());
           newProduct.setDescription(productDescriptionField.getText());
           newProduct.setPrice(Double.parseDouble(productPriceField.getText()));
           newProduct.setColor(productColorField.getText());
           newProduct.setImageUrl(productImageUrlField.getText());
           newProduct.setWarehouse(selectedWarehouse);
           newProduct.setProductType(productType.getValue());
           // Set quantities for each size based on spinner values
           List<InventoryItem> inventoryItems = new ArrayList<>();
           inventoryItems.add(createInventoryItem("S", (Integer) quantitySpinner.getValue(), newProduct));
           inventoryItems.add(createInventoryItem("M", (Integer) quantityMSpinner.getValue(), newProduct));
           inventoryItems.add(createInventoryItem("L", (Integer) quantityLSpinner.getValue(), newProduct));
           inventoryItems.add(createInventoryItem("XL", (Integer) quantityXLSpinner.getValue(), newProduct));

           newProduct.setInventory(inventoryItems);

           // Add the new product to the database or perform any other necessary actions
           genericHib.create(newProduct);

           // Clear the fields and reload the product list
           clearFields();
           loadProductListManager();
    }
       else {
           JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product error", "Error during create", "Please select a warehouse");
       }
   }
    private InventoryItem createInventoryItem(String size, int quantity, Product product) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setSize(size);
        inventoryItem.setQuantity(quantity);
        inventoryItem.setProduct(product); // Set the product reference
        return inventoryItem;
    }

    public void loadProductData() {
        Product product = productListManager.getSelectionModel().getSelectedItem();
        if (product != null) {
            productTitleField.setText(product.getTitle());
            productDescriptionField.setText(product.getDescription());
            warehouseComboBox.setValue(product.getWarehouse());
            productPriceField.setText(String.valueOf(product.getPrice()));
            productColorField.setText(product.getColor());
            productImageUrlField.setText(product.getImageUrl());
            productType.setValue(product.getProductType());
            
            for (InventoryItem item : product.getInventory()) {
                String size = item.getSize();
                int quantity = item.getQuantity();
                switch (size) {
                    case "S":
                        quantitySpinner.getValueFactory().setValue(quantity);
                        break;
                    case "M":
                        quantityMSpinner.getValueFactory().setValue(quantity);
                        break;
                    case "L":
                        quantityLSpinner.getValueFactory().setValue(quantity);
                        break;
                    case "XL":
                        quantityXLSpinner.getValueFactory().setValue(quantity);
                        break;
                    default:
                        break;
                }
            }
            
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Invalid product", "Product is not chosen", "Choose a product from the list");
        }

    }

    /*public void updateProduct() {
        try {
            Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                if (selectedProduct.getClass() == Vinyl.class)
                    ((Vinyl) selectedProduct).setRpm(productUniqueAttributeField.getText());
                else if (selectedProduct.getClass() == CompactDisc.class)
                    ((CompactDisc) selectedProduct).setAudioFormat(productUniqueAttributeField.getText());
                else if (selectedProduct.getClass() == Cassette.class)
                    ((Cassette) selectedProduct).setType(productUniqueAttributeField.getText());
                selectedProduct.setTitle(productTitleField.getText());
                selectedProduct.setLabel(productLabelField.getText());
                selectedProduct.setGenre(productGenreComboBox.getSelectionModel().getSelectedItem());
                selectedProduct.setLength(Integer.parseInt(productLengthField.getText()));
                selectedProduct.setDescription(productDescriptionField.getText());
                selectedProduct.setPrice(Double.parseDouble(productPriceField.getText()));
                selectedProduct.setReleaseDate(productReleaseDateField.getValue());
                for (Track t : tempTrackList) {
                    if (!selectedProduct.getTrackList().contains(t)) {
                        selectedProduct.addTrack(t);
                    }
                }
                selectedProduct.setWarehouse(warehouseComboBox.getSelectionModel().getSelectedItem());
                genericHib.update(selectedProduct);
                loadProductListManager();
                loadProductData();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Update Successful", "Product was updated successfully", "Good Job!");
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update error", "Product is not selected", "Please select a product.");
            }
            loadProductListManager();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update error", "Product was not updated", "Please get help.");
        }
    }*/

    public void deleteProduct() {
        Product product = productListManager.getSelectionModel().getSelectedItem();
        try {
            if (product != null) {
                genericHib.deleteProduct(product.getId());
                loadProductListManager();
                //clearFields();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Deletion successful", "Product was deleted successfully", "Delete more!");
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Deletion error", "Product is not selected", "Please select a product.");

            }
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Deletion error", "Product was not deleted", "Please get help.");
        }
    }

    private void initializeSpinner(Spinner<Integer> spinner) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinner.setValueFactory(valueFactory);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSpinner(quantitySpinner);
        initializeSpinner(quantityMSpinner);
        initializeSpinner(quantityLSpinner);
        initializeSpinner(quantityXLSpinner);
        productType.getItems().addAll(ProductType.values());
    }

    public void clearFields() {
        productTitleField.setText(null);
        productDescriptionField.setText(null);
        productPriceField.setText(null);
        productColorField.setText(null);
        productImageUrlField.setText(null);

        quantitySpinner.getValueFactory().setValue(0);
        quantityMSpinner.getValueFactory().setValue(0);
        quantityLSpinner.getValueFactory().setValue(0);
        quantityXLSpinner.getValueFactory().setValue(0);

        warehouseComboBox.getSelectionModel().clearSelection();
        productType.getSelectionModel().clearSelection();

    }


}
