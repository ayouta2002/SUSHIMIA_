package services;

import entities.CrudEvenement;
import entities.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.MyDB;

public class ServiceEvenement implements CrudEvenement<Evenement> {

    public Connection conx;
    public Statement stm;


    public ServiceEvenement() {
        conx = MyDB.getInstance().getConx();

    }

    @Override
    public void ajouter(Evenement event) {
        String req =
                "INSERT INTO evenement"
                        + "(nom,nbParticipant,dateDebut,dateFin,image,description)"
                        + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, event.getNom());
            ps.setInt(2, event.getNbParticipant());
            ps.setDate(3, new java.sql.Date(event.getDateDebut().getTime()));
            ps.setDate(4, new java.sql.Date(event.getDateFin().getTime()));
            ps.setString(5, event.getImage());
            ps.setString(6, event.getDescription());
            ps.executeUpdate();
            System.out.println("Evenement Ajoutée !!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Evenement event) {
        try {
            String req = "UPDATE evenement SET nom=?, nbParticipant=?, dateDebut=?, dateFin=?, image=?, description=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(7, event.getId());
            pst.setString(1, event.getNom());
            pst.setInt(2, event.getNbParticipant());
            pst.setDate(3, new java.sql.Date(event.getDateDebut().getTime()));
            pst.setDate(4, new java.sql.Date(event.getDateFin().getTime()));
            pst.setString(5, event.getImage());
            pst.setString(6, event.getDescription());
            pst.executeUpdate();
            System.out.println("Evenement Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM evenement WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Evenement suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    @Override
    public List<Evenement> Show() {
        List<Evenement> list = new ArrayList<>();

        try {
            String req = "SELECT * from evenement";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Evenement(rs.getInt("id"), rs.getString("nom"),
                        rs.getInt("nbParticipant"), rs.getDate("dateDebut"),
                        rs.getDate("dateFin"), rs.getString("image"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Evenement> triNom() {
        List<Evenement> list1 = new ArrayList<>();
        List<Evenement> list2 = Show();

        list1 = list2.stream().sorted((o1, o2) -> o1.getNom().compareTo(o2.getNom())).collect(Collectors.toList());
        return list1;
    }

    public List<Evenement> triNbParticipant() {
        List<Evenement> list1 = new ArrayList<>();
        List<Evenement> list2 = Show();

        list1 = list2.stream().sorted((o1, o2) -> String.valueOf(o1.getNbParticipant()).compareTo(String.valueOf(o2.getNbParticipant()))).collect(Collectors.toList());
        return list1;
    }

    public List<Evenement> triDescription() {
        List<Evenement> list1 = new ArrayList<>();
        List<Evenement> list2 = Show();

        list1 = list2.stream().sorted((o1, o2) -> o1.getDescription().compareTo(o2.getDescription())).collect(Collectors.toList());
        return list1;
    }

    public Evenement getEventById(int id){
        String sql = "SELECT * FROM evenement WHERE id = ?";

        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Evenement(rs.getInt("id"), rs.getString("nom"),
                        rs.getInt("nbParticipant"), rs.getDate("dateDebut"),
                        rs.getDate("dateFin"), rs.getString("image"), rs.getString("description"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
