package com.esprit.controles;

import com.esprit.models.CategorieMenu;
import com.esprit.services.CategorieService;
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
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;


public class AjoutCategorieControllers implements Initializable {

    @FXML
    private TextField ftdescriptioncategorie;

    @FXML
    private TextField ftnomcategorie;

    @FXML
    private TextField supp_ID;
    @FXML
    private ImageView fimage;
    @FXML
    private Button closebutton;


    @FXML
    private TableColumn<CategorieMenu,String > cdescription;

    @FXML
    private TableColumn<CategorieMenu, Integer > cid;

    @FXML
    private TableColumn<CategorieMenu, String> cnom;

    @FXML
    private TableView<CategorieMenu> tabcategorie;
    public String url_image;
    private String path;
    File selectedFile;
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
          /* fimage.setOnDragOver(new EventHandler<DragEvent>() {
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
                            fimage.setImage_categorie(new Image("file:" + file.getAbsolutePath()));
                            File destinationFile = new File("C:\\xampp\\htdocs\\imageplat\\" + file.getName());
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

            fimage.setImage_categorie(new Image("file:C:\\Users\\linas\\OneDrive\\Bureau\\drag-drop.gif"));*/


        }

   /* @FXML
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
            fimage.setImage_categorie(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\imageplat\\" + selectedFile.getName());
            url_image = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }*/

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
        // Associer la liste observable à la table view
        tabcategorie.setItems(zones);
    }

    @FXML
    void addcategorie(ActionEvent event) throws IOException {
        CategorieService cs = new CategorieService();
        String image =url_image;
        cs.ajouter(new CategorieMenu(ftnomcategorie.getText(), ftdescriptioncategorie.getText(),url_image));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("categorie Ajoutée");
        alert.setContentText("categorie Ajoutée !");
        alert.show();
        rafraichirTableView();
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

    }



