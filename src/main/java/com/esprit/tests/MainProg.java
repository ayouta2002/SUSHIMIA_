package com.esprit.tests;

import com.esprit.models.Plat;
import com.esprit.models.CategorieMenu;

import com.esprit.services.PlatService;
import com.esprit.services.CategorieService;

import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {
        /*plat*/
      PlatService ps = new  PlatService();
       // ps.ajouter(new Plat("spaghetti", "sauce rouge" , 20,3, "japonaise","lina"));
 // ps.supprimer(new Plat(9, "spaghetti", "sauce rouge" , 20,3,"japonaise","lina"));
      ps.modifier(new Plat(26, "spaghetti", "sauce blanche" , 50,40,"japonaise","lina"));
        System.out.println(ps.afficher());
      }
        /*categorie*//*
        CategorieService cs = new  CategorieService();
        cs.ajouter(new CategorieMenu("japonaise", "very good" ));
        //cs.supprimer(new CategorieMenu(34, "dfgh", "fghj"));
       // cs.modifier(new CategorieMenu(1, "japonnaise", "delicious"));
       // System.out.println(cs.afficher());


    }*/
}
