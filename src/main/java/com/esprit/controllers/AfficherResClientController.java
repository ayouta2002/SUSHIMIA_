package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.ZonesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherResClientController implements Initializable {

    @FXML
    private TableColumn<Reservation, Date> dateconf;

    @FXML
    private TableColumn<Reservation, Date> dateres;

    @FXML
    private TableColumn<Reservation, String> reszone;

    @FXML
    private TableColumn<Reservation, String> statusres;

    @FXML
    private TableColumn<Reservation, Integer> tableres;

    @FXML
    private TableView<Reservation> tabreservation;
    @FXML
    private TableColumn<Reservation, Void> actionres;
    private static final int idClientStatic = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        tabreservation.setEditable(true);

         //tableres.setCellFactory(TextFieldTableCell.<Reservation>forTableColumn());
        reszone.setCellFactory(TextFieldTableCell.<Reservation>forTableColumn());
        // dateres.setCellFactory(TextFieldTableCell.<Reservation>forTableColumn());
        // dateconf.setCellFactory(TextFieldTableCell.<Reservation>forTableColumn());
        modifiertable();
    }

    public void modifiertable() {
        reszone.setOnEditCommit(event -> {
            // Obtenez la réservation à partir de l'événement
            Reservation reservation = event.getRowValue();

            // Mettez à jour la zone avec la nouvelle valeur
            reservation.setZone(event.getNewValue());


            // Appelez la méthode de modification de votre service (remplacez ReservationService par le nom réel de votre classe de service)
            ReservationService reservationService = new ReservationService();
            reservationService.modifier(reservation);
            rafraichirTableView();
        });
    }

    private void afficherReservationsClient(int idClient) {
        // Récupérer les réservations associées au client à partir d'une source de données
        ReservationService rs = new ReservationService();
        List<Reservation> reservations = rs.getReservationsByClientId(idClient);

        // Ajouter les réservations au TableView
        tabreservation.getItems().addAll(reservations);
    }
    @FXML
    private void rafraichirTableView() {
        ReservationService reservationService = new ReservationService();

        dateres.setCellValueFactory(new PropertyValueFactory<>("dateR"));
        reszone.setCellValueFactory(new PropertyValueFactory<>("zone"));
        tableres.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        statusres.setCellValueFactory(new PropertyValueFactory<>("status"));

        actionres.setCellFactory(param -> new TableCell<Reservation, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    // Logique de suppression de la zone
                    reservationService.supprimer(reservation);


                });

                editButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    // Logique de modification de la zone
                    // showEditDialog(zone);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(deleteButton);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        });

        // Afficher les réservations du client statique
        afficherReservationsClient(idClientStatic);

    }
    @FXML
    void Return(ActionEvent event) {
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
