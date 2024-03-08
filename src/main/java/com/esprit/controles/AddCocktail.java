package com.esprit.controles;

import com.esprit.models.Cocktails;
import com.esprit.models.Ingredients;
import com.esprit.services.Cocktailsservice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.FloatStringConverter;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCocktail implements Initializable {

    @FXML
    private TextField AddDescriptionCo, AddNameCO, AddPriceCO;
    @FXML
    private ComboBox<String> AddCategoryCO;
    @FXML
    private ComboBox<Boolean> AddTypeCO;


    @FXML
    private TableView<Cocktails> TableCocktail;
    @FXML
    private TableColumn<Cocktails, String> colum_Description, colum_Name;
    @FXML
    private TableColumn<Cocktails, Integer> colum_Id_cocktail;
    @FXML
    private TableColumn<Cocktails, Float> colum_Price;
    @FXML
    private TableColumn<Cocktails, Void> colum_action;
    @FXML
    private TableColumn<Cocktails, String> colum_Category, colum_Imageurl;
    @FXML
    private TableColumn<Cocktails, Boolean> colum_Type;
    @FXML
    private TableColumn<Cocktails, Integer> colum_Id_creator;
    @FXML
    private Label username1;
    @FXML
    private TextField recherche_cocktail;

    @FXML
    private ImageView infoIconImageView;
    private Cocktailsservice service = new Cocktailsservice();
    private ObservableList<Cocktails> cocktailDataTable = FXCollections.observableArrayList();

    private String[] categoryList = {"Italian Cocktail", "Japanese Cocktail"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cocktailCategoryList();
        cocktailTypeList();
        displayUserName();
        initTableCocktail();
        Tooltip infoTooltip = new Tooltip("Si 'True', le cocktail est standard. Si 'False', le cocktail est personnalisé.");
        Tooltip.install(infoIconImageView, infoTooltip);


    }

    public void cocktailCategoryList() {
        ObservableList<String> categoryL = FXCollections.observableArrayList(categoryList);
        AddCategoryCO.setItems(categoryL);
    }

    public void cocktailTypeList() {
        ObservableList<Boolean> typeL = FXCollections.observableArrayList(Boolean.FALSE, Boolean.TRUE);
        AddTypeCO.setItems(typeL);
    }

    public void AjouterCocktailT(ActionEvent actionEvent) {
        try {
            String nom = AddNameCO.getText();
            String categorie = AddCategoryCO.getValue();
            String description = AddDescriptionCo.getText();
            boolean estPersonnalise = AddTypeCO.getValue();
            float prix = Float.parseFloat(AddPriceCO.getText());
            String image_url = "url_image_par_defaut";
            int idCreateur = 1; // Idéalement, cet identifiant doit être récupéré dynamiquement ou fourni par le contexte de l'application.

            Cocktails cocktail = new Cocktails(nom, categorie, description, estPersonnalise, prix, image_url, idCreateur);
            service.ajouter(cocktail);
            refreshTable();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format: " + e.getMessage());
        }
    }

    private void initTableCocktail() {
        TableCocktail.setEditable(true);

        colum_Id_cocktail.setCellValueFactory(new PropertyValueFactory<>("id_cocktail"));

        //colum_name editable
        colum_Name.setCellValueFactory(new PropertyValueFactory<>("nom_cocktail"));
        colum_Name.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_Name.setOnEditCommit(event -> {
            Cocktails cocktail = event.getRowValue();
            cocktail.setNom_cocktail(event.getNewValue());
            service.modifier(cocktail);
            refreshTable();
        });
        colum_Description.setCellValueFactory(new PropertyValueFactory<>("description_cocktail"));
        colum_Description.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_Description.setOnEditCommit(event -> {
            Cocktails cocktail = event.getRowValue();
            cocktail.setDescription_cocktail(event.getNewValue());
            service.modifier(cocktail);
            refreshTable();
        });
        colum_Price.setCellValueFactory(new PropertyValueFactory<>("prix_cocktail"));
        colum_Price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        colum_Price.setOnEditCommit(event -> {
            Cocktails cocktail = event.getRowValue();
            cocktail.setPrix_cocktail(event.getNewValue());
            service.modifier(cocktail);
            refreshTable();
        });

        colum_Category.setCellValueFactory(new PropertyValueFactory<>("categorie_cocktail"));
        colum_Type.setCellValueFactory(new PropertyValueFactory<>("est_personnalise"));
    // Configuration de la colonne "Image URL"
        colum_Imageurl.setCellValueFactory(new PropertyValueFactory<>("image_url"));
        colum_Imageurl.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_Imageurl.setOnEditCommit(event -> {
            Cocktails cocktail = event.getRowValue();
            cocktail.setImage_url(event.getNewValue());
            service.modifier(cocktail);
            refreshTable();
        });


        // Configuration de la colonne "ID Créateur"
        colum_Id_creator.setCellValueFactory(new PropertyValueFactory<>("idCreateur"));
        // Ajoute les gestionnaires d'événements pour le commit des modifications ici, si nécessaire.

        setupActionColumn();
        refreshTable();
    }

    private void setupActionColumn() {
        colum_action.setCellFactory(param -> new TableCell<Cocktails, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Cocktails cocktail = getTableView().getItems().get(getIndex());
                    service.supprimer(cocktail);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(deleteButton));
            }
        });
    }

    private void refreshTable() {
        cocktailDataTable.setAll(service.afficher());
        TableCocktail.setItems(cocktailDataTable);
    }

    public void displayUserName() {
        String user = "mahdi";
        username1.setText(user);
    }

    public void AfficheInterfaceIngredient(ActionEvent actionEvent) {
        try {
            Parent interfaceGra = FXMLLoader.load(getClass().getResource("/AddIngredient.fxml"));
            username1.getScene().setRoot(interfaceGra);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'interface des ingrédients: " + e.getMessage());
        }
    }
}
