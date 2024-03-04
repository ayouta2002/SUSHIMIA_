package com.esprit.controles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.models.CategorieMenu;
import com.esprit.services.CategorieService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemCategorieControllers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Nom_zone;

    @FXML
    private Label description;

    @FXML
    private ImageView img;

    private MyListener myListener;
    private CategorieMenu re;
    static CategorieMenu r = new CategorieMenu();
    private int id;

    public void setData(int id_categorie, String nom_categorie, String description_categorie, String image_categorie, MyListener myListener) {

        this.id = id_categorie;
        this.myListener = myListener;
        Nom_zone.setText(nom_categorie);
        description.setText(description_categorie);
        String fullurl = "C:\\xampp\\htdocs\\image_trippie_reclamation\\" + image_categorie;

        try {
            img.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }
    }

    public void onClick() {
        myListener.onClick(re);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    void Click(MouseEvent event) {

        CategorieService rc = new CategorieService();
        List<CategorieMenu> L = new ArrayList<>();
        myListener.onClick(re);
        //covt.setId_co(Integer.parseInt(id_co.getText()));
        L = rc.rechCategorie(id);
        r.setId_categorie(L.get(0).getId_categorie());
        r.setNom_categorie(L.get(0).getNom_categorie());
        r.setDescriprtion_categorie(L.get(0).getDescription_categorie());
        r.setImage_categorie(L.get(0).getImage_categorie());
    }

    @FXML
    void initialize() {
    }

    public interface MyListener {

        void onClick(CategorieMenu re);
    }

}
