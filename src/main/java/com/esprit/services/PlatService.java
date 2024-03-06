package com.esprit.services;

import com.esprit.models.CategorieMenu;
import com.esprit.models.Like;
import com.esprit.models.Plat;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class PlatService implements IService<Plat> {

    private Connection connection;

    public PlatService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Plat Plat){
        String req = "INSERT into plat(nom_plat, description_plat, prix, quantite,nom_categorie,image) values ('" + Plat.getNom_plat() + "', '" + Plat.getDescription_plat() + "', '" + Plat.getPrix() + "','" + Plat.getQuantite() + "', '" + Plat.getNom_categorie() + " ','" + Plat.getImage() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Plat Plat) {
        String req = "UPDATE Plat set nom_plat = '" + Plat.getNom_plat() + "', description_plat = '" + Plat.getDescription_plat() + "' , prix = '" + Plat.getPrix() + "', quantite = '" + Plat.getQuantite()+ "' , nom_categorie = '" + Plat.getNom_categorie() + "', like_plat = '" + Plat.getLike()  + "', dislike_plat = '" + Plat.getDislike()  + "' where id_plat = " + Plat.getId_plat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Plat Plat) {
        String req = "DELETE from Plat where id_plat = " + Plat.getId_plat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeletePlat (int idplat) {
        String req = "DELETE from Plat where id_plat = " + idplat+ ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Plat supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Plat> afficher() {
        List<Plat> Plat = new ArrayList<>();

        String req = "SELECT * from Plat";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Plat.add(new Plat(rs.getInt("id_plat"), rs.getString("nom_plat"), rs.getString("description_plat"),rs.getFloat("prix") ,rs.getInt("quantite"), rs.getString("nom_categorie"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Plat;
    }


@Override
    public List<Plat> recherchePlat(int id) {
        List<Plat> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `plat` WHERE id_plat= " + id;
            Statement st = connection.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Plat plat = new Plat();
                plat.setId_plat(RS.getInt("id_plat"));
                plat.setNom_plat(RS.getString("nom_plat"));
                plat.setDescription_plat(RS.getString("description_plat"));
                plat.setPrix(RS.getInt("prix"));
                plat.setQuantite(RS.getInt("quantite"));
                plat.setNom_categorie(RS.getString("nom_categorie"));
                plat.setImage(RS.getString("image"));
                plat.setLike(RS.getInt("like_plat"));
                plat.setLike(RS.getInt("dislike_plat"));

                System.out.println(plat);
                list.add(plat);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }


    public void add_like_dislike_plat(int idUser, int idPlat, String status) throws SQLException {
        String query = "INSERT INTO like_user (id_user, id_plat, status) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idUser);
        preparedStatement.setInt(2, idPlat);
        preparedStatement.setString(3, status);
        preparedStatement.executeUpdate();
    }

    public void modifier_like_dislike(int id, String status) {
        String req = "UPDATE like_user set status = '" + status + "' where id_like = " + id + ";";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("like modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int rech_index_Like(int idUser, int idPlat) throws SQLException {
        String req = "SELECT * FROM like_user WHERE id_user = ? AND id_plat = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, idUser);
        preparedStatement.setInt(2, idPlat);
        ResultSet RS = preparedStatement.executeQuery();
        int index;
        if (RS.next())
            index = RS.getInt("id_like");
        else
            index = -1;
        return index;
    }

    public List<Like> rech_Like(int id_like) throws SQLException {
        List<Like> list = new ArrayList<>();
        String req = "SELECT * FROM like_user WHERE id_like = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_like);
        ResultSet RS = preparedStatement.executeQuery();
        while (RS.next()) {
            Like r = new Like();
            r.setId_like(RS.getInt("id_like"));
            r.setId_user(RS.getInt("id_user"));
            r.setId_categorie(RS.getInt("id_plat"));
            r.setStaus(RS.getString("status"));

            list.add(r);
        }
        return list;
    }

    public List<Plat> rechPlat(int id) {
        List<Plat> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `categorie_menu` WHERE id_categorie= " + id;
            Statement st = connection.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Plat plat = new Plat();
                plat.setId_plat(RS.getInt("id_plat"));
                plat.setNom_plat(RS.getString("nom_plat"));
                plat.setDescription_plat(RS.getString("description_plat"));
                plat.setPrix(RS.getInt("prix"));
                plat.setQuantite(RS.getInt("quantite"));
                plat.setNom_categorie(RS.getString("nom_categorie"));
                plat.setImage(RS.getString("image"));

                list.add(plat);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }


    public List<Plat> recherchePlat(CategorieMenu categorie) {
        List<Plat> list = new ArrayList<>();
        try {
            String nomCategorie = categorie.getNom_categorie();
            String req = "SELECT * FROM plat WHERE nom_categorie = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, nomCategorie);
            ResultSet RS = preparedStatement.executeQuery();
            System.out.println(RS);

            while (RS.next()) {
                Plat r = new Plat();
                r.setId_plat(RS.getInt(1));
                r.setNom_plat(RS.getString(2));
                r.setDescription_plat(RS.getString(3));
                r.setPrix(RS.getFloat(4));
                r.setQuantite(RS.getInt(5));
                r.setNom_categorie(RS.getString(6));
                r.setImage(RS.getString(7));

                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
}


