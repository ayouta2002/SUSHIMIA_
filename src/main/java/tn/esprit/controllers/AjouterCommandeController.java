package tn.esprit.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.Commande;
import tn.esprit.models.Resto;
import tn.esprit.models.status;
import tn.esprit.services.CommandeService;
import tn.esprit.services.RestoService;
import tn.esprit.zizo.HelloApplication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterCommandeController {

    @FXML
    private TextField RechereField;
    @FXML
    private ComboBox<status> statusComboBox;

    @FXML
    private TextField numField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField panierField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Resto> restoComboBox;

    private final RestoService restoService = new RestoService();

    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private TableColumn<Commande, String> numColumn;

    @FXML
    private TableColumn<Commande, String> adresseColumn;

    @FXML
    private TableColumn<Commande, String> panierColumn;

    @FXML
    private TableColumn<Commande, Integer> priceColumn;

    @FXML
    private TableColumn<Commande, String> statusColumn;

    private final CommandeService commandeService = new CommandeService();

    private ObservableList<Commande> commandeList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    public void initialize() {
        setupTableView();
        refreshTableView();
        populateRestoComboBox();
        statusComboBox.getItems().setAll(status.values());
        List<String> commandeAttributes = List.of("ID", "Numéro", "Adresse", "Description", "Prix", "Statut");
        filterComboBox.setItems(FXCollections.observableArrayList(commandeAttributes));
        filterComboBox.setOnAction(event -> applyFilter());
        commandeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                numField.setText(newSelection.getNumC());
                adresseField.setText(newSelection.getAdresseC());
                panierField.setText(newSelection.getPanier());
                priceField.setText(String.valueOf(newSelection.getPrice()));
                statusComboBox.setValue(newSelection.getStatus());
            } else {
                clearFields();
            }
        });
    }
    private void applyFilter() {
        String selectedAttribute = filterComboBox.getValue();
        String keyword = RechereField.getText().toLowerCase().trim();

        if (selectedAttribute == null || keyword.isEmpty()) {
            refreshTableView(); // If no attribute is selected or search keyword is empty, refresh the table with all entries
        } else {
            List<Commande> filteredList = commandeList.stream().filter(commande -> {
                switch (selectedAttribute) {
                    case "ID":
                        return String.valueOf(commande.getId_commande()).toLowerCase().contains(keyword);
                    case "Numéro":
                        return commande.getNumC().toLowerCase().contains(keyword);
                    case "Adresse":
                        return commande.getAdresseC().toLowerCase().contains(keyword);
                    case "Description":
                        return commande.getPanier().toLowerCase().contains(keyword);
                    case "Prix":
                        return String.valueOf(commande.getPrice()).contains(keyword);
                    case "Statut":
                        return commande.getStatus().toString().toLowerCase().contains(keyword);
                    default:
                        return false;
                }
            }).collect(Collectors.toList());

            commandeTableView.setItems(FXCollections.observableArrayList(filteredList));
        }
    }
    private void setupTableView() {
        numColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumC()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresseC()));
        panierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPanier()));
        priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int) cellData.getValue().getPrice()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    }

    private void refreshTableView() {
        commandeList.clear();
        commandeList.addAll(commandeService.afficher());
        commandeTableView.setItems(commandeList);
    }

    @FXML
    void createCommande() {
        if (numField.getText().isEmpty() ||
                adresseField.getText().isEmpty() || panierField.getText().isEmpty() || priceField.getText().isEmpty() ||
                statusComboBox.getValue() == null || restoComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required!");
            alert.showAndWait();
        } else {
            Commande newCommande = new Commande();
            newCommande.setNumC(numField.getText());
            newCommande.setAdresseC(adresseField.getText());
            newCommande.setPanier(panierField.getText());
            newCommande.setPrice(Double.parseDouble(priceField.getText()));
            newCommande.setStatus(statusComboBox.getValue());
            newCommande.setIdResto(restoComboBox.getValue().getId_resto());
            commandeService.ajouter(newCommande);
            refreshTableView();
        }
    }

    @FXML
    void updateCommande() {
        if (numField.getText().isEmpty() ||
                adresseField.getText().isEmpty() || panierField.getText().isEmpty() || priceField.getText().isEmpty() ||
                statusComboBox.getValue() == null || restoComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required!");
            alert.showAndWait();
        } else {
            Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
            if (selectedCommande != null) {
                selectedCommande.setNumC(numField.getText());
                selectedCommande.setAdresseC(adresseField.getText());
                selectedCommande.setPanier(panierField.getText());
                selectedCommande.setPrice(Double.parseDouble(priceField.getText()));
                selectedCommande.setStatus(statusComboBox.getValue());
                selectedCommande.setIdResto(restoComboBox.getValue().getId_resto());
                commandeService.modifier(selectedCommande);
                refreshTableView();
            }
        }
    }

    @FXML
    void deleteCommande() {
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            commandeService.supprimer(selectedCommande);
            refreshTableView();
        }
    }

    private void populateRestoComboBox() {
        restoComboBox.setItems(FXCollections.observableArrayList(restoService.afficher()));
    }

    private void clearFields() {
        numField.clear();
        adresseField.clear();
        panierField.clear();
        priceField.clear();
    }

    public void onResto(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RestoGestionMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) numField.getScene().getWindow();
        stage.setTitle("Gestioon Resto");
        stage.setScene(scene);
        stage.show();
    }

    public void onFront(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CommandeFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) numField.getScene().getWindow();
        stage.setTitle("Commande Front");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnRecherche(ActionEvent actionEvent) {
        String keyword = RechereField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            refreshTableView();
        } else {
            List<Commande> searchResults = commandeList.stream()
                    .filter(commande -> commande.getNumC().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
            commandeTableView.setItems(FXCollections.observableArrayList(searchResults));
        }
    }
}
