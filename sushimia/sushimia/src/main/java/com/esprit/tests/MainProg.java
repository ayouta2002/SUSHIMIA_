package com.esprit.tests;
import com.esprit.models.*;
import com.esprit.services.ServiceUtilisateurs;
import com.mysql.cj.xdevapi.Client;

public class MainProg {

    public static void main(String[] args) {

        ServiceUtilisateurs su = new ServiceUtilisateurs();
        //Utilisateurs u1=new Utilisateurs(2,"ouma","amd","test","test","Client");
       //su.readAll().forEach(System.out::println);
     //  su.update(new Utilisateurs(39,"eya","med","ahmed","ahmed@esprit","Livreur"));
        su.readAll().forEach(System.out::println);


        //Utilisateurs ad1 = new Utilisateurs(12,"oumaima","amdouni","tttttt","ouma@esprit.tn","Admin");
     //su.add(new Utilisateurs("med","med","ahmed","ahmed@esprit","Admin"));
      //  su.readAll().forEach(System.out::println);
       // su.supprimer(new Client(4,"eya","ben","123","eya@esprit.tn","Client","ibnsina"));
    //su.modifier(new Utilisateurs(3,"amdouni","amd","aaaaa","amd@esprit.tn","Client"));
       /*
        ServiceClient sc=new ServiceClient();
        ServiceLivreur sl=new ServiceLivreur();
       */
       /* Admin ad1=new Admin(1,"skander","amd","aaaaa","skander@esprit.tn","Admin");
        Admin ad2=new Admin(2,"hamza","amd","bbbb","hamza@esprit.tn","Admin");
        Client cl1= new Client("mrj",8,"hiba","brahem","tttttttttt","hiba@esprit.tn","Client1");
        Client cl2=new Client("ibnsina",6,"malek","yakoubi","pppppp","malek@esprit.tn","client2");
        Livreur lv1=new Livreur("oui",11,"ahlem","amd","vvvvv","ahlem@esprit.tn","Livreur");*/
        //sa.ajouter(new Admin(3,"skander","amd","aaaaa","skander@esprit.tn","Admin"));
        //sa.ajouter(new Admin(2,"hamza","amd","iiiii","hamza@esprit.tn","Admin"));
       // sc.ajouter(new Client("mrj",8,"hiba","brahem","tttttttttt","hiba@esprit.tn","Client"));
       // sl.ajouter(new Livreur("oui",11,"ahlem","amd","vvvvv","ahlem@esprit.tn","Livreur"));
      //  sa.modifier(new Admin(7,"skander","amd","aaaaa","skander@esprit.tn","Livreur"));
       // sa.modifier(new Admin(9,"hamza","amd","aaaaa","hamza@esprit.tn","Livreur"));
      //  sc.modifier(new Client("ibnsina",6,"malek","yakoubi","pppppp","malek@esprit.tn","Livreur"));
       // sc.modifier(new Client("ibnsina",6,"malek","yakoubi","pppppp","malek@esprit.tn","Livreur"));
      //  sl.modifier(new Livreur("oui",20,"ahlem","amdouni","vvvvv","ahlem@esprit.tn","Livreur"));
          //sa.supprimer(new Admin(10,"skander","amd","aaaaa","skander@€sprit.tn","Admin"));
        //sc.supprimer(new Client("mrj",8,"hiba","brahem","tttttttttt","hiba@esprit.tn","Client1"));
       // sl.supprimer(new Livreur("oui",20,"ahlem","amd","vvvvv","ahlem@esprit.tn","livreur1"));
     //   ps.supprimer(new Personne(3, "Fedi2", "Naoufel"));
       //ps.modifier(new Utilisateurs(3,"oumaima", "benslimen","123","eya@esprit.tn","client"));
       // System.out.println(su.afficher());
      //  PersonneService2 ps2 = new PersonneService2();
      //  ps2.ajouter(new Personne("Fedi2", "Naoufel"));
    }
}
