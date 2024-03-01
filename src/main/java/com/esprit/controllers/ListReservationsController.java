package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ListReservationsController implements Initializable {

    @FXML
    private TableColumn<Reservation, Date> col_Date;

    @FXML
    private TableColumn<Reservation, Integer> col_IDT;

    @FXML
    private TableColumn<Reservation, Integer> col_idC;

    @FXML
    private TableColumn<Reservation, String> col_zone;

    @FXML
    private TableView<Reservation> tableRes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableView();
        rafraichirTableView();
    }
    public void initializeTableView() {
        ReservationService reservationService = new ReservationService();
        List<Reservation> allRes = reservationService.afficher();

        // Créer une observable list pour les zones affichées dans la table
        ObservableList<Reservation> displayedRes = FXCollections.observableArrayList();

        // Ajouter toutes les zones à la liste observable
        displayedRes.addAll(allRes);

        // Associer la liste observable à la table view
        tableRes.setItems(displayedRes);
    }

    @FXML
    private void rafraichirTableView() {
        ReservationService reservationService = new ReservationService();
        List<Reservation> tableList = reservationService.afficher();
        ObservableList<Reservation> reservation = FXCollections.observableArrayList(tableList);

        // Associer les propriétés des zones aux colonnes de la table view

        // id_tabcol.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        col_idC.setCellValueFactory(new PropertyValueFactory<>("id_C"));
        col_zone.setCellValueFactory(new PropertyValueFactory<>("zone"));
        col_IDT.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("dateR"));
        tableRes.setItems(reservation);
    }



}

