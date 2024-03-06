package com.esprit.services;

import com.esprit.models.Plat;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    public void ajouter(T t) throws SQLException;
   public void modifier(T t);
    public void supprimer(T t);
    public List<T> afficher();

  public   List<Plat> recherchePlat(int id);
}
