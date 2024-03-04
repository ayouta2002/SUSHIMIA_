package com.esprit.controles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.models.CategorieMenu;
import com.esprit.services.CategorieService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AfficherCtegorieControllers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Description;

    @FXML
    private TextField Nom_plat;

    @FXML
    private VBox chosen;

    @FXML
    private TextField des_plat;

    @FXML
    private ChoiceBox<?> filtreOptions;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchInput;

    private List<CategorieMenu> recDataList = FXCollections.observableArrayList();
    private CategorieService rec = new CategorieService();
    private ItemCategorieControllers.MyListener myListener;

    @FXML
    void initialize() throws IOException {
        System.out.println("hello");
        recDataList.addAll(rec.afficher());
        System.out.println("load data");
        if (recDataList.size() > 0) {
            setChosenRec(recDataList.get(0));
            myListener = new ItemCategorieControllers.MyListener() {
                @Override
                public void onClick(CategorieMenu re) {
                    System.out.println("mouse clicked");
                    setChosenRec(re);
                }
            };
        }

        int column = 0;
        int row = 3;
        for (int i = 0; i < recDataList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemCategorie.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemCategorieControllers item = fxmlLoader.getController();
                item.setData(recDataList.get(i).getId_categorie(), recDataList.get(i).getNom_categorie(), recDataList.get(i).getDescription_categorie(), recDataList.get(i).getImage_categorie(), myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
        }
    }

    private void setChosenRec(CategorieMenu r) {

        Nom_plat.setText(ItemCategorieControllers.r.getNom_categorie());
        des_plat.setText(ItemCategorieControllers.r.getDescription_categorie());
        String imagePath = "C:\\xampp\\htdocs\\image_trippie_reclamation\\" + ItemCategorieControllers.r.getImage_categorie();
        try {
            img.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

    }

    @FXML
    void afficher_menu(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/AfficherMenu.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
