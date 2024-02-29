package com.esprit.controllers;

import com.esprit.models.Zones;
import com.esprit.services.MyListener;
import com.esprit.services.ZonesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




public class AffichageZoneController implements Initializable {





    @FXML
    private TextField Nom_zone;

    @FXML
    private TextField cap_zone;


    @FXML
    private VBox chosen;

    @FXML
    private TextField des_zone;

    @FXML
    private ChoiceBox<?> filtreOptions;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchInput;

    private  int zone_id;

    MyListener myListener;

    public Zones selectedZone;
    private List<Zones> zonesList = new ArrayList<>();


    private void setChosenZone(Zones p) {
        selectedZone = p;
        zone_id = p.getZone_id();
        Nom_zone.setText(p.getNom());
        des_zone.setText(p.getDescription());
        cap_zone.setText(Integer.toString(p.getCapacity()));
        /* Image image = new Image(p.getImage());
        img.setImage(image);
        System.out.println(p.getImage());*/
        String imagePath = "C:\\xampp\\htdocs\\Imagezones" + p.getImage();
        try {
            img.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
   public void Update() {
        Zones p;
        ZonesService zones = new ZonesService();
        zonesList.clear();
        grid.getChildren().clear();
        zonesList.addAll(zones.afficher());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Zones p) {
                setChosenZone(p);
            }
        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < zonesList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ZoneItemController zoneController = fxmlLoader.getController();
                zoneController.setData(zonesList.get(i), myListener);
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

    @FXML
    void ResZone(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationClient.fxml"));
        Parent root = loader.load();
        Nom_zone.getScene().setRoot(root);

        ReservationClientController apc = loader.getController();
        apc.setLnomzone(Nom_zone.getText());

    }

    @FXML
    void AfficherTTres(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherResClient.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
