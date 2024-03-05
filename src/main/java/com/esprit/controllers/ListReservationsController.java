package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

//import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class ListReservationsController implements Initializable {
    @FXML
    private Button PDFres;
    @FXML
    private TableColumn<Reservation, Date> col_Date;

    @FXML
    private TableColumn<Reservation, Integer> col_IDT;

    @FXML
    private TableColumn<Reservation, Integer> col_idC;

    @FXML
    private TableColumn<Reservation, String> col_zone;

    @FXML
    private TableColumn<Reservation, String> col_status;

    @FXML
    private TableView<Reservation> tableRes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableView();
        rafraichirTableView();
    }
    public void initializeTableView() {
        ReservationService reservationService = new ReservationService();
        List<Reservation> allRes = reservationService.afficher();

        // Créer une observable list pour les zones affichées dans la table
        ObservableList<Reservation> displayedRes = FXCollections.observableArrayList();

        // Ajouter toutes les zones à la liste observable
        displayedRes.addAll(allRes);

        // Associer la liste observable à la table view
        tableRes.setItems(displayedRes);
    }

    @FXML
    private void rafraichirTableView() {
        ReservationService reservationService = new ReservationService();
        List<Reservation> tableList = reservationService.afficher();
        ObservableList<Reservation> reservation = FXCollections.observableArrayList(tableList);

        // Associer les propriétés des zones aux colonnes de la table view

        // id_tabcol.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        col_idC.setCellValueFactory(new PropertyValueFactory<>("id_C"));
        col_zone.setCellValueFactory(new PropertyValueFactory<>("zone"));
        col_IDT.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("dateR"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableRes.setItems(reservation);
    }

    @FXML
    void accepter_reservation(ActionEvent event) {
        Reservation reservation = tableRes.getSelectionModel().getSelectedItem();
        reservation.setStatus("Accepté");
        ReservationService reservationService = new ReservationService();
        reservationService.modifier(reservation);
        rafraichirTableView();
        System.out.println("Reservation accepté");

        String clientEmail = "eya.benslimen@esprit.tn"; // Remplacez par l'adresse e-mail du client
        String subject = "Confirmation de réservation";
        String message = "Votre réservation a été accepté avec succès.";

        envoyerMail(clientEmail, subject, message);
    }

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

    @FXML
    void refuser_reservation(ActionEvent event) {
        Reservation reservation = tableRes.getSelectionModel().getSelectedItem();
        reservation.setStatus("Refusé");
        ReservationService reservationService = new ReservationService();
        reservationService.modifier(reservation);
        System.out.println("Reservation refusé");
        rafraichirTableView();

        String clientEmail = "eya.benslimen@esprit.tn"; // Remplacez par l'adresse e-mail du client
        String subject = "Confirmation de réservation";
        String message = "Votre réservation a été refusé.";

        envoyerMail(clientEmail, subject, message);
    }



    @FXML
    void PDFres(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Liste des reservation.pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                TableView<Reservation> tableView = tableRes;

                double tableWidth = 500; // Largeur de la table
                double yStartNewPage = page.getMediaBox().getHeight() - 50; // Position de départ pour une nouvelle page
                double yStart = yStartNewPage;
                double bottomMargin = 70; // Marge inférieure
                float fontSize = 12; // Taille de police

                List<Double> colWidths = new ArrayList<>(); // Liste des largeurs des colonnes
                double tableHeight = 0; // Hauteur de la table

                // Récupère les largeurs des colonnes et calcule la hauteur totale de la table
                for (TableColumn<Reservation, ?> col : tableRes.getColumns()) {
                    double colWidth = col.getWidth();
                    colWidths.add(colWidth);
                    tableHeight = tableRes.getItems().size() * 20; // Supposons que chaque ligne a une hauteur de 20
                }

                // Vérifie si la table dépasse la page actuelle et crée une nouvelle page si nécessaire
                if (yStart - tableHeight < bottomMargin) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yStart = yStartNewPage;
                }

                // Dessine les en-têtes de colonnes
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                double yPosition = yStart;
                double xPosition = 0; // Position horizontale initiale
                for (int i = 0; i < tableRes.getColumns().size(); i++) {
                    TableColumn<Reservation, ?> col = tableRes.getColumns().get(i);
                    double colWidth = colWidths.get(i);

                    contentStream.beginText();
                    contentStream.newLineAtOffset((float) (xPosition + colWidth / 2), (float) (yPosition - 15));
                    contentStream.showText(col.getText());
                    contentStream.endText();

                    xPosition += colWidth; // Met à jour la position horizontale pour la prochaine colonne
                }

                // Dessine les lignes de données
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                yPosition -= 20; // Décale la position de départ pour les lignes de données
                for (Reservation item : tableRes.getItems()) {
                    yPosition -= 20;
                    xPosition = 0; // Réinitialise la position horizontale pour chaque ligne

                    for (int i = 0; i < tableRes.getColumns().size(); i++) {
                        TableColumn<Reservation, ?> col = tableRes.getColumns().get(i);
                        double colWidth = colWidths.get(i);

                        Object cellData = col.getCellData(item);
                        String cellValue = (cellData != null) ? cellData.toString() : "";

                        contentStream.beginText();
                        contentStream.newLineAtOffset((float) (xPosition + colWidth / 2), (float) yPosition);
                        contentStream.showText(cellValue);
                        contentStream.endText();

                        xPosition += colWidth; // Met à jour la position horizontale pour la prochaine colonne
                    }
                }

                contentStream.close();
                document.save(file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void retourM(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menuu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }}
}

