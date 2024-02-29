package com.esprit.models;

public class Tab {
    private int table_id;

    private int capacit_t;
    private String nom_zone;

    public Tab(int table_id, int capacit_t, String nom_zone) {
        this.table_id = table_id;
        this.capacit_t = capacit_t;
        this.nom_zone = nom_zone;
    }

    public Tab(int capacit_t, String nom_zone) {
        this.capacit_t = capacit_t;
        this.nom_zone = nom_zone;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }



    public int getCapacit_t() {
        return capacit_t;
    }

    public void setCapacit_t(int capacit_t) {
        this.capacit_t = capacit_t;
    }

    public String getNom_zone() {
        return nom_zone;
    }

    public void setNom_zone(String nom_zone) {
        this.nom_zone = nom_zone;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "table_id=" + table_id +
                ", capacit_t=" + capacit_t +
                ", nom_zone='" + nom_zone + '\'' +
                '}';
    }
}
