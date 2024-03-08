package com.esprit.controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import com.esprit.models.Cocktails;
import com.esprit.services.Cocktailsservice;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuCocktailItalienController implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private Button italien_cocktail;

    @FXML
    private Button japanese_cocktail;
    private Cocktailsservice serviceCocktails = new Cocktailsservice();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chargerTousLesCocktails();
    }

    private void chargerTousLesCocktails() {
        List<Cocktails> tousLesCocktails = serviceCocktails.afficher();
        for (Cocktails cocktail : tousLesCocktails) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ItemCocktail.fxml"));
                Node vueCocktail = loader.load();
                ItemCocktail controleur = loader.getController();
                controleur.setData(cocktail);
                grid.add(vueCocktail, grid.getChildren().size() % 3, grid.getChildren().size() / 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void menuitalien_cocktail(ActionEvent actionEvent) {
        try {
            Parent interfaceGra = FXMLLoader.load(getClass().getResource("/menuCocktailItalien.fxml"));
            italien_cocktail.getScene().setRoot(interfaceGra);
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
    }
    @FXML
    private void menujapanese_cocktail(ActionEvent event) {
        try {
            Parent interfaceGra = FXMLLoader.load(getClass().getResource("/menuCocktailjaponais.fxml"));
            japanese_cocktail.getScene().setRoot(interfaceGra);
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
    }
}
