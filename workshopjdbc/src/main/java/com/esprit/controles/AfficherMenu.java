package com.esprit.controles;

import com.esprit.controles.itemcontrollers;
import com.esprit.models.Plat;

import com.esprit.services.PlatService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




public class AfficherMenu implements Initializable {



    @FXML
    private Label Description;

    @FXML
    private TextField Nom_plat;

    @FXML
    private VBox chosen;

    @FXML
    private TextField des_plat;



    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label prix;

    @FXML
    private TextField prix_plat;

    @FXML
    private ScrollPane scroll;



    private  int plat_id;

    MyListener myListener;

    public Plat selectedPlat;
    private List<Plat> platList = new ArrayList<>();


    private void setChosenZone(Plat p) {
        selectedPlat = p;
        plat_id = p.getId_plat();
        Nom_plat.setText(p.getNom_plat());
        des_plat.setText(p.getDescription_plat());
        prix_plat.setText(Float.toString(p.getPrix()));


        Image image = new Image(p.getImage());
        img.setImage(image);
        System.out.println(p.getImage());
        //image.setText(p.getImage());
        // image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
        // imgProd_ch.setImage(image);
    }
    public void Update() {
        Plat p;
        PlatService plat = new PlatService();
        platList.clear();
        grid.getChildren().clear();
        platList.addAll(plat.afficher());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Plat p) {
                setChosenZone(p);
            }


        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < platList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                itemcontrollers platController = fxmlLoader.getController();
                platController.setData(platList.get(i), myListener);
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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Update();
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
}
