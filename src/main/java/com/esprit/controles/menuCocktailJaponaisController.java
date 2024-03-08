package com.esprit.controles;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
public class menuCocktailJaponaisController implements Initializable {

    @FXML
    private Button italien_cocktail;

    @FXML
    private Button japanese_cocktail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
