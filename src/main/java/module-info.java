module com.example.gkl {
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.fxml;
    requires maven.plugin.api;
    requires maven.plugin.annotations;

    opens com.example.gkl to javafx.fxml;
    exports com.example.gkl;
    opens com.example.gkl.fxControllers to javafx.fxml, javafx.base;
    exports com.example.gkl.fxControllers to javafx.fxml;
    opens com.example.gkl.model to org.hibernate.orm.core;
}
