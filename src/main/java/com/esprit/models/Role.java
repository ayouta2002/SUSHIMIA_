package com.esprit.models;
public enum Role {
    ADMIN, CLIENT, LIVREUR;

    @Override
    public String toString() {
        return name();
    }
}