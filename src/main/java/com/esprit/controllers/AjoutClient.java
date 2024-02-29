package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class AjoutClient implements Initializable  {

    @FXML
    private TextField ftemail;

    @FXML
    private PasswordField ftmot_de_passe;

    @FXML
    private TextField ftnom;

    @FXML
    private TextField ftprenom;
    @FXML
    void sinscrire(ActionEvent event) throws IOException {
        String nom = ftnom.getText();
        String prenom = ftprenom.getText();
        String motDePasse = ftmot_de_passe.getText();
        String email = ftemail.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || motDePasse.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si des champs sont manquants
        }
        //VERIFIER SI LE NOM EST VALIDE
        if (!isValidName(nom)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Nom invalide");
            alert.setContentText("Veuillez saisir un nom valide (seuls les caractères alphabétiques sont autorisés).");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le nom est invalide
        }


        // Vérifier si l'email est valide
        if (!isValidEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email invalide");
            alert.setContentText("Veuillez saisir une adresse email valide.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si l'email est invalide
        }

        // Ajouter l'utilisateur
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        su.add(new Utilisateurs(nom, prenom, motDePasse, email, Role.Client));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur ajouté!");
        alert.setContentText("Utilisateur ajouté");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour vérifier si l'email est valide
    private boolean isValidEmail(String email) {
        // Implémentez votre logique de validation d'email ici
        // Vous pouvez utiliser des expressions régulières ou d'autres méthodes de validation
        // Par exemple, une vérification simple pourrait être de voir si l'email contient un "@" et un "."
        return email.contains("@") && email.contains(".");
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
