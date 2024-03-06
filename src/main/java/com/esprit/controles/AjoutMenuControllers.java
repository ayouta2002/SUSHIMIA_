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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


import java.net.URL;
import java.util.ResourceBundle;
public class AjoutMenuControllers implements Initializable {

    @FXML
    private TextField ftdescriptionplat;

    @FXML
    private TextField ftnomplat;

    @FXML
    private TextField ftprixplat;

    @FXML
    private TextField ftquantiteplat;
    @FXML
    private TextField ftnomcategorie;
    @FXML
    private TextField suppid;
    @FXML
    private ImageView fimage;
    @FXML
    private TextField ftrecherche;
    @FXML
    private Button closebutton;


    @FXML
    private TableColumn<Plat, String> tvdes;

    @FXML
    private TableColumn<Plat, String> tvnomcategorie;

    @FXML
    private TableColumn<Plat, String> tvnom;

    @FXML
    private TableColumn<Plat, Integer> tvprix;

    @FXML
    private TableColumn<Plat, Integer> tvquantite;
    @FXML
    private TableColumn<Plat, Integer> tvimage;

    @FXML
    private TableColumn<Plat, Void> tvaction;
/*
    @FXML
    private TextField tfrecherche;*/

    /*@FXML
    private TableColumn<Plat, Integer> tvid;*/

    @FXML
    private TableView<Plat> tabplat;
    public String url_image;
    private String path;
    File selectedFile;
    private PlatService ps =new PlatService();
    private ObservableList<Plat> displayedPlat = FXCollections.observableArrayList(ps.afficher());



    private ObservableList<Plat> listplats = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableView();
        FillForm();
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
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath(😕"C:\Users\X\Desktop\ScreenShot.6.png"
                        fimage.setImage(new Image("file:" + file.getAbsolutePath()));
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

        fimage.setImage(new Image("file:C:\\Users\\linas\\OneDrive\\Bureau\\drag-drop.gif"));


    }

    @FXML
    void image_add(MouseEvent event) {

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", ".jpg", ".png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());

