/*package com.esprit.controles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.models.Plat;
import com.esprit.services.PlatService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
public class AffichageMenucontrollers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView det_plat_img;

    @FXML
    private GridPane grid;

    @FXML
    private Label nomplat;

    @FXML
    private Label prixplat;

    @FXML
    private ScrollPane scroll;

    private List<Plat> PlatDataList = FXCollections.observableArrayList();
    private PlatService platService = new PlatService();
    private cardplatcontrollers.MyListener myListener;

    @FXML
    void initialize() {
        System.out.println("hello");
        PlatDataList.addAll(platService.afficher());
        System.out.println("load data");
        if (PlatDataList.size() > 0) {
            setChosenRec(PlatDataList.get(0));
            myListener = new cardplatcontrollers.MyListener() {

                @Override
                public void onClick(Plat plat) {
                    System.out.println("mouse clicked");
                    setChosenRec(plat);
                }
            };
        }

        int column = 0;
        int row = 3;
        for (int i = 0; i < PlatDataList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cardplat.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                cardplatcontrollers card = fxmlLoader.getController();
                System.out.println("plat details  " + PlatDataList.get(i).getNom_plat() + " url : C:/xampp/htdocs/imageplat/" + PlatDataList.get(i).getImage());
                card.setData(PlatDataList.get(i).getId_plat(),PlatDataList.get(i).getNom_plat(), PlatDataList.get(i).getDescription_plat(), PlatDataList.get(i).getPrix(),    PlatDataList.get(i).getImage(), myListener);


                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                System.out.println("problem");
            }
        }

    }

    private void setChosenRec(Plat p) {

        System.out.println(cardplatcontrollers.p.getNom_plat());
        System.out.println(cardplatcontrollers.p.getPrix());
        nomplat.setText(cardplatcontrollers.p.getNom_plat());
        prixplat.setText(String.valueOf(Math.round(cardplatcontrollers.p.getPrix())));
        String imagePath = "C:/xampp/htdocs/imageplat/" + cardplatcontrollers.p.getImage();
        try {
            det_plat_img.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

    }

}
*/



