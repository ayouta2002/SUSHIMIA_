package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.TableService;
import com.esprit.services.ZonesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TablesController implements Initializable {

    @FXML
    private TableColumn<Tab, Integer> captabcol;

    /*@FXML
    private TableColumn<Tab, Integer> id_tabcol;*/
    @FXML
    private TableColumn<Tab, Void> action_tab;
    @FXML
    private TableColumn<Tab, Integer> idzonecol;
    @FXML
    private TableView<Tab> tabtable;

    @FXML
    private TextField tfcaptab;
    @FXML
    private Button CloseButton;
    @FXML
    private ListView<String> nomlistview;
    @FXML
    private TextField tfzone_idtab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableView();
        FillForm();
        rafraichirListView1();
        tabtable.setEditable(true);
    }
    public void initializeTableView() {
        TableService tableService = new TableService();
        List<Tab> allTables = tableService.afficher();

        // Créer une observable list pour les zones affichées dans la table
        ObservableList<Tab> displayedTable = FXCollections.observableArrayList();

        // Ajouter toutes les zones à la liste observable
        displayedTable.addAll(allTables);

        // Associer la liste observable à la table view
        tabtable.setItems(displayedTable);
    }

    @FXML
    private void rafraichirTableView() {
        TableService tableService = new TableService();
        List<Tab> tableList = tableService.afficher();
        ObservableList<Tab> tables = FXCollections.observableArrayList(tableList);

        // Associer les propriétés des zones aux colonnes de la table view

       // id_tabcol.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        idzonecol.setCellValueFactory(new PropertyValueFactory<>("nom_zone"));
        captabcol.setCellValueFactory(new PropertyValueFactory<>("capacit_t"));

        action_tab.setCellFactory(param -> new TableCell<Tab, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Tab table = getTableView().getItems().get(getIndex());
                    // Logique de suppression de la zone
                    tableService.supprimer(table);
                    initializeTableView();
                });

                editButton.setOnAction(event -> {
                    Tab tables = getTableView().getItems().get(getIndex());
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
        // Associer la liste observable à la table view
        tabtable.setItems(tables);
    }

    @FXML
    void AddTable(ActionEvent event) {
        String nom_zone = nomlistview.getSelectionModel().getSelectedItem();
        String capacitetab = tfcaptab.getText().trim();

        if (nom_zone == null || capacitetab.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une zone et remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capacitetab);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une valeur numérique pour la capacité.");
            alert.showAndWait();
            return;
        }

        // Vérifier si le nom de zone existe dans l'entité "zones"
        ZonesService zoneService = new ZonesService();
        if (!zoneService.zoneExists(nom_zone)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nom de zone sélectionné n'existe pas. Veuillez sélectionner un nom de zone valide.");
            alert.showAndWait();
            return;
        }

        TableService ts = new TableService();
        ts.ajouter(new Tab(capacite, nom_zone));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Table ajoutée");
        alert.setContentText("Table ajoutée !");
        alert.show();
        initializeTableView();
        resetFormulaire();
    }

    @FXML
    void UpDateTable(ActionEvent event) {
        Tab TabsSelectionne = tabtable.getSelectionModel().getSelectedItem();
        if (TabsSelectionne != null) {
            int idtabModifier = TabsSelectionne.getTable_id();

            String capacitytableValueString = tfcaptab.getText().trim();
            String Zonenomvalue = tfzone_idtab.getText().trim();

            if (capacitytableValueString.isEmpty() || Zonenomvalue.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            int capacitytableValue;
            try {
                capacitytableValue = Integer.parseInt(capacitytableValueString);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une valeur numérique pour la capacité.");
                alert.showAndWait();
                return;
            }

            // Vérifier si le nom de zone existe dans l'entité "zones"
            ZonesService zoneService = new ZonesService();
            if (!zoneService.zoneExists(Zonenomvalue)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom de zone saisi n'existe pas. Veuillez saisir un nom de zone valide.");
                alert.showAndWait();
                return;
            }

            Tab nouvellesValeursTables = new Tab(idtabModifier, capacitytableValue, Zonenomvalue);
            TableService tableService = new TableService();
            tableService.modifier(nouvellesValeursTables);
            initializeTableView();
            resetFormulaire();
        }


    }
    private void FillForm(){
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabtable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                Tab TabsSelectionne = tabtable.getSelectionModel().getSelectedItem();
                if (TabsSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée

                    tfcaptab.setText(String.valueOf(TabsSelectionne.getCapacit_t()));
                    tfzone_idtab.setText(TabsSelectionne.getNom_zone());

                    //ftcapacite.setText(ZonesSelectionne.getCapacity());
                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });}
    private void resetFormulaire() {
        tfzone_idtab.setText("");
        tfcaptab.setText("");

    }
    @FXML
    void Close(ActionEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void menureturn(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menuu.fxml"));
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

    public void rafraichirListView1() {
        ZonesService zonesService = new ZonesService();
        List<Zones> allZones = zonesService.afficher();

        // Créer une observable list pour les noms de zones
        ObservableList<String> zoneNames = FXCollections.observableArrayList();

        // Ajouter tous les noms de zones à la liste observable
        for (Zones zone : allZones) {
            zoneNames.add(zone.getNom());
        }

        // Associer la liste observable à la ListView
        nomlistview.setItems(zoneNames);
    }
}

