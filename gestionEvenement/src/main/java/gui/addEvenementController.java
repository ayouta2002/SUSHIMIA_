package gui;

import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.ServiceEvenement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;





public class addEvenementController implements Initializable {

    @FXML
    private AnchorPane addEvenementPane;

    @FXML
    private Button btnAddEvenement;

    @FXML
    private Button btnClearEvenement;

    @FXML
    private Button btnReturnEvenement;

    @FXML
    private Button btnImport;

    @FXML
    private DatePicker txtDateDebut;

    @FXML
    private DatePicker txtDateFin;

    @FXML
    private TextField txtNbParticipant;

    @FXML
    private TextField txtNomEvenement;

    @FXML
    private ImageView imageInput;

    @FXML
    private TextArea txtDescription;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private File selectedImageFile;
    private String imageName = null ;


    @FXML
    void ajouterImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }

    @FXML
    void AjoutEvenement(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddEvenement){
            if (imageName == null || txtNomEvenement.getText().isEmpty() || txtNbParticipant.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Evenement.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajouterEvenement();
                //send_SMS();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Evenement a été ajoutée avec succès.");

                Optional<ButtonType> option = alert.showAndWait();
                clearFieldsEvenement();
            }
        }
        if(event.getSource() == btnClearEvenement){
            clearFieldsEvenement();
        }
    }

    @FXML
    void clearFieldsEvenement() {
        txtNomEvenement.clear();
        txtNbParticipant.clear();
        txtDateFin.getEditor().clear();
        txtDateDebut.getEditor().clear();
        imageInput.setImage(null);
        txtDescription.clear();
    }

    @FXML
    void return_ListEvenement()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("listEvenement.fxml"));
        addEvenementPane.getChildren().removeAll();
        addEvenementPane.getChildren().setAll(fxml);
    }


    private void ajouterEvenement() {

        // From Formulaire

        Date dateDebut = null;
        try {
            LocalDate localDate = txtDateDebut.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateDebut = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Date dateFin = null;
        try {
            LocalDate localDate = txtDateFin.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateFin = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        String nomEvent = txtNomEvenement.getText();
        int nbPart = Integer.parseInt(txtNbParticipant.getText());
        String img = imageName;
        String description = txtDescription.getText();


        Evenement p = new Evenement(
                nomEvent, nbPart, dateDebut, dateFin, img, description);
        ServiceEvenement ps = new ServiceEvenement();
        ps.ajouter(p);
    }


}
