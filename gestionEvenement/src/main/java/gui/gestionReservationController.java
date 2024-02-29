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


public class gestionReservationController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnListe;

    @FXML
    private AnchorPane gestionReservation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_addReservation()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("addReservation.fxml"));
        gestionReservation.getChildren().removeAll();
        gestionReservation.getChildren().setAll(fxml);
    }

    @FXML
    void open_listReservation()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("listReservation.fxml"));
        gestionReservation.getChildren().removeAll();
        gestionReservation.getChildren().setAll(fxml);
    }


}
