package com.esprit.controles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.esprit.models.Cocktails;
import com.esprit.services.Cocktailsservice;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemCocktail implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView img;

    private Cocktails cocktail;
    private Cocktailsservice service = new Cocktailsservice();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setData(Cocktails cocktail) {
        this.cocktail = cocktail;
        nameLabel.setText(cocktail.getNom_cocktail());
        priceLabel.setText(String.format("%.2f $", cocktail.getPrix_cocktail()));
        try {
            Image image = new Image(cocktail.getImage_url(), true);
            img.setImage(image);
        } catch (Exception e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());

        }
    }

    // Ici, vous pouvez ajouter d'autres méthodes pour interagir avec votre service, par exemple, pour supprimer ou modifier un cocktail.
}
