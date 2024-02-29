package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.services.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class ReservationClientController {


    @FXML
    private TextField tfnom_zone;

    @FXML
    private TextField tftable_idR;
    @FXML
    private DatePicker dateR;
    private static final int idClientStatic = 2;
    @FXML
    private Label Lnomzone;
    public void setLnomzone(String zone) {
        Lnomzone.setText(zone);
    }
    @FXML
    void AddReservation(ActionEvent event) throws IOException {
        ReservationService rs = new ReservationService();
        int idClient = idClientStatic;
        int idTable = Integer.parseInt(tftable_idR.getText());
        LocalDate selectedDate = dateR.getValue();
        Date date = Date.valueOf(selectedDate);

// Create a confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Confirmer la réservation");
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir confirmer cette réservation ?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rs.ajouter(new Reservation(idClient, Lnomzone.getText(), idTable, date));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reservation ajoutée");
            alert.setContentText("Reservation ajoutée !");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmationDeReservation.fxml"));
            Parent root = loader.load();
            dateR.getScene().setRoot(root);

            ConfirmationDeReservationController apc = loader.getController();

            apc.setLdate(dateR.getValue().toString());
            apc.setLtable_id(tftable_idR.getText());
            apc.setLzone(Lnomzone.getText());
        }

    }

}
