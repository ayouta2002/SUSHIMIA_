package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.ZonesService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.esprit.utils.DataSource;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import animatefx.animation.Shake;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.controlsfx.control.Notifications;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class AjoutZoneController implements Initializable {
    @FXML
    private TableColumn<Zones, Integer> capzone;

    @FXML
    private TableColumn<Zones, String> deszone;
    @FXML
    private ResourceBundle resources;
    @FXML
    private TextField recherchetf;
    @FXML
    private URL location;
    @FXML
    private ImageView image_zone;
   /* @FXML
    private TableColumn<Zones, Integer> id_zone;*/

    @FXML
    private TableColumn<Zones, String> nomzone;

    @FXML
    private TableView<Zones> tabzone;
    @FXML
    private TextField ftcapacite;

    @FXML
    private TextField ftdescription;
    @FXML
    private Button PDFzone;
    @FXML
    private TextField ftnom;
    @FXML
    private TableColumn<Zones, Void> actioncol;

    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Zones, Image> imagecol;

    public String url_image;
    private String path;
    File selectedFile;
    private ZonesService zs =new ZonesService();
    private ObservableList<Zones> displayedZone = FXCollections.observableArrayList(zs.afficher());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableView();
        FillForm();
        tabzone.setEditable(true);


        deszone.setCellFactory(TextFieldTableCell.<Zones>forTableColumn());
        nomzone.setCellFactory(TextFieldTableCell.<Zones>forTableColumn());

       //capzone.setCellFactory(TextFieldTableCell.<Zones>forTableColumn());

        modifiertable();
        image_zone.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        image_zone.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        url_image = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath(😕"C:\Users\X\Desktop\ScreenShot.6.png"
                        image_zone.setImage(new Image("file:" + file.getAbsolutePath()));
                        File destinationFile = new File("C:\\xampp\\htdocs\\Imagezones\\" + file.getName());
                        try {
                            // Copy the selected file to the destination file
                            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        image_zone.setImage(new Image("file:C:\\Users\\eyabe\\Desktop\\drag-drop.gif"));

    }

    public void modifiertable() {
        deszone.setOnEditCommit(event -> {
            // Obtenez la réservation à partir de l'événement
            Zones zones = event.getRowValue();
            // Mettez à jour la zone avec la nouvelle valeur
            zones.setDescription(event.getNewValue());
            // Appelez la méthode de modification de votre service (remplacez ReservationService par le nom réel de votre classe de service)
            ZonesService zonesService = new ZonesService();
            zonesService.modifier(zones);
        });
        nomzone.setOnEditCommit(event -> {
            // Obtenez la réservation à partir de l'événement
            Zones zones = event.getRowValue();
            // Mettez à jour la zone avec la nouvelle valeur
            zones.setNom(event.getNewValue());
            // Appelez la méthode de modification de votre service (remplacez ReservationService par le nom réel de votre classe de service)
            ZonesService zonesService = new ZonesService();
            zonesService.modifier(zones);
        });
    }
    @FXML
    void image_add(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("Image", ".jpg", ".png")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());

            //url_image = file.toURI().toString();
            System.out.println(selectedFile.toURI().toString());
            image_zone.setImage(image1);

            File destinationFile = new File("C:\\xampp\\htdocs\\Imagezones", selectedFile.getName());
            url_image = selectedFile.getName();
            String destinationFilePath = destinationFile.getAbsolutePath();
            url_image = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }
    public void initializeTableView() {
        ZonesService zonesService = new ZonesService();
        List<Zones> allZones = zonesService.afficher();

        // Créer une observable list pour les zones affichées dans la table
        ObservableList<Zones> displayedZone = FXCollections.observableArrayList();

        // Ajouter toutes les zones à la liste observable
        displayedZone.addAll(allZones);

        // Associer la liste observable à la table view
        tabzone.setItems(displayedZone);


    }

    @FXML
    private void rafraichirTableView() {
        ZonesService zonesService = new ZonesService();
        List<Zones> zonesList = zonesService.afficher();
        ObservableList<Zones> zones = FXCollections.observableArrayList(zonesList);

        // Associer les propriétés des zones aux colonnes de la table view
        //id_zone.setCellValueFactory(new PropertyValueFactory<>("zone_id"));
        nomzone.setCellValueFactory(new PropertyValueFactory<>("nom"));
        deszone.setCellValueFactory(new PropertyValueFactory<>("description"));
        capzone.setCellValueFactory(new PropertyValueFactory<>("capacity"));
      imagecol.setCellValueFactory(new PropertyValueFactory<>("image"));

       //TableColumn<Zones, Void> actioncol = new TableColumn<>("action");
        actioncol.setCellFactory(param -> new TableCell<Zones, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Zones zone = getTableView().getItems().get(getIndex());
                    // Logique de suppression de la zone
                    zonesService.supprimer(zone);
                    initializeTableView();
                });

                editButton.setOnAction(event -> {
                    Zones zone = getTableView().getItems().get(getIndex());
                    // Logique de modification de la zone
                    // showEditDialog(zone);
                });
            }

           @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(deleteButton);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        });

       // tabzone.getColumns().add(actioncol);
        tabzone.setItems(zones);
    }

   @FXML
   void AddZone(ActionEvent event) throws IOException {
       String nom = ftnom.getText().trim();
       String image = url_image;
       String description = ftdescription.getText().trim();
       String capaciteStr = ftcapacite.getText().trim();

       if (nom.isEmpty() || description.isEmpty() || capaciteStr.isEmpty()) {
           ftnom.setStyle("-fx-background-color: white;-fx-border-color: red; -fx-border-width: 1px");
           new animatefx.animation.Bounce(ftnom).play();
           ftdescription.setStyle("-fx-background-color: white;-fx-border-color: red; -fx-border-width: 1px");
           new Shake(ftdescription).play();
           ftcapacite.setStyle("-fx-background-color: white;-fx-border-color: red; -fx-border-width: 1px");
           new Shake(ftcapacite).play();
       }else{
               ftnom.setStyle(null);
           ftdescription.setStyle(null);
           ftcapacite.setStyle(null);
          Notifications.create()
                   .darkStyle()
                   .title(" Veuillez remplir tous les champs.")
                   .position(Pos.CENTER) // Modifier la position ici
                   .hideAfter(Duration.seconds(20))
                   .show();

           return;
       }

       // Contrôle de saisie pour le nom
       if (!isStringValid(nom)) {
           Notifications.create()
                   .darkStyle()
                   .title("Veuillez saisir un nom valide.")
                   .position(Pos.CENTER) // Modifier la position ici
                   .hideAfter(Duration.seconds(20))
                   .show();

           return;
       }

       // Contrôle de saisie pour la description
     /*  if (!isStringValid(description)) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Erreur de saisie");
           alert.setHeaderText(null);
           alert.setContentText("Veuillez saisir une description valide.");
           alert.showAndWait();
           return;
       }*/

       int capacite;
       try {
           capacite = Integer.parseInt(capaciteStr);
       } catch (NumberFormatException e) {
           Notifications.create()
                   .darkStyle()
                   .title("Veuillez saisir une valeur numérique pour la capacité.")
                   .position(Pos.CENTER) // Modifier la position ici
                   .hideAfter(Duration.seconds(20))
                   .show();

           resetFormulaire();
           return;
       }

       ZonesService zs = new ZonesService();
       zs.ajouter(new Zones(nom, description, capacite, url_image));

       Notifications.create()
               .darkStyle()
               .title("zone Ajouté avec succès")
               .position(Pos.CENTER) // Modifier la position ici
               .hideAfter(Duration.seconds(20))
               .show();
       initializeTableView();
       resetFormulaire();
   }

    private boolean isStringValid(String str) {
        return str.matches("[a-zA-Z]+");
    }

    @FXML
    void UpdateZone(ActionEvent event) {
        Zones ZonesSelectionne = tabzone.getSelectionModel().getSelectedItem();
        if (ZonesSelectionne != null) {
            int idzoneModifier = ZonesSelectionne.getZone_id();
            String nomzoneValue = ftnom.getText();
            String descriptionzoneValue = ftdescription.getText();
            int capacityzoneValue = Integer.parseInt(ftcapacite.getText());
            String imageValue = url_image;

            if (nomzoneValue.isEmpty() || descriptionzoneValue.isEmpty() || ftcapacite.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs");
                return;
            }
            // Contrôle de saisie pour le nom
            if (!isStringValid(nomzoneValue)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un nom valide.");
                alert.showAndWait();
                return;
            }

            // Contrôle de saisie pour la description
           /* if (!isStringValid(descriptionzoneValue)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir une description valide.");
                alert.showAndWait();
                return;
            }*/

            Zones nouvellesValeursZones = new Zones(idzoneModifier, nomzoneValue, descriptionzoneValue, capacityzoneValue,imageValue);
            ZonesService zonesService = new ZonesService(); // Créez une instance de la classe ZonesService si elle n'existe pas déjà
            zonesService.modifier(nouvellesValeursZones);
            initializeTableView();
            resetFormulaire();
        }

    }

    private void FillForm() {
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabzone.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                Zones ZonesSelectionne = tabzone.getSelectionModel().getSelectedItem();
                if (ZonesSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée
                    ftnom.setText(ZonesSelectionne.getNom());
                    ftdescription.setText(ZonesSelectionne.getDescription());
                    ftcapacite.setText(String.valueOf(ZonesSelectionne.getCapacity()));

                    /*String imageValue = ZonesSelectionne.getImage();
                    if (imageValue != null && !imageValue.isEmpty()) {
                        Image image = new Image(imageValue);
                        image_zone.setImage(image);
                    }*/

                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });
    }

    private void resetFormulaire() {
        ftnom.setText("");
        ftdescription.setText("");
        ftcapacite.setText("");
    }

    @FXML
    void Close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    void ReturnM(ActionEvent event) {

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

    @FXML
    void ZoneRechercheKey(KeyEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = recherchetf.getText().toLowerCase();

        // Créer une liste pour stocker les utilisateurs filtrés
        ObservableList<Zones> filteredZone = FXCollections.observableArrayList();

        // Parcourir toutes les zones affichées dans la TableView
        for (Zones zone : displayedZone) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les zones dont le nom commence par ce caractère
                if (zone.getNom().toLowerCase().startsWith(keyword)) {
                    filteredZone.add(zone);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage et tri
                if (zone.getNom().toLowerCase().contains(keyword) ||
                        zone.getDescription().toLowerCase().contains(keyword) ||
                        String.valueOf(zone.getCapacity()).toLowerCase().contains(keyword)) {
                    filteredZone.add(zone);
                }
            }

        }

        // Si l'utilisateur n'a entré aucun caractère, afficher toutes les zones
        if (keyword.isEmpty()) {
            filteredZone.clear();
            filteredZone.addAll(displayedZone);

        }

        // Trier les zones filtrées par nom
        var comparator = Comparator.comparing(Zones::getNom);
        FXCollections.sort(filteredZone, comparator);

        // Afficher les zones filtrées dans la TableView
        tabzone.setItems(filteredZone);
        //rafraichirTableView();
    }
    @FXML
    void PDFzone(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("leszones.pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                double tableWidth = 500; // Largeur de la table
                double yStartNewPage = page.getMediaBox().getHeight() - 50; // Position de départ pour une nouvelle page
                double yStart = yStartNewPage;
                double bottomMargin = 70; // Marge inférieure
                float fontSize = 12; // Taille de police

                List<Double> colWidths = new ArrayList<>(); // Liste des largeurs des colonnes
                double tableHeight = 0; // Hauteur de la table

                // Récupère les largeurs des colonnes et calcule la hauteur totale de la table
                for (TableColumn<Zones, ?> col : tabzone.getColumns()) {
                    double colWidth = col.getWidth();
                    colWidths.add(colWidth);
                    tableHeight = tabzone.getItems().size() * 20; // Supposons que chaque ligne a une hauteur de 20
                }

                // Vérifie si la table dépasse la page actuelle et crée une nouvelle page si nécessaire
                if (yStart - tableHeight < bottomMargin) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yStart = yStartNewPage;
                }

                // Dessine les en-têtes de colonnes avec une couleur différente et une bordure
                contentStream.setLineWidth(1.5f); // Épaisseur de la ligne
                contentStream.setNonStrokingColor(100, 100, 100); // Couleur grise pour le texte
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

                double yPosition = yStart;
                double xPosition = 50; // Position horizontale initiale
                for (int i = 0; i < tabzone.getColumns().size(); i++) {
                    TableColumn<Zones, ?> col = tabzone.getColumns().get(i);
                    double colWidth = colWidths.get(i);

                    // Dessine une bordure autour de l'en-tête de colonne
                    contentStream.setLineJoinStyle(1); // Style de jointure de ligne arrondi
                    contentStream.addRect((float) xPosition, (float) (yPosition - 15), (float) colWidth, 15);
                    contentStream.stroke(); // Dessine la bordure

                    contentStream.beginText();
                    contentStream.newLineAtOffset((float) (xPosition + colWidth / 2), (float) (yPosition - 10));
                    contentStream.showText(col.getText());
                    contentStream.endText();

                    xPosition += colWidth; // Met à jour la position horizontale pour la prochaine colonne
                }

                // Dessine les lignes de données avec une police différente
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                contentStream.setNonStrokingColor(0, 0, 0); // Revenir à la couleur noire pour le texte

                // ... Reste du code ...


                contentStream.close();
                document.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}





