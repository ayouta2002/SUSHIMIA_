package com.esprit.models;

public class Zones {

    private int zone_id;
    private String nom;
    private String description;
    private int capacity;
    private String image;

    public Zones(int zone_id, String nom, String description, int capacity, String image) {
        this.zone_id = zone_id;
        this.nom = nom;
        this.description = description;
        this.capacity = capacity;
        this.image = image;
    }

    public Zones(String nom, String description, int capacity, String image) {
        this.nom = nom;
        this.description = description;
        this.capacity = capacity;
        this.image = image;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Zones{" +
                "zone_id=" + zone_id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", image='" + image + '\'' +
                '}';
    }
}
