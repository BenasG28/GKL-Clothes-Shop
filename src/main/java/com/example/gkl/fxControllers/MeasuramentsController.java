package com.example.gkl.fxControllers;

import com.example.gkl.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MeasuramentsController implements Initializable {
    @FXML
    public TextField chestmeasure;
    @FXML
    public TextField shouldermeasure;
    @FXML
    public TextField backmeasure;
    @FXML
    public TextField sleevemeasure;
    @FXML
    public TextField hipmeasure;
    @FXML
    public TextField outseammeasure;
    @FXML
    public TextField inseammeasure;
    @FXML
    public TextField waistmeasure;
    public ImageView personImage;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private String locationOfFile;

    public void setData(EntityManagerFactory entityManagerFactory, String location, User user) {
        this.user = user;
        this.locationOfFile = location;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("kursinis-parduotuve");
        String imageUrl = "https://drive.google.com/uc?export=view&id=your_file_id";
        Image image = new Image(imageUrl);
        personImage.setImage(image);
    }
}
