module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jbcrypt;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    opens com.example.demo1.fxControllers to javafx.fxml, javafx.base;
    exports com.example.demo1.fxControllers to javafx.fxml;
    opens com.example.demo1.model to org.hibernate.orm.core;


}