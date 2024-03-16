package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.*;
import com.example.gkl.model.*;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainShopController implements Initializable {
    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Product> currentOrderList;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab warehouseTab;
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public TextField addressWarehouseField;
    @FXML
    public TextField titleWarehouseField;
    @FXML
    public Tab ordersTab;
    @FXML
    public Tab productsTab;


    @FXML
    public TabPane tabPane;
    @FXML
    public Tab primaryTab;
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextField productDescriptionField;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public Label totalAmountLabel;
    @FXML
    public TextField productPriceField;
    @FXML
    public Button leaveCommentButton;
    @FXML
    public Button addToCartButton;
    public TextField productLabelField;
    public DatePicker productReleaseDateField;
    public TextField productLengthField;

    public Button openTrackEditorButton;
    public SplitPane trackEditor;
    public TextField trackTitleField;
    public TextField trackLengthField;
    public Button addTrackButton;
    public Button updateTrackButton;
    public Button deleteTrackButton;
    public Button closeTrackEditorButton;
    public TextField productUniqueAttributeField;
    public Button addNewProductButton;
    public Tab commentTab;
    public ListView<Comment> commentList;
    public TextField commentTitleField;
    public TextArea commentBodyField;
    public DatePicker commentDateField;
    public Button addWarehouseButton;
    public Button updateWarehouseButton;
    public Button removeWarehouseButton;
    public ListView<Purchase> orderListManager;
    public Button addOrderButton;
    public Button updateOrderButton;
    public Button removeOrderButton;
    public ListView<Product> orderProductsListManager;
    public Button updateProductButton;
    public Button deleteProductButton;
    public Button placeOrderButton;
    public Button clearButton;
    public Button addCommentButton;
    public Button updateCommentButton;
    public Button removeCommentButton;
    public Button deleteCartButton;
    public CheckBox soldoutCheckBox;
    public Button addUserButton;
    public TextField orderClientField;
    public TextField orderAmountField;
    public DatePicker orderDatePicker;
    public ListView<Product> productListForComments;
    public TextField commentProductTitleField;
    public TreeView<Comment> commentsTree;
    public Button rateProductButton;
    public Button leaveRatingButton;
    public DatePicker filterDateStart;
    public DatePicker filterDateEnd;
    public Button statusFilterButton;
    public ComboBox<PurchaseStatus> statusComboBox;
    public Button dateFilterButton;
    public TextField customerIdField;
    public Button customerFilterButton;
    public Tab orderStatsTab;
    public BarChart<String, Number> ordersChart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;
    public DatePicker statsDateStart;
    public DatePicker statsDateEnd;
    public ComboBox<PurchaseStatus> statsOrderCombo;
    public Button statsDateFilter;
    public Button statsStatusFilter;
    public TextField statsCustomerIdTextField;
    public Button statsCustomerFilterButton;
    public TableColumn<StatsTableParameters, Integer> statsIdColumn;
    public TableColumn<StatsTableParameters, Double> statsAmountColumn;
    public TableColumn<StatsTableParameters, String> statsDateColumn;
    public TableView<StatsTableParameters> statsTable;
    public MenuItem replyContext;
    public BorderPane imageBorderPane;
    public ImageView imageView;
    public Button deleteUserButton;
    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHib genericHib;
    private CartHib cartHib;
    private Cart userCart;
    private ProductHib productHib;
    private PurchaseHib purchaseHib;
    private CommentHib commentHib;
    private final ObservableList<StatsTableParameters> statsData = FXCollections.observableArrayList();



    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);
        cartHib = new CartHib(entityManagerFactory);
        productHib = new ProductHib(entityManagerFactory);
        purchaseHib = new PurchaseHib(entityManagerFactory);
        commentHib = new CommentHib(entityManagerFactory);
        createOrGetCart();
        limitAccess();
        loadCatalogue();
        loadCartItems();
    }

    private void loadCatalogue() {
        productList.getItems().clear();
        productList.getItems().addAll(productHib.getAllProductWithNoCart());
    }

    public void loadTabValues() {
        if (primaryTab.isSelected()) {
            loadCartItems();
            loadCatalogue();
        } else if (productsTab.isSelected()) {
            loadProductListManager();
            warehouseComboBox.getItems().clear();
            warehouseComboBox.getItems().addAll(genericHib.getAllRecords(Warehouse.class));
        } else if (warehouseTab.isSelected()) {

        } else if (commentTab.isSelected()) {

        } else if (ordersTab.isSelected()) {
            getAllOrders();
            statusComboBox.getItems().clear();
            statusComboBox.getItems().addAll(PurchaseStatus.values());
        } else if (usersTab.isSelected()) {

        }
        else if(orderStatsTab.isSelected()) {
            statsOrderCombo.getItems().clear();
            statsOrderCombo.getItems().addAll(PurchaseStatus.values());
            ordersChart.getData().clear();
            populateChart(genericHib.getAllRecords(Purchase.class));
            loadStatsTable(genericHib.getAllRecords(Purchase.class));
        }

    }
    public void filterStatsByDate(){
        statsTable.getItems().clear();
        LocalDate startDate = statsDateStart.getValue();
        LocalDate endDate = statsDateEnd.getValue();
        loadStatsTable(purchaseHib.filterByDate(startDate,endDate));
        populateChart(purchaseHib.filterByDate(startDate,endDate));
    }
    public void filterStatsByStatus(){
        statsTable.getItems().clear();
        PurchaseStatus status = statsOrderCombo.getValue();
        loadStatsTable(purchaseHib.filterByStatus(status));
        populateChart(purchaseHib.filterByStatus(status));
    }

    public void filterStatsByCustomer() {
        statsTable.getItems().clear();
        int customerId = Integer.parseInt(statsCustomerIdTextField.getText());
        loadStatsTable(purchaseHib.filterByCustomer(customerId));
        populateChart(purchaseHib.filterByCustomer(customerId));
    }
    private void loadStatsTable(List<Purchase> purchaseList){
        statsTable.getItems().clear();
        statsIdColumn.setCellValueFactory(new PropertyValueFactory<StatsTableParameters, Integer>("id"));
        statsAmountColumn.setCellValueFactory(new PropertyValueFactory<StatsTableParameters, Double>("salesAmount"));
        statsDateColumn.setCellValueFactory(new PropertyValueFactory<StatsTableParameters, String>("date"));
        for(Purchase purchase : purchaseList){
            StatsTableParameters statsTableParameters = new StatsTableParameters();
            statsTableParameters.setId(purchase.getId());
            statsTableParameters.setSalesAmount(purchase.getPurchaseAmount());
            statsTableParameters.setDate(String.valueOf(purchase.getDateCreated()));
            statsData.add(statsTableParameters);
            statsTable.setItems(statsData);
        }
    }
    public void populateChart(List<Purchase> purchaseList){
        ordersChart.getData().clear();
        Map<LocalDate, Double> summedValuesByDate = new HashMap<>();
        for (Purchase purchase : purchaseList) {
            LocalDate date = purchase.getDateCreated();
            Double totalPrice = purchase.getPurchaseAmount();
            if (summedValuesByDate.containsKey(date)) {
                summedValuesByDate.put(date, summedValuesByDate.get(date) + totalPrice);
            } else {
                summedValuesByDate.put(date, totalPrice);
            }
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        summedValuesByDate.forEach((date, sum) -> {
            series.getData().add(new XYChart.Data<>(date.toString(), sum));
        });
        series.setName("Orders by date and sum");
        ordersChart.getData().add(series);
        ordersChart.getXAxis().setLabel("Date");
        ordersChart.getYAxis().setLabel("Total Amount");

    }



    private void limitAccess() {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            if (!manager.isAdmin()) {
//                managerTable.setDisable(true);
            }
        } else if (currentUser.getClass() == Customer.class) {
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(warehouseTab);
            tabPane.getTabs().remove(productsTab);
            tabPane.getTabs().remove(orderStatsTab);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "WTF", "WTF", "WTF");
        }
    }

    public void leaveComment() {
        tabPane.getSelectionModel().select(commentTab);
    }
    @FXML
    private void loadWarehouseTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/warehouseTab.fxml"));
        try{
            Parent warehouseRoot = loader.load();
            WarehouseController warehouseController = loader.getController();
            warehouseController.setData(entityManagerFactory);
            warehouseTab.setContent(warehouseRoot);
            warehouseController.loadWarehouseList();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void loadUserTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/userTab.fxml"));
        try{
            Parent userRoot = loader.load();
            UserController userController = loader.getController();
            userController.setData(entityManagerFactory, currentUser);
            usersTab.setContent(userRoot);
            userController.loadCustomerTable();
            userController.loadManagerTable();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void loadCommentTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/commentTab.fxml"));
        try{
            Parent commentRoot = loader.load();
            CommentController commentController = loader.getController();
            commentController.setData(entityManagerFactory, currentUser);
            commentTab.setContent(commentRoot);
            commentController.loadCommentList();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initializeWarehouseController();
//        productGenreComboBox.getItems().addAll(ProductGenre.values());
//        productType.getItems().addAll(ProductType.values());

        rateProductButton.setOnAction(event -> {
            try {
                leaveRating(productList.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        leaveRatingButton.setOnAction(event -> {
            try {
                leaveRating(orderProductsListManager.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    //----------------------Cart functionality-------------------------------//
    private void createOrGetCart() {
        if (cartHib.getCartByUser(currentUser) == null) {
            userCart = new Cart(LocalDate.now(), currentUser, 0, new ArrayList<>());
            genericHib.create(userCart);
        } else {
            userCart = cartHib.getCartByUser(currentUser);
        }
    }

    public void addToCart() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && !userCart.getItemsInCart().contains(selectedProduct)) {
            selectedProduct.setCart(userCart);
            userCart.getItemsInCart().add(selectedProduct);
            genericHib.update(userCart);
            System.out.println("LISTAAAAAAAAAAAAAAS CARTE: " + userCart.getItemsInCart());
            loadCartItems();
            loadCatalogue();

        } else
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Invalid product", "No product selected", "Choose a product");

    }

    public void removeFromCart() {
        Product selectedProduct = currentOrderList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setCart(null);
            userCart.getItemsInCart().remove(selectedProduct);
            updateCartAmount();
            genericHib.update(selectedProduct);
            genericHib.update(userCart);
            loadCartItems();
            loadCatalogue();
        } else
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Invalid product", "No product selected", "Choose a product");
    }

    private void updateCartAmount() {
        userCart.setAmount();
        genericHib.update(userCart);
        totalAmountLabel.setText(String.valueOf(userCart.getTotalPrice()));
    }

    public void clearCart() {
        Iterator<Product> iterator = userCart.getItemsInCart().iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            p.setCart(null);
            iterator.remove();
            genericHib.update(p);
        }
        updateCartAmount();
        genericHib.update(userCart);
    }

    public void loadCartItems() {
        currentOrderList.getItems().clear();
        if (userCart != null) {
            currentOrderList.getItems().addAll(userCart.getItemsInCart());
            updateCartAmount();
        }

    }

    public void placeOrder() {
        purchaseHib.createPurchase(userCart);
        clearCart();
        loadCartItems();
        loadCatalogue();
    }

    //----------------------Order functionality-------------------------------//
    public void loadOrderData() {
        Purchase purchase = orderListManager.getSelectionModel().getSelectedItem();
        orderProductsListManager.getItems().clear();
        orderProductsListManager.getItems().addAll(purchase.getItemsInPurchase());
        User orderUser = purchase.getUser();
        orderClientField.setText(orderUser.getLogin());
        orderAmountField.setText(String.valueOf(purchase.getPurchaseAmount()));
        orderDatePicker.setValue(purchase.getDateCreated());
    }

    public void getAllOrders() {
        orderListManager.getItems().clear();
        if (currentUser.getClass() == Customer.class) {
            orderListManager.getItems().addAll(purchaseHib.getAllPurchaseByUser(currentUser));
        } else {
            orderListManager.getItems().addAll(genericHib.getAllRecords(Purchase.class));
        }
    }
    public void clearPurchaseFields(){
        orderProductsListManager.getItems().clear();
        orderClientField.clear();
        orderAmountField.clear();
        orderDatePicker.setValue(null);
    }

    public void deleteOrder() {
        Purchase selectedOrder = orderListManager.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                purchaseHib.deletePurchase(selectedOrder.getId());
                clearPurchaseFields();
                getAllOrders();
            } catch (Exception e) {
                e.printStackTrace();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Deletion error", "Could not delete", e.getMessage());
            }
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "No Order", "You have not chosen an order", "Choose an order");
        }
    }

    //----------------------Product functionality-------------------------------//
   /* public void changeProductField() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.VINYL) {
            productUniqueAttributeField.setPromptText("RPM");
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.COMPACT_DISC) {
            productUniqueAttributeField.setPromptText("Audio Format");
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.CASSETTE) {
            productUniqueAttributeField.setPromptText("Type");
        }
    }*/

    private void loadProductListManager() {
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
   public void filterByDate() {
       orderListManager.getItems().clear();
       LocalDate startDate = filterDateStart.getValue();
       LocalDate endDate = filterDateEnd.getValue();
       orderListManager.getItems().addAll(purchaseHib.filterByDate(startDate,endDate));
   }
    public void filterByStatus(){
        orderListManager.getItems().clear();
        PurchaseStatus status = statusComboBox.getValue();
        orderListManager.getItems().addAll(purchaseHib.filterByStatus(status));
    }

    public void filterByCustomer() {
        orderListManager.getItems().clear();
        int customerId = Integer.parseInt(customerIdField.getText());
        orderListManager.getItems().addAll(purchaseHib.filterByCustomer(customerId));
    }

    public void leaveRating(Product product) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentForm.fxml"));
        Parent parent = fxmlLoader.load();
        CommentFormController commentFormController = fxmlLoader.getController();
        commentFormController.setData(genericHib, product.getId(), 0, currentUser, CommentType.COMMENT);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Comment");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void reply() throws IOException {
        TreeItem<Comment> commentTreeItem = commentsTree.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentForm.fxml"));
        Parent parent = fxmlLoader.load();
        CommentFormController commentForm = fxmlLoader.getController();
        commentForm.setData(genericHib, 0, commentTreeItem.getValue().getId(),currentUser, CommentType.REPLY);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Comment");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    public void loadTreeComments(){
        Product selectedProduct = genericHib.getEntityById(Product.class, productList.getSelectionModel().getSelectedItem().getId());
        commentsTree.setRoot(new TreeItem<>());
        commentsTree.setShowRoot(false);
        commentsTree.getRoot().setExpanded(true);
        selectedProduct.getCommentList().forEach(comment -> addTreeItem(comment, commentsTree.getRoot()));
    }
    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }
}