            //url_image = file.toURI().toString();
            System.out.println(selectedFile.toURI().toString());
            fimage.setImage(image1);

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

    }

    public void initializeTableView() {
        PlatService PlatServiceService = new PlatService(); // Create an instance of CategorieService
        List<Plat> allPlats = PlatServiceService.afficher(); // Call afficher() method on the instance

        // Rest of your code remains the same...
        ObservableList<Plat> displayedPlat = FXCollections.observableArrayList();
        displayedPlat.addAll(allPlats);
        tabplat.setItems(displayedPlat);
    }

    @FXML
    private void rafraichirTableView() {
        PlatService platService = new PlatService(); // Corrected service name
        List<Plat> platsList = platService.afficher(); // Corrected service name
        ObservableList<Plat> plats = FXCollections.observableArrayList(platsList);

        // Associer les propriétés des zones aux colonnes de la table view
       // tvid.setCellValueFactory(new PropertyValueFactory<>("id_plat"));
        tvnom.setCellValueFactory(new PropertyValueFactory<>("nom_plat"));
        tvdes.setCellValueFactory(new PropertyValueFactory<>("description_plat"));
        tvprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tvquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tvnomcategorie.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        tvimage.setCellValueFactory(new PropertyValueFactory<>("image"));

        tvaction.setCellFactory(param -> new TableCell<Plat, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    Plat plat = getTableView().getItems().get(getIndex());
                    // Logique de suppression de la zone
                    platService.supprimer(plat);
                    initializeTableView();
                });

                editButton.setOnAction(event -> {
                    Plat plat = getTableView().getItems().get(getIndex());
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
        tabplat.setItems(plats);
    }




    /*
    @FXML
    void AddPlat(ActionEvent event) throws IOException {
        PlatService ps = new PlatService();
        ps.ajouter(new Plat(ftnomplat.getText(), ftdescriptionplat.getText(),ftprixplat.getText(),ftquantiteplat.getText(),ftidcategorie.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("plat Ajouté");
        alert.setContentText("plat Ajouté !");
        alert.show();
       // rafraichirTableView();
    }
*/
@FXML
void AddPlat(ActionEvent event) throws IOException {
    try {
        // Convertir les chaînes de caractères en types appropriés (float pour le prix, int pour la quantité et l'ID de catégorie)
        float prix = Float.parseFloat(ftprixplat.getText());
        int quantite = Integer.parseInt(ftquantiteplat.getText());
       // int idCategorie = Integer.parseInt(ftnomcategorie.getText());

        // Créer un nouvel objet Plat en utilisant les valeurs converties
        Plat nouveauPlat = new Plat(
                ftnomplat.getText(),
                ftdescriptionplat.getText(),
                prix,
                quantite,
                ftnomcategorie.getText(),
                url_image
        );

        // Ajouter le nouveau plat en utilisant le service approprié (ps)
        PlatService ps = new PlatService();

            ps.ajouter(nouveauPlat);




        // Afficher une boîte de dialogue d'information pour confirmer l'ajout du plat
        Notifications.create()
                .darkStyle()
                .title( "Le plat a été ajouté avec succès !")
                .position(Pos.TOP_RIGHT) // Modifier la position ici
                .hideAfter(Duration.seconds(20))
                .show();
    } catch (NumberFormatException e) {
        // Gérer les erreurs de conversion de chaîne en nombre
        Notifications.create()
                .darkStyle()
                .title( "Veuillez saisir des valeurs numériques valides pour le prix, la quantité et l'ID de catégorie !")
                .position(Pos.TOP_RIGHT) // Modifier la position ici
                .hideAfter(Duration.seconds(20))
                .show();

    } catch (Exception e) {
        // Gérer toute autre exception inattendue
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'ajout du plat.");
        alert.showAndWait();
        e.printStackTrace();


    }
    rafraichirTableView();
    ftnomplat.clear();
    ftdescriptionplat.clear();
    ftprixplat.clear();
    ftquantiteplat.clear();
    ftnomcategorie.clear();


}
    @FXML
    void UpdateMenu(ActionEvent event) {
        String nomplatValue = ftnomplat.getText();
        String descriptionplatValue = ftdescriptionplat.getText();
        float prixplatValue = Float.parseFloat(ftprixplat.getText());
        int quantiteplatValue = Integer.parseInt(ftquantiteplat.getText());
        String nom_categorietValue = ftnomcategorie.getText();

        Plat TableSelection = tabplat.getSelectionModel().getSelectedItem();
        int idplat = TableSelection.getId_plat();


        if (nomplatValue.isEmpty() || descriptionplatValue.isEmpty() || prixplatValue==0 || quantiteplatValue==0 || nom_categorietValue.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs");
            return;
        }

        // Création d'une instance de Plat avec les nouvelles valeurs
        Plat nouvellesValeursPlat = new Plat(idplat, nomplatValue, descriptionplatValue, prixplatValue, quantiteplatValue,nom_categorietValue,url_image);

        PlatService platServiceService = new PlatService(); // Créez
        System.out.println(nomplatValue);
        System.out.println(descriptionplatValue);
        System.out.println(prixplatValue);
        System.out.println(quantiteplatValue);
        System.out.println(nom_categorietValue);

        platServiceService.modifier(nouvellesValeursPlat);
        rafraichirTableView();
        ftnomplat.clear();
        ftdescriptionplat.clear();
        ftprixplat.clear();
        ftquantiteplat.clear();
        ftnomcategorie.clear();



    }


    @FXML
    void DeletePlat(ActionEvent event) throws IOException{
        try {
            int platId = Integer.parseInt(suppid.getText());

            // Vérifier si l'ID de catégorie est valide
            if (platId <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Veuillez saisir un identifiant de plat valide.");
                alert.show();
                return; // Sortir de la méthode si l'ID de catégorie est invalide
            }

            // Appeler la méthode delete de votre service ou gestionnaire de données
            PlatService ps = new PlatService();
            ps.DeletePlat(platId);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("plat supprimée");
            alert.setContentText("Le plat a été supprimée avec succès !");
            alert.show();

            // Appeler la méthode pour rafraîchir votre TableView
            rafraichirTableView();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion du texte en entier
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez saisir un identifiant de plat valide.");
            alert.show();
            rafraichirTableView();
        }


    }
    private void FillForm(){
        // Ajoutez un événement de clic sur la TableView pour mettre à jour le formulaire avec les valeurs de la ligne sélectionnée
        tabplat.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifiez si un clic unique a été effectué
                // Obtenez la ligne sélectionnée
                Plat PlatSelectionne = tabplat.getSelectionModel().getSelectedItem();
                if (PlatSelectionne != null) {
                    // Mettez à jour le formulaire avec les valeurs de la ligne sélectionnée
                    ftnomplat.setText(PlatSelectionne.getNom_plat());
                    ftdescriptionplat.setText(PlatSelectionne.getDescription_plat());
                    ftprixplat.setText(String.valueOf(PlatSelectionne.getPrix()));
                    ftquantiteplat.setText(String.valueOf(PlatSelectionne.getQuantite()));
                    ftnomcategorie.setText(String.valueOf(PlatSelectionne.getNom_categorie()));


                    //ftcapacite.setText(ZonesSelectionne.getCapacity());
                    // Vous pouvez également mettre à jour d'autres champs du formulaire ici
                }
            }
        });}

    @FXML
    void retourplat(ActionEvent event) {
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
    void search(ActionEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = ftrecherche.getText().toLowerCase();

        ObservableList<Plat> filteredPlat = FXCollections.observableArrayList();

        // Parcourir tous les plats affichés dans la TableView
        for (Plat plat : displayedPlat) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les plats dont le nom commence par ce caractère
                if (plat.getNom_plat().toLowerCase().startsWith(keyword)) {
                    filteredPlat.add(plat);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage
                if (plat.getNom_plat().toLowerCase().contains(keyword) ||
                        plat.getDescription_plat().toLowerCase().contains(keyword) ||
                        String.valueOf(plat.getPrix()).contains(keyword) ||//
                        String.valueOf(plat.getQuantite()).contains(keyword) ||//
                        plat.getNom_categorie().toLowerCase().contains(keyword)) {

                    filteredPlat.add(plat);
                }
            }
        }

        // Si l'utilisateur n'a pas entré de texte de recherche, afficher tous les plats
        if (keyword.isEmpty()) {
            filteredPlat.clear();
            filteredPlat.addAll(displayedPlat);
        }

        // Trier les plats filtrés par nom s'il y en a
        if (!filteredPlat.isEmpty()) {
            var comparator = Comparator.comparing(Plat::getNom_plat);
            FXCollections.sort(filteredPlat, comparator);
        }

        // Afficher les plats filtrés dans la TableView
        tabplat.setItems(filteredPlat);
       // rafraichirTableView();

    }
    @FXML
    void searchkey(KeyEvent event) {
        // Récupérer le texte entré dans le champ de recherche
        String keyword = ftrecherche.getText().toLowerCase();

        ObservableList<Plat> filteredPlat = FXCollections.observableArrayList();

        // Parcourir tous les plats affichés dans la TableView
        for (Plat plat : displayedPlat) {
            // Vérifier si l'utilisateur a entré un seul caractère
            if (keyword.length() == 1) {
                // Si oui, filtrer les plats dont le nom commence par ce caractère
                if (plat.getNom_plat().toLowerCase().startsWith(keyword)) {
                    filteredPlat.add(plat);
                }
            } else {
                // Sinon, effectuer une recherche normale avec filtrage
                if (plat.getNom_plat().toLowerCase().contains(keyword) ||
                        plat.getDescription_plat().toLowerCase().contains(keyword) ||
                        String.valueOf(plat.getPrix()).contains(keyword) ||//
                        String.valueOf(plat.getQuantite()).contains(keyword) ||//
                        plat.getNom_categorie().toLowerCase().contains(keyword)) {

                    filteredPlat.add(plat);
                }
            }
        }

        // Si l'utilisateur n'a pas entré de texte de recherche, afficher tous les plats
        if (keyword.isEmpty()) {
            filteredPlat.clear();
            filteredPlat.addAll(displayedPlat);
        }

        // Trier les plats filtrés par nom s'il y en a
        if (!filteredPlat.isEmpty()) {
            var comparator = Comparator.comparing(Plat::getNom_plat);
            FXCollections.sort(filteredPlat, comparator);
        }

        // Afficher les plats filtrés dans la TableView
        tabplat.setItems(filteredPlat);
        // rafraichirTableView();

    }

    private void resetFormulaire() {
        ftnomplat.setText("");
        ftdescriptionplat.setText("");
        ftprixplat.setText("");
        ftquantiteplat.setText("");
       // fimage.setText("");

    }
    @FXML
    void closeplat(ActionEvent event) {
        Stage stage = (Stage) closebutton.getScene().getWindow();

        stage.close();

    }



}
