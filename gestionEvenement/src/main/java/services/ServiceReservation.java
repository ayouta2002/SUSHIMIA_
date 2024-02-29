package services;

import entities.CrudReservation;
import entities.Evenement;
import entities.Reservation;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceReservation implements CrudReservation<Reservation> {

    public Connection conx;
    public Statement stm;


    public ServiceReservation() {
        conx = MyDB.getInstance().getConx();

    }

    @Override
    public void ajouter(Reservation r) {
        String req =
                "INSERT INTO reservation"
                        + "(description,date,id_evenement)"
                        + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, r.getDescription());
            ps.setDate(2, new java.sql.Date(r.getDate().getTime()));
            ps.setInt(3, r.getId_evenement());
            ps.executeUpdate();
            System.out.println("Reservation Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reservation r) {
        try {
            String req = "UPDATE reservation SET description=?, date=?, id_evenement=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(4, r.getId());
            pst.setString(1, r.getDescription());
            pst.setDate(2, new java.sql.Date(r.getDate().getTime()));
            pst.setInt(3, r.getId_evenement());
            pst.executeUpdate();
            System.out.println("Reservation Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reservation WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reservation suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    @Override
    public List<Reservation> Show() {
        List<Reservation> list = new ArrayList<>();

        try {
            String req = "SELECT * from reservation";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Reservation(rs.getInt("id"), rs.getString("description"),
                        rs.getDate("date"), rs.getInt("id_evenement")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Reservation> triDescription() {
        List<Reservation> list1 = new ArrayList<>();
        List<Reservation> list2 = Show();

        list1 = list2.stream().sorted((o1, o2) -> o1.getDescription().compareTo(o2.getDescription())).collect(Collectors.toList());
        return list1;
    }
}
