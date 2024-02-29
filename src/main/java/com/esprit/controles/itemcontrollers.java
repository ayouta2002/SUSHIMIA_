package com.esprit.controles;



import com.esprit.models.Plat;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;



public class itemcontrollers implements Initializable {
    @FXML
    private ImageView img;

    @FXML
    private Label Nom_zone;



    private MyListener myListener;
    private Plat plat ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setData(Plat plat, MyListener myListener) {
        this.plat= plat;

        this.myListener = myListener;
        Nom_zone.setText(plat.getNom_plat());
        // Image image = new Image(zones.getImage());
        //img.setImage(image);
        try {
            Image image = new Image(plat.getImage());
            img.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL for image: " + plat.getImage());
            e.printStackTrace();
            // You might want to set a default image or handle the error in a different way.
        }

    }


    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(plat);
    }
}


