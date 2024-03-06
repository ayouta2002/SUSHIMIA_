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
        String query = "INSERT INTO utilisateurs (nom, prenom, mot_de_passe, email, role, image) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, utilisateurs.getNom());
            preparedStatement.setString(2, utilisateurs.getPrenom());
            preparedStatement.setString(3, utilisateurs.getMot_de_passe());
            preparedStatement.setString(4, utilisateurs.getEmail());
            preparedStatement.setString(5, utilisateurs.getRole().toString());
            preparedStatement.setString(6, utilisateurs.getImage());
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
    public void changePassword(String email, String newPassword) {
        String query = "UPDATE utilisateurs SET mot_de_passe=? WHERE email=?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mot de passe modifié avec succès");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                String image = rs.getString("image");
                // Convertir la chaîne de caractères en une valeur d'énumération Role
                Role role = Role.valueOf(roleString.toUpperCase());

                list.add(new Utilisateurs(id, nom, prenom, motDePasse, email, role,image));
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
    public boolean doesEmailExist(String email) {
        String query = "SELECT COUNT(*) AS count FROM utilisateurs WHERE email=?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}