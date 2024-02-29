package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionEvenementController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnListe;

    @FXML
    private AnchorPane gestionEvenement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_addEvenement()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("addEvenement.fxml"));
        gestionEvenement.getChildren().removeAll();
        gestionEvenement.getChildren().setAll(fxml);
    }

    @FXML
    void open_listEvenement()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("listEvenement.fxml"));
        gestionEvenement.getChildren().removeAll();
        gestionEvenement.getChildren().setAll(fxml);
    }


}
