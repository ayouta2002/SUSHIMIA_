/*package com.esprit.controles;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Plat;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemCategorieControllers { @FXML
private ImageView img;

    @FXML
    private Label Nom_zone;



    private MyListener myListener;
    private CategorieMenu categorieMenu ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setData(CategorieMenu categorieMenu, MyListener myListener) {
        this.categorieMenu= categorieMenu;

        this.myListener = myListener;
        Nom_zone.setText(categorieMenu.getNom_categorie());
        // Image image = new Image(zones.getImage());
        //img.setImage(image);
        try {
            Image image = new Image(categorieMenu.getImage());
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
*/


