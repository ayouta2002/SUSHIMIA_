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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
        String imagePath = "C:\\xampp\\htdocs\\Imagezones\\" + p.getImage();
        try {
            img.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    public void Update1() {
        ZonesService zones = new ZonesService();
        zonesList = zones.afficher();
        filterZones("");
    }
    private void filterZones(String keyword) {
        List<Zones> filteredList = new ArrayList<>();

        for (Zones zone : zonesList) {
            if (zone.getNom().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(zone);
            }
        }

        grid.getChildren().clear();
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
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < filteredList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ZoneItemController zoneController = fxmlLoader.getController();
                zoneController.setData(filteredList.get(i), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(10);
        scroll.setContent(grid);
        grid.requestLayout();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Update1();

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filterZones(newValue);
        });
    }

    @FXML
    void ResZone(ActionEvent event) throws IOException {
        String nomZone = Nom_zone.getText();

        if (!nomZone.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationClient.fxml"));
            Parent root = loader.load();

            // Assuming you have a controller in ReservationClient.fxml, you can set values or perform actions
            ReservationClientController apc = loader.getController();
            apc.setLnomzone(nomZone);

            // Switch to the new interface
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // Show an alert if tfnomzone is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Bienvenue");
            alert.setContentText("Choisir votre zone");
            alert.showAndWait();
        }
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
