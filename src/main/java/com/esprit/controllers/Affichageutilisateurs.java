package com.esprit.controllers;

import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import com.esprit.utils.DataSource;
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
    private TextField ftemail;

    @FXML
    private TextField ftnom;

    @FXML
    private TextField ftprenom;

    @FXML
    private TableView<Utilisateurs> tabutilisateurs;

    private ServiceUtilisateurs su = new ServiceUtilisateurs();

    private ObservableList<Utilisateurs> displayedUtilisateurs = FXCollections.observableArrayList(su.readAll());

    @FXML
    public void initializeTableViewT() {
        FillForm();
        ServiceUtilisateurs su = new ServiceUtilisateurs();
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

    private void FillForm() {
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabutilisateurs.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                Utilisateurs UtilisateursSelectionne = tabutilisateurs.getSelectionModel().getSelectedItem();
                if (UtilisateursSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée
                    ftnom.setText(UtilisateursSelectionne.getNom());
                    ftprenom.setText(UtilisateursSelectionne.getPrenom());
                    ftemail.setText(String.valueOf(UtilisateursSelectionne.getEmail()));

                    /*String imageValue = ZonesSelectionne.getImage();
                    if (imageValue != null && !imageValue.isEmpty()) {
                        Image image = new Image(imageValue);
                        image_zone.setImage(image);
                    }*/

                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });

    }


   @FXML
    void MODIFIER(ActionEvent event) {

        Connection connection = DataSource.getInstance().getCnx();  // Get connection
        String nom = ftnom.getText();
        String prenom = ftprenom.getText();
        String email = ftemail.getText();

        // Vérifier si les champs ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            // Afficher un message d'erreur si des champs sont vides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();

        }else{
        // Créer un objet Utilisateurs avec les nouvelles informations
        Utilisateurs utilisateurs = new Utilisateurs(nom, prenom, email);

        // Mettre à jour l'utilisateur dans la base de données
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        try {
            su.update(utilisateurs);
            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les informations ont été mises à jour avec succès.");
            alert.showAndWait();
        } catch (RuntimeException e) {
            // Afficher un message d'erreur en cas d'échec de la mise à jour
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de mise à jour");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la mise à jour des informations. Veuillez réessayer.");
            alert.showAndWait();
            e.printStackTrace(); // Afficher la stack trace de l'exception pour le débogage
        }
    }}
}