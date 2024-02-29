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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class listEvenementCardFrontController implements Initializable {

    @FXML
    private ImageView imgEve;

    @FXML
    private Label labelDateDebutEve;

    @FXML
    private Label labelDateFinEve;

    @FXML
    private Label labelDescriptionEve;

    @FXML
    private Label labelNomEve;

    @FXML
    private Button btnReserver;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    Evenement eve;
    MyListener myListener;
    private int idEve;

    public void setData (Evenement eve, MyListener myListener){
        this.eve = eve ;
        this.myListener = myListener;
        imgEve.setImage(new Image("C:\\Users\\rabii\\Documents\\New folder (3)\\gestionEvenement\\src\\main\\java\\uploads\\"+eve.getImage()));
        labelNomEve.setText(eve.getNom());
        labelDateDebutEve.setText(String.valueOf(eve.getDateDebut())+" | ");
        labelDateFinEve.setText(String.valueOf(eve.getDateFin()));
        labelDescriptionEve.setText(eve.getDescription());
        this.idEve=eve.getId();
    }

    @FXML
    void ajouterReservationFront(ActionEvent event){
        if(event.getSource() == btnReserver) {
            // From Formulaire
            String description = "En attente";
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
            int id_event = this.idEve;


            Reservation p = new Reservation(
                    description, dateRes, id_event);
            ServiceReservation sr = new ServiceReservation();
            sr.ajouter(p);
            send_SMS();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajouté avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Réservation a été ajoutée avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
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
