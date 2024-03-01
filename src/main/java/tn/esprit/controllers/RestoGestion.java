package tn.esprit.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import tn.esprit.models.Resto;
import tn.esprit.services.RestoService;
import tn.esprit.zizo.HelloApplication;

import java.io.IOException;
import java.util.*;

public class RestoGestion {

    @FXML
    private TextField nomRField;

    @FXML
    private TextField adresseRField;

    @FXML
    private TextField numRField;

    @FXML
    public ListView<Resto> restoListView;

    private final RestoService restoService = new RestoService();

    @FXML
    public void initialize() {
        restoListView.setCellFactory(param -> new ListCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            @Override
            protected void updateItem(Resto item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Button deleteButton = new Button("Supprimer");
                    deleteButton.setOnAction(event -> {
                        restoService.supprimer(item);
                        refreshListView();
                    });
                    setText(item.getNomR() + " - " + item.getAdresseR() + " - " + item.getNumR());
                    setGraphic(deleteButton);
                }
            }
        });

        refreshListView();

        restoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Resto>() {
            @Override
            public void changed(ObservableValue<? extends Resto> observable, Resto oldValue, Resto newValue) {
                if (newValue != null) {
                    nomRField.setText(newValue.getNomR());
                    adresseRField.setText(newValue.getAdresseR());
                    numRField.setText(String.valueOf(newValue.getNumR()));
                } else {
                    nomRField.clear();
                    adresseRField.clear();
                    numRField.clear();
                }
            }
        });
    }

    private void refreshListView() {
        restoListView.getItems().clear();
        restoListView.getItems().addAll(restoService.afficher());
    }

    public void createCommande(ActionEvent actionEvent) {
        if (nomRField.getText().isEmpty() || adresseRField.getText().isEmpty() || numRField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }
        Resto resto = new Resto(nomRField.getText(), adresseRField.getText(), Integer.parseInt(numRField.getText()));
        restoService.ajouter(resto);
        refreshListView();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void updateCommande(ActionEvent actionEvent) {
        if (nomRField.getText().isEmpty() || adresseRField.getText().isEmpty() || numRField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }
        Resto selectedResto = restoListView.getSelectionModel().getSelectedItem();
        if (selectedResto != null) {
            selectedResto.setNomR(nomRField.getText());
            selectedResto.setAdresseR(adresseRField.getText());
            selectedResto.setNumR(Integer.parseInt(numRField.getText()));
            restoService.modifier(selectedResto);
            refreshListView();
        }
    }

    public void onCoammande(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CommandeGestionMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) restoListView.getScene().getWindow();
        stage.setTitle("Gestion Commande");
        stage.setScene(scene);
        stage.show();
    }

    public void OnSort(ActionEvent actionEvent) {
        List<Resto> restos = restoService.afficher();
        Collections.sort(restos, Comparator.comparing(Resto::getNomR));
        restoListView.getItems().setAll(restos);
    }

    public void OnStats(ActionEvent actionEvent) {
        List<Resto> restos = restoListView.getItems();
        Map<String, Integer> addressFrequency = new HashMap<>();
        for (Resto resto : restos) {
            String address = resto.getAdresseR();
            addressFrequency.put(address, addressFrequency.getOrDefault(address, 0) + 1);
        }
        String mostCommonAddress = null;
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : addressFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostCommonAddress = entry.getKey();
            }
        }
        if (mostCommonAddress != null) {
            showAlert(Alert.AlertType.INFORMATION, "Statistics",
                    "Most Common Address: " + mostCommonAddress);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Statistics", "No data available.");
        }
    }

}
