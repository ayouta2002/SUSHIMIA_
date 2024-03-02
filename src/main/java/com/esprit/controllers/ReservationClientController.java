package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.services.ReservationService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import com.esprit.services.SendMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ReservationClientController {


    @FXML
    private TextField tfnom_zone;

    @FXML
    private TextField tftable_idR;
    @FXML
    private DatePicker dateR;
    private static final int idClientStatic = 2;
    @FXML
    private Label Lnomzone;
    public void setLnomzone(String zone) {
        Lnomzone.setText(zone);
    }
    @FXML
    void AddReservation(ActionEvent event) throws IOException, InterruptedException {
        ReservationService rs = new ReservationService();
        int idClient = idClientStatic;
        int idTable = Integer.parseInt(tftable_idR.getText());
        LocalDate selectedDate = dateR.getValue();
        Date date = Date.valueOf(selectedDate);

        // Create a confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Confirmer la réservation");
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir confirmer cette réservation ?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rs.ajouter(new Reservation(idClient, Lnomzone.getText(), idTable, date));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reservation ajoutée");
            alert.setContentText("Reservation ajoutée !");
            alert.show();

            // Envoyer l'e-mail
            String clientEmail = "eya.benslimen@esprit.tn"; // Remplacez par l'adresse e-mail du client
            String subject = "Confirmation de réservation";
            String message = "Votre réservation a été confirmée avec succès.";

            envoyerMail(clientEmail, subject, message);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmationDeReservation.fxml"));
            Parent root = loader.load();
            dateR.getScene().setRoot(root);

            ConfirmationDeReservationController apc = loader.getController();

            apc.setLdate(dateR.getValue().toString());
            apc.setLtable_id(tftable_idR.getText());
            apc.setLzone(Lnomzone.getText());
        }}
    public  void envoyerMail(String email,String Subject,String Object) {

        final String username = "oussama.sfaxi@esprit.tn";
        final String password = "211JMT6879";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject(Subject);

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(Object);

            emailContent.addBodyPart(textBodyPart);
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    }
