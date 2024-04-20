package com.example.gkl.fxControllers;

import com.example.gkl.StartGui;
import com.example.gkl.hibernateControllers.CartHib;
import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.hibernateControllers.ProductHib;
import com.example.gkl.hibernateControllers.PurchaseHib;
import com.example.gkl.model.*;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

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
    public Tab ordersTab;
    @FXML
    public Tab productsTab;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab primaryTab;
    @FXML
    public Label totalAmountLabel;
    @FXML
    public Button addToCartButton;
    public Tab commentTab;
    public Button placeOrderButton;
    public Button deleteCartButton;
    public TreeView<Comment> commentsTree;
    public Button rateProductButton;
    public MenuItem replyContext;
    public Tab accountTab;
    public AnchorPane productContainer;
    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHib genericHib;
    private CartHib cartHib;
    private Cart userCart;
    private ProductHib productHib;
    private PurchaseHib purchaseHib;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        genericHib = new GenericHib(entityManagerFactory);
        cartHib = new CartHib(entityManagerFactory);
        productHib = new ProductHib(entityManagerFactory);
        purchaseHib = new PurchaseHib(entityManagerFactory);
        createOrGetCart();
        limitAccess();
        displayProductCards();
        loadCartItems();
    }

//    private void loadCatalogue() {
//        productList.getItems().clear();
//        productList.getItems().addAll(productHib.getAllProductWithNoCart());
//    }

    public void loadTabValues() {
        if (primaryTab.isSelected()) {
            loadCartItems();
            displayProductCards();
        } else if (productsTab.isSelected()) {


        } else if (warehouseTab.isSelected()) {

        } else if (commentTab.isSelected()) {

        } else if (ordersTab.isSelected()) {

        } else if (usersTab.isSelected()) {

        }
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
    private void displayProductCards() {
        List<Product> products = productHib.getAllProductWithNoCart(); // Retrieve products from database

        // Clear existing children from the product container
        productContainer.getChildren().clear();

        // Create a VBox to hold the product cards vertically
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between product cards

        // Dynamically create product cards for each product
        for (Product product : products) {
            ProductCard productCard = new ProductCard();
            productCard.setProductData(product); // Set product data
            vbox.getChildren().add(productCard.getNode()); // Add product card to VBox
        }

        // Set the VBox as the content of the product container
        productContainer.getChildren().add(vbox);
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
    @FXML
    private void loadProductTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/productTab.fxml"));
        try{
            Parent productRoot = loader.load();
            ProductController productController = loader.getController();
            productController.setData(entityManagerFactory);
            productsTab.setContent(productRoot);
            productController.loadProductListManager();
            productController.setupWarehouseComboBox();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void loadOrderTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/orderTab.fxml"));
        try{
            Parent orderRoot = loader.load();
            OrderController orderController = loader.getController();
            orderController.setData(entityManagerFactory, currentUser);
            ordersTab.setContent(orderRoot);
            orderController.getAllOrders();
            orderController.setupStatusComboBox();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void loadAccountTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gkl/accountTab.fxml"));
        try{
            Parent accountRoot = loader.load();
            AccountController accountController = loader.getController();
            accountTab.setContent(accountRoot);
            accountController.setData(entityManagerFactory, currentUser);
            accountController.loadUserInfo();
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
            displayProductCards();

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
            displayProductCards();
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
        displayProductCards();
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

