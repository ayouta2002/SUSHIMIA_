package com.esprit.models;

public class Cocktails {
    private int id_cocktail  ;
    private String nom_cocktail ;
    private String categorie_cocktail;
    private String description_cocktail ;
    private boolean est_personnalise ;
    private  float prix_cocktail ;
    private  String image_url;
    private int idCreateur ;



   public Cocktails(int id_cocktail , String nom_cocktail ,  String categorie_cocktail , String description_cocktail, boolean est_personnalise , float prix_cocktail, String image_url , int idCreateur )
   {
        this.id_cocktail=id_cocktail ;
        this.nom_cocktail=nom_cocktail;
       this.categorie_cocktail=categorie_cocktail;
        this.description_cocktail=description_cocktail;
        this.est_personnalise=est_personnalise ;
        this.prix_cocktail=prix_cocktail;
        this.image_url = image_url ;
       this.idCreateur = idCreateur;
   }
    public Cocktails( String nom_cocktail ,String categorie_cocktail,  String description_cocktail, boolean est_personnalise , float prix_cocktail,  String image_url , int idCreateur )
    {
        this.nom_cocktail=nom_cocktail;
        this.categorie_cocktail=categorie_cocktail;
        this.description_cocktail=description_cocktail;
        this.est_personnalise=est_personnalise ;
        this.prix_cocktail=prix_cocktail;
        this.image_url = image_url ;
        this.idCreateur = idCreateur;
    }



    public int getId_cocktail ()
    {
        return id_cocktail;
    }
    public void setId_cocktail (int id_cocktail)
    {
        this.id_cocktail=id_cocktail;
    }

    public String getNom_cocktail() {
        return nom_cocktail;
    }

    public void setNom_cocktail(String nom_cocktail) {
        this.nom_cocktail = nom_cocktail;
    }
    public String getCategorie_cocktail() {
        return categorie_cocktail;
    }

    public void setCategorie_cocktail(String categorie_cocktail) {
        this.categorie_cocktail = categorie_cocktail;
    }

    public String getDescription_cocktail() {
        return description_cocktail;
    }

    public void setDescription_cocktail(String description_cocktail) {
        this.description_cocktail = description_cocktail;
    }

    public boolean isEst_personnalise() {
        return est_personnalise;
    }

    public void setEst_personnalise(boolean est_personnalise) {
        this.est_personnalise = est_personnalise;
    }

    public float getPrix_cocktail() {
        return prix_cocktail;
    }

    public void setPrix_cocktail(float prix_cocktail) {
        this.prix_cocktail = prix_cocktail;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    @Override
    public String toString() {
        return "Cocktails{" +
                "id_cocktail=" + id_cocktail +
                ", nom_cocktail=" + nom_cocktail +
                ", categorie_cocktail=" + categorie_cocktail +
                ", description_cocktail=" + description_cocktail +
                ", est_personnalise=" + est_personnalise +
                ", prix_cocktail=" + prix_cocktail +
                ", image_url=" + image_url +
                '}';
    }
}
