package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.TableService;
import com.esprit.services.ZonesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class TablesController implements Initializable {

    @FXML
    private TableColumn<Tab, Integer> captabcol;
    @FXML
    private Button PDFtable;
    @FXML
    private TableColumn<Tab, Void> action_tab;
    @FXML
    private TableColumn<Tab, Integer> idzonecol;
    @FXML
    private TableView<Tab> tabtable;

    @FXML
    private TextField tfcaptab;
    @FXML
    private Button CloseButton;
    @FXML
    private ListView<String> nomlistview;
    @FXML
    private TextField Recherche;
    private TableService ts =new TableService();
    private ObservableList<Tab> displayedTable = FXCollections.observableArrayList(ts.afficher());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableView();
        FillForm();
        rafraichirListView1();
        tabtable.setEditable(true);
    }
    public void initializeTableView() {
        TableService tableService = new TableService();
        List<Tab> allTables = tableService.afficher();

        // Créer une observable list pour les zones affichées dans la table
        ObservableList<Tab> displayedTable = FXCollections.observableArrayList();

        // Ajouter toutes les zones à la liste observable
        displayedTable.addAll(allTables);

        // Associer la liste observable à la table view
        tabtable.setItems(displayedTable);
    }

    @FXML
    private void rafraichirTableView() {
        TableService tableService = new TableService();
        List<Tab> tableList = tableService.afficher();
        ObservableList<Tab> tables = FXCollections.observableArrayList(tableList);

        // Associer les propriétés des zones aux colonnes de la table view

        idzonecol.setCellValueFactory(new PropertyValueFactory<>("nom_zone"));
        captabcol.setCellValueFactory(new PropertyValueFactory<>("capacit_t"));

        action_tab.setCellFactory(param -> new TableCell<Tab, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Tab table = getTableView().getItems().get(getIndex());
                    // Logique de suppression de la zone
                    tableService.supprimer(table);
                    initializeTableView();
                });

                editButton.setOnAction(event -> {
                    Tab tables = getTableView().getItems().get(getIndex());
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
        // Associer la liste observable à la table view
        tabtable.setItems(tables);
    }

    @FXML
    void AddTable(ActionEvent event) {
        String nom_zone = nomlistview.getSelectionModel().getSelectedItem();
        String capacitetab = tfcaptab.getText().trim();

        if (nom_zone == null || capacitetab.isEmpty()) {
            Notifications.create()
                    .darkStyle()
                    .title(" Veuillez sélectionner une zone et remplir les champs.")
                    .position(Pos.CENTER) // Modifier la position ici
                    .hideAfter(Duration.seconds(20))
                    .show();
            return;
        }

        int capacite;
        try {
            capacite = Integer.parseInt(capacitetab);
        } catch (NumberFormatException e) {
            Notifications.create()
                    .darkStyle()
                    .title(" Veuillez saisir une valeur numérique pour la capacité.")
                    .position(Pos.CENTER) // Modifier la position ici
                    .hideAfter(Duration.seconds(20))
                    .show();
            return;
        }
        TableService ts = new TableService();
        ts.ajouter(new Tab(capacite, nom_zone));

        Notifications.create()
                .darkStyle()
                .title(" Table ajoutee avec succees.")
                .position(Pos.CENTER) // Modifier la position ici
                .hideAfter(Duration.seconds(20))
                .show();
        initializeTableView();
        resetFormulaire();
    }

    @FXML
    void UpDateTable(ActionEvent event) {
        Tab TabsSelectionne = tabtable.getSelectionModel().getSelectedItem();
        if (TabsSelectionne != null) {
            int idtabModifier = TabsSelectionne.getTable_id();

            String capacitytableValueString = tfcaptab.getText().trim();
            String Zonenomvalue = nomlistview.getSelectionModel().getSelectedItem();
          //  String Zonenomvalue = tfzone_idtab.getText().trim();

            if (capacitytableValueString.isEmpty() || Zonenomvalue.isEmpty()) {
                Notifications.create()
                        .darkStyle()
                        .title(" Veuillez remplir tous les champs.")
                        .position(Pos.CENTER) // Modifier la position ici
                        .hideAfter(Duration.seconds(20))
                        .show();
                return;
            }

            int capacitytableValue;
            try {
                capacitytableValue = Integer.parseInt(capacitytableValueString);
            } catch (NumberFormatException e) {
                Notifications.create()
                        .darkStyle()
                        .title(" Veuillez saisir une valeur numérique pour la capacité.")
                        .position(Pos.CENTER) // Modifier la position ici
                        .hideAfter(Duration.seconds(20))
                        .show();
                return;
            }

            Tab nouvellesValeursTables = new Tab(idtabModifier, capacitytableValue, Zonenomvalue);
            TableService tableService = new TableService();
            tableService.modifier(nouvellesValeursTables);
            initializeTableView();
            resetFormulaire();
        }


    }
    private void FillForm(){
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabtable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                Tab TabsSelectionne = tabtable.getSelectionModel().getSelectedItem();
                if (TabsSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée

                    tfcaptab.setText(String.valueOf(TabsSelectionne.getCapacit_t()));
                   // nomlistview.getItems().setAll(TabsSelectionne.getNom_zone());

                    //ftcapacite.setText(ZonesSelectionne.getCapacity());
                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });}
    private void resetFormulaire() {

        tfcaptab.setText("");

    }
    @FXML
    void Close(ActionEvent event) {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void menureturn(ActionEvent event) {

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

    public void rafraichirListView1() {
        ZonesService zonesService = new ZonesService();
        List<Zones> allZones = zonesService.afficher();

        // Créer une observable list pour les noms de zones
        ObservableList<String> zoneNames = FXCollections.observableArrayList();

        // Ajouter tous les noms de zones à la liste observable
        for (Zones zone : allZones) {
            zoneNames.add(zone.getNom());
        }

        // Associer la liste observable à la ListView
        nomlistview.setItems(zoneNames);
    }

    @FXML
    void PDFtable(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("LEStables.pdf");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                TableView<Tab> tableView = tabtable;

                double tableWidth = 500; // Largeur de la table
                double yStartNewPage = page.getMediaBox().getHeight() - 50; // Position de départ pour une nouvelle page
                double yStart = yStartNewPage;
                double bottomMargin = 70; // Marge inférieure
                float fontSize = 12; // Taille de police
                float borderWidth = 1.0f; // Ajustez selon vos besoins
                float[] borderColor = new float[] {0, 0, 0}; // Couleur noire (ajustez pour différentes couleurs)

                List<Double> colWidths = new ArrayList<>(); // Liste des largeurs des colonnes
                double tableHeight = 0; // Hauteur de la table

                // Récupère les largeurs des colonnes et calcule la hauteur totale de la table
                for (TableColumn<Tab, ?> col : tabtable.getColumns()) {
                    double colWidth = col.getWidth();
                    colWidths.add(colWidth);
                    tableHeight = tabtable.getItems().size() * 20; // Supposons que chaque ligne a une hauteur de 20
                }

                // Vérifie si la table dépasse la page actuelle et crée une nouvelle page si nécessaire
                if (yStart - tableHeight < bottomMargin) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yStart = yStartNewPage;
                }

                // Dessine le titre
                contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 16);

                contentStream.beginText();

// Calcule la position horizontale pour centrer le texte
                float textWidth = PDType1Font.HELVETICA_BOLD_OBLIQUE.getStringWidth("Liste des Tables") / 1000f * 16;
                float centerX = (page.getMediaBox().getWidth() - textWidth) / 2;

// Définit la matrice de transformation pour centrer le texte
                contentStream.setTextMatrix(Matrix.getTranslateInstance(centerX, (float) yStart));


                contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 30); // Modifie la taille de police du titre
                contentStream.showText("Liste des Tables");
                contentStream.endText();

                yStart -= 30; // Décale la position de départ après le titre

                // Dessine les en-têtes de colonnes
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

                double yPosition = yStart;
                double xPosition = 50; // Position horizontale initiale
                float acc= (float)xPosition;

                for (int i = 0; i < tabtable.getColumns().size(); i++) {
                    TableColumn<Tab, ?> col = tabtable.getColumns().get(i);
                    double colWidth = colWidths.get(i);
                    acc+=acc;

                    // Dessine la bordure supérieure de la cellule d'en-tête
                    contentStream.setLineWidth(borderWidth);
                    contentStream.setStrokingColor(borderColor[0], borderColor[1], borderColor[2]);
                    contentStream.moveTo((float) xPosition, (float) (yPosition - 15));
                    contentStream.lineTo((float) (xPosition + colWidth), (float) (yPosition - 15));
                    contentStream.stroke();

                    // Dessine la bordure inférieure de la cellule d'en-tête
                    contentStream.moveTo((float) xPosition, (float) (yPosition - 35));
                    contentStream.lineTo((float) (xPosition + colWidth), (float) (yPosition - 35));
                    contentStream.stroke();

                    // Dessine le texte de l'en-tête
                    contentStream.beginText();
                    contentStream.newLineAtOffset((float) (xPosition + colWidth / 2), (float) (yPosition - 25));
                    contentStream.showText(col.getText());
                    contentStream.endText();

                    xPosition += colWidth; // Met à jour la position horizontale pour la prochaine colonne
                }

                // Dessine les lignes de données
                contentStream.setFont(PDType1Font.HELVETICA, fontSize);
                yPosition -= 40; // Décale la position de départ pour les lignes de données
                for (Tab item : tabtable.getItems()) {
                    yPosition -= 20;
                    xPosition = 50; // Réinitialise la position horizontale pour chaque ligne

                    for (int i = 0; i < tabtable.getColumns().size(); i++) {
                        TableColumn<Tab,?> col = tabtable.getColumns().get(i);
                        double colWidth = colWidths.get(i);

                        Object cellData = col.getCellData(item);
                        String cellValue = (cellData != null) ? cellData.toString() : "";
                        contentStream.beginText();
                        contentStream.newLineAtOffset((float) (xPosition + colWidth / 2), (float) yPosition);
                        contentStream.showText(cellValue);
                        contentStream.endText();

                        // Dessine la bordure supérieure de la cellule de données
                        contentStream.setLineWidth(borderWidth);
                        contentStream.setStrokingColor(borderColor[0], borderColor[1], borderColor[2]);
                        contentStream.moveTo((float) xPosition, (float) (yPosition - 5));
                        contentStream.lineTo((float) (xPosition + colWidth), (float) (yPosition - 5));
                        contentStream.stroke();

                        // Dessine la bordure inférieure de la cellule de données
                        contentStream.moveTo((float) xPosition, (float) (yPosition - 25));
                        contentStream.lineTo((float) (xPosition + colWidth), (float) (yPosition - 25));
                        contentStream.stroke();

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
    void searche(KeyEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = Recherche.getText().toLowerCase();

        // Créer une liste pour stocker les utilisateurs filtrés
        ObservableList<Tab> filteredTab = FXCollections.observableArrayList();

        // Parcourir toutes les zones affichées dans la TableView
        for (Tab tab : displayedTable) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les zones dont le nom commence par ce caractère
                if (tab.getNom_zone().toLowerCase().startsWith(keyword)) {
                    filteredTab.add(tab);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage et tri
                if (tab.getNom_zone().toLowerCase().contains(keyword) ||

                        String.valueOf(tab.getCapacit_t()).toLowerCase().contains(keyword)) {
                    filteredTab.add(tab);
                }
            }

        }

        // Si l'utilisateur n'a entré aucun caractère, afficher toutes les zones
        if (keyword.isEmpty()) {
            filteredTab.clear();
            filteredTab.addAll(displayedTable);

        }

        // Trier les zones filtrées par nom
        var comparator = Comparator.comparing(Tab::getNom_zone);
        FXCollections.sort(filteredTab, comparator);

        // Afficher les zones filtrées dans la TableView
        tabtable.setItems(filteredTab);

    }
}

