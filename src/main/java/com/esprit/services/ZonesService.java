package com.esprit.services;
import com.esprit.models.Zones;
import com.esprit.utils.DataSource;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ZonesService implements IService<Zones> {

    private Connection connection;

    public ZonesService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Zones Zones) {
        String req = "INSERT into zones(nom, description,capacity,image) values ('" + Zones.getNom() + "', '" + Zones.getDescription() + "', '" + Zones.getCapacity() + "', '" + Zones.getImage() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zones ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Zones Zones) {
        String req = "UPDATE zones set nom = '" + Zones.getNom() + "', description = '" + Zones.getDescription()+ "', capacity = '" + Zones.getCapacity() + "', image = '" + Zones.getImage() + "' where zone_id = " + Zones.getZone_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zones modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(Zones Zones) {
        String req = "DELETE from zones where zone_id = " + Zones.getZone_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zone supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Zones> afficher() {
        List<Zones> Zones = new ArrayList<>();

        String req = "SELECT * from zones";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Zones.add(new Zones(rs.getInt("zone_id"), rs.getString("nom"), rs.getString("description"), rs.getInt("capacity"), rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Zones;
    }
    public void supprimer2(int zone_id)
    {
        String req = "DELETE FROM zones WHERE zone_id = " + zone_id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zone supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean zoneExists(String nom_zone) {

        String query = "SELECT COUNT(*) FROM zones WHERE nom = '" + nom_zone + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void supprimer4(int id_R){}
    public List<Zones> rechercher(String searchTerm) {
        // Effectuer la recherche dans votre source de données et renvoyer les résultats
        // par exemple, si vous avez une liste de zones en mémoire :
        List<Zones> zonesList = new ArrayList<>();

        for (Zones zone : zonesList) {
            if (zone.getNom().contains(searchTerm)) {
                // Ajouter la zone à la liste des résultats de recherche
            }
        }

        return zonesList;
    }

}
