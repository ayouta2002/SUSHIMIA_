package com.esprit.controllers;

import com.esprit.services.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.util.Optional;

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

}
