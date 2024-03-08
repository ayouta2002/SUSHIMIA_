package com.esprit.controles;

import com.esprit.models.Ingredients;
import com.esprit.services.Ingredientsservice;
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
import javafx.util.Callback;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddIngredient implements Initializable {
    @FXML
    private TextField AddCategorieIN, AddNameIN, AddPriceIN, AddStatusIN, AddStockIN, AddUniteIN;
    @FXML
    private Button AddINbtn, ImportINbtn, button_cocktail, button_customre, button_dashboard, button_ingredient;
    @FXML
    private TableView<Ingredients> TableIngredient;
    @FXML
    private TableColumn<Ingredients, String> colum_categorie, colum_name, colum_unite, colum_status;
    @FXML
    private TableColumn<Ingredients, Integer> colum_id_ingredient , colum_stock;
    @FXML
    private TableColumn<Ingredients, Float> colum_price;
    @FXML
    private TableColumn<Ingredients, Void> colum_action;
    @FXML
    private Label username;

    private Ingredientsservice service = new Ingredientsservice();
    private ObservableList<Ingredients> ingredientDataTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUserName ();
        initTableIngredient();
        TableIngredient.setEditable(true);
    }

    private void initTableIngredient() {
        // Rendre la colonne "ID d'ingrédient" non éditable
        colum_id_ingredient.setCellValueFactory(new PropertyValueFactory<>("id_ingredient"));

        // Rendre les autres colonnes éditables
        colum_categorie.setCellValueFactory(new PropertyValueFactory<>("categorie_ingredient"));
        colum_categorie.setCellFactory(TextFieldTableCell.forTableColumn()); // Rendre la colonne éditable
        colum_categorie.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setCategorie_ingredient(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        colum_name.setCellValueFactory(new PropertyValueFactory<>("nom_ingredient"));
        colum_name.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_name.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setNom_ingredient(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        colum_stock.setCellValueFactory(new PropertyValueFactory<>("quantite_ingredient"));
        colum_stock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colum_stock.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setQuantite_ingredient(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        colum_unite.setCellValueFactory(new PropertyValueFactory<>("unite_mesure"));
        colum_unite.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_unite.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setUnite_mesure(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        colum_price.setCellValueFactory(new PropertyValueFactory<>("prix_ingredient"));
        colum_price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        colum_price.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setPrix_ingredient(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        colum_status.setCellValueFactory(new PropertyValueFactory<>("status_ingredient"));
        colum_status.setCellFactory(TextFieldTableCell.forTableColumn());
        colum_status.setOnEditCommit(event -> {
            Ingredients ingredient = event.getRowValue();
            ingredient.setStatus_ingredient(event.getNewValue());
            service.modifier(ingredient);
            refreshTable();
        });

        setupActionColumn();

        refreshTable();
    }

    private void setupActionColumn() {
        colum_action.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");
            private final HBox pane = new HBox( deleteButton);

            {

                deleteButton.setOnAction(event -> {
                    Ingredients ingredient = getTableView().getItems().get(getIndex());
                    service.supprimer(ingredient);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }
    public void displayUserName ()
    {
        String user = "mahdi" ;
        username.setText(user);

    }

    @FXML
    void AjouterIngredientT(ActionEvent event) {
        try {
            String categorie = AddCategorieIN.getText();
            String nom = AddNameIN.getText();
            int quantite = Integer.parseInt(AddStockIN.getText());
            String unite = AddUniteIN.getText();
            float prix = Float.parseFloat(AddPriceIN.getText());
            String status = AddStatusIN.getText();

            Ingredients ingredient = new Ingredients( categorie, nom, quantite, unite, prix, status);
            service.ajouter(ingredient);
            refreshTable();
            clearFields();
        } catch (Exception e) {
            // Gérer l'exception
            System.out.println("Erreur lors de l'ajout de l'ingrédient: " + e.getMessage());
        }
    }

    private void refreshTable() {
        ingredientDataTable.setAll(service.afficher());
        TableIngredient.setItems(ingredientDataTable);
    }

    private void clearFields() {
        AddCategorieIN.clear();
        AddNameIN.clear();
        AddStockIN.clear();
        AddUniteIN.clear();
        AddPriceIN.clear();
        AddStatusIN.clear();
    }


    public void INGREDIENNT(ActionEvent actionEvent) {


    }

    public void afficherInterfaceCocktail(ActionEvent actionEvent) {
        try{
            Parent interfaceGra =  FXMLLoader.load(getClass().getResource("/AddCocktail.fxml"));
            username.getScene().setRoot(interfaceGra);
        }catch (Exception ignored){
            System.out.println(ignored.getMessage());
        }
    }


}
