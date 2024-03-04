package com.esprit.controles;



import com.esprit.models.Plat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        String fullurl = "C:\\xampp\\htdocs\\image_trippie_reclamation\\" + plat.getImage();

        try {
            img.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }

    }


    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(plat);
    }
}


