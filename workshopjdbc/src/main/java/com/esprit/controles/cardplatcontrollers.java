/*package com.esprit.controles;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


import com.esprit.models.Plat;
import com.esprit.services.PlatService;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class cardplatcontrollers implements Initializable{




        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label descriptioncard;

        @FXML
        private ImageView img;

        @FXML
        private Label nomcard;

        @FXML
        private Label prixcard;

    private Plat plat = new Plat();
    public static Plat p = new Plat();
    private int id_plat;
    private MyListener myListener;

    public void setData(int idP, String nom_plat, String description_plat,float prix , String url, MyListener myListener) {

        this.id_plat = idP;
        this.myListener = myListener;
        nomcard.setText(nom_plat);
        descriptioncard.setText(description_plat);
        prixcard.setText(String.valueOf(prix));

        String fullurl = "C:\\xampp\\htdocs\\user_images\\" + url;
        System.out.println("full url " + fullurl);

        try {
            img.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }
    }

    /**
     * Initializes the controller class.
     */
   /* @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onClick() {
        myListener.onClick(plat);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    private void Click(MouseEvent event) {
        PlatService platService = new PlatService();
        List<Plat> L = new ArrayList<>();
        myListener.onClick(plat);
        //covt.setId_co(Integer.parseInt(id_co.getText()));
        L = platService.recherchePlat(id_plat);
        p.setId_plat(L.get(0).getId_plat());
        p.setNom_plat(L.get(0).getNom_plat());
        p.setDescription_plat(L.get(0).getDescription_plat());
        p.setPrix(L.get(0).getPrix());

        p.setImage(L.get(0).getImage());
    }

    public interface MyListener {

        void onClick(Plat plat);
    }



}*/
