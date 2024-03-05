package com.esprit.controllers;

import com.esprit.services.ReservationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.esprit.models.Tab;
import java.util.Optional;
import javafx.event.ActionEvent;
public class ConfirmationDeReservationController {

    @FXML
    private Label Ldate;

    @FXML
    private Label Ltable_id;



    @FXML
    private Label Lzone;

    public void setLdate(String date) {
        Ldate.setText(date);
    }

    public void setLtable_id(String table_id) {
        Ltable_id.setText(table_id);
    }


    public void setLzone(String zone) {
        Lzone.setText(zone);
    }

    @FXML
    void RetourM(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageZone.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    }

