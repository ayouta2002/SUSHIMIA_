package gui;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Evenement;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.ServiceEvenement;
import services.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class addReservationController implements Initializable {

    @FXML
    private Button btnAddReservation;

    @FXML
    private Button btnClearReservation;

    @FXML
    private Button btnReturnReservation;

    @FXML
    private AnchorPane listReservationPane;

    @FXML
    private TextArea txtDescriptionRes;

    @FXML
    private ComboBox<String> txtEventRes;

    ServiceEvenement se = new ServiceEvenement();
    List<Evenement> events = se.Show();
    private int eventId=-1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> valuesMap = new HashMap<>();
        for(Evenement e : events){
            txtEventRes.getItems().add(e.getNom());
            valuesMap.put(e.getNom(),e.getId());
        }

        txtEventRes.setOnAction(event ->{
            String SelectedOption = null;
            SelectedOption = txtEventRes.getValue();
            int SelectedValue = 0;
            SelectedValue = valuesMap.get(SelectedOption);
            eventId = SelectedValue;
        });
    }

    @FXML
    void AjoutReservation(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddReservation){
            if (eventId==-1 || txtDescriptionRes.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Reservation.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajouterReservation();
                send_SMS();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Reservation a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
                clearFieldsReservation();
            }
        }
        if(event.getSource() == btnClearReservation){
            clearFieldsReservation();
        }
    }

    @FXML
    void clearFieldsReservation() {
        txtDescriptionRes.clear();
        txtEventRes.getEditor().clear();
    }

    @FXML
    void return_ListReservation(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("listReservation.fxml"));
        listReservationPane.getChildren().removeAll();
        listReservationPane.getChildren().setAll(fxml);
    }

    private void ajouterReservation() {

        // From Formulaire
        String description = txtDescriptionRes.getText();
        Date dateRes = null;
        try {
            // Get the current system date
            LocalDate currentDate = LocalDate.now();
            // Convert LocalDate to ZonedDateTime
            ZonedDateTime zonedDateTime = currentDate.atStartOfDay(ZoneId.systemDefault());
            // Convert ZonedDateTime to Instant
            Instant instant = zonedDateTime.toInstant();
            // Convert Instant to Date
            dateRes = Date.from(instant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int id_event = eventId;


        Reservation p = new Reservation(
                description, dateRes, id_event);
        ServiceReservation sr = new ServiceReservation();
        sr.ajouter(p);

    }

    void send_SMS (){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte
        String ACCOUNT_SID = "AC4a2dd0e037178bb6d202fad0c5a10258";
        String AUTH_TOKEN = "d5b1d248e28c9c38fb6b0bb14f1dba9e";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+21629703092";
        String message = "Bonjour Mr ,\n"
                + "Nous sommes ravis de vous informer qu'un evenement a été ajouté.\n "
                + "Veuillez contactez l'administration pour plus de details.\n "
                + "Merci de votre fidélité et à bientôt.\n"
                + "Cordialement";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+13123130156"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}
