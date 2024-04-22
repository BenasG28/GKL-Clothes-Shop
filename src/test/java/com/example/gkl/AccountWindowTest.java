//package com.example.gkl;
//
//import com.example.gkl.fxControllers.AccountController;
//import com.example.gkl.fxControllers.ChangePasswordController;
//import javafx.application.Platform;
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.scene.control.TextField;
//import javafx.scene.text.Text;
//import javafx.util.converter.NumberStringConverter;
//import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformResolver;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//public class AccountWindowTest {
//    //    @Mock
////    private TextField nameTextfield;
//    @Mock
//    private TextField surnameTextField;
//    @Mock
//    private TextField loginTextfield;
//    @Mock
//    private TextField emailTextfield;
//    @Mock
//    private TextField waistTextfield;
//    @Mock
//    private TextField hipTextfield;
//    @Mock
//    private TextField inseamTextfield;
//    @Mock
//    private TextField legLengthTextfield;
//    @Mock
//    private TextField shoulderTextfield;
//    @Mock
//    private TextField chestTextfield;
//    @Mock
//    private TextField backTextfield;
//    @Mock
//    private TextField sleeveTextfield;
//
//    @Mock
//    private StringProperty firstName;
//
//    private AccountController accountController;
//
//    @BeforeEach
//    public void setUp() {
////        MockitoAnnotations.openMocks(this);
//        Platform.startup(() -> {
////            MockitoAnnotations.openMocks(this);
////            accountController = new AccountController();
////            accountController.nameTextfield = nameTextfield;
////            // Set up the properties for testing
////            accountController.firstName = new SimpleStringProperty();
////            accountController.lastName = new SimpleStringProperty();
////            accountController.login = new SimpleStringProperty();
////            accountController.email = new SimpleStringProperty();
////            accountController.waistMeas = new SimpleDoubleProperty();
////            accountController.hipMeas = new SimpleDoubleProperty();
////            accountController.inseamMeas = new SimpleDoubleProperty();
////            accountController.legLengthMeas = new SimpleDoubleProperty();
////            accountController.shoulderMeas = new SimpleDoubleProperty();
////            accountController.chestMeas = new SimpleDoubleProperty();
////            accountController.backMeas = new SimpleDoubleProperty();
////            accountController.sleeveMeas = new SimpleDoubleProperty();
//        });
//
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        Platform.exit();
//    }
//
//    @Test
//    void testInitialize() {
//        Platform.runLater(() -> {
//            accountController = new AccountController();
//            TextField nameTextfield = new TextField();
//            TextField surnameTextField = new TextField();
//            TextField loginTextfield = new TextField();
//            TextField emailTextfield = new TextField();
//            TextField waistTextfield = new TextField();
//            TextField hipTextfield = new TextField();
//            TextField inseamTextfield = new TextField();
//            TextField legLengthTextfield = new TextField();
//            TextField shoulderTextfield = new TextField();
//            TextField chestTextfield = new TextField();
//            TextField backTextfield = new TextField();
//            TextField sleeveTextfield = new TextField();
//            accountController.nameTextfield = nameTextfield;
//            accountController.surnameTextField = surnameTextField;
//            accountController.loginTextfield = loginTextfield;
//            accountController.emailTextfield = emailTextfield;
//            accountController.waistTextfield = waistTextfield;
//            accountController.hipTextfield = hipTextfield;
//            accountController.inseamTextfield = inseamTextfield;
//            accountController.legLengthTextfield = legLengthTextfield;
//            accountController.shoulderTextfield = shoulderTextfield;
//            accountController.chestTextfield = chestTextfield;
//            accountController.backTextfield = backTextfield;
//            accountController.sleeveTextfield = sleeveTextfield;
//            accountController.firstName = new SimpleStringProperty("");
//
////            AccountController accountController = mock(AccountController.class);
////            accountController.firstName = new SimpleStringProperty();
//            accountController.initialize();
////
////
//////            TextField mockNameTextField = new TextField();
////
////            // Verify bindings are set up correctly
//            assertEquals("", accountController.firstName.getValue());
////            verify(nameTextfield).textProperty().bindBidirectional(accountController.firstName);
//
////            verify(accountController).nameTextfield.textProperty().bindBidirectional(accountController.firstName);
////            verify(surnameTextField).textProperty().bindBidirectional(accountController.lastName);
////            verify(loginTextfield).textProperty().bindBidirectional(accountController.login);
////            verify(emailTextfield).textProperty().bindBidirectional(accountController.email);
////            verify(waistTextfield).textProperty().bindBidirectional(accountController.waistMeas, new NumberStringConverter());
////            verify(hipTextfield).textProperty().bindBidirectional(accountController.hipMeas, new NumberStringConverter());
////            verify(inseamTextfield).textProperty().bindBidirectional(accountController.inseamMeas, new NumberStringConverter());
////            verify(legLengthTextfield).textProperty().bindBidirectional(accountController.legLengthMeas, new NumberStringConverter());
////            verify(shoulderTextfield).textProperty().bindBidirectional(accountController.shoulderMeas, new NumberStringConverter());
////            verify(chestTextfield).textProperty().bindBidirectional(accountController.chestMeas, new NumberStringConverter());
////            verify(backTextfield).textProperty().bindBidirectional(accountController.backMeas, new NumberStringConverter());
////            verify(sleeveTextfield).textProperty().bindBidirectional(accountController.sleeveMeas, new NumberStringConverter());
//
//        });
//
//    }
//
//}
//
//
////    Field nameTextfield = null;
////            try {
////                    nameTextfield = AccountController.class.getDeclaredField("nameTextfield");
////        } catch (NoSuchFieldException e) {
////        throw new RuntimeException(e);
////        }
////        nameTextfield.setAccessible(true);
////        try {
////        nameTextfield.set(accountController, createMockPasswordField("b"));
////        } catch (IllegalAccessException e) {
////        throw new RuntimeException(e);
////        }