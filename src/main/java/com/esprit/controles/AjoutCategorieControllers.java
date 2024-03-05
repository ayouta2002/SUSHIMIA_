package com.esprit.controles;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Plat;
import com.esprit.services.CategorieService;
import com.esprit.services.PlatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


public class AjoutCategorieControllers implements Initializable {

    @FXML
    private TextField ftdescriptioncategorie;

    @FXML
    private TextField ftnomcategorie;
    @FXML
    private TextField chercher;

    @FXML
    private TextField supp_ID;
    @FXML
    private ImageView fimage;
    @FXML
    private Button closebutton;
    @FXML
    private TableColumn<CategorieMenu,String> colimage;

    @FXML
    private TableColumn<CategorieMenu,String > cdescription;

    @FXML
    private TableColumn<CategorieMenu, Integer > cid;

    @FXML
    private TableColumn<CategorieMenu, String> cnom;
    @FXML
    private TableColumn<CategorieMenu, Float> taction;



    @FXML
    private TableView<CategorieMenu> tabcategorie;

    public String url_image;
    private String path;
    File selectedFile;
    private CategorieMenu cs =new CategorieMenu();
    private ObservableList<CategorieMenu> displayedCategorie = FXCollections.observableArrayList(new CategorieService().afficher());

    private ObservableList<CategorieMenu> listcategories = FXCollections.observableArrayList();



/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageCategorie.fxml"));
        Parent root = loader.load();
        ftnomcategorie.getScene().setRoot(root);

        AffichageCategorieControllers apc = loader.getController();
        //apc.setLbzone_id(ftzone_id.getText());
        apc.setlbnomcategorie(ftnomcategorie.getText());
        apc.setlbdescriptioncategorie(ftdescriptioncategorie.getText());

      */

    //ObservableList<CategorieMenu> listcategories = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            rafraichirTableView();
            initializeTableView();
            FillForm();
            tabcategorie.setEditable(true);


            cnom.setCellFactory(TextFieldTableCell.<CategorieMenu>forTableColumn());
            cdescription.setCellFactory(TextFieldTableCell.<CategorieMenu>forTableColumn());


