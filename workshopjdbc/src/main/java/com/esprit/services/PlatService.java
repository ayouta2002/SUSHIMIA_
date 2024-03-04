package com.esprit.services;

import com.esprit.models.Plat;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class PlatService implements IService<Plat> {

    private Connection connection;

    public PlatService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Plat Plat){
        String req = "INSERT into plat(nom_plat, description_plat, prix, quantite,nom_categorie,image) values ('" + Plat.getNom_plat() + "', '" + Plat.getDescription_plat() + "', '" + Plat.getPrix() + "','" + Plat.getQuantite() + "', '" + Plat.getNom_categorie() + " ','" + Plat.getImage() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Plat Plat) {
        String req = "UPDATE Plat set nom_plat = '" + Plat.getNom_plat() + "', description_plat = '" + Plat.getDescription_plat() + "' , prix = '" + Plat.getPrix() + "', quantite = '" + Plat.getQuantite()+ "' , nom_categorie = '" + Plat.getNom_categorie() + "' where id_plat = " + Plat.getId_plat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Plat Plat) {
        String req = "DELETE from Plat where id_plat = " + Plat.getId_plat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeletePlat (int idplat) {
        String req = "DELETE from Plat where id_plat = " + idplat+ ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Plat> afficher() {
        List<Plat> Plat = new ArrayList<>();

        String req = "SELECT * from Plat";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Plat.add(new Plat(rs.getInt("id_plat"), rs.getString("nom_plat"), rs.getString("description_plat"),rs.getFloat("prix") ,rs.getInt("quantite"), rs.getString("nom_categorie"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Plat;
    }


@Override
    public List<Plat> recherchePlat(int id) {
        List<Plat> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM plat WHERE id_plat =" + id+ ";";
            Statement st = connection.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Plat plat = new Plat();
                plat.setId_plat(RS.getInt("id_plat"));
                plat.setNom_plat(RS.getString("nom_plat"));
                plat.setDescription_plat(RS.getString("description_plat"));
                plat.setPrix(RS.getInt("prix"));
                plat.setQuantite(RS.getInt("quantite"));
                plat.setNom_categorie(RS.getString("nom_categorie"));
                plat.setImage(RS.getString("image"));

                System.out.println(plat);
                list.add(plat);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }


    public List<Plat> recherchePlat(String nom) {
        List<Plat> list = new ArrayList<>();
        try {
            String req = "Select * from plat  WHERE `nom_categorie` = " + nom;
            Statement st = connection.createStatement();

            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Plat r = new Plat();
                r.setId_plat(RS.getInt(1));
                r.setNom_plat(RS.getString(2));
                r.setDescription_plat(RS.getString(3));
                r.setPrix(RS.getFloat(4));
                r.setQuantite(RS.getInt(5));
                r.setNom_categorie(RS.getString(6));
                r.setImage(RS.getString(7));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}


