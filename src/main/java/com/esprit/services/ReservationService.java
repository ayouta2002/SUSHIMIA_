package com.esprit.services;
import com.esprit.models.*;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ReservationService implements IService<Reservation> {

    private Connection connection;

    public ReservationService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation Reservation) {
        String req = "INSERT into reservation(id_C, zone,table_id,dateR,status) values (?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, Reservation.getId_C());
            preparedStatement.setString(2, Reservation.getZone());
            preparedStatement.setInt(3, Reservation.getTable_id());
            preparedStatement.setDate(4, Reservation.getDateR());
            preparedStatement.setString(5, "En attente");
            preparedStatement.executeUpdate();
            System.out.println("Reservation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reservation Reservation) {
        String req = "UPDATE reservation set id_C = '" + Reservation.getId_C() + "', zone = '" + Reservation.getZone() + "', table_id = '" + Reservation.getTable_id() + "', dateR = '" + Reservation.getDateR() + "', status = '" + Reservation.getStatus() + "' where id_R = " + Reservation.getId_R() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Reservation modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Reservation Reservation) {
        String req = "DELETE from reservation where id_R = " + Reservation.getId_R() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Reservation supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> afficher() {
        List<Reservation> Reservation = new ArrayList<>();

        String req = "SELECT * from reservation";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation.add(new Reservation(rs.getInt("id_R"), rs.getInt("id_C"), rs.getString("zone"), rs.getInt("table_id"), rs.getDate("dateR"), rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Reservation;
    }
    public void supprimer2(int zone_id){}
    public void supprimer4(int id_R){}

    public List<Reservation> getReservationsByClientId(int clientId) {
        List<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : afficher()) {
            if (reservation.getId_C() == clientId) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

}