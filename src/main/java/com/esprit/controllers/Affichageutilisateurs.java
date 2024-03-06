package com.esprit.controllers;

import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.esprit.models.Utilisateurs;
import com.esprit.services.ServiceUtilisateurs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.control.cell.TextFieldTableCell;

import static java.lang.Integer.parseInt;

public class Affichageutilisateurs implements Initializable {
    public ImageView PdfImage;
    public ImageView ExcelImage;
    @FXML
    private ComboBox<String> FilterComboBox;
    @FXML
    private TextField searchinput;

    @FXML
    private TableColumn<Utilisateurs, String> Email;
    @FXML
    private TableColumn<Utilisateurs, Void> actionCol;

    @FXML
    private TableColumn<Utilisateurs, String> Nom;

    @FXML
    private TableColumn<Utilisateurs, String> Prenom;

    @FXML
    private TableColumn<Utilisateurs, String> Role;

    @FXML
    private TableView<Utilisateurs> tabutilisateurs;

    private ServiceUtilisateurs su = new ServiceUtilisateurs();

    private ObservableList<Utilisateurs> displayedUtilisateurs = FXCollections.observableArrayList(su.readAll());

    @FXML
    public void initializeTableViewT() {
        List<Utilisateurs> allUtilisateurs = su.readAll();

        // Créer une observable list pour les utilisateurs affichées dans la table
        ObservableList<Utilisateurs> displayedUtilisateurs = FXCollections.observableArrayList();

        // Ajouter toutes les utilisateurs à la liste observable
        displayedUtilisateurs.addAll(allUtilisateurs);

        // Associer la liste observable à la table view
        tabutilisateurs.setItems(displayedUtilisateurs);

        PdfImage.setOnMouseClicked(event -> {
            try {
                System.out.println("Exported");
                OnExport(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ExcelImage.setOnMouseClicked(event -> {
            try {
                System.out.println("Exported");
                OnExportExcel(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void rafraichirTableView() {
        tabutilisateurs.setEditable(true);
        ServiceUtilisateurs su = new ServiceUtilisateurs();
        List<Utilisateurs> utilisateursList = su.readAll();
        ObservableList<Utilisateurs> utilisateurs = FXCollections.observableArrayList(utilisateursList);

        // Associer les propriétés des utilisateurs aux colonnes de la table view
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Créer la colonne "Action"
        actionCol.setCellFactory(param -> new TableCell<Utilisateurs, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Utilisateurs utilisateur = getTableView().getItems().get(getIndex());
                    // Logique de suppression de l'utilisateur
                    su.delete(utilisateur);
                    initializeTableViewT();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Ajouter la colonne "Action" à la table view
        tabutilisateurs.getColumns().add(actionCol);

        // Associer la liste observable à la table view
        tabutilisateurs.setItems(utilisateurs);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableViewT();
        tabutilisateurs.setEditable(true);
        populateFilterComboBox();
        Nom.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Prenom.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Email.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());
        Role.setCellFactory(TextFieldTableCell.<Utilisateurs>forTableColumn());

        modifier();

    }

    private void populateFilterComboBox() {
// Create a list to hold the filter options
        List<String> filters = new ArrayList<>();

// Add the filter options to the list
        filters.add("nom");
        filters.add("prenom");
        filters.add("email");
        filters.add("role");

// Create an observable list from the filters list
        ObservableList<String> filtersObservable = FXCollections.observableArrayList(filters);

// Set the items of the ComboBox to the observable list
        FilterComboBox.setItems(filtersObservable);

    }

    public void modifier() {
        Nom.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setNom(event.getNewValue());
            su.update(utilisateurs);
        });
        Prenom.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setPrenom(event.getNewValue());
            su.update(utilisateurs);
        });
        Email.setOnEditCommit(event -> {
            Utilisateurs utilisateurs = event.getRowValue();
            utilisateurs.setEmail(event.getNewValue());
            su.update(utilisateurs);
        });
    }

    @FXML
    void recherche(ActionEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = searchinput.getText().toLowerCase();

        // Créer une liste pour stocker les utilisateurs filtrés
        ObservableList<Utilisateurs> filteredUtilisateurs = FXCollections.observableArrayList();

        // Parcourir toutes les utilisateurs affichées dans la TableView
        for (Utilisateurs utilisateur : displayedUtilisateurs) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les utilisateurs dont le nom commence par ce caractère
                if (utilisateur.getNom().toLowerCase().startsWith(keyword)) {
                    filteredUtilisateurs.add(utilisateur);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage et tri
                if (utilisateur.getNom().toLowerCase().contains(keyword) ||
                        utilisateur.getPrenom().toLowerCase().contains(keyword) ||
                        utilisateur.getEmail().toLowerCase().contains(keyword)) {
                    filteredUtilisateurs.add(utilisateur);
                }
            }
        }
        // Si l'utilisateur n'a entré aucun caractère, afficher tous les utilisateurs
        if (keyword.isEmpty()) {
            filteredUtilisateurs.addAll(displayedUtilisateurs);
        }
        // Trier les utilisateurs filtrés par nom
        var comparator = Comparator.comparing(Utilisateurs::getNom);
        FXCollections.sort(filteredUtilisateurs, comparator);
        // Afficher les utilisateurs filtrés dans la TableView
        tabutilisateurs.setItems(filteredUtilisateurs);
    }

    public void OnAdd(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajoututilisateurs.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = new Stage();
        currentStage.close();
    }

    @FXML
    public void OnSort(ActionEvent actionEvent) {
        // Get the selected filter from the ComboBox
        String selectedFilter = FilterComboBox.getValue();

        // Sort the table based on the selected filter
        Comparator<Utilisateurs> comparator = null;
        switch (selectedFilter) {
            case "nom":
                comparator = Comparator.comparing(Utilisateurs::getNom);
                break;
            case "prenom":
                comparator = Comparator.comparing(Utilisateurs::getPrenom);
                break;
            case "email":
                comparator = Comparator.comparing(Utilisateurs::getEmail);
                break;
            case "role":
                comparator = Comparator.comparing(Utilisateurs::getRole);
                break;
            default:
                // Default to sorting by nom if no valid filter is selected
                comparator = Comparator.comparing(Utilisateurs::getNom);
                break;
        }

        ObservableList<Utilisateurs> displayedUsers = tabutilisateurs.getItems();
        FXCollections.sort(displayedUsers, comparator);
        tabutilisateurs.setItems(displayedUsers);
    }
    public void OnExportExcel(ActionEvent actionEvent) throws IOException {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Utilisateurs");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Nom", "Prenom", "Email"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Create data rows
        int rowNum = 1;
        for (Utilisateurs utilisateur : displayedUtilisateurs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(utilisateur.getNom());
            row.createCell(1).setCellValue(utilisateur.getPrenom());
            row.createCell(2).setCellValue(utilisateur.getEmail());
        }

        // Write the workbook to a file
        String excelFilePath = "utilisateurs.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }

        // Close the workbook
        workbook.close();
    }
    public void OnExport(ActionEvent actionEvent) throws IOException {
        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Add a page to the document
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a content stream for adding content to the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define the parameters for the cards
        float cardWidth = 200f;
        float cardHeight = 100f;
        float cardMargin = 20f;
        float startX = 50f;
        float startY = page.getMediaBox().getHeight() - 50f;
        // Add title to the document
        String title = "Utilisateurs Report";
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000f * 16;
        contentStream.beginText();
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, startY+20f);
        contentStream.showText(title);
        contentStream.endText();
        // Loop through each user and create a card for them
        for (Utilisateurs utilisateur : displayedUtilisateurs) {
            // Draw the card outline
            contentStream.addRect(startX, startY - cardHeight, cardWidth, cardHeight);
            contentStream.stroke();

            // Add the user information to the card
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX + cardMargin, startY - cardMargin);
            contentStream.showText("Nom: " + utilisateur.getNom());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Prenom: " + utilisateur.getPrenom());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Email: " + utilisateur.getEmail());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Role: " + utilisateur.getRole());
            contentStream.endText();

            // Move to the next card position
            startX += cardWidth + cardMargin;
            if (startX + cardWidth + cardMargin > page.getMediaBox().getWidth() - 50f) {
                startX = 50f;
                startY -= cardHeight + cardMargin;
            }

            // If we reached the end of the page, add a new page
            if (startY - cardHeight < 50f) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                startY = page.getMediaBox().getHeight() - 50f;
            }
        }

        // Close the content stream
        contentStream.close();

        // Save the document
        document.save("utilisateurs.pdf");

        // Close the document
        document.close();
    }




}