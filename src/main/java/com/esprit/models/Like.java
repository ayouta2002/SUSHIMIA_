package com.esprit.models;

public class Like {

    private int id_like;
    private int id_user;
    private int id_categorie;
    private String staus;

    public Like() {
    }

    public Like(int id_like, int id_user, int id_categorie, String staus) {
        this.id_like = id_like;
        this.id_user = id_user;
        this.id_categorie = id_categorie;
        this.staus = staus;
    }

    public Like(int id_user, int id_categorie, String staus) {
        this.id_user = id_user;
        this.id_categorie = id_categorie;
        this.staus = staus;
    }

    public int getId_like() {
        return id_like;
    }

    public void setId_like(int id_like) {
        this.id_like = id_like;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }
}
