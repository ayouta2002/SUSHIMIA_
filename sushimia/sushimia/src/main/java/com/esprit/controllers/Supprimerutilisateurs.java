package com.esprit.controllers;

import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Supprimerutilisateurs {

    @FXML
    private TextField ftid;

    @FXML
    void supprimer(ActionEvent event) throws IOException {
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        su.delete(new Utilisateurs(Integer.parseInt(ftid.getText())));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur supprimé!");
        alert.setContentText("Utilisateur supprimé");
        alert.show();

    }

}
