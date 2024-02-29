package gui;

import entities.Evenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listEvenementFrontController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listEvenementFront;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;

    private MyListener myListener;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceEvenement as = new ServiceEvenement();
            List<Evenement> act = as.Show();
            pag.setPageCount((int) Math.ceil(act.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre d'articles à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, act.size()); i++) {

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("listEvenementFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listEvenementCardFrontController itemController = fxmlLoader.getController();
                        itemController.setData(act.get(i), myListener);
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listEvenementController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
