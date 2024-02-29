package com.esprit.services;

import com.esprit.models.Zones;
import com.esprit.models.Tab;
import com.esprit.models.Reservation;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
   public void modifier(T t);
    public void supprimer(T t);
    public void supprimer2(int zone_id);
    //public void supprimer3(int table_id);
    public List<T> afficher();
   public void supprimer4(int id_R);

}
