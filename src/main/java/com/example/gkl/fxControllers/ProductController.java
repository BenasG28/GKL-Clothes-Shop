package com.example.gkl.fxControllers;

import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.Product;
import com.example.gkl.model.Warehouse;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.scene.control.*;

public class ProductController {
    public ListView<Product> productListManager;
    public TextField productTitleField;
    public TextField productDescriptionField;
    public TextField productLabelField;
    public ComboBox<Warehouse> warehouseComboBox;
    public ComboBox productType;
    public DatePicker productReleaseDateField;
    public Button addNewProductButton;
    public Button updateProductButton;
    public Button deleteProductButton;
    public TextField productPriceField;
    public TextField productLengthField;
    public ListView productTrackListManager;
    public TextField productUniqueAttributeField;
    public ComboBox productGenreComboBox;
    public Button clearButton;
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


    /*public void addTrack() {
        Track track = new Track(trackTitleField.getText(), Integer.parseInt(trackLengthField.getText()));
        tempTrackList.add(track);
        loadTrackList();
    }

    public void removeTrack() {
        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
        tempTrackList.remove(track);
        loadTrackList();
    }

    public void updateTrack() {
        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
        track.setName(trackTitleField.getText());
        track.setLengthBySeconds(Integer.parseInt(trackLengthField.getText()));
        loadTrackList();
    }

    public void loadTrackData() {
        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
        trackTitleField.setText(track.getName());
        trackLengthField.setText(String.valueOf(track.getLengthBySeconds()));
    }

    private void loadTrackList() {
        productTrackListManager.getItems().clear();
        for (Track t : tempTrackList) {
            productTrackListManager.getItems().add(t);
        }
    }
*/
   /* public void addNewProduct() {
        //  try {
        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
        ProductGenre selectedProductGenre = productGenreComboBox.getSelectionModel().getSelectedItem();
        Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        if (productType.getSelectionModel().getSelectedItem() == ProductType.VINYL) {
            Vinyl vinyl = new Vinyl(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
            for (Track t : tempTrackList) {
                vinyl.addTrack(t);
            }
            tempTrackList.clear();
            genericHib.create(vinyl);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.COMPACT_DISC) {
            CompactDisc compactDisc = new CompactDisc(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
            for (Track t : tempTrackList) {
                compactDisc.addTrack(t);
            }
            tempTrackList.clear();
            genericHib.create(compactDisc);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.CASSETTE) {
            Cassette cassette = new Cassette(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
            for (Track t : tempTrackList) {
                cassette.addTrack(t);
            }
            tempTrackList.clear();
            genericHib.create(cassette);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product error", "Error during create", "Wrong product type");
        }
        clearFields();
        loadProductListManager();
        //}
//        } catch (Exception e) {
//            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product creation error", "Error during create", "Product not created, check entered data");
//        }
    }*/

    /*public void loadProductData() {
        Product product = productListManager.getSelectionModel().getSelectedItem();
        if (product != null) {
            if (product.getClass() == Vinyl.class) {
                productType.setValue(ProductType.VINYL);
                productUniqueAttributeField.setText(((Vinyl) product).getRpm());
            } else if (product.getClass() == CompactDisc.class) {
                productType.setValue(ProductType.COMPACT_DISC);
                productUniqueAttributeField.setText(((CompactDisc) product).getAudioFormat());
            } else if (product.getClass() == Cassette.class) {
                productType.setValue(ProductType.CASSETTE);
                productUniqueAttributeField.setText(((Cassette) product).getType());
            }
            productTitleField.setText(product.getTitle());
            productLabelField.setText(product.getLabel());
            productGenreComboBox.setValue(product.getGenre());
            productLengthField.setText(String.valueOf(product.getLength()));
            productDescriptionField.setText(product.getDescription());
            productPriceField.setText(String.valueOf(product.getPrice()));
            productReleaseDateField.setValue(product.getReleaseDate());
            warehouseComboBox.setValue(product.getWarehouse());
            productTrackListManager.getItems().clear();
            productTrackListManager.getItems().addAll(product.getTrackList());
            tempTrackList = product.getTrackList();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Invalid product", "Product is not chosen", "Choose a product from the list");
        }

    }*/

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

   /* public void clearFields() {
        productType.getSelectionModel().clearSelection();
        productTitleField.setText(null);
        productLabelField.setText(null);
        productUniqueAttributeField.setText(null);
        productGenreComboBox.getSelectionModel().clearSelection();
        productLengthField.setText(null);
        productPriceField.setText(null);
        productReleaseDateField.setValue(null);
        productTrackListManager.getItems().clear();
        tempTrackList.clear();
        productDescriptionField.setText(null);
        warehouseComboBox.getSelectionModel().clearSelection();
        soldoutCheckBox.setDisable(true);
        soldoutCheckBox.setSelected(false);
    }
*/
}
