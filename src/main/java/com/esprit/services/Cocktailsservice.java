package com.esprit.services;

import com.esprit.models.Cocktails;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cocktailsservice implements IService<Cocktails> {
    private Connection connection;

    public Cocktailsservice() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cocktails cocktail) {
        String req = "INSERT INTO cocktails (nom_cocktail, categorie_cocktail, description_cocktail, est_personnalise, prix_cocktail, image_url, idCreateur) VALUES ('" +
                cocktail.getNom_cocktail() + "', '" +
                cocktail.getCategorie_cocktail() + "', '" +
                cocktail.getDescription_cocktail() + "', " +
                (cocktail.isEst_personnalise() ? 1 : 0) + ", " +
                cocktail.getPrix_cocktail() + ", '" +
                cocktail.getImage_url() + "', " +
                cocktail.getIdCreateur() + ");";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cocktail ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du cocktail : " + e.getMessage());
        }
    }
    @Override
    public void modifier(Cocktails cocktail) {
        String req = "UPDATE cocktails SET nom_cocktail = '" + cocktail.getNom_cocktail() +
                "', categorie_cocktail = '" + cocktail.getCategorie_cocktail() +
                "', description_cocktail = '" + cocktail.getDescription_cocktail() +
                "', est_personnalise = " + (cocktail.isEst_personnalise() ? 1 : 0) +
                ", prix_cocktail = " + cocktail.getPrix_cocktail() +
                ", image_url = '" + cocktail.getImage_url() +
                "', idCreateur = " + cocktail.getIdCreateur() +
                " WHERE id_cocktail = " + cocktail.getId_cocktail() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cocktail modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<Cocktails> afficher() {
        List<Cocktails> cocktail = new ArrayList<>();

        String req = "SELECT * from cocktails";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cocktail.add(new Cocktails (rs.getInt("id_cocktail"),
                        rs.getString("nom_cocktail"),
                        rs.getString("categorie_cocktail"),
                        rs.getString("description_cocktail"),
                        rs.getBoolean("est_personnalise"),
                        rs.getFloat("prix_cocktail"),
                        rs.getString("image_url"),
                        rs.getInt("idCreateur")
                ));
        }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cocktail;
    }
    @Override
    public void supprimer(Cocktails cocktail) {
        String req = "DELETE from cocktails where id_cocktail = " + cocktail.getId_cocktail() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cocktail supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
