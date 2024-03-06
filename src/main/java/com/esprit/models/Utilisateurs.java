package com.esprit.models;
import com.esprit.models.Role;
public  class Utilisateurs {

    private int id;
    private String nom;
    private String prenom;
    private String mot_de_passe;
    private String email;
    private Role role;
    private String image;

    public Utilisateurs(int id) {
        this.id = id;
    }

    public Utilisateurs() {
    }

    public Utilisateurs(String nom, String prenom, String mot_de_passe, String email, Role role, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.role = role;
        this.image = image;
    }

    public Utilisateurs(int id, String nom, String prenom, String mot_de_passe, String email, Role role, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.role = role;
        this.image = image;
    }

    public Utilisateurs(String nom, String prenom, String motDePasse, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = motDePasse;
        this.email = email;
    }

    public Utilisateurs(String nom, String prenom, String motDePasse, String email, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = motDePasse;
        this.email = email;
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", image='" + image + '\'' +
                '}';
    }
}