package com.esprit.controllers;

import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import com.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifierProfil {

    @FXML
    private TextField ftemail;

    @FXML
    private TextField ftmot_de_passe;

    @FXML
    private TextField ftnom;

    @FXML
    private TextField ftprenom;

    @FXML
    void modifier(ActionEvent event) throws IOException{

        Connection connection = DataSource.getInstance().getCnx();  // Get connection
        String nom = ftnom.getText();
        String prenom = ftprenom.getText();
        String mot_de_passe = ftmot_de_passe.getText();
        String email = ftemail.getText();

        // Vérifier si les champs ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mot_de_passe.isEmpty()) {
            // Afficher un message d'erreur si des champs sont vides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Créer un objet Utilisateurs avec les nouvelles informations
        Utilisateurs utilisateurs = new Utilisateurs(nom, prenom, mot_de_passe, email);

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
    }
    }