            modifiertable();
            fimage.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    if (db.hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    } else {
                        event.consume();
                    }
                }
            });

            fimage.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasFiles()) {
                        success = true;
                        path = null;
                        for (File file : db.getFiles()) {
                            url_image = file.getName();
                            selectedFile = new File(file.getAbsolutePath());
                            System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath("C:\Users\X\Desktop\ScreenShot.6.png"
                            fimage.setImage(new Image("file:" + file.getAbsolutePath()));
                            File destinationFile = new File("C:\\xampp\\htdocs\\image_categorie\\" + file.getName());
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

            fimage.setImage(new Image("file:C:\\Users\\linas\\OneDrive\\Bureau\\drag-drop.gif"));


        }

   @FXML
    void image_add(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());
            System.out.println(selectedFile.toURI().toString());
            fimage.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\image_categorie\\" + selectedFile.getName());
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
        CategorieService categorieService = new CategorieService(); // Create an instance of CategorieService
        List<CategorieMenu> allCategories = categorieService.afficher(); // Call afficher() method on the instance

        // Rest of your code remains the same...
        ObservableList<CategorieMenu> displayedCategorie = FXCollections.observableArrayList();
        displayedCategorie.addAll(allCategories);
        tabcategorie.setItems(displayedCategorie);
    }

    @FXML
    private void rafraichirTableView() {
        /*CategorieService categorieService = new CategorieService();
        List<CategorieMenu> zonesList = categorieService.afficher();

        // Clear the existing list and add the new data
        listcategories.clear();
        listcategories.addAll(zonesList);

        // Associer les propriétés des zones aux colonnes de la table view
        cid.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        cdescription.setCellValueFactory(new PropertyValueFactory<>("description_categorie"));

        // Associer la liste observable à la table view
        tabcategorie.setItems(listcategories);*/
        CategorieService categorieService = new CategorieService();
        List<CategorieMenu> zonesList = categorieService.afficher();
        ObservableList<CategorieMenu> zones = FXCollections.observableArrayList(zonesList);

        // Associer les propriétés des zones aux colonnes de la table view
        cid.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        cdescription.setCellValueFactory(new PropertyValueFactory<>("description_categorie"));
        colimage.setCellValueFactory(new PropertyValueFactory<>("image_categorie"));
        taction.setCellFactory(param -> {
            return new TableCell<CategorieMenu, Float>() {
                private final Button deleteButton = new Button("Supprimer");
                private final Button editButton = new Button("Modifier");

                {
                    deleteButton.setOnAction(event -> {
                        CategorieMenu categorieMenu = getTableView().getItems().get(getIndex());
                        // Logique de suppression de la zone
                        categorieService.supprimer(categorieMenu);
                        initializeTableView();
                    });

                    editButton.setOnAction(event -> {
                        CategorieMenu categorieMenu = getTableView().getItems().get(getIndex());
                        // Logique de modification de la zone
                        // showEditDialog(zone);
                    });
                }

                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);


                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(deleteButton);
                        hbox.setSpacing(10);
                        setGraphic(hbox);
                    }
                }
            };
        });

        // tabzone.getColumns().add(actioncol);
        // Associer la liste observable à la table view*/
        tabcategorie.setItems(zones);
    }
    public void modifiertable() {
        cnom.setOnEditCommit(event -> {
            // Obtenez la réservation à partir de l'événement
            CategorieMenu categorieMenu = event.getRowValue();
            // Mettez à jour la zone avec la nouvelle valeur
            categorieMenu.setNom_categorie(event.getNewValue());
            // Appelez la méthode de modification de votre service (remplacez ReservationService par le nom réel de votre classe de service)
            CategorieService categorieService = new CategorieService();
            categorieService.modifier(categorieMenu);
        });
        cdescription.setOnEditCommit(event -> {
            // Obtenez la réservation à partir de l'événement
            CategorieMenu categorieMenu = event.getRowValue();
            // Mettez à jour la zone avec la nouvelle valeur
            categorieMenu.setDescriprtion_categorie(event.getNewValue());
            // Appelez la méthode de modification de votre service (remplacez ReservationService par le nom réel de votre classe de service)
            CategorieService categorieService = new CategorieService();
            categorieService.modifier(categorieMenu);
        });

    }
    @FXML
    void addcategorie(ActionEvent event) throws SQLException {
        // Récupérer les valeurs des champs de texte
        String nomCategorie = ftnomcategorie.getText().trim();
        String descriptionCategorie = ftdescriptioncategorie.getText().trim();

        // Vérifier si les champs sont vides ou contiennent des caractères non autorisés
        if (nomCategorie.isEmpty() || descriptionCategorie.isEmpty() || selectedFile == null || !estChaineValide(nomCategorie) || !estChaineValide(descriptionCategorie)) {
            // Afficher une alerte en cas de champ vide ou de caractères non autorisés
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs avec des caractères valides.");
            alert.show();
            return; // Sortir de la méthode si la saisie est incorrecte
        }

        // Si tous les champs sont remplis avec des caractères valides, continuer avec l'ajout
        CategorieService cs = new CategorieService();
        String image = url_image;
        CategorieMenu categorieMenu = new CategorieMenu(nomCategorie, descriptionCategorie, selectedFile.getName());
        cs.ajouter(categorieMenu);

        // Afficher une alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Catégorie ajoutée");
        alert.setContentText("Catégorie ajoutée avec succès !");
        alert.show();

        // Rafraîchir la table view ou toute autre opération nécessaire
        rafraichirTableView();
        ftnomcategorie.clear();
        ftdescriptioncategorie.clear();
    }

    // Méthode pour vérifier si une chaîne contient uniquement des lettres
    private boolean estChaineValide(String chaine) {
        return chaine.matches("[a-zA-Z]+");
    }



    @FXML
    void DeleteCategorie(ActionEvent event) throws IOException {
        /*CategorieService cs = new CategorieService();
        cs.delete(Integer.parseInt(supp_ID.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("categorie Supprimée");
        alert.setContentText("categorie Supprimée !");
        alert.show();
        rafraichirTableView();*/
        try {
            int categorieId = Integer.parseInt(supp_ID.getText());

            // Vérifier si l'ID de catégorie est valide
            if (categorieId <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Veuillez saisir un identifiant de catégorie valide.");
                alert.show();
                return; // Sortir de la méthode si l'ID de catégorie est invalide
            }

            // Appeler la méthode delete de votre service ou gestionnaire de données
            CategorieService cs = new CategorieService();
            cs.delete(categorieId);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Catégorie supprimée");
            alert.setContentText("La catégorie a été supprimée avec succès !");
            alert.show();

            // Appeler la méthode pour rafraîchir votre TableView
            rafraichirTableView();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion du texte en entier
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir un identifiant de catégorie valide.");
            alert.show();
        }

    }



