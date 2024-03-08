package com.esprit.services;

import com.esprit.models.Ingredients;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ingredientsservice implements IService<Ingredients> {
    private Connection connection;

    public Ingredientsservice() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Ingredients ingredient) {
        String req = "INSERT INTO ingredients (categorie   , nom_ingredient, quantite_ingredient, unite_mesure, prix_ingredient, status) VALUES ('" +
                ingredient.getCategorie_ingredient() + "', '" +
                ingredient.getNom_ingredient() + "', " +
                ingredient.getQuantite_ingredient() + ", '" +
                ingredient.getUnite_mesure() + "', " +
                ingredient.getPrix_ingredient() + ", '" +
                ingredient.getStatus_ingredient() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Ingrédient ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'ingrédient : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Ingredients ingredient) {
        String req = "UPDATE ingredients SET categorie = '" + ingredient.getCategorie_ingredient() +
                "', nom_ingredient = '" + ingredient.getNom_ingredient() +
                "', quantite_ingredient = " + ingredient.getQuantite_ingredient() +
                ", unite_mesure = '" + ingredient.getUnite_mesure() +
                "', prix_ingredient = " + ingredient.getPrix_ingredient() +
                ", status = '" + ingredient.getStatus_ingredient() +
                "' WHERE id_ingredient = " + ingredient.getId_ingredient() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Ingrédient modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'ingrédient : " + e.getMessage());
        }
    }


    @Override
    public List<Ingredients> afficher() {
        List<Ingredients> ingredients = new ArrayList<>();
        String req = "SELECT * FROM ingredients";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ingredients.add(new Ingredients(
                        rs.getInt("id_ingredient"),
                        rs.getString("categorie"),
                        rs.getString("nom_ingredient"),
                        rs.getInt("quantite_ingredient"),
                        rs.getString("unite_mesure"),
                        rs.getFloat("prix_ingredient"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des ingrédients : " + e.getMessage());
        }
        return ingredients;
    }

    @Override
    public void supprimer(Ingredients ingredient) {
        String req = "DELETE FROM ingredients WHERE id_ingredient = " + ingredient.getId_ingredient();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Ingrédient supprimé !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'ingrédient : " + e.getMessage());
        }
    }
}
