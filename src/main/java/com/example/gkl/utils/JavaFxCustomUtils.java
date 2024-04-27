package com.example.gkl.utils;

import javafx.scene.control.Alert;

public class JavaFxCustomUtils {
    private static boolean alertShown = false;
    public static void generateAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setOnShown(event -> alertShown =true);
        alert.setOnCloseRequest(event -> alertShown =false);
        alert.show();
    }
    public static boolean isAlertShown() {
        return alertShown;
    }

}
