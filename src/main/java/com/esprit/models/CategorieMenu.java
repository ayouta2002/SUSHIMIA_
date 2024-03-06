package com.esprit.models;

public class CategorieMenu {
    private int id_categorie;
    private String nom_categorie;
    private String description_categorie;
    private String image_categorie;

    public CategorieMenu(int id_categorie, String nom_categorie, String description_categorie, String image_categorie) {
        this.id_categorie = id_categorie;
        this.nom_categorie = nom_categorie;
        this.description_categorie = description_categorie;
        this.image_categorie = image_categorie;
    }

    public CategorieMenu(String nom_categorie, String description_categorie, String image_categorie) {
        this.nom_categorie = nom_categorie;
        this.description_categorie = description_categorie;
        this.image_categorie = image_categorie;
    }

    public CategorieMenu() {
    }


    /* public CategorieMenu(String text, String text1, String text2, String text3) {
    }*/

    public int getId_categorie() {
        return id_categorie;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public String getDescription_categorie() {
        return description_categorie;
    }

    public String getImage_categorie() {
        return image_categorie;
    }


    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public void setDescriprtion_categorie(String descriprtion_categorie) {
        this.description_categorie = descriprtion_categorie;
    }

    public void setImage_categorie(String image_categorie) {
        this.image_categorie = image_categorie;
    }

    @Override
    public String toString() {
        return "CategorieMenu{" +
                "id_categorie=" + id_categorie +
                ", nom_categorie='" + nom_categorie + '\'' +
                ", descriprtion_categorie='" + description_categorie + '\'' +
               // ", image_categorie='" + image_categorie + '\'' +//
                '}';
    }
}

