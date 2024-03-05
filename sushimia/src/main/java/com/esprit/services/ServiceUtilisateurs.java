package com.esprit.services;
import com.esprit.models.Utilisateurs;
import com.esprit.models.Role;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceUtilisateurs implements IService<Utilisateurs> {
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;

    public ServiceUtilisateurs() {
        connexion = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Utilisateurs utilisateurs) {
        String query = "INSERT INTO utilisateurs (nom, prenom, mot_de_passe, email, role) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, utilisateurs.getNom());
            preparedStatement.setString(2, utilisateurs.getPrenom());
            preparedStatement.setString(3, utilisateurs.getMot_de_passe());
            preparedStatement.setString(4, utilisateurs.getEmail());
            preparedStatement.setString(5, utilisateurs.getRole().toString());
            preparedStatement.executeUpdate();
            System.out.println("user ajouté");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Utilisateurs utilisateurs) {
        try {
            String requete = "delete from utilisateurs where id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, utilisateurs.getId());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Utilisateurs utilisateurs) {
        try {
            String requete = "UPDATE utilisateurs SET nom=?, prenom=?, mot_de_passe=?, email=?  WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setString(1, utilisateurs.getNom());
            pst.setString(2, utilisateurs.getPrenom());
            pst.setString(3, utilisateurs.getMot_de_passe());
            pst.setString(4, utilisateurs.getEmail());
            pst.setInt(5, utilisateurs.getId());  // Utilisation de l'ID pour identifier l'utilisateur à mettre à jour
            pst.executeUpdate();
            System.out.println("Mise à jour avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public List<Utilisateurs> readAll() {
        String requete = "SELECT * from utilisateurs";
        List<Utilisateurs> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String motDePasse = rs.getString("mot_de_passe");
                String email = rs.getString("email");
                String roleString = rs.getString("role");

                // Convertir la chaîne de caractères en une valeur d'énumération Role
                //  Role role = Role.valueOf(roleString.toUpperCase());

                list.add(new Utilisateurs(id, nom, prenom, motDePasse, email));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void deleteById(int idUtilisateur) {
        try {
            String requete = "DELETE FROM utilisateurs WHERE id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
            System.out.println("Suppression avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

}
/*

    public ObservableList<Utilisateurs> triParNom() {
        ObservableList<Utilisateurs> utilisateursList = FXCollections.observableArrayList();

        String requete = "SELECT * FROM utilisateurs ORDER BY nom ASC";
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String motDePasse = rs.getString("mot_de_passe");
                String email = rs.getString("email");
                String roleString = rs.getString("role");

                // Convertir la chaîne de caractères en une valeur d'énumération Role
                // Role role = Role.valueOf(roleString.toUpperCase());

                utilisateursList.add(new Utilisateurs(id, nom, prenom, motDePasse, email));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilisateursList;
    }*/
   /* @Override
    public Utilisateurs readById(int id) {
        String requete="select * from utilisateurs where id="+id;
       Utilisateurs utilisateurs = null;
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                utilisateurs = new Utilisateurs(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilisateurs;
    }*/


