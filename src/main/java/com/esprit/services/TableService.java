package com.esprit.services;
import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.models.Zones;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class TableService implements IService<Tab> {

    private Connection connection;

    public TableService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Tab Tab) {
        String req = "INSERT into tables(table_id,capacite_t,nom_zone) values ('" + Tab.getTable_id() + "', '" + Tab.getCapacit_t()  + "', '" + Tab.getNom_zone() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("table ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Tab Tab) {
        String req = "UPDATE tables set capacite_t = '" + Tab.getCapacit_t() + "', nom_zone = '" + Tab.getNom_zone() + "' where table_id = " + Tab.getTable_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Table modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Tab Tab) {
        String req = "DELETE from tables where table_id = " + Tab.getTable_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("table supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tab> afficher() {
        List<Tab> Tab = new ArrayList<>();

        String req = "SELECT * from tables";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Tab.add(new Tab(rs.getInt("table_id"), rs.getInt("capacite_t"), rs.getString("nom_zone")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Tab;
    }
    public void supprimer3(int table_id) {
        String req = "DELETE FROM tables WHERE table_id = " + table_id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Table supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}



    public void supprimer2(int zone_id)
    {}
    public void supprimer4(int id_R){}
}