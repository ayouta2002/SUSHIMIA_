package com.esprit.services;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Like;
import com.esprit.models.Plat;
import com.esprit.utils.DataSource;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CategorieService implements IService<CategorieMenu> {

    private Connection connection;

    public CategorieService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(CategorieMenu CategorieMenu) throws SQLException {
        String query = "INSERT INTO categorie_menu (nom_categorie, description_categorie, image_categorie) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, CategorieMenu.getNom_categorie());
        preparedStatement.setString(2, CategorieMenu.getDescription_categorie());
        preparedStatement.setString(3, CategorieMenu.getImage_categorie());
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(CategorieMenu CategorieMenu) {
        String req = "UPDATE categorie_menu set nom_categorie = '" + CategorieMenu.getNom_categorie() + "', description_categorie = '" + CategorieMenu.getDescription_categorie()  + "' where id_categorie = " + CategorieMenu.getId_categorie() + ";";
      //  String req = "UPDATE Plat set nom_plat = '" + Plat.getNom_plat() + "', description_plat = '" + Plat.getDescription_plat() + "' , prix = '" + Plat.getPrix() + "' , id_categorie = '" + Plat.getId_categorie() + "' where id_plat = " + Plat.getId_plat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(CategorieMenu CategorieMenu) {
        String req = "DELETE from categorie_menu where id_categorie = " + CategorieMenu.getId_categorie() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void delete(int id) {
        String req = "DELETE from categorie_menu where id_categorie = " + id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    @Override
    public List<CategorieMenu> afficher() {
        List<CategorieMenu> list = new ArrayList<>();
        try {
            String req = "Select * from categorie_menu";
            Statement st = connection.createStatement();

            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                CategorieMenu r = new CategorieMenu();
                r.setId_categorie(RS.getInt(1));
                r.setNom_categorie(RS.getString(2));
                r.setDescriprtion_categorie(RS.getString(3));
                r.setImage_categorie(RS.getString(4));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;

    }

    @Override
    public List<Plat> recherchePlat(int id) {
        return null;
    }

    public List<CategorieMenu> rechCategorie(int id) {
        List<CategorieMenu> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `categorie_menu` WHERE id_categorie= " + id;
            Statement st = connection.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                CategorieMenu r = new CategorieMenu();
                r.setId_categorie(RS.getInt("id_categorie"));
                r.setNom_categorie(RS.getString("nom_categorie"));
                r.setDescriprtion_categorie(RS.getString("description_categorie"));
                r.setImage_categorie(RS.getString("image_categorie"));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }

}


