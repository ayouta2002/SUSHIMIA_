package tn.esprit.services;

import tn.esprit.models.Commande;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
    public List<Commande> afficherparstatus(String x);
    // public void modifier(T t);
    //  public void supprimer(T t);
    //  public List<T> afficher();
}
