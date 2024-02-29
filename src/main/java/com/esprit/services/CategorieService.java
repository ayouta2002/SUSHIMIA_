package com.esprit.services;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Plat;
import com.esprit.utils.DataSource;
import com.esprit.services.IService;

import java.sql.*;
import java.util.*;

public class CategorieService implements IService<CategorieMenu> {

    private Connection connection;

    public CategorieService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(CategorieMenu CategorieMenu) {
        String req = "INSERT into categorie_menu(nom_categorie, description_categorie ) values ('" + CategorieMenu.getNom_categorie() + "', '" + CategorieMenu.getDescription_categorie() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("CategorieMenu ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        List<CategorieMenu> CategorieMenu = new ArrayList<>();

        String req = "SELECT * from categorie_menu";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                CategorieMenu.add(new CategorieMenu(rs.getInt("id_categorie"), rs.getString("nom_categorie"), rs.getString("description_categorie"), rs.getString("image_categorie")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return CategorieMenu;








    }

    @Override
    public List<Plat> recherchePlat(int id) {
        return null;
    }

}


