package tn.esprit.services;

import tn.esprit.models.Commande;
import tn.esprit.models.Resto;
import tn.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestoService implements tn.esprit.services.IService<Resto> {

    private Connection connection;

    public RestoService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Resto resto) {
        String req = "INSERT INTO `resto`(`nomR`, `adresseR`, `numR`) values ('" + resto.getNomR() + "','" + resto.getAdresseR() + "', '" + resto.getNumR() +  "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Resto ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Commande> afficherparstatus(String x) {
        return null;
    }

    public void modifier(Resto resto) {
        String req = " UPDATE resto set nomR =  '"+ resto.getNomR() + "', adresseR ='"+ resto.getAdresseR()  + "' , numR = '"+ resto.getNumR()+ "'  where id_resto ='"+ resto.getId_resto() +"';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("resto modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void supprimer(Resto resto) {
        String req = "DELETE from resto where id_resto = '"+ resto.getId_resto() +"';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Resto supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Resto> afficher() {
        List<Resto> resto = new ArrayList<>();

        String req = "SELECT * from resto";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                resto.add(new Resto(rs.getInt("id_resto"), rs.getString("nomR"), rs.getString("adresseR"), rs.getInt("numR")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resto);
        return resto;
    }




}
