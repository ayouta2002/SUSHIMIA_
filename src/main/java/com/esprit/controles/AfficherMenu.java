package com.esprit.controles;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Plat;

import com.esprit.services.PlatService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




public class AfficherMenu implements Initializable {



    @FXML
    private Label Description;

    @FXML
    private TextField Nom_menu;

    @FXML
    private VBox chosen;

    @FXML
    private TextField des_menu;




    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label prix;

    @FXML
    private TextField prix_menu;
    @FXML
    private TextField trecherche;

    @FXML
    private ScrollPane scroll;



    private  int plat_id;

    private List<Plat> recDataList = FXCollections.observableArrayList();
    private PlatService rec = new PlatService();
    private itemcontrollers.MyListener myListener;


    private void setChosenZone(Plat r) {

        Nom_menu.setText(itemcontrollers.r.getNom_plat());
        des_menu.setText(itemcontrollers.r.getDescription_plat());
        prix_menu.setText(String.valueOf(itemcontrollers.r.getPrix()));
        String imagePath = "C:\\xampp\\htdocs\\imageplat\\" + itemcontrollers.r.getImage();
        try {
            img.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("hello");
        recDataList.addAll(rec.afficher());
        System.out.println("load data");
        if (recDataList.size() > 0) {
            setChosenZone(recDataList.get(0));
            myListener = new itemcontrollers.MyListener() {
                @Override
                public void onClick(Plat r) {
                    System.out.println("mouse clicked");
                    setChosenZone(r);
                }
            };
        }

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < recDataList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                itemcontrollers platController = fxmlLoader.getController();
                platController.setData(recDataList.get(i).getId_plat(), recDataList.get(i).getNom_plat(), recDataList.get(i).getDescription_plat(), recDataList.get(i).getPrix(), recDataList.get(i).getQuantite(), recDataList.get(i).getNom_categorie(), recDataList.get(i).getImage(), myListener);
                if (c > 2) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
                //grid weight
                grid.setMinWidth(215);
                grid.setPrefWidth(215);
                grid.setMaxWidth(215);//
                //height
                grid.setMinHeight(170);
                grid.setPrefHeight(170);
                grid.setMaxHeight(170);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(215, 0, 0, 65));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        trecherche.textProperty().addListener((observable, oldValue, newValue) -> search());
    }

/*
    @FXML
    void ajoutProduit(ActionEvent event) {

    }

    @FXML
    void modifierZone(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void supprimerProduit(ActionEvent event) {

    }
*/
@FXML
void search() {
    String searchTerm = trecherche.getText().trim().toLowerCase();
    List<Plat> searchResults = new ArrayList<>();

    for (Plat plat : recDataList) {
        if (plat.getNom_plat().toLowerCase().contains(searchTerm) ) {
            searchResults.add(plat);
        }
    }

    displaySearchResults(searchResults);
}

    private void displaySearchResults(List<Plat> searchResults) {
        // Clear the existing grid
        grid.getChildren().clear();

        int column = 0;
        int row = 3;

        for (Plat plat : searchResults) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                itemcontrollers item = fxmlLoader.getController();
                item.setData(plat.getId_plat(), plat.getNom_plat(), plat.getDescription_plat(),plat.getPrix(),plat.getQuantite(),plat.getNom_categorie(), plat.getImage(), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                System.out.println("Problem loading category details");
            }
        }
    }

}
