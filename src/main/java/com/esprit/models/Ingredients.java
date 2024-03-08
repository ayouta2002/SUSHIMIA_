package com.esprit.models;

public class Ingredients {
    private int id_ingredient ;

    private String categorie_ingredient;
    private String nom_ingredient;
    private int quantite_ingredient ;
    private String unite_mesure ;
    private float prix_ingredient ;

    private String status_ingredient ;

    public Ingredients(int id_ingredient, String categorie_ingredient , String nom_ingredient,
                       int quantite_ingredient, String unite_mesure, float prix_ingredient , String status_ingredient) {
        this.id_ingredient = id_ingredient;
        this.categorie_ingredient=categorie_ingredient;
        this.nom_ingredient = nom_ingredient;
        this.quantite_ingredient = quantite_ingredient;
        this.unite_mesure = unite_mesure;
        this.prix_ingredient = prix_ingredient;
        this.status_ingredient=status_ingredient;
    }

    public Ingredients(String categorie_ingredient , String nom_ingredient,
                       int quantite_ingredient, String unite_mesure, float prix_ingredient, String status_ingredient)
    {
        this.categorie_ingredient=categorie_ingredient;
        this.nom_ingredient = nom_ingredient;
        this.quantite_ingredient = quantite_ingredient;
        this.unite_mesure = unite_mesure;
        this.prix_ingredient = prix_ingredient;
        this.status_ingredient = status_ingredient;
    }

    public String getCategorie_ingredient() {
        return categorie_ingredient;
    }

    public void setCategorie_ingredient(String categorie_ingredient) {
        this.categorie_ingredient = categorie_ingredient;
    }



    public String getStatus_ingredient() {
        return status_ingredient;
    }

    public void setStatus_ingredient(String status_ingredient) {
        this.status_ingredient = status_ingredient;
    }

    public int getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public String getNom_ingredient() {
        return nom_ingredient;
    }

    public void setNom_ingredient(String nom_ingredient) {
        this.nom_ingredient = nom_ingredient;
    }

    public int getQuantite_ingredient()
    {
        return quantite_ingredient;
    }

    public void setQuantite_ingredient(int quantite_ingredient)
    {
        this.quantite_ingredient = quantite_ingredient;
    }

    public String getUnite_mesure()
    {
        return unite_mesure;
    }

    public void setUnite_mesure(String unite_mesure)
    {
        this.unite_mesure = unite_mesure;
    }

    public float getPrix_ingredient()
    {
        return prix_ingredient;
    }

    public void setPrix_ingredient(float prix_ingredient)
    {
        this.prix_ingredient = prix_ingredient;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id_ingredient=" + id_ingredient +
                ", categorie-ingredient='" + categorie_ingredient + '\'' +
                ", nom_ingredient='" + nom_ingredient + '\'' +
                ", quantite_ingredient=" + quantite_ingredient +
                ", unite_mesure='" + unite_mesure + '\'' +
                ", prix_ingredient=" + prix_ingredient +
                ", status_ingredient=" + status_ingredient +
                '}';
    }
}
