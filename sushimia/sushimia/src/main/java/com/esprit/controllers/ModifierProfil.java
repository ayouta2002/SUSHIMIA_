package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import com.esprit.utils.DataSource;
import com.mysql.cj.xdevapi.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
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
    File selectedFile;

    @FXML
    void modifier(ActionEvent event) throws IOException {

        Connection connection = DataSource.getInstance().getCnx();  // Get connection
        String nom = ftnom.getText();
        String prenom = ftprenom.getText();
        String mot_de_passe = ftmot_de_passe.getText();
        String email = ftemail.getText();

        // Vérifier si les champs ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mot_de_passe.isEmpty()) {
            // Afficher un message d'erreur si des champs sont vides
            showAlert(Alert.AlertType.ERROR, "Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si le nom est une chaîne de caractères
        if (!nom.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Nom invalide", "Le nom doit contenir uniquement des lettres.");
            return;
        }

        // Vérifier si l'email est valide
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email invalide", "Veuillez entrer un email valide.");
            return;
        }

        // Créer un objet Utilisateurs avec les nouvelles informations
        Utilisateurs utilisateurs = new Utilisateurs(nom, prenom, mot_de_passe, email);

        // Mettre à jour l'utilisateur dans la base de données
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        try {
            su.update(utilisateurs);
            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Mise à jour réussie", "Les informations ont été mises à jour avec succès.");
        } catch (RuntimeException e) {
            // Afficher un message d'erreur en cas d'échec de la mise à jour
            showAlert(Alert.AlertType.ERROR, "Erreur de mise à jour", "Erreur lors de la mise à jour des informations. Veuillez réessayer.");
            e.printStackTrace(); // Afficher la stack trace de l'exception pour le débogage
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour valider un email
    private boolean isValidEmail(String email) {
        // Vous pouvez implémenter une validation plus détaillée selon vos besoins
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    @FXML
    void supprimer(ActionEvent event)  {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur supprimé");
        alert.setContentText("Utilisateur supprimé");
        alert.show();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}