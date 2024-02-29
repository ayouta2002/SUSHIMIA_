package com.esprit.controllers;


import com.esprit.models.Zones;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;



public class ZoneItemController implements Initializable {
    @FXML
    private ImageView img;

    @FXML
    private Label Nom_zone;



    private MyListener myListener;
        private Zones zones ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setData(Zones zones, MyListener myListener) {
        this.zones = zones;
        this.myListener = myListener;
        Nom_zone.setText(zones.getNom());

        try {
            File file = new File("C:\\xampp\\htdocs\\Imagezones\\" + zones.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            img.setImage(image);
            System.out.println(zones.getImage());
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL for image: " + zones.getImage());
            e.printStackTrace();
            // You might want to set a default image or handle the error in a different way.
        }
    }


        @FXML
        private void click(MouseEvent event) {
            myListener.onClickListener(zones);
        }
    }


