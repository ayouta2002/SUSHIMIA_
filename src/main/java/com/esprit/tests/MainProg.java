package com.esprit.tests;

import com.esprit.models.Cocktails;
import com.esprit.services.Cocktailsservice;
import com.esprit.models.Ingredients;
import com.esprit.services.Ingredientsservice;
import com.esprit.utils.DataSource;


public class MainProg {

   public static void main(String[] args) {
        Cocktailsservice cock = new Cocktailsservice();
        cock.ajouter(new Cocktails ("olaaaaa","olaaaaa" , "descigptoin du cocktail" , false , 5100 , "bureagu/3a/java" ,1 ));
        //cock.supprimer(new Cocktails(3, "regiiina", "italienne" ,"dididi du cocktail" , false , 700 , "bureau/3a/java" ,1  ));
      //cock.modifier(new Cocktails(1, "ferffaf", "regiiina", "dididi du cocktail" , false , 700 , "bureau/3a/java" ,1  ));

      System.out.println(cock.afficher());



      /*  Ingredientsservice ingredientService = new Ingredientsservice();

       // Ajout d'un ingrédient
        ingredientService.ajouter(new Ingredients("poudre", "sucre" ,500 ,
                "gramme", 2.5f, "available" ));

        // Modification d'un ingrédient
        Ingredients ingredientAModifier = new Ingredients(15, "poudre", "farine" ,500 ,
                       "gramme", 2.5f, "not" );
        ingredientService.modifier(ingredientAModifier);

        // Suppression d'un ingrédient
        Ingredients ingredientASupprimer = new Ingredients(13,"poudre", "sucre" ,500 ,
                       "gramme", 2.5f, "available" );
        ingredientService.supprimer(ingredientASupprimer);

         //Affichage des ingrédients
        System.out.println(ingredientService.afficher());


*/

}}
