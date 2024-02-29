package gui;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import services.ServiceEvenement;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




public class listEvenementController implements Initializable {

    @FXML
    private TableColumn<Evenement, Date> DateDebutCell;

    @FXML
    private TableColumn<Evenement, Date> DateFinCell;

    @FXML
    private TableColumn<Evenement, Integer> NbParticipantCell;

    @FXML
    private TableColumn<Evenement, String> NomCell;

    @FXML
    private TableColumn<Evenement, String> DescriptionCell;

    @FXML
    private TableColumn<Evenement, String> ImageCell;

    @FXML
    private Button bntAddEvenement;

    @FXML
    private Button btnDeleteEvenement;

    @FXML
    private Button btnPDF;

    @FXML
    private ComboBox<String> comboBoxTri;

    @FXML
    private TableView<Evenement> tableEvenement;

    @FXML
    private TextField txtSearchEvenement;

    @FXML
    private AnchorPane listEvenementPane;



    ObservableList<Evenement> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AfficherEvenement();
    }

    @FXML
    void open_addEvenement(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("addEvenement.fxml"));
        listEvenementPane.getChildren().removeAll();
        listEvenementPane.getChildren().setAll(fxml);
    }

    public void AfficherEvenement()
    {
        ServiceEvenement ps = new ServiceEvenement();
        ps.Show().stream().forEach((c) -> {
            data.add(c);
        });

        NomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        NomCell.setCellFactory(TextFieldTableCell.forTableColumn());
        NomCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, String> event) {
                Evenement p = event.getRowValue();
                p.setNom(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        NbParticipantCell.setCellValueFactory(new PropertyValueFactory<>("nbParticipant"));
        NbParticipantCell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        NbParticipantCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, Integer> event) {
                Evenement p = event.getRowValue();
                p.setNbParticipant(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        DateDebutCell.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        DateDebutCell.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public String toString(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Date fromString(String string) {
                try {
                    // Parse the string into a Date object using the defined format
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // If the string can't be parsed, return null
                    return null;
                }
            }
        }));
        DateDebutCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, Date> event) {
                Evenement p = event.getRowValue();
                p.setDateDebut(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        DateFinCell.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        DateFinCell.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public String toString(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Date fromString(String string) {
                try {
                    // Parse the string into a Date object using the defined format
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // If the string can't be parsed, return null
                    return null;
                }
            }
        }));
        DateFinCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, Date> event) {
                Evenement p = event.getRowValue();
                p.setDateFin(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        ImageCell.setCellValueFactory(new PropertyValueFactory<>("image"));
        ImageCell.setCellFactory(TextFieldTableCell.forTableColumn());
        ImageCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, String> event) {
                Evenement p = event.getRowValue();
                p.setImage(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));
        DescriptionCell.setCellFactory(TextFieldTableCell.forTableColumn());
        DescriptionCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Evenement, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Evenement, String> event) {
                Evenement p = event.getRowValue();
                p.setDescription(event.getNewValue());
                ServiceEvenement ps = new ServiceEvenement();
                ps.modifier(p);
            }
        });
        tableEvenement.setItems(data);
        comboBoxTri.getItems().addAll("Trier Selon",  "Nom", "Nb Participant", "Description");
        try {
            searchEvenement();
        } catch (SQLException ex) {
            Logger.getLogger(listEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void TriChoice(ActionEvent event) throws IOException {
        if (comboBoxTri.getValue().equals("Nom")) {
            TriNom();
        } else if (comboBoxTri.getValue().equals("Nb Participant")) {
            TriNbParticipant();
        } else if (comboBoxTri.getValue().equals("Description")) {
            TriDescription();
        }
    }

    private void TriNom() {
        ServiceEvenement se = new ServiceEvenement();
        List<Evenement> a = se.triNom();
        NomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        NbParticipantCell.setCellValueFactory(new PropertyValueFactory<>("nbParticipant"));
        DateDebutCell.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        DateFinCell.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        ImageCell.setCellValueFactory(new PropertyValueFactory<>("image"));
        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableEvenement.setItems(FXCollections.observableList(a));
    }

    private void TriNbParticipant() {
        ServiceEvenement se = new ServiceEvenement();
        List<Evenement> a = se.triNbParticipant();
        NomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        NbParticipantCell.setCellValueFactory(new PropertyValueFactory<>("nbParticipant"));
        DateDebutCell.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        DateFinCell.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        ImageCell.setCellValueFactory(new PropertyValueFactory<>("image"));
        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableEvenement.setItems(FXCollections.observableList(a));
    }
    private void TriDescription() {
        ServiceEvenement se = new ServiceEvenement();
        List<Evenement> a = se.triDescription();
        NomCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
        NbParticipantCell.setCellValueFactory(new PropertyValueFactory<>("nbParticipant"));
        DateDebutCell.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        DateFinCell.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        ImageCell.setCellValueFactory(new PropertyValueFactory<>("image"));
        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableEvenement.setItems(FXCollections.observableList(a));
    }


    @FXML
    void supprimerEvenement(ActionEvent event) throws SQLException {
        ServiceEvenement ps = new ServiceEvenement();

        if (tableEvenement.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner l'evenement à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet evenement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'evenement sélectionnée dans la vue de la table
            int id = tableEvenement.getSelectionModel().getSelectedItem().getId();

            // Supprimer l'evenement de la base de données
            ps.supprimer(id);
            // Rafraîchir la liste de données
            data.clear();
            AfficherEvenement();
            // Rafraîchir la vue de la table
            tableEvenement.refresh();
        }
    }

    public ServiceEvenement se = new ServiceEvenement();

    public void searchEvenement() throws SQLException {
        FilteredList<Evenement> filteredData = new FilteredList<>(FXCollections.observableArrayList(se.Show()), p -> true);
        txtSearchEvenement.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(eve -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String nom = String.valueOf(eve.getNom());
                String nbParticipant = String.valueOf(eve.getNbParticipant());
                String dateDebut = String.valueOf(eve.getDateDebut());
                String dateFin = String.valueOf(eve.getDateFin());
                String image = String.valueOf(eve.getImage());
                String description = String.valueOf(eve.getDescription());
                String lowerCaseFilter = newValue.toLowerCase();

                if (nom.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (nom.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (nbParticipant.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dateDebut.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dateFin.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (image.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (description.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableEvenement.comparatorProperty());
        tableEvenement.setItems(sortedData);
    }

    @FXML
    void genererPDF(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            ServiceEvenement se = new ServiceEvenement();
            List<Evenement> eventList = se.Show();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                /*Image image = Image.getInstance(System.getProperty("user.dir") + "/src/images/LogoGymBlack.png");
                // Positionner l'image en haut à gauche
                image.setAbsolutePosition(5, document.getPageSize().getHeight() - 120);
                // Modifier la taille de l'image
                image.scaleAbsolute(152, 100);
                // Ajouter l'image au document
                document.add(image);*/

                // Créer une police personnalisée pour la date
                Font fontDate = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
                BaseColor color = new BaseColor(114, 0, 0); // Rouge: 114, Vert: 0, Bleu: 0
                fontDate.setColor(color); // Définir la couleur de la police

                // Créer un paragraphe avec le lieu
                Paragraph tunis = new Paragraph("Ariana", fontDate);
                tunis.setIndentationLeft(455); // Définir la position horizontale
                tunis.setSpacingBefore(-30); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(tunis);

                // Obtenir la date d'aujourd'hui
                LocalDate today = LocalDate.now();

                // Créer un paragraphe avec la date
                Paragraph date = new Paragraph(today.toString(), fontDate);

                date.setIndentationLeft(437); // Définir la position horizontale
                date.setSpacingBefore(1); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(date);

                // Créer une police personnalisée
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);
                BaseColor titleColor = new BaseColor(114, 0, 0); //
                font.setColor(titleColor);

                // Ajouter le contenu au document
                Paragraph title = new Paragraph("Liste des Evenements", font);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingBefore(50); // Ajouter une marge avant le titre pour l'éloigner de l'image
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(5); // 5 colonnes pour les 5 attributs des evenements
                table.setWidthPercentage(100);
                table.setSpacingBefore(30f);
                table.setSpacingAfter(30f);

                // Ajouter les en-têtes de colonnes
                Font hrFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
                BaseColor hrColor = new BaseColor(255, 255, 255); //
                hrFont.setColor(hrColor);

                PdfPCell cell1 = new PdfPCell(new Paragraph("Nom", hrFont));
                BaseColor bgColor = new BaseColor(114, 0, 0);
                cell1.setBackgroundColor(bgColor);
                cell1.setBorderColor(titleColor);
                cell1.setPaddingTop(20);
                cell1.setPaddingBottom(20);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell2 = new PdfPCell(new Paragraph("Nb Participant", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("Date Debut", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Date Fin", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell5 = new PdfPCell(new Paragraph("Description", hrFont));
                cell5.setBackgroundColor(bgColor);
                cell5.setBorderColor(titleColor);
                cell5.setPaddingTop(20);
                cell5.setPaddingBottom(20);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);

                Font hdFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                BaseColor hdColor = new BaseColor(255, 255, 255); //
                hrFont.setColor(hdColor);
                // Ajouter les données des evenements
                for (Evenement eve : eventList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(eve.getNom(), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(String.valueOf(eve.getNbParticipant()), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(String.valueOf(eve.getDateDebut()), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);

                    PdfPCell cellR4 = new PdfPCell(new Paragraph(String.valueOf(eve.getDateFin()), hdFont));
                    cellR4.setBorderColor(titleColor);
                    cellR4.setPaddingTop(10);
                    cellR4.setPaddingBottom(10);
                    cellR4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR4);

                    PdfPCell cellR5 = new PdfPCell(new Paragraph(eve.getDescription(), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);

                }
                table.setSpacingBefore(20);
                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