@FXML
void UpdateCategorie(ActionEvent event) {
    String nomcategorieValue = ftnomcategorie.getText();
    String descriptioncategorieValue = ftdescriptioncategorie.getText();
    CategorieMenu TableSelection = tabcategorie.getSelectionModel().getSelectedItem();
    int idcat = TableSelection.getId_categorie();
String imagevalue = url_image;

    if (nomcategorieValue.isEmpty() || descriptioncategorieValue.isEmpty() ) {
        System.out.println("Veuillez remplir tous les champs");
        return;
    }

    CategorieMenu nouvellesValeursCategorie = new CategorieMenu(idcat, nomcategorieValue, descriptioncategorieValue,url_image);
    CategorieService categorieServiceService = new CategorieService(); // Créez
    System.out.println(nomcategorieValue);
    System.out.println(descriptioncategorieValue);
    categorieServiceService.modifier(nouvellesValeursCategorie);
    rafraichirTableView();
    ftnomcategorie.clear();
    ftdescriptioncategorie.clear();


    }

    @FXML
    void Close(ActionEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();

        stage.close();
    }


    private void FillForm(){
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabcategorie.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                CategorieMenu CategorieMenuSelectionne = tabcategorie.getSelectionModel().getSelectedItem();
                if (CategorieMenuSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée
                    ftnomcategorie.setText(CategorieMenuSelectionne.getNom_categorie());
                    ftdescriptioncategorie.setText(CategorieMenuSelectionne.getDescription_categorie());


                    //ftcapacite.setText(ZonesSelectionne.getCapacity());
                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });}

    @FXML
    void retourcategorie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void searchkey(KeyEvent event) {

        // Récupérer le texte entré dans le champ de recherche
        String keyword = chercher.getText().toLowerCase();

        ObservableList<CategorieMenu> filteredCategorie = FXCollections.observableArrayList();

        // Parcourir tous les plats affichés dans la TableView
        for (CategorieMenu categorieMenu : displayedCategorie) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les plats dont le nom commence par ce caractère
                if (categorieMenu.getNom_categorie().toLowerCase().startsWith(keyword)) {
                    filteredCategorie.add(categorieMenu);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage
                if (categorieMenu.getNom_categorie().toLowerCase().contains(keyword) ||
                        categorieMenu.getDescription_categorie().toLowerCase().contains(keyword))
                        {

                            filteredCategorie.add(categorieMenu);
                }
            }
        }

        // Si l'utilisateur n'a pas entré de texte de recherche, afficher tous les plats
        if (keyword.isEmpty()) {
            filteredCategorie.clear();
            filteredCategorie.addAll(displayedCategorie);
        }

        // Trier les plats filtrés par nom s'il y en a
        if (!filteredCategorie.isEmpty()) {
            var comparator = Comparator.comparing(CategorieMenu::getNom_categorie);
            FXCollections.sort(filteredCategorie, comparator);
        }

        // Afficher les plats filtrés dans la TableView
        tabcategorie.setItems(filteredCategorie);
        // rafraichirTableView();

    }

    private void resetFormulaire() {
        ftnomcategorie.setText("");
        ftdescriptioncategorie.setText("");

        // fimage.setText("");

    }

    }



