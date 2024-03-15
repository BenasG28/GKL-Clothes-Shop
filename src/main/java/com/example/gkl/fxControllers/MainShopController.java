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
    public  Tab testbyDo;
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

    //public ComboBox<ProductType> productType;
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
    //public ListView<Track> productTrackListManager;
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
    //public ComboBox<ProductGenre> productGenreComboBox;
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
    //private List<Track> tempTrackList = new ArrayList<>();
    private CartHib cartHib;
    private Cart userCart;
    private ProductHib productHib;
    private PurchaseHib purchaseHib;
    private CommentHib commentHib;
    public TableView<CustomerTableParameters> customerTable;
    public TableColumn<CustomerTableParameters, Integer> customerIdCol;
    public TableColumn<CustomerTableParameters, String> customerLoginCol;
    public TableColumn<CustomerTableParameters, String> customerPasswordCol;
    public TableColumn<CustomerTableParameters, String> customerContactMailCol;
    public TableColumn<CustomerTableParameters, String> customerBirthDateCol;
    public TableColumn<CustomerTableParameters, String> customerFirstNameCol;
    public TableColumn<CustomerTableParameters, String> customerLastNameCol;
    public TableView<ManagerTableParameters> managerTable;
    public TableColumn<ManagerTableParameters, Integer> managerIdCol;
    public TableColumn<ManagerTableParameters, String> managerLoginCol;
    public TableColumn<ManagerTableParameters, String> managerPasswordCol;
    public TableColumn<ManagerTableParameters, String> managerContactMailCol;
    public TableColumn<ManagerTableParameters, String> managerBirthDateCol;
    public TableColumn<ManagerTableParameters, String> managerFirstNameCol;
    public TableColumn<ManagerTableParameters, String> managerLastNameCol;
    private final ObservableList<CustomerTableParameters> customerData = FXCollections.observableArrayList();
    private final ObservableList<ManagerTableParameters> managerData = FXCollections.observableArrayList();
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
            loadWarehouseList();
        } else if (commentTab.isSelected()) {
            loadCommentList();
        } else if (ordersTab.isSelected()) {
            getAllOrders();
            statusComboBox.getItems().clear();
            statusComboBox.getItems().addAll(PurchaseStatus.values());
        } else if (usersTab.isSelected()) {
            loadCustomerTable();
            loadManagerTable();
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

    private void loadCustomerTable() {
        customerTable.getItems().clear();
        customerIdCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, Integer>("id"));
        customerLoginCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("login"));
        customerPasswordCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("password"));
        customerFirstNameCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("firstName"));
        customerLastNameCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("lastName"));
        customerContactMailCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("contactMail"));
        customerBirthDateCol.setCellValueFactory(new PropertyValueFactory<CustomerTableParameters, String>("birthDate"));
        for (Customer customer : genericHib.getAllRecords(Customer.class)) {
            CustomerTableParameters customerTableParameters = new CustomerTableParameters();
            customerTableParameters.setId(customer.getId());
            customerTableParameters.setLogin(customer.getLogin());
            customerTableParameters.setPassword(customer.getPassword());
            customerTableParameters.setContactMail(customer.getContactMail());
            customerTableParameters.setBirthDate(String.valueOf(customer.getBirthDate()));
            customerTableParameters.setFirstName(customer.getFirstName());
            customerTableParameters.setLastName(customer.getLastName());
            customerData.add(customerTableParameters);
            customerTable.setItems(customerData);
        }
    }

    private void loadManagerTable() {
        managerTable.getItems().clear();
        managerIdCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, Integer>("id"));
        managerLoginCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("login"));
        managerPasswordCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("password"));
        managerFirstNameCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("firstName"));
        managerLastNameCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("lastName"));
        managerContactMailCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("contactMail"));
        managerBirthDateCol.setCellValueFactory(new PropertyValueFactory<ManagerTableParameters, String>("birthDate"));
        for (Manager manager : genericHib.getAllRecords(Manager.class)) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();
            managerTableParameters.setId(manager.getId());
            managerTableParameters.setLogin(manager.getLogin());
            managerTableParameters.setPassword(manager.getPassword());
            managerTableParameters.setContactMail(manager.getContactMail());
            managerTableParameters.setBirthDate(String.valueOf(manager.getBirthDate()));
            managerTableParameters.setFirstName(manager.getFirstName());
            managerTableParameters.setLastName(manager.getLastName());
            managerData.add(managerTableParameters);
            managerTable.setItems(managerData);
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
            tabPane.getTabs().remove(orderStatsTab);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "WTF", "WTF", "WTF");
        }
    }

    public void leaveComment() {
        tabPane.getSelectionModel().select(commentTab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trackEditor.setVisible(false);
        //productGenreComboBox.getItems().addAll(ProductGenre.values());
        //productType.getItems().addAll(ProductType.values());
        customerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (checkAdmin(currentUser)) {
                    getSelectedUser();
                } else {
                    JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
                }
            }
        });
        managerTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                getSelectedUser();
            }
        });
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

    //----------------------User functionality-------------------------------//
    public void getSelectedUser() {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            var selectedItem = customerTable.getSelectionModel().getSelectedItem();
            editUser(selectedItem);
            customerTable.getSelectionModel().clearSelection();
        }
        if (managerTable.getSelectionModel().getSelectedItem() != null) {
            var selectedItem = managerTable.getSelectionModel().getSelectedItem();
            Manager manager = (Manager) currentUser;
            if (selectedItem.getId() == manager.getId() || manager.isAdmin()) {
                editUser(selectedItem);
                managerTable.getSelectionModel().clearSelection();
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
            }
        }
    }

    public boolean checkAdmin(User currentUser) {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            return manager.isAdmin();
        }
        return false;
    }

    @FXML
    private void createUser() throws IOException {
        if (checkAdmin(currentUser)) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
            Parent parent = fxmlLoader.load();
            RegistrationController registrationController = fxmlLoader.getController();
            registrationController.setData(entityManagerFactory, true, "main-shop.fxml", currentUser);
            Stage stage = (Stage) tabPane.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Create User");
            stage.setScene(scene);
            stage.show();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
        }
    }

    public void deleteUser() {
        if (checkAdmin(currentUser)) {
            if (customerTable.getSelectionModel().getSelectedItem() != null) {
                var selectedItem = customerTable.getSelectionModel().getSelectedItem();
                int selectedId = selectedItem.getId();
                genericHib.delete(User.class, selectedId);
                loadCustomerTable();
            }
            if (managerTable.getSelectionModel().getSelectedItem() != null) {
                var selectedItem = managerTable.getSelectionModel().getSelectedItem();
                int selectedId = selectedItem.getId();
                genericHib.delete(User.class, selectedId);
                loadManagerTable();
            }
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Permission denied", "You do not have the permission to edit users.", "Please contact your superior.");
        }

    }

    private void editUser(TableParameters selectedUser) {
        int selectedId = selectedUser.getId();
        User user = genericHib.getEntityById(User.class, selectedId);
        var userType = user.getClass();
        Stage editCustomerStage = new Stage();
        VBox root = new VBox();
        TextField loginField = new TextField(user.getLogin());
        TextField passwordField = new TextField(user.getPassword());
        TextField firstNameField = new TextField(user.getFirstName());
        TextField lastNameField = new TextField(user.getLastName());
        TextField contactMailField = new TextField(user.getContactMail());
        TextField phoneNumberField = new TextField(user.getPhoneNumber());
        DatePicker birthDatePicker = new DatePicker(user.getBirthDate());
        CheckBox discountCardCheck = new CheckBox("Discount card");
        discountCardCheck.setDisable(true);
        TextField addressField = new TextField(user.getAddress());
        CheckBox adminCheck = new CheckBox("Admin");
        adminCheck.setDisable(true);
        TextField employeeIdField = new TextField();
        employeeIdField.setDisable(true);
        TextField medCertificateField = new TextField();
        medCertificateField.setDisable(true);
        DatePicker employmentDatePicker = new DatePicker();
        employmentDatePicker.setDisable(true);
        if (userType.equals(Customer.class)) {
            Customer customer = (Customer) user;
            discountCardCheck.setDisable(false);
            discountCardCheck.setSelected(customer.isDiscountCard());
        } else if (userType.equals(Manager.class)) {
            Manager manager = (Manager) user;
            employeeIdField.setDisable(false);
            employeeIdField.setText(manager.getEmployeeId());
            medCertificateField.setDisable(false);
            medCertificateField.setText(manager.getMedCertificate());
            employmentDatePicker.setDisable(false);
            employmentDatePicker.setValue(manager.getEmploymentDate());
            if (checkAdmin(currentUser)) {
                adminCheck.setDisable(false);
                adminCheck.setSelected(manager.isAdmin());
            }
        }
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label loginLabel = new Label("Login:");
        Label passwordLabel = new Label("Password:");
        Label contactMailLabel = new Label("Contact mail:");
        Label phoneNumberLabel = new Label("Phone number:");
        Label birthDateLabel = new Label("Birth Date:");
        Label addressLabel = new Label("Address:");
        Label employeeIdLabel = new Label("Employee ID:");
        Label medCertLabel = new Label("Medical Certificate:");
        Label employeeDateLabel = new Label("Employee Date Of Employ:");
        Button updateUserButton = new Button("Update");
        updateUserButton.setOnAction(event -> {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setLogin(loginField.getText());
            user.setPasswordHashed(passwordField.getText());
            user.setContactMail(contactMailField.getText());
            user.setPhoneNumber(phoneNumberField.getText());
            user.setBirthDate(birthDatePicker.getValue());
            user.setAddress(addressField.getText());
            if (user instanceof Customer customer) {
                customer.setDiscountCard(discountCardCheck.isSelected());
            } else if (user instanceof Manager manager) {
                manager.setEmployeeId(employeeIdField.getText());
                manager.setAdmin(adminCheck.isSelected());
                manager.setMedCertificate(medCertificateField.getText());
                manager.setEmploymentDate(employmentDatePicker.getValue());
            }
            try {
                genericHib.update(user);
                if (user instanceof Customer) {
                    loadCustomerTable();
                } else if (user instanceof Manager) {
                    loadManagerTable();
                }
                editCustomerStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, firstNameLabel, firstNameField, lastNameLabel, lastNameField, contactMailLabel, contactMailField, phoneNumberLabel, phoneNumberField, birthDateLabel, birthDatePicker, addressLabel, addressField, discountCardCheck, employeeIdLabel, employeeIdField, medCertLabel, medCertificateField, employeeDateLabel, employmentDatePicker, adminCheck, updateUserButton);
        Scene scene = new Scene(root, 500, 650);
        editCustomerStage.setTitle("Edit User");
        editCustomerStage.setScene(scene);
        editCustomerStage.show();
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
    public void changeProductField() {
//        if (productType.getSelectionModel().getSelectedItem() == ProductType.VINYL) {
//            productUniqueAttributeField.setPromptText("RPM");
//        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.COMPACT_DISC) {
//            productUniqueAttributeField.setPromptText("Audio Format");
//        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.CASSETTE) {
//            productUniqueAttributeField.setPromptText("Type");
//        }
    }

    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(genericHib.getAllRecords(Product.class));
    }

    public void openTrackEditor() {
        trackEditor.setVisible(true);
    }

    public void closeTrackEditor() {
        trackEditor.setVisible(false);
    }

    public void addTrack() {
//        Track track = new Track(trackTitleField.getText(), Integer.parseInt(trackLengthField.getText()));
//        tempTrackList.add(track);
//        loadTrackList();
    }

    public void removeTrack() {
//        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
//        tempTrackList.remove(track);
//        loadTrackList();
    }

    public void updateTrack() {
//        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
//        track.setName(trackTitleField.getText());
//        track.setLengthBySeconds(Integer.parseInt(trackLengthField.getText()));
//        loadTrackList();
    }

    public void loadTrackData() {
//        Track track = productTrackListManager.getSelectionModel().getSelectedItem();
//        trackTitleField.setText(track.getName());
//        trackLengthField.setText(String.valueOf(track.getLengthBySeconds()));
    }

    private void loadTrackList() {
//        productTrackListManager.getItems().clear();
//        for (Track t : tempTrackList) {
//            productTrackListManager.getItems().add(t);
//        }
    }

    public void addNewProduct() {
        //  try {
//        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
//        ProductGenre selectedProductGenre = productGenreComboBox.getSelectionModel().getSelectedItem();
//        Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
//        if (productType.getSelectionModel().getSelectedItem() == ProductType.VINYL) {
//            Vinyl vinyl = new Vinyl(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
//            for (Track t : tempTrackList) {
//                vinyl.addTrack(t);
//            }
//            tempTrackList.clear();
//            genericHib.create(vinyl);
//        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.COMPACT_DISC) {
//            CompactDisc compactDisc = new CompactDisc(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
//            for (Track t : tempTrackList) {
//                compactDisc.addTrack(t);
//            }
//            tempTrackList.clear();
//            genericHib.create(compactDisc);
//        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.CASSETTE) {
//            Cassette cassette = new Cassette(Double.parseDouble(productPriceField.getText()), productTitleField.getText(), productReleaseDateField.getValue(), selectedProductGenre, productDescriptionField.getText(), Integer.parseInt(productLengthField.getText()), productLabelField.getText(), warehouse, productUniqueAttributeField.getText());
//            for (Track t : tempTrackList) {
//                cassette.addTrack(t);
//            }
//            tempTrackList.clear();
//            genericHib.create(cassette);
//        } else {
//            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product error", "Error during create", "Wrong product type");
//        }
//        clearFields();
//        loadProductListManager();
//        //}
////        } catch (Exception e) {
////            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product creation error", "Error during create", "Product not created, check entered data");
////        }
    }

    public void loadProductData() {
//        Product product = productListManager.getSelectionModel().getSelectedItem();
//        if (product != null) {
//            if (product.getClass() == Vinyl.class) {
//                productType.setValue(ProductType.VINYL);
//                productUniqueAttributeField.setText(((Vinyl) product).getRpm());
//            } else if (product.getClass() == CompactDisc.class) {
//                productType.setValue(ProductType.COMPACT_DISC);
//                productUniqueAttributeField.setText(((CompactDisc) product).getAudioFormat());
//            } else if (product.getClass() == Cassette.class) {
//                productType.setValue(ProductType.CASSETTE);
//                productUniqueAttributeField.setText(((Cassette) product).getType());
//            }
//            productTitleField.setText(product.getTitle());
//            productLabelField.setText(product.getLabel());
//            productGenreComboBox.setValue(product.getGenre());
//            productLengthField.setText(String.valueOf(product.getLength()));
//            productDescriptionField.setText(product.getDescription());
//            productPriceField.setText(String.valueOf(product.getPrice()));
//            productReleaseDateField.setValue(product.getReleaseDate());
//            warehouseComboBox.setValue(product.getWarehouse());
//            productTrackListManager.getItems().clear();
//            productTrackListManager.getItems().addAll(product.getTrackList());
//            tempTrackList = product.getTrackList();
//        } else {
//            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Invalid product", "Product is not chosen", "Choose a product from the list");
//        }

    }

    public void updateProduct() {
        try {
//            Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
//            if (selectedProduct != null) {
//                if (selectedProduct.getClass() == Vinyl.class)
//                    ((Vinyl) selectedProduct).setRpm(productUniqueAttributeField.getText());
//                else if (selectedProduct.getClass() == CompactDisc.class)
//                    ((CompactDisc) selectedProduct).setAudioFormat(productUniqueAttributeField.getText());
//                else if (selectedProduct.getClass() == Cassette.class)
//                    ((Cassette) selectedProduct).setType(productUniqueAttributeField.getText());
//                selectedProduct.setTitle(productTitleField.getText());
//                selectedProduct.setLabel(productLabelField.getText());
//                selectedProduct.setGenre(productGenreComboBox.getSelectionModel().getSelectedItem());
//                selectedProduct.setLength(Integer.parseInt(productLengthField.getText()));
//                selectedProduct.setDescription(productDescriptionField.getText());
//                selectedProduct.setPrice(Double.parseDouble(productPriceField.getText()));
//                selectedProduct.setReleaseDate(productReleaseDateField.getValue());
//                for (Track t : tempTrackList) {
//                    if (!selectedProduct.getTrackList().contains(t)) {
//                        selectedProduct.addTrack(t);
//                    }
//                }
//                selectedProduct.setWarehouse(warehouseComboBox.getSelectionModel().getSelectedItem());
//                genericHib.update(selectedProduct);
//                loadProductListManager();
//                loadProductData();
//                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Update Successful", "Product was updated successfully", "Good Job!");
//            } else {
//                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update error", "Product is not selected", "Please select a product.");
//            }
            loadProductListManager();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Update error", "Product was not updated", "Please get help.");
        }
    }

    public void deleteProduct() {
        Product product = productListManager.getSelectionModel().getSelectedItem();
        try {
            if (product != null) {
                genericHib.deleteProduct(product.getId());
                loadProductListManager();
                clearFields();
                JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Deletion successful", "Product was deleted successfully", "Delete more!");
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Deletion error", "Product is not selected", "Please select a product.");

            }
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Deletion error", "Product was not deleted", "Please get help.");
        }
    }

    public void clearFields() {
       // productType.getSelectionModel().clearSelection();
        productTitleField.setText(null);
        productLabelField.setText(null);
        productUniqueAttributeField.setText(null);
       // productGenreComboBox.getSelectionModel().clearSelection();
        productLengthField.setText(null);
        productPriceField.setText(null);
        productReleaseDateField.setValue(null);
//        productTrackListManager.getItems().clear();
//        tempTrackList.clear();
        productDescriptionField.setText(null);
        warehouseComboBox.getSelectionModel().clearSelection();
        soldoutCheckBox.setDisable(true);
        soldoutCheckBox.setSelected(false);
    }

    //----------------------Warehouse functionality-----------------------------//

    private void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(genericHib.getAllRecords(Warehouse.class));
    }

    public void addNewWarehouse() {
        genericHib.create(new Warehouse(titleWarehouseField.getText(), addressWarehouseField.getText()));
        clearWarehouseFields();
        loadWarehouseList();
    }

    public void clearWarehouseFields() {
        titleWarehouseField.setText(null);
        addressWarehouseField.setText(null);
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        warehouse.setTitle(titleWarehouseField.getText());
        warehouse.setAddress(addressWarehouseField.getText());
        genericHib.update(warehouse);
        loadWarehouseList();
    }

    public void removeWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        genericHib.delete(Warehouse.class, selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        titleWarehouseField.setText(selectedWarehouse.getTitle());
        addressWarehouseField.setText(selectedWarehouse.getAddress());
    }

    //----------------------Comment functionality-----------------------------//
    public void selectProduct() {
        Product selectedProduct = productListForComments.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) commentProductTitleField.setText(selectedProduct.getTitle());
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

    private void loadCommentList() {
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
}
