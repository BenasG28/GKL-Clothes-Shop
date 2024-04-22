package com.example.gkl.fxControllers;

import com.example.gkl.hibernateControllers.GenericHib;
import com.example.gkl.model.User;
import com.example.gkl.utils.JavaFxCustomUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import static javafx.event.Event.fireEvent;

public class ChangePasswordController {

    public PasswordField currentPasswordTextfield;
    public PasswordField newPasswordTextfield;
    public PasswordField confNewPasswordTextfield;
    public Text currentPasswordText;
    public Text newPasswordText;
    public Text confNewPasswordText;
    public Button updatePasswordButton;
    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private GenericHib genericHib;
    private Stage passwordStage;
    private PasswordChangedCallback callback;
    public void setCallback(PasswordChangedCallback callback){
        this.callback = callback;
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Stage passwordStage){
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        this.passwordStage = passwordStage;
        genericHib = new GenericHib(entityManagerFactory);

    }
    public boolean isCurrentPasswordValid(){
        return BCrypt.checkpw(currentPasswordTextfield.getText(), currentUser.getPassword());
    }
    public boolean areNewPasswordMatch(){
        return newPasswordTextfield.getText().equals(confNewPasswordTextfield.getText());
    }
    public void updatePassword(){
        currentUser.setPasswordHashed(newPasswordTextfield.getText());
        genericHib.update(currentUser);
    }
    private void notifyPasswordChangedCallback() {
        if (callback != null) {
            callback.onPasswordChanged();
        }
    }
    private void closePasswordStage() {
        passwordStage.close();
    }
    private void displayPasswordErrorAlert(){
        if(!isCurrentPasswordValid()){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "Password Error", "Wrong Current Password", "Please enter current password correctly.");
        }
        else{
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING,"Password Error", "Passwords Do Not Match", "Please enter matching passwords.");
        }
    }
    @FXML
    public void saveNewPassword(){
        if(isCurrentPasswordValid() && areNewPasswordMatch()){
                updatePassword();
                notifyPasswordChangedCallback();
                closePasswordStage();

            }else{
                displayPasswordErrorAlert();
            }
        }
}



