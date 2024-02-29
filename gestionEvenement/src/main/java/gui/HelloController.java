package gui;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Button btnEvenements;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnReservations;

    @FXML
    private AnchorPane viewPages;

    @FXML
    void switchForm(ActionEvent event) throws IOException{
        if(event.getSource()== btnEvenements){
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionEvenement.fxml"));
            viewPages.getChildren().removeAll();
            viewPages.getChildren().setAll(fxml);
        }else if(event.getSource()== btnReservations){
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionReservation.fxml"));
            viewPages.getChildren().removeAll();
            viewPages.getChildren().setAll(fxml);

        }
    }




}