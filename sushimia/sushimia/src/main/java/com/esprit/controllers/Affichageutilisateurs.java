package com.esprit.controllers;

import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import com.esprit.utils.DataSource;
import com.itextpdf.barcodes.qrcode.WriterException;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.esprit.models.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import static java.lang.Integer.parseInt;

public class Affichageutilisateurs implements Initializable {
    @FXML
    private TextField searchinput;

    @FXML
    private TableColumn<Utilisateurs, String> Email;
    @FXML
    private TableColumn<Utilisateurs, Void> actionCol;

    @FXML
    private TableColumn<Utilisateurs, String> Nom;

    @FXML
    private TableColumn<Utilisateurs, String> Prenom;

    @FXML
    private TableColumn<Utilisateurs, String> Role;

    @FXML
    private TableView<Utilisateurs> tabutilisateurs;

    private ServiceUtilisateurs su = new ServiceUtilisateurs();

    private ObservableList<Utilisateurs> displayedUtilisateurs = FXCollections.observableArrayList(su.readAll());

    @FXML
    public void initializeTableViewT() {
        List<Utilisateurs> allUtilisateurs = su.readAll();

        // Créer une observable list pour les utilisateurs affichées dans la table
        ObservableList<Utilisateurs> displayedUtilisateurs = FXCollections.observableArrayList();

        // Ajouter toutes les utilisateurs à la liste observable
        displayedUtilisateurs.addAll(allUtilisateurs);

        // Associer la liste observable à la table view
        tabutilisateurs.setItems(displayedUtilisateurs);


    }

    @FXML
    private void rafraichirTableView() {
        tabutilisateurs.setEditable(true);
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        List<Utilisateurs> utilisateursList = su.readAll();
        ObservableList<Utilisateurs> utilisateurs = FXCollections.observableArrayList(utilisateursList);

        // Associer les propriétés des utilisateurs aux colonnes de la table view
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Créer la colonne "Action"
        actionCol.setCellFactory(param -> new TableCell<Utilisateurs, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Utilisateurs utilisateur = getTableView().getItems().get(getIndex());
                    // Logique de suppression de l'utilisateur
                    su.delete(utilisateur);
                    initializeTableViewT();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Ajouter la colonne "Action" à la table view
        tabutilisateurs.getColumns().add(actionCol);

        // Associer la liste observable à la table view
        tabutilisateurs.setItems(utilisateurs);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableViewT();
        tabutilisateurs.setEditable(true);

        Nom.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Prenom.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Email.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Role.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());

        modifier();

    }

    public void modifier() {
        Nom.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setNom(event.getNewValue());
            su.update(utilisateurs);
        });
        Prenom.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setPrenom(event.getNewValue());
            su.update(utilisateurs);
        });
        Email.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setEmail(event.getNewValue());
            su.update(utilisateurs);
        });
    }

    @FXML
    void recherche(ActionEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = searchinput.getText().toLowerCase();

        // Créer une liste pour stocker les utilisateurs filtrés
        ObservableList<Utilisateurs> filteredUtilisateurs = FXCollections.observableArrayList();

        // Parcourir toutes les utilisateurs affichées dans la TableView
        for (Utilisateurs utilisateur : displayedUtilisateurs) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les utilisateurs dont le nom commence par ce caractère
                if (utilisateur.getNom().toLowerCase().startsWith(keyword)) {
                    filteredUtilisateurs.add(utilisateur);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage et tri
                if (utilisateur.getNom().toLowerCase().contains(keyword) ||
                        utilisateur.getPrenom().toLowerCase().contains(keyword) ||
                        utilisateur.getEmail().toLowerCase().contains(keyword)) {
                    filteredUtilisateurs.add(utilisateur);
                }
            }
        }
        // Si l'utilisateur n'a entré aucun caractère, afficher tous les utilisateurs
        if (keyword.isEmpty()) {
            filteredUtilisateurs.addAll(displayedUtilisateurs);
        }
        // Trier les utilisateurs filtrés par nom
        var comparator = Comparator.comparing(Utilisateurs::getNom);
        FXCollections.sort(filteredUtilisateurs, comparator);
        // Afficher les utilisateurs filtrés dans la TableView
        tabutilisateurs.setItems(filteredUtilisateurs);
    }


/*
    @FXML
    private void triParNom(ActionEvent event) {
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        // Récupération de la liste triée par nom
        ObservableList<Utilisateurs> sortedUtilisateurs = su.triParNom();
        // Mise à jour des éléments affichés dans la TableView
        tabutilisateurs.setItems(sortedUtilisateurs);
    }*/
}