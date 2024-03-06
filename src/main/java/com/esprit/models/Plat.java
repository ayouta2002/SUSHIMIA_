package com.esprit.models;

public class Plat {
    private int id_plat;
    private String nom_plat;
    private String description_plat;
    private float prix;
    private int quantite;
    private String nom_categorie;
    private String image;
    private int like;
    private int dislike;

    public Plat(int id_plat, String nom_plat, String description_plat, float prix, int quantite, String nom_categorie, String image) {
        this.id_plat = id_plat;
        this.nom_plat = nom_plat;
        this.description_plat = description_plat;
        this.prix = prix;
        this.quantite = quantite;
        this.nom_categorie = nom_categorie;
        this.image = image;
    }

    public Plat(String nom_plat, String description_plat, float prix, int quantite, String nom_categorie, String image) {
        this.nom_plat = nom_plat;
        this.description_plat = description_plat;
        this.prix = prix;
        this.quantite = quantite;
        this.nom_categorie = nom_categorie;
        this.image = image;
    }

    public Plat(int id_plat, String nom_plat, String description_plat, float prix, int quantite, String nom_categorie, String image, int like, int dislike) {
        this.id_plat = id_plat;
        this.nom_plat = nom_plat;
        this.description_plat = description_plat;
        this.prix = prix;
        this.quantite = quantite;
        this.nom_categorie = nom_categorie;
        this.image = image;
        this.like = like;
        this.dislike = dislike;
    }

    public Plat(String nom_plat, String description_plat, float prix, int quantite, String nom_categorie, String image, int like, int dislike) {
        this.nom_plat = nom_plat;
        this.description_plat = description_plat;
        this.prix = prix;
        this.quantite = quantite;
        this.nom_categorie = nom_categorie;
        this.image = image;
        this.like = like;
        this.dislike = dislike;
    }

    public Plat() {

    }

    public int getId_plat() {
        return id_plat;
    }

    public String getNom_plat() {
        return nom_plat;
    }

    public String getDescription_plat() {
        return description_plat;
    }

    public float getPrix() {
        return prix;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public String getImage() {
        return image;
    }

    public void setId_plat(int id_plat) {
        this.id_plat = id_plat;
    }

    public void setNom_plat(String nom_plat) {
        this.nom_plat = nom_plat;
    }

    public void setDescription_plat(String description_plat) {
        this.description_plat = description_plat;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id_plat=" + id_plat +
                ", nom_plat='" + nom_plat + '\'' +
                ", description_plat='" + description_plat + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", nom_categorie=" + nom_categorie +
                '}';
    }


}
