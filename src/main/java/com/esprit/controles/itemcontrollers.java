package com.esprit.controles;



import com.esprit.models.Plat;
import com.esprit.services.PlatService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;



public class itemcontrollers implements Initializable {
    @FXML
    private ImageView img;

    @FXML
    private Label Nom_zone;

    private MyListener myListener;
    private Plat re;
    static Plat r = new Plat();
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setData(int id_plat, String nom_plat, String description_plat, float prix, int quantite, String nom_categorie, String image, MyListener myListener) {
        this.id = id_plat;
        this.myListener = myListener;
        Nom_zone.setText(nom_categorie);
        String fullurl = "C:\\xampp\\htdocs\\imageplat\\" + image;

        try {
            img.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }

    }


    @FXML
    private void click(MouseEvent event) throws SQLException {
        PlatService rc = new PlatService();
        List<Plat> L = new ArrayList<>();
        myListener.onClick(re);
        //covt.setId_co(Integer.parseInt(id_co.getText()));
        L = rc.recherchePlat(id);
        r.setId_plat(L.get(0).getId_plat());
        r.setNom_plat(L.get(0).getNom_plat());
        r.setDescription_plat(L.get(0).getDescription_plat());
        r.setPrix(L.get(0).getPrix());
        r.setQuantite(L.get(0).getQuantite());
        r.setNom_categorie(L.get(0).getNom_categorie());
        r.setImage(L.get(0).getImage());
    }

    public void onClick() throws SQLException {
        myListener.onClick(re);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public interface MyListener {

        void onClick(Plat re) throws SQLException;
    }
}